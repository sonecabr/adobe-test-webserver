package com.adobe.test.webserver.http.handler;

import com.adobe.test.webserver.http.spec.*;
import com.adobe.test.webserver.server.WebServerConfigs;
import lombok.extern.slf4j.Slf4j;
import java.io.PrintWriter;
import java.time.Instant;

/**
 * Basic implementation for Http dispatcher
 * <ul>
 *     <li>manage requests for No Supported method</li>
 *     <li>threat all headers from client side</li>
 *     <li>write headers and body (html)</li>
 *     <li>configure response headers and code</li>
 * </ul>
 *
 *
 * @author Andre Rocha
 * @since 2019-06-09
 */
@Slf4j
public class BaseXXHttpHandler {

    /**
     * identify and return file extension based on request headers
     * @param uri
     * @return String - (.html, .htm)
     */
    public String extractExtension(String uri) {
        if(uri.contains(".")){
            String[] parts = uri.split("\\.");
            return String.format(".%s", parts[parts.length -1]);
        } else {
            return "";
        }
    }

    /**
     * Write headers to client response
     * @param httpCode
     * @param headerResponseStream
     * @param contentType
     * @param lenght
     * @param clientVersion
     */
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
