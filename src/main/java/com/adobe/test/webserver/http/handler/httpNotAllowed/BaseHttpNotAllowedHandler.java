package com.adobe.test.webserver.http.handler.httpNotAllowed;

import com.adobe.test.webserver.http.exception.HttpRequestHandlingException;
import com.adobe.test.webserver.http.handler.BaseXXHttpHandler;
import com.adobe.test.webserver.http.spec.ClientHeader;
import com.adobe.test.webserver.http.spec.ContentType;
import com.adobe.test.webserver.http.spec.HttpStatusCode;
import com.adobe.test.webserver.io.WebContentFile;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Http client not supported (fallback)
 * <ul>
 *     <li>manage requests for No Supported method</li>
 *     <li>threat all headers from client side</li>
 *     <li>write headers and body (html)</li>
 *     <li>configure response headers and code</li>
 * </ul>
 *
 *
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-09
 */
@Slf4j
public class BaseHttpNotAllowedHandler extends BaseXXHttpHandler {

    final int HTTP_CODE = HttpStatusCode.METHOD_NOT_ALLOWED.getCode();

    public String extractExtension(String uri) {
        if(uri.contains(".")){
            String[] parts = uri.split("\\.");
            return String.format(".%s", parts[parts.length -1]);
        } else {
            return "";
        }
    }

    public void dispatch(ClientHeader clientHeaders,
                         BufferedReader requestStream,
                         PrintWriter headerResponseStream,
                         BufferedOutputStream payloadResponseStream) {

        log.info(String.format("Received a Http Not Allowed request %s", clientHeaders.getUrl()));
        String uri = clientHeaders.getUrl();

        if (ContentType.byExtension(extractExtension(uri)).equals(ContentType.DEFAULT)) {
            uri += ContentType.HTML.getExtension();
        }
        WebContentFile content = WebContentFile
                .builder()
                .lenght(0)
                .content(payloadResponseStream)
                .build();
        printHeaders(
                HTTP_CODE,
                headerResponseStream,
                ContentType.byExtension(extractExtension(uri)),
                content.getLenght(),
                clientHeaders.getProtocolVersion());

        try {
            requestStream.close();
            headerResponseStream.flush();
            payloadResponseStream.flush();
            headerResponseStream.close();
            payloadResponseStream.close();
        } catch (IOException e) {
            log.error("Error closing client request", e);
        }
    }


    public void printPayload(BufferedOutputStream payloadResponseStream, byte[] payload, int lenght) throws HttpRequestHandlingException {
        try {
            payloadResponseStream.write(payload, 0, lenght);
        } catch (IOException e) {
            throw new HttpRequestHandlingException("Not possible to write the payload", e);
        }

    }

    public void printHeaders(int httpCode, PrintWriter headerResponseStream, ContentType contentType, int lenght, String clientVersion) {
        headerResponseStream.println(String.format("%s %d Method Not Allowed", clientVersion, httpCode));
        super.printHeaders(httpCode, headerResponseStream, contentType, lenght, clientVersion);
    }
}
