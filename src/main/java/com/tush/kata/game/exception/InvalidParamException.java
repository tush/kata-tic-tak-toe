package com.tush.kata.game.exception;

/**
 * InvalidParamException
 * The class hold exception message for Invalid Player, Invalid Game
 */
public class InvalidParamException extends BaseException {

    public static final String MESSAGE_INVALID_PLAYER = "Player is not valid";
    public static final String MESSAGE_INVALID_GAME = "Game with provided id doesn't exist";

    public InvalidParamException(String message) {
        super(message);
    }
}
