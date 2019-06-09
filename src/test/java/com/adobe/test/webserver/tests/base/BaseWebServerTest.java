package com.adobe.test.webserver.tests.base;

import com.adobe.test.webserver.WebServerApplication;
import org.junit.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseWebServerTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(1);

    @Before
    public void setUp() {

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                new WebServerApplication().main(new String[]{});
            }
        });
        while (!WebServerApplication.isRunning){
            try {
                System.out.println("Server is not ready...");
                Thread.sleep(1000l);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Server is ready...");

    }

    @After
    public void tearDown(){
        executorService.shutdownNow();
    }
}

