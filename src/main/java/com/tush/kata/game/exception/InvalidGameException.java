package com.tush.kata.game.exception;

/**
 * InvalidGameException
 * The class hold exception message for Invalid Game, Game Not Found, Game Finished
 */
public class InvalidGameException extends BaseException {

    public static final String MESSAGE_INVALID_GAME = "Game is not valid anymore";
    public static final String MESSAGE_GAME_NOT_FOUND = "Game not found";
    public static final String MESSAGE_GAME_FINISHED = "Game is already finished";

    public InvalidGameException(String message) { super(message); }
}
