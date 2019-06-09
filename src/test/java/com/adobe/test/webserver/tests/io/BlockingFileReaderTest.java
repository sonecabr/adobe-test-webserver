package com.adobe.test.webserver.tests.io;

import com.adobe.test.webserver.io.BlockingFileReader;
import com.adobe.test.webserver.io.FileReader;
import com.adobe.test.webserver.io.WebContentFile;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

@RunWith(JUnit4.class)
public class BlockingFileReaderTest {

    @Test
    public void shouldGetWebContentFile(){
        FileReader reader = BlockingFileReader.getInstance();
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(
                    new FileOutputStream(new File("cachedOS.testfile")));


            URL url = this.getClass().getClassLoader().getResource("www");
            WebContentFile result = reader.readContent(url.getPath(), "/test.html", outputStream);
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getLenght(), 108);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
