package com.adobe.test.webserver.http.handler.http1x;

import com.adobe.test.webserver.http.handler.HttpPOSTHandler;
import com.adobe.test.webserver.http.spec.ClientFormDataUrlEncoded;
import com.adobe.test.webserver.http.spec.ClientHeader;
import com.adobe.test.webserver.http.spec.ContentType;
import com.adobe.test.webserver.http.spec.HttpStatusCode;
import com.adobe.test.webserver.io.WebContentFile;
import com.adobe.test.webserver.io.exception.FileNotFoundUnreadableException;
import com.adobe.test.webserver.server.WebServerConfigs;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
@Builder
public class Http1xPOSTHandler extends BaseHttp1xHandler implements HttpPOSTHandler {

    final int HTTP_CODE = HttpStatusCode.CREATED_201.getCode();

    @Override
    public void handle(ClientHeader clientHeaders, BufferedReader requestStream,
                       PrintWriter headerResponseStream, BufferedOutputStream payloadResponseStream) {

        log.info(String.format("Received a GET request %s", clientHeaders.getUrl()));
        String uri = clientHeaders.getUrl();

        if(uri.equals("/")) { //hadle root request
            uri += WebServerConfigs.DEFAULT_FILES.get(0); //FIXME improve to validate file stream
        }

        try {
            if(ContentType.byExtension(extractExtension(uri)).equals(ContentType.DEFAULT)) {
                uri += ContentType.HTML.getExtension();
            }
            //payloadResponseStream.write(((ClientFormDataUrlEncoded) clientHeaders.getFormData()).getRaw().getBytes());
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
                    .handle(clientHeaders, requestStream, headerResponseStream, payloadResponseStream);
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
    public void printHeaders(int httpCode, PrintWriter headerResponseStream, ContentType contentType, int length, String clientVersion) {
        headerResponseStream.println(String.format("%s %d CREATED", clientVersion, httpCode));
        super.printHeaders(httpCode, headerResponseStream, contentType, length, clientVersion);
    }
}
