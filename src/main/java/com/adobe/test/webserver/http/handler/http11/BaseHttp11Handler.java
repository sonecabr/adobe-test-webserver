package com.adobe.test.webserver.http.handler.http11;

import com.adobe.test.webserver.http.exception.HttpRequestHandlingException;
import com.adobe.test.webserver.http.spec.ClientVersion;
import com.adobe.test.webserver.http.spec.ContentType;
import com.adobe.test.webserver.io.FileReader;
import com.adobe.test.webserver.io.NonBlockingFileReader;
import com.adobe.test.webserver.server.WebServerConfigs;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;

public class BaseHttp11Handler {

    final FileReader fileReader = NonBlockingFileReader.getInstance();
    final String CLIENT_VERSION = ClientVersion.HTTP11.getClientVersionStr();

    public String extractExtension(String uri) {
        if(uri.contains(".")){
            String[] parts = uri.split("\\.");
            return String.format(".%s", parts[parts.length -1]);
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

    public void printHeaders(int httpCode, PrintWriter headerResponseStream, ContentType contentType, int lenght) {
        headerResponseStream.println(String.format("%s %s OK", CLIENT_VERSION, httpCode));
        headerResponseStream.println(String.format("Server: %s", WebServerConfigs.SERVERNAME));
        headerResponseStream.println("Date: " + Instant.now());
        headerResponseStream.println("Content-type: " + contentType.getDescription());
        headerResponseStream.println("Content-length: " + lenght);
        headerResponseStream.println();
    }
}
