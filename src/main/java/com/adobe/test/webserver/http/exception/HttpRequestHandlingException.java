package com.adobe.test.webserver.http.exception;


/**
 * Http request exception for handling/dispatching business errors
 *
 * @author Andre Rocha
 * @since 2019-06-09
 */
public class HttpRequestHandlingException extends Exception {
    public HttpRequestHandlingException() {
    }

    public HttpRequestHandlingException(String message) {
        super(message);
    }

    public HttpRequestHandlingException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpRequestHandlingException(Throwable cause) {
        super(cause);
    }

    public HttpRequestHandlingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
