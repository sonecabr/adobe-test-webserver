package com.adobe.test.webserver.http.handler;

import com.adobe.test.webserver.http.spec.ClientHeaders;

import java.io.*;

public interface BaseHttpHandler {
    void handle(ClientHeaders clientHeaders, BufferedReader requestStream, PrintWriter headerResponseStream, BufferedOutputStream payloadResponseStream);
}
