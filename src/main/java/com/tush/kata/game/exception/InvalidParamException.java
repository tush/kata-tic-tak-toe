package com.tush.kata.game.exception;

/**
 * InvalidParamException
 * The class hold exception message for Invalid Player
 */
public class InvalidParamException extends BaseException {

    public static final String MESSAGE_INVALID_PLAYER = "Player is not valid";

    public InvalidParamException(String message) {
        super(message);
    }
}
