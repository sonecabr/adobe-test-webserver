package com.adobe.test.webserver.io;

import com.adobe.test.webserver.io.exception.FileNotFoundUnreadableException;

import java.io.BufferedOutputStream;


public interface FileReader {

    WebContentFile readContent(String path, BufferedOutputStream outputStream) throws FileNotFoundUnreadableException;

    WebContentFile readContent(String webroot, String path, BufferedOutputStream outputStream) throws FileNotFoundUnreadableException;

}
