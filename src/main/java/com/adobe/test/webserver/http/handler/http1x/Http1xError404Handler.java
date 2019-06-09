package com.adobe.test.webserver.http.handler.http1x;

import com.adobe.test.webserver.http.handler.HttpError404Handler;
import com.adobe.test.webserver.http.spec.ClientHeader;
import com.adobe.test.webserver.http.spec.ContentType;
import com.adobe.test.webserver.http.spec.HttpStatusCode;
import com.adobe.test.webserver.io.WebContentFile;
import com.adobe.test.webserver.io.exception.FileNotFoundUnreadableException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Http1.x 404 page not found dispatcher api
 *
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-09
 */
@Builder
@Slf4j
public class Http1xError404Handler extends BaseHttp1xHandler implements HttpError404Handler {

    final int HTTP_CODE = HttpStatusCode.NOT_FOUND_404.getCode();

    @Override
    public void dispatch(ClientHeader clientHeaders, BufferedReader requestStream,
                         PrintWriter headerResponseStream, BufferedOutputStream payloadResponseStream) {

        log.info(String.format("Page not found for request %s", clientHeaders.getUrl()));

        String uri = "/404.html";

        try {

            WebContentFile content = fileReader.readContent(uri, payloadResponseStream);
            printHeaders(
                    HTTP_CODE,
                    headerResponseStream,
                    ContentType.byExtension(extractExtension(uri)),
                    content.getLenght(),
                    clientHeaders.getProtocolVersion());

        } catch (FileNotFoundUnreadableException e) {
            Http1xError404Handler
                    .builder()
                    .build()
                    .dispatch(clientHeaders, requestStream, headerResponseStream, payloadResponseStream);
        } finally {
            try {
                headerResponseStream.flush();
                payloadResponseStream.flush();
                headerResponseStream.close();
                payloadResponseStream.close();
                requestStream.close();
            } catch (IOException e) {
                log.error("Error closing client request", e);
            }
        }
    }


    @Override
    public void printHeaders(int httpCode, PrintWriter headerResponseStream, ContentType contentType, int lenght, String clientVersion) {
        headerResponseStream.println(String.format("%s %s Not Found", clientVersion, httpCode));
        super.printHeaders(httpCode, headerResponseStream, contentType, lenght, clientVersion);
    }
}
