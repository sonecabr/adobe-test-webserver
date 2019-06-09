package com.adobe.test.webserver.server;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class WebServerConfigs {
    public static final Integer THREAD_LIMIT = 4;
    public static final Integer PORT_TO_LISTEN = 8080;
    public static final String WEB_ROOT = "/Users/aldrocha/others/workspaces/adobe-test-webserver/src/main/resources/www";
    public static final String[] DEFAULT_FILES = {"index.html", "default.html"};
    public static final String NOT_FOUND_FILE = "404.html";
    public static final String BACKEND_ERROR_FILE = "500.html";
    public static final String CHARSET = "UTF-8";
    public static final String SERVERNAME = "Adobe Test WebServer 0.1-SNAPSHOT";

}
