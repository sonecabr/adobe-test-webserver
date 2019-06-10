package com.adobe.test.webserver.tests.io;

import com.adobe.test.webserver.io.FileReader;
import com.adobe.test.webserver.io.NonBlockingFileReader;
import com.adobe.test.webserver.io.WebContentFile;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class NonBlockingFileReaderTest {

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

    private String testFile = "out/cachedOS.testfile";
    @Test
    public void shouldGetWebContentFile(){
        FileReader reader = NonBlockingFileReader.getInstance();
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(
                    new FileOutputStream(new File(testFile)));


            URL url = this.getClass().getClassLoader().getResource("www");
            WebContentFile result = reader.readContent(url.getPath(), "/test.html", outputStream);
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getLenght(), 96);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown(){
        try {
            Files.delete(Paths.get(testFile));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
