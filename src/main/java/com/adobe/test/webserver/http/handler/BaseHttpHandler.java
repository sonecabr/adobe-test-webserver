package com.adobe.test.webserver.http.handler;

import com.adobe.test.webserver.http.spec.ClientHeader;

import java.io.*;

public interface BaseHttpHandler {
    void dispatch(ClientHeader clientHeaders, BufferedReader requestStream, PrintWriter headerResponseStream, BufferedOutputStream payloadResponseStream);
}
