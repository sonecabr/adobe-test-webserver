package com.adobe.test.webserver.http.spec;

import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Http Content-Type type definition
 *
 * @author Andre Rocha <devel.andrerocha@gmail.com>
 * @since 2019-06-09
 */
@Getter
public enum ContentType {
    HTM("text/htm", ".htm"),
    HTML("text/html", ".html"),
    URL_ENCODED("application/x-www-form-urlencoded", ".html"),
    DEFAULT("text/html", "");

    String description = "text/html";
    String extension = ".html";

    ContentType(String description, String extension) {
        this.description = description;
        this.extension = extension;
    }

    public static ContentType byExtension(String extension) {
        return Arrays.asList(ContentType.values())
                .stream()
                .filter(t -> t.getExtension().toLowerCase().equals(extension.toLowerCase()))
                .collect(Collectors.toList()).get(0);
    }
}
