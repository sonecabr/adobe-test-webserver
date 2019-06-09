package com.adobe.test.webserver.tests;

import com.adobe.test.webserver.WebServerApplication;
import com.adobe.test.webserver.tests.base.BaseWebServerTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class WebServerApplicationTest extends BaseWebServerTest {

    @Test
    public void shouldValidateTestEnv(){
        Assert.assertTrue(WebServerApplication.isRunning);
    }
}
