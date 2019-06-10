package com.adobe.test.webserver.tests.http;


import com.adobe.test.webserver.http.handler.BaseXXHttpHandler;
import com.adobe.test.webserver.http.spec.ClientVersion;
import com.adobe.test.webserver.http.spec.ContentType;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class BaseXXHttpHandlerTest {

    private String testFile = "out/testfile.test";

    @Before
    public void setUp(){
        try {
            if(!Files.isDirectory(Paths.get("out"))){
                Files.createDirectories(Paths.get("out"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetHtmlExtension(){
        BaseXXHttpHandler dispatcher = new BaseXXHttpHandler();
        String ext = dispatcher.extractExtension("/index.html");
        Assert.assertEquals(".html", ext);
    }

    @Test
    public void shouldGetBlankExtension(){
        BaseXXHttpHandler dispatcher = new BaseXXHttpHandler();
        String ext = dispatcher.extractExtension("/");
        Assert.assertEquals("", ext);
    }

    @Test
    public void shouldGetHtmExtension(){
        BaseXXHttpHandler dispatcher = new BaseXXHttpHandler();
        String ext = dispatcher.extractExtension("/index.htm");
        Assert.assertEquals(".htm", ext);
    }

    @Test
    public void shouldGetCssExtension(){
        BaseXXHttpHandler dispatcher = new BaseXXHttpHandler();
        String ext = dispatcher.extractExtension("/main.css");
        Assert.assertEquals(".css", ext);
    }

    @Test
    public void shouldGetJavascriptExtension(){
        BaseXXHttpHandler dispatcher = new BaseXXHttpHandler();
        String ext = dispatcher.extractExtension("/main.js");
        Assert.assertEquals(".js", ext);
    }

    @Test
    public void shouldPrintHeaders() {
        BaseXXHttpHandler dispatcher = new BaseXXHttpHandler();
        try {
            File example = new File(testFile);
            PrintWriter writer = new PrintWriter(example);
            dispatcher.printHeaders(200,
                    writer,
                    ContentType.HTML,
                    Integer.valueOf(String.valueOf(example.length())),
                    ClientVersion.HTTP11.getClientVersionStr());
            writer.flush();
            writer.close();
            Assert.assertEquals(139, example.length());
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
