package com.adobe.test.webserver;

import com.adobe.test.webserver.server.BasicWebServerRequestHandler;
import com.adobe.test.webserver.http.spec.ClientHeaders;
import com.adobe.test.webserver.http.spec.ClientRequestPattern;
import com.adobe.test.webserver.http.spec.HttpMethod;
import com.adobe.test.webserver.http.exception.HttpRequestHandlingException;
import com.adobe.test.webserver.http.handler.http11.Http11GETHandler;
import com.adobe.test.webserver.http.spec.ClientVersion;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;


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

            //pre flight
            String firstLine = requestStream.readLine();
            try {
                ClientHeaders clientHeaders
                        = extractClientHeaders(firstLine);

                if(clientHeaders.getMethod().equals(HttpMethod.GET)) {
                    if(clientHeaders.getProtocolVersion().equals(ClientVersion.HTTP11.getClientVersionStr())){
                        Http11GETHandler.builder().build().handle(clientHeaders, requestStream, responseHeaderStream, responsePayloadStream);
                    }
                }
            } catch (HttpRequestHandlingException e) {
                log.error("Catched error", e);
                clientSocket.close();
            }


        } catch (Exception e) {
            log.error("Error handling request", e);
        }
    }

    public ClientHeaders extractClientHeaders(String line) throws HttpRequestHandlingException {

        Matcher matcher = ClientRequestPattern.firstLinePattern.matcher(line);
        if(matcher.matches()){
            return  ClientHeaders
                    .builder()
                    .method(HttpMethod.valueOf(matcher.group(1).toUpperCase()))
                    .url(String.format("/%s", matcher.group(2).toLowerCase()))
                    .protocolVersion(String.format("%s/%s", matcher.group(3), matcher.group(4)))
                    .build();
        } else {
            matcher = ClientRequestPattern.firstLineRootPattern.matcher(line);
            if(!matcher.matches()){
                throw new HttpRequestHandlingException("Header is not valid");
            }
            return  ClientHeaders
                    .builder()
                    .method(HttpMethod.valueOf(matcher.group(1).toUpperCase()))
                    .url(String.format("/%s", matcher.group(2).toLowerCase()))
                    .protocolVersion(String.format("%s/%s", matcher.group(3), matcher.group(4)))
                    .build();
        }

    }
}
