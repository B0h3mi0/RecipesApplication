package com.example.recetario_app.domain.exception;

public class DatabaseTransactionException extends RuntimeException {

    public DatabaseTransactionException(String message, Throwable cause) {
        super(message);
        super.initCause(cause);
    }
}
