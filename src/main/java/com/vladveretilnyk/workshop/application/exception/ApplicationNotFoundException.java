package com.vladveretilnyk.workshop.application.exception;

public class ApplicationNotFoundException extends Exception {

    public ApplicationNotFoundException(String message) {
        super(message);
    }

    public ApplicationNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
