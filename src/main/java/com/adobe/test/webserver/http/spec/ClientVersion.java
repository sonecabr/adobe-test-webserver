package com.adobe.test.webserver.http.spec;

import lombok.Getter;

/**
 * Supported http client version definition
 *
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-09
 */
public enum  ClientVersion {
    HTTP10("HTTP/1.0"),
    HTTP11("HTTP/1.1"),
    HTTP20("HTTP/2.0"),
    HTTP30("HTTP/3.0");

    @Getter
    private String clientVersionStr;

    ClientVersion(String s) {
        this.clientVersionStr = s;
    }

    ClientVersion fromString(String s) {
        if(s.toUpperCase().equals(HTTP10.getClientVersionStr())){
            return HTTP10;
        }
        if(s.toUpperCase().equals(HTTP11.getClientVersionStr())){
            return HTTP11;
        }
        if(s.toUpperCase().equals(HTTP20.getClientVersionStr())){
            return HTTP20;
        }
        if(s.toUpperCase().equals(HTTP30.getClientVersionStr())){
            return HTTP30;
        }
        return HTTP11;
    }
}
