package com.example.production.exception;

public class DuplicatedItems extends Exception{
    public DuplicatedItems() {
    }

    public DuplicatedItems(String message) {
        super(message);
    }

    public DuplicatedItems(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedItems(Throwable cause) {
        super(cause);
    }

    public DuplicatedItems(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
