package com.adobe.test.webserver.server;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Builder
@Data
public class WebServerConfigs {
    public static Integer THREAD_LIMIT;
    public static Integer PORT_TO_LISTEN;
    public static String WEB_ROOT;
    public static List<String> DEFAULT_FILES;
    public static String NOT_FOUND_FILE;
    public static String BACKEND_ERROR_FILE;
    public static String CHARSET;
    public static String SERVERNAME;
    public static boolean KEEP_ALIVE_ENABLED;
    public static Integer KEEP_ALIVE_TIMEOUT;
    public static Integer KEEP_ALIVE_MAX;


    public WebServerConfigs(){
        init();
    }

    public void init(){

        Config defaultConf = ConfigFactory.parseResources("default.conf");

        Config config = ConfigFactory.systemEnvironment()
                .withFallback(defaultConf)
                .resolve();


        THREAD_LIMIT = config.getInt("server_threadlimit");
        PORT_TO_LISTEN = config.getInt("server_port");
        WEB_ROOT = config.getString("server_webroot");
        DEFAULT_FILES = config.getStringList("server_defaultfiles");
        NOT_FOUND_FILE = config.getString("server_notfoundfile");
        BACKEND_ERROR_FILE = config.getString("server_backenderrorfile");
        CHARSET = config.getString("server_charset");
        SERVERNAME = config.getString("server_servername");
        KEEP_ALIVE_ENABLED = config.getBoolean("server_keepaliveactive");
        KEEP_ALIVE_TIMEOUT = config.getInt("server_keepalivetimeout");
        KEEP_ALIVE_MAX = config.getInt("server_keepalivemax");

    }



}
