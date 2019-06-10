package com.adobe.test.webserver.server;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.Builder;
import lombok.Data;
import java.util.List;


/**
 * WebServer configs
 * <ul>
 *     <li>Suports file resource @see src/main/resources/default.conf</li>
 *     <li>Suports environments (same name as file)</li>
 * </ul>
 * @author Andre Rocha
 * @since 2019-06-08
 */
@Builder
@Data
public class WebServerConfigs {
    public static Integer THREAD_LIMIT; //number of the threads to reserve
    public static Integer PORT_TO_LISTEN; //port to listen
    public static String WEB_ROOT; //folder where webcontent would be lotated
    public static List<String> DEFAULT_FILES; //default return in case of missing uri information
    public static String NOT_FOUND_FILE; //404 file
    public static String BACKEND_ERROR_FILE; //500 file
    public static String CHARSET; //charset of files in WebROOT
    public static String SERVERNAME; //server name (which is visibile in http response
    public static boolean KEEP_ALIVE_ENABLED; //keep alive enabled?
    public static Integer KEEP_ALIVE_TIMEOUT; //keep alive time to wait before close
    public static Integer KEEP_ALIVE_MAX; //keep alive max
    public static Integer CLIENT_TIMEOUT; //timeout to request


    public WebServerConfigs(){
        init();
    }

    /**
     * Read configs from resources or environment
     */
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
        CLIENT_TIMEOUT = config.getInt("client_timeout");

    }



}
