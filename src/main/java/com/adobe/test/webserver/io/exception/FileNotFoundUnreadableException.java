package com.adobe.test.webserver.io.exception;

public class FileNotFoundUnreadableException extends Exception {

    public FileNotFoundUnreadableException() {
    }

    public FileNotFoundUnreadableException(String message) {
        super(message);
    }

    public FileNotFoundUnreadableException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundUnreadableException(Throwable cause) {
        super(cause);
    }

    public FileNotFoundUnreadableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
