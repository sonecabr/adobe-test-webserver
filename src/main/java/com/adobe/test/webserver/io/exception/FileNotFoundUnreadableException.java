package com.adobe.test.webserver.io.exception;

/**
 * File not readable or unavailable exception
 * <p>
 *     Define exception for wrong access or path configuration in webroot
 * </p>
 * @author Andre Rocha
 * @since 2019-06-08
 */
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
