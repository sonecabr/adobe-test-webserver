package com.adobe.test.webserver;



import com.adobe.test.webserver.server.WebServerConfigs;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class WebServerApplication {

    public static Boolean isRunning = Boolean.FALSE;

    public static void main(String[] args) {

        WebServerConfigs webServerConfigs = new WebServerConfigs();


        log.info("Starting Adobe test webserver...");
        log.info("\n        yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy\n" +
                "        yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy\n" +
                "        yyyyy/                  +yyyyyyyyyyyyyy+                  +y///y\n" +
                "        yyyyy/                 /yyyyyyyyyyyyyyyy:                 +yo+oy\n" +
                "        yyyyy/                -yyyyyyyyyyyyyyyyyy-                +yyyyy\n" +
                "        yyyyy/               .syyyyyyyyyyyyyyyyyys.               +yyyyy\n" +
                "        yyyyy/              `syyyyyyyyyyyyyyyyyyyys`              +yyyyy\n" +
                "        yyyyy/             `oyyyyyyyyyyyyyyyyyyyyyy+`             +yyyyy\n" +
                "        yyyyy/             +yyyyyyyyyyyyyyyyyyyyyyyy/             +yyyyy\n" +
                "        yyyyy/            :yyyyyyyyyyyyyyyyyyyyyyyyyy:            +yyyyy\n" +
                "        yyyyy/           -yyyyyyyyyyyyyooyyyyyyyyyyyyy-           +yyyyy\n" +
                "        yyyyy/          .syyyyyyyyyyyyo``syyyyyyyyyyyys.          +yyyyy\n" +
                "        yyyyy/         `syyyyyyyyyyyys.  .syyyyyyyyyyyyo`         +yyyyy\n" +
                "        yyyyy/        `oyyyyyyyyyyyyy-    -yyyyyyyyyyyyy+         +yyyyy\n" +
                "        yyyyy/        /yyyyyyyyyyyyy:      /yyyyyyyyyyyyy/        +yyyyy\n" +
                "        yyyyy/       :yyyyyyyyyyyyy/        +yyyyyyyyyyyyy:       +yyyyy\n" +
                "        yyyyy/      -yyyyyyyyyyyyy+         `oyyyyyyyyyyyyy.      +yyyyy\n" +
                "        yyyyy/     .syyyyyyyyyyyyo`          `syyyyyyyyyyyys`     +yyyyy\n" +
                "        yyyyy/    `syyyyyyyyyyyys`            .syyyyyyyyyyyyo`    +yyyyy\n" +
                "        yyyyy/    oyyyyyyyyyyyyy.              -yyyyyyyyyyyyy+    +yyyyy\n" +
                "        yyyyy/   /yyyyyyyyyyyyys////////:       :yyyyyyyyyyyyy/   +yyyyy\n" +
                "        yyyyy/  :yyyyyyyyyyyyyyyyyyyyyyyy:       +yyyyyyyyyyyyy-  +yyyyy\n" +
                "        yyyyy/ -yyyyyyyyyyyyyyyyyyyyyyyyyy.       oyyyyyyyyyyyyy. +yyyyy\n" +
                "        yyyyy/.syyyyyyyyyyyyyyyyyyyyyyyyyys`      `syyyyyyyyyyyys`+yyyyy\n" +
                "        yyyyy+oyyyyyyyyyyyyyyyyyyyyyyyyyyyy+       .syyyyyyyyyyyyo+yyyyy\n" +
                "        yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy\n" +
                "        yyyyyyyyys+++syyyyyyyyyo//oyyyyyyyyyyyy///syyyyyyyyyyyyyyyyyyyyy\n" +
                "        yyyyyyyyy-   -yyyyyyyyy:  :yyyyyyyyyyyy.  oyyyyyyyyyyyyyyyyyyyyy\n" +
                "        yyyyyyyys` . `syyyyyssy:  :yyyyyssyyyyy.  oyssyyyyyyysssyyyyyyyy\n" +
                "        yyyyyyyy/ `o  /yyyo-``..  :yyo-```.:syy.  :.``-syys/.```-oyyyyyy\n" +
                "        yyyyyyys` -y` .yys` `/+`  :yo` .o+  .sy.  -+:  .yy. `oo. `syyyyy\n" +
                "        yyyyyyy+  /s:  oy/  :yy:  :y-  +yy.  +y.  oyy`  o+  .//-  +yyyyy\n" +
                "        yyyyyyy.  ```  -y:  :yy:  :y-  +yy-  +y.  oyy`  o/  `-----oyyyyy\n" +
                "        yyyyyyo  -ooo` `s+  .ss-  :y/  -ss` `sy.  /s+  .yo` .osyssyyyyyy\n" +
                "        yyyyyy-  oyyy:  /y:` `..  :yy:` .``.+yy` `..` .oyy+.``...:yyyyyy\n" +
                "        yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy\n" +
                "        yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy\n\n\n");

        ExecutorService executorService = Executors.newFixedThreadPool(WebServerConfigs.THREAD_LIMIT);
        try {
            ServerSocket serverSocket = new ServerSocket(WebServerConfigs.PORT_TO_LISTEN);

            while (true) {
                isRunning = Boolean.TRUE;
                executorService.submit(
                        WebServerRequestHandler
                                .builder()
                                .clientSocket(serverSocket.accept())
                                .build());
            }

        } catch (IOException e) {
            if(e.getMessage().toLowerCase().contains("bind")) {
                log.error(String.format("Server failed to start, probably the port %d is not available", WebServerConfigs.PORT_TO_LISTEN));
            } else {
                log.error("Server failed to start...");
            }
        }
    }
}
