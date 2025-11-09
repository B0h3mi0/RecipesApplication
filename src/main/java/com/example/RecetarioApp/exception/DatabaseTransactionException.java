package com.example.RecetarioApp.exception;

public class DatabaseTransactionException extends RuntimeException {

    public DatabaseTransactionException(String message, Throwable cause) {
        super(message);
        super.initCause(cause);
    }
}
