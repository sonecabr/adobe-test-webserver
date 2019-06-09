package com.adobe.test.webserver.http.handler;

import com.adobe.test.webserver.http.exception.HttpRequestHandlingException;
import com.adobe.test.webserver.http.spec.*;
import com.adobe.test.webserver.server.WebServerConfigs;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

@Slf4j
public class BaseXXHttpHandler {

    public String extractExtension(String uri) {
        if(uri.contains(".")){
            String[] parts = uri.split("\\.");
            return String.format(".%s", parts[parts.length -1]);
        } else {
            return "";
        }
    }

    public void printHeaders(int httpCode, PrintWriter headerResponseStream, ContentType contentType, int lenght, String clientVersion) {
        headerResponseStream.println(String.format("Server: %s", WebServerConfigs.SERVERNAME));
        headerResponseStream.println("Date: " + Instant.now());
        headerResponseStream.println("Content-type: " + contentType.getDescription());
        headerResponseStream.println("Content-length: " + lenght);
        if(clientVersion.equals(ClientVersion.HTTP11.getClientVersionStr())) {
            headerResponseStream.println(
                    String.format("Keep-Alive: timeout=%d, max=%d",
                            WebServerConfigs.KEEP_ALIVE_TIMEOUT, WebServerConfigs.KEEP_ALIVE_MAX));
        }
        headerResponseStream.println();
    }

}
