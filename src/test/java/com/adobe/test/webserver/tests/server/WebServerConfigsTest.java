package com.adobe.test.webserver.tests.server;

import com.adobe.test.webserver.server.WebServerConfigs;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class WebServerConfigsTest {

    @Test
    public void shouldPopulateConfigsFromDefaultFIle(){
        WebServerConfigs webServerConfigs = new WebServerConfigs();
        webServerConfigs.init();
        Assert.assertEquals(WebServerConfigs.DEFAULT_FILES, Arrays.asList("index.html", "default.html"));
        Assert.assertEquals(WebServerConfigs.PORT_TO_LISTEN, new Integer(8080));
        Assert.assertEquals(WebServerConfigs.WEB_ROOT, "/var/www");
        Assert.assertEquals(WebServerConfigs.SERVERNAME, "Adobe Test WebServer 1.0");
        Assert.assertEquals(WebServerConfigs.BACKEND_ERROR_FILE, "500.html");
        Assert.assertEquals(WebServerConfigs.CHARSET, "UTF-8");
        Assert.assertEquals(WebServerConfigs.NOT_FOUND_FILE, "404.html");
        Assert.assertEquals(WebServerConfigs.KEEP_ALIVE_ENABLED, true);
        Assert.assertEquals(WebServerConfigs.KEEP_ALIVE_TIMEOUT, new Integer(5));
        Assert.assertEquals(WebServerConfigs.KEEP_ALIVE_MAX, new Integer(1000));
        Assert.assertEquals(WebServerConfigs.THREAD_LIMIT, new Integer(4));
        Assert.assertEquals(WebServerConfigs.CLIENT_TIMEOUT, new Integer(30000));
    }
}
