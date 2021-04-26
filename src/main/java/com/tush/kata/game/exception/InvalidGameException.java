package com.tush.kata.game.exception;

/**
 * InvalidGameException
 * The class hold exception message for Invalid Game
 */
public class InvalidGameException extends BaseException {

    public static final String MESSAGE_INVALID_GAME = "Game is not valid anymore";

    public InvalidGameException(String message) { super(message); }
}
