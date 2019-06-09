package com.adobe.test.webserver.io;

import com.adobe.test.webserver.io.exception.FileNotFoundUnreadableException;

import java.io.BufferedOutputStream;

/**
 * File reader api
 * <p>
 *     Define api to read files from webroot folder
 * <p/>
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-08
 */
public interface FileReader {

    /**
     * Read file from WebROOT
     * @param path
     * @param outputStream
     * @return WebContentFile - with file content
     * @throws FileNotFoundUnreadableException
     */
    WebContentFile readContent(String path, BufferedOutputStream outputStream) throws FileNotFoundUnreadableException;

    /**
     * Read file from WebROOT
     * @param webroot
     * @param path
     * @param outputStream
     * @return WebContentFIle - with file content
     * @throws FileNotFoundUnreadableException
     */
    WebContentFile readContent(String webroot, String path, BufferedOutputStream outputStream) throws FileNotFoundUnreadableException;

}
