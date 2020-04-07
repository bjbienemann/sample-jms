package com.agilliza.exception;

public class JmsConnectionException extends RuntimeException {

    public JmsConnectionException(String message) {
        super(message);
    }

    public JmsConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
