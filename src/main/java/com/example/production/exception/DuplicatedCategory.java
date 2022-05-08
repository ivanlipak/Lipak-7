package com.example.production.exception;

public class DuplicatedCategory extends RuntimeException {
    public DuplicatedCategory() {
    }

    public DuplicatedCategory(String message) {
        super(message);
    }

    public DuplicatedCategory(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedCategory(Throwable cause) {
        super(cause);
    }

    public DuplicatedCategory(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
