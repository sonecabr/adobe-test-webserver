package com.adobe.test.webserver.http.spec;


import lombok.Getter;

@Getter
public enum HttpStatusCode {
    OK_200(200),
    CREATED_201(201),
    NOT_FOUND_404(404),
    METHOD_NOT_ALLOWED(405),
    HTTP_VERSION_NOT_SUPPORTED(505);

    int code;

    HttpStatusCode(int code) {
        this.code = code;
    }
}
