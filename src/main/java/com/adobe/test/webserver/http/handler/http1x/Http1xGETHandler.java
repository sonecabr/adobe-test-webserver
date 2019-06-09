package com.adobe.test.webserver.http.handler.http1x;

import com.adobe.test.webserver.http.handler.HttpGETHandler;
import com.adobe.test.webserver.http.spec.ClientHeader;
import com.adobe.test.webserver.http.spec.ContentType;
import com.adobe.test.webserver.http.spec.HttpStatusCode;
import com.adobe.test.webserver.io.WebContentFile;
import com.adobe.test.webserver.io.exception.FileNotFoundUnreadableException;
import com.adobe.test.webserver.server.WebServerConfigs;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Builder
public class Http1xGETHandler extends BaseHttp1xHandler implements HttpGETHandler  {

    final int HTTP_CODE = HttpStatusCode.OK_200.getCode();

    @Override
    public void handle(ClientHeader clientHeaders,
                       BufferedReader requestStream,
                       PrintWriter headerResponseStream,
                       BufferedOutputStream payloadResponseStream) {

        log.info(String.format("Received a GET request %s", clientHeaders.getUrl()));
        String uri = clientHeaders.getUrl();

        if(uri.equals("/")) { //hadle root request
            uri += WebServerConfigs.DEFAULT_FILES.get(0); //FIXME improve to validate file stream
        }

        try {
            if(ContentType.byExtension(extractExtension(uri)).equals(ContentType.DEFAULT)) {
                uri += ContentType.HTML.getExtension();
            }
            WebContentFile content = fileReader.readContent(uri, payloadResponseStream);
            this.printHeaders(
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
    public void printHeaders(int httpCode, PrintWriter headerResponseStream, ContentType contentType, int lenght, String clientVersion) {
        headerResponseStream.println(String.format("%s %d OK", clientVersion, httpCode));
        super.printHeaders(httpCode, headerResponseStream, contentType, lenght, clientVersion);
    }


}
