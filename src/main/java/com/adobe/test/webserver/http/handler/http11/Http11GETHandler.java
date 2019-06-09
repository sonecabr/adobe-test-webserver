package com.adobe.test.webserver.http.handler.http11;

import com.adobe.test.webserver.http.exception.HttpRequestHandlingException;
import com.adobe.test.webserver.http.handler.HttpGETHandler;
import com.adobe.test.webserver.http.spec.ClientHeaders;
import com.adobe.test.webserver.http.spec.ClientVersion;
import com.adobe.test.webserver.http.spec.ContentType;
import com.adobe.test.webserver.http.spec.HttpStatusCode;
import com.adobe.test.webserver.io.BlockingFileReader;
import com.adobe.test.webserver.io.FileReader;
import com.adobe.test.webserver.io.NonBlockingFileReader;
import com.adobe.test.webserver.io.WebContentFile;
import com.adobe.test.webserver.io.exception.FileNotFoundUnreadableException;
import com.adobe.test.webserver.server.WebServerConfigs;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

@Slf4j
@Builder
public class Http11GETHandler implements HttpGETHandler {

    final FileReader fileReader = NonBlockingFileReader.getInstance();
    final int HTTP_CODE = HttpStatusCode.SUCCESS_200.getCode();
    final String CLIENT_VERSION = ClientVersion.HTTP11.getClientVersionStr();

    @Override
    public void handle(ClientHeaders clientHeaders,
                       BufferedReader requestStream,
                       PrintWriter headerResponseStream,
                       BufferedOutputStream payloadResponseStream) {

        log.info(String.format("Received a GET request %s", clientHeaders.getUrl()));
        String uri = clientHeaders.getUrl();

        if(uri.equals("/")) { //hadle root request
            uri += WebServerConfigs.DEFAULT_FILES[0];
        }



        try {
            if(ContentType.byExtension(extractExtension(uri)).equals(ContentType.DEFAULT)) {
                uri += ContentType.HTML.getExtension();
            }
            WebContentFile content = fileReader.readContent(uri, payloadResponseStream);
            printHeaders(
                    headerResponseStream,
                    ContentType.byExtension(extractExtension(uri)),
                    content.getLenght());

            //printPayload(payloadResponseStream, content.getContent(), content.getLenght());

        } catch (FileNotFoundUnreadableException e) {
            Http11Error404Handler
                    .builder()
                    .build()
                    .handle(clientHeaders, requestStream, headerResponseStream, payloadResponseStream);
        } finally {
            try {
                headerResponseStream.flush();
                payloadResponseStream.flush();
                headerResponseStream.close();
                payloadResponseStream.close();
            } catch (IOException e) {
                log.error("Error closing client request", e);
            }
        }
    }

    public String extractExtension(String uri) {
        if(uri.contains("\\.")){
            String[] parts = uri.split("\\.");
            return parts[parts.length];
        } else {
            return "";
        }



    }

    public void printPayload(BufferedOutputStream payloadResponseStream, byte[] payload, int lenght) throws HttpRequestHandlingException {
        try {
            payloadResponseStream.write(payload, 0, lenght);
        } catch (IOException e) {
            throw new HttpRequestHandlingException("Not possible to write the payload", e);
        }

    }

    public void printHeaders(PrintWriter headerResponseStream, ContentType contentType, int lenght) {
        headerResponseStream.println(String.format("%s %s OK", CLIENT_VERSION, HTTP_CODE));
        headerResponseStream.println(String.format("Server: %s", WebServerConfigs.SERVERNAME));
        headerResponseStream.println("Date: " + Instant.now());
        headerResponseStream.println("Content-type: " + contentType.getDescription());
        headerResponseStream.println("Content-length: " + lenght);
        headerResponseStream.println();
    }
}
