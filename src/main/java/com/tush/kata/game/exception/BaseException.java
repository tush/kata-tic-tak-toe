package com.tush.kata.game.exception;

/**
 * BaseException
 * The class used to hold exception message
 */
public class BaseException extends Exception {

    private final String message;

    public BaseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
