package com.vladveretilnyk.workshop.user.exception;

public class UserNotFountException extends Exception {

    public UserNotFountException(String message) {
        super(message);
    }

    public UserNotFountException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
