package com.adobe.test.webserver.http.handler.http1x;


import com.adobe.test.webserver.http.handler.BaseXXHttpHandler;
import com.adobe.test.webserver.io.FileReader;
import com.adobe.test.webserver.io.NonBlockingFileReader;


public class BaseHttp1xHandler extends BaseXXHttpHandler {
    final FileReader fileReader = NonBlockingFileReader.getInstance();
}
