package com.adobe.test.webserver.http.handler.http11;

import com.adobe.test.webserver.http.handler.HttpError404Handler;
import com.adobe.test.webserver.http.spec.ClientHeaders;
import lombok.Builder;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;

@Builder
public class Http11Error404Handler implements HttpError404Handler {

    @Override
    public void handle(ClientHeaders clientHeaders, BufferedReader requestStream,
                       PrintWriter headerResponseStream, BufferedOutputStream payloadResponseStream) {

    }
}
