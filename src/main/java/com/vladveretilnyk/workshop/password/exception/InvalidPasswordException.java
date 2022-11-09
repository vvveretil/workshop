package com.vladveretilnyk.workshop.password.exception;

public class InvalidPasswordException extends Exception{

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
