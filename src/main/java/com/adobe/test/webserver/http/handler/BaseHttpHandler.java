package com.adobe.test.webserver.http.handler;

import com.adobe.test.webserver.http.spec.ClientHeader;

import java.io.*;

/**
 * BaseHttp dispatcher api
 *
 *
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-09
 */
public interface BaseHttpHandler {
    /**
     * send request to proper handler
     * @param clientHeaders
     * @param requestStream
     * @param headerResponseStream
     * @param payloadResponseStream
     */
    void dispatch(ClientHeader clientHeaders, BufferedReader requestStream, PrintWriter headerResponseStream, BufferedOutputStream payloadResponseStream);
}
