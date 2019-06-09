package com.adobe.test.webserver.http.spec;

import lombok.Getter;

public enum  ClientVersion {

    HTTP11("HTTP/1.1");

    @Getter
    private String clientVersionStr;

    ClientVersion(String s) {
        this.clientVersionStr = s;
    }

    ClientVersion fromString(String s) {
        if(s.toUpperCase().equals(HTTP11.getClientVersionStr())){
            return HTTP11;
        }
        return HTTP11;
    }
}
