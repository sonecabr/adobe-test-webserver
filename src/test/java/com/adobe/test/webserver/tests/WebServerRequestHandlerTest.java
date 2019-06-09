package com.adobe.test.webserver.tests;

import com.adobe.test.webserver.WebServerRequestHandler;
import com.adobe.test.webserver.http.spec.ClientHeader;
import com.adobe.test.webserver.http.spec.ClientVersion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.net.Socket;

@RunWith(JUnit4.class)
public class WebServerRequestHandlerTest {

    @Test
    public void shouldDefineKeepAliveAsTrue(){
        WebServerRequestHandler webServerRequestHandler = WebServerRequestHandler
                .builder()
                .clientSocket(new Socket())
                .build();

        webServerRequestHandler
                .defineKeepAliveBehavior(
                        ClientHeader
                                .builder()
                                .protocolVersion(ClientVersion.HTTP11.getClientVersionStr())
                                .connection("keep-alive")
                                .build()
                );

        try {
            Assert.assertTrue(webServerRequestHandler.getClientSocket().getKeepAlive());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDefineKeepAliveAsFalseForOldClient(){
        WebServerRequestHandler webServerRequestHandler = WebServerRequestHandler
                .builder()
                .clientSocket(new Socket())
                .build();

        webServerRequestHandler
                .defineKeepAliveBehavior(
                        ClientHeader
                                .builder()
                                .protocolVersion(ClientVersion.HTTP10.getClientVersionStr())
                                .connection("keep-alive")
                                .build()
                );

        try {
            Assert.assertFalse(webServerRequestHandler.getClientSocket().getKeepAlive());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDefineKeepAliveAsFalseForClientRejection(){
        WebServerRequestHandler webServerRequestHandler = WebServerRequestHandler
                .builder()
                .clientSocket(new Socket())
                .build();

        webServerRequestHandler
                .defineKeepAliveBehavior(
                        ClientHeader
                                .builder()
                                .protocolVersion(ClientVersion.HTTP11.getClientVersionStr())
                                .build()
                );

        try {
            Assert.assertFalse(webServerRequestHandler.getClientSocket().getKeepAlive());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
