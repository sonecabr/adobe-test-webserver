package com.adobe.test.webserver;

import com.adobe.test.webserver.http.handler.http1x.Http1xOPTIONSHandler;
import com.adobe.test.webserver.http.handler.http1x.Http1xPOSTHandler;
import com.adobe.test.webserver.http.handler.httpNotAllowed.HttpNotAllowedGETHandler;
import com.adobe.test.webserver.http.handler.httpNotAllowed.HttpNotAllowedOPTIONSHandler;
import com.adobe.test.webserver.http.spec.*;
import com.adobe.test.webserver.server.BasicWebServerRequestHandler;
import com.adobe.test.webserver.http.exception.HttpRequestHandlingException;
import com.adobe.test.webserver.http.handler.http1x.Http1xGETHandler;
import com.adobe.test.webserver.server.WebServerConfigs;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WebServerRequestHandler implements BasicWebServerRequestHandler {


    private Socket clientSocket;


    @Override
    public void run() {
        handleRequest();
    }



    public void handleRequest() {
        BufferedReader requestStream = null;
        PrintWriter responseHeaderStream;
        BufferedOutputStream responsePayloadStream = null;
        String request = null;

        try {
            //prepare streams
            requestStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            responseHeaderStream = new PrintWriter(clientSocket.getOutputStream());
            responsePayloadStream = new BufferedOutputStream(clientSocket.getOutputStream());

            try {
                ClientHeader clientHeaders
                        = extractClientHeaders(requestStream);
                defineKeepAliveBehavior(clientHeaders);
                if(clientHeaders.getMethod().equals(HttpMethod.GET)) {
                    if(clientHeaders.getProtocolVersion().equals(ClientVersion.HTTP10 .getClientVersionStr())
                            || clientHeaders.getProtocolVersion().equals(ClientVersion.HTTP11.getClientVersionStr())){
                        Http1xGETHandler.builder().build().handle(clientHeaders, requestStream, responseHeaderStream, responsePayloadStream);
                    } else {
                        //http2.x and 3.0 is not yet supported
                        HttpNotAllowedGETHandler.builder().build().handle(clientHeaders, requestStream, responseHeaderStream, responsePayloadStream);
                    }
                } else if(clientHeaders.getMethod().equals(HttpMethod.OPTIONS)) {
                    if (clientHeaders.getProtocolVersion().equals(ClientVersion.HTTP10.getClientVersionStr())
                            || clientHeaders.getProtocolVersion().equals(ClientVersion.HTTP11.getClientVersionStr())) {
                        Http1xOPTIONSHandler.builder().build().handle(clientHeaders, requestStream, responseHeaderStream, responsePayloadStream);
                    } else {
                        HttpNotAllowedOPTIONSHandler.builder().build().handle(clientHeaders, requestStream, responseHeaderStream, responsePayloadStream);
                    }
                } else if(clientHeaders.getMethod().equals(HttpMethod.POST)) {
                    if(clientHeaders.getProtocolVersion().equals(ClientVersion.HTTP10.getClientVersionStr())
                            || clientHeaders.getProtocolVersion().equals(ClientVersion.HTTP11.getClientVersionStr())) {
                        Http1xPOSTHandler.builder().build().handle(clientHeaders, requestStream, responseHeaderStream, responsePayloadStream);
                    }
                } else {
                    HttpNotAllowedGETHandler.builder().build().handle(clientHeaders, requestStream, responseHeaderStream, responsePayloadStream);
                }
            } catch (HttpRequestHandlingException e) {
                log.error("Catched error", e);
                clientSocket.close();
            }

        } catch (Exception e) {
            log.error("Error handling request", e);
        }
    }

    /**
     * Define if clients should use keep alive based on Client Version informed in request
     * @param clientHeaders
     */
    public void defineKeepAliveBehavior(ClientHeader clientHeaders) {
        if(WebServerConfigs.KEEP_ALIVE_ENABLED
                && clientHeaders.getProtocolVersion().equals(ClientVersion.HTTP11.getClientVersionStr())
                && clientHeaders.getConnection() != null
                && clientHeaders.getConnection().equals("keep-alive")){
            log.info("Enabling keep alive to client");
            try {
                clientSocket.setKeepAlive(true);

            } catch (SocketException e) {
                log.error("Keep Alive not available for this client");
            }
        }
    }


    public Map<String, String> extractRequestHeaders(BufferedReader requestStream) {
        Map<String, String> headers = new HashMap<>();
        try {
            String line = "";
            while ((line = requestStream.readLine()) != null &&
                    line.length() != 0) {

                Pattern headerPattern = Pattern.compile("^(\\S+): (\\S+)*$");
                try {
                    Matcher matcher = headerPattern.matcher(line);
                    if(!line.startsWith("Cookie") && //
                            matcher.matches() && matcher.groupCount() == 2) {
                        headers.put(matcher.group(1), matcher.group(2));
                    }
                } catch (Exception e){
                    log.error("Error handling header", e);
                }

            }
        } catch (IOException e) {
            log.error("Error reading post payload", e);
        }
        return headers;
    }

    public ClientFormData extractFormData(Map<String, String> headers, BufferedReader requestStream) {
        String formData = "";
        try {
            if(headers.containsKey("Content-Length")
                    && headers.get("Content-Length").length() > 0) {
                Integer contentLenght = Integer.parseInt(headers.get("Content-Length"));
                char[] bytes = new char[contentLenght];
                requestStream.read(bytes, 0, contentLenght);
                formData = new String(bytes);
            }
        } catch (IOException e){
            log.error("Error extracting request headers", e);
        }
        if(headers.containsKey(HttpRequestHeader.CONTENT_TYPE.getDescription())) {
            if(headers.get(HttpRequestHeader.CONTENT_TYPE.getDescription()).equals(ContentType.URL_ENCODED.getDescription())){
                return ClientFormDataUrlEncoded
                        .builder()
                        .raw(formData)
                        .data(
                                Arrays.asList(
                                        formData
                                                .split("&"))
                                                .stream()
                                                .map(item -> item.split("="))
                                                .collect(Collectors.toMap(item -> item[0], item -> item[1])))
                        .build();
            }
        }
        return ClientFormDataUrlEncoded
                .builder()
                .build();

    }

    public ClientHeader extractClientHeaders(BufferedReader requestStream) throws HttpRequestHandlingException {
        try {
            String firstLine = requestStream.readLine();

            Map<String, String> requestHeaders = extractRequestHeaders(requestStream);

            ClientFormData formData = extractFormData(requestHeaders, requestStream);

            Matcher matcher = ClientRequestPattern.firstLinePattern.matcher(firstLine);
            if(matcher.matches()){
                return  ClientHeader
                        .builder()
                        .method(HttpMethod.valueOf(matcher.group(1).toUpperCase()))
                        .url(String.format("/%s", matcher.group(2).toLowerCase()))
                        .protocolVersion(String.format("%s/%s", matcher.group(3), matcher.group(4)))
                        .accept(requestHeaders.get(HttpRequestHeader.ACCEPT.getDescription()))
                        .host(requestHeaders.get(HttpRequestHeader.HOST.getDescription()))
                        .contentType(requestHeaders.get(HttpRequestHeader.CONTENT_TYPE.getDescription()))
                        .contentLength(requestHeaders.get(HttpRequestHeader.CONTENT_LENGTH.getDescription()))
                        .cacheControl(requestHeaders.get(HttpRequestHeader.CACHE_CONTROL.getDescription()))
                        .connection(requestHeaders.get(HttpRequestHeader.CONNECTION.getDescription()))
                        .formData(formData)
                        .build();
            } else {
                matcher = ClientRequestPattern.firstLineRootPattern.matcher(firstLine);
                if(!matcher.matches()){
                    matcher = ClientRequestPattern.firstLineRootWithBarPattern.matcher(firstLine);
                    if(!matcher.matches()){
                        throw new HttpRequestHandlingException("Header is not valid");
                    } else {
                        return  ClientHeader
                                .builder()
                                .method(HttpMethod.valueOf(matcher.group(1).toUpperCase()))
                                .url(String.format("/%s", "/"))
                                .protocolVersion(String.format("%s/%s", matcher.group(2), matcher.group(3)))
                                .accept(requestHeaders.get(HttpRequestHeader.ACCEPT.getDescription()))
                                .host(requestHeaders.get(HttpRequestHeader.HOST.getDescription()))
                                .contentType(requestHeaders.get(HttpRequestHeader.CONTENT_TYPE.getDescription()))
                                .contentLength(requestHeaders.get(HttpRequestHeader.CONTENT_LENGTH.getDescription()))
                                .cacheControl(requestHeaders.get(HttpRequestHeader.CACHE_CONTROL.getDescription()))
                                .connection(requestHeaders.get(HttpRequestHeader.CONNECTION.getDescription()))
                                .formData(formData)
                                .build();
                    }
                } else {
                    return  ClientHeader
                            .builder()
                            .method(HttpMethod.valueOf(matcher.group(1).toUpperCase()))
                            .url(String.format("/%s", matcher.group(2).toLowerCase()))
                            .protocolVersion(String.format("%s/%s", matcher.group(3), matcher.group(4)))
                            .accept(requestHeaders.get(HttpRequestHeader.ACCEPT.getDescription()))
                            .host(requestHeaders.get(HttpRequestHeader.HOST.getDescription()))
                            .contentType(requestHeaders.get(HttpRequestHeader.CONTENT_TYPE.getDescription()))
                            .contentLength(requestHeaders.get(HttpRequestHeader.CONTENT_LENGTH.getDescription()))
                            .cacheControl(requestHeaders.get(HttpRequestHeader.CACHE_CONTROL.getDescription()))
                            .connection(requestHeaders.get(HttpRequestHeader.CONNECTION.getDescription()))
                            .formData(formData)
                            .build();
                }
            }
        } catch (IOException e) {
            throw new HttpRequestHandlingException("Error handling request headers", e);
        }

    }
}
