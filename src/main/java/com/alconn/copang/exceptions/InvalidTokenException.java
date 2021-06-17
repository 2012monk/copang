package com.alconn.copang.exceptions;

public class InvalidTokenException extends Exception {

    private String message = "token is invalid";

    public InvalidTokenException(String message) {
        this.message = message;
    }

    public InvalidTokenException() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
