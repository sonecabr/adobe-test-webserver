package com.adobe.test.webserver.http.spec;


import lombok.Getter;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Http request header type definition
 *
 * @author Andre Rocha
 * @since 2019-06-09
 */
@Getter
public enum HttpRequestHeader {

    ACCEPT("Accept"),
    USER_AGENT("User-Agent"),
    HOST("Host"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    CONNECTION("Connection"),
    ACCEPT_LANGUAGE("Accept-Language"),
    CACHE_CONTROL("Cache-Control");


    String description;

    HttpRequestHeader(String description) {
        this.description = description;
    }

    public static HttpRequestHeader byName(String description) {
        return Arrays.asList(HttpRequestHeader.values())
                .stream()
                .filter(t -> t.getDescription().toLowerCase().equals(description.toLowerCase()))
                .collect(Collectors.toList()).get(0);
    }

}
