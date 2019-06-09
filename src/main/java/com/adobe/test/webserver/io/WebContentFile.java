package com.adobe.test.webserver.io;


import lombok.Builder;
import lombok.Value;

import java.io.BufferedOutputStream;

/**
 * Web content file
 * <p>
 *     Content of files available in server (html)
 * </p>
 * @author Andre Rocha
 * @since 2019-06-08
 */
@Builder
@Value
public class WebContentFile {

    private BufferedOutputStream content;
    private int lenght;
}
