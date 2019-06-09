package com.adobe.test.webserver.io;


import lombok.Builder;
import lombok.Value;

import java.io.BufferedOutputStream;

@Builder
@Value
public class WebContentFile {

    private BufferedOutputStream content;
    private int lenght;
}
