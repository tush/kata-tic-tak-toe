package com.tush.kata.game.service;

import com.tush.kata.game.data.GameData;
import com.tush.kata.game.exception.InvalidGameException;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Game;
import com.tush.kata.game.model.GameStep;
import com.tush.kata.game.model.Player;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.tush.kata.game.model.enums.GameStatus.*;

/**
 * GameService
 * The class hold business logic for Game Engine
 */
@Component
public class GameService {

    public Game createGame(Player player) throws InvalidParamException {
        if (player == null || player.getName().isEmpty()) {
            throw new InvalidParamException(InvalidParamException.MESSAGE_INVALID_PLAYER);
        }
        Game game = new Game();
        game.setBoard(new int[3][3]);
        game.setId(UUID.randomUUID().toString());
        game.setPlayerX(player);
        game.setStatus(NEW);
        GameData.getInstance().setGame(game);
        return game;
    }

    public Game connectToGame(Player playerO, String gameId) throws InvalidParamException, InvalidGameException {
        if (!GameData.getInstance().getGames().containsKey(gameId)) {
            throw new InvalidParamException(InvalidParamException.MESSAGE_INVALID_GAME);
        }
        if (playerO == null || playerO.getName() == null || playerO.getName().isEmpty()) {
            throw new InvalidParamException(InvalidParamException.MESSAGE_INVALID_PLAYER);
        }
        Game game = GameData.getInstance().getGames().get(gameId);
        if (game.getPlayerO() != null) {
            throw new InvalidGameException(InvalidGameException.MESSAGE_INVALID_GAME);
        }
        game.setPlayerO(playerO);
        game.setStatus(IN_PROGRESS);
        GameData.getInstance().setGame(game);
        return game;
    }

    public Game playGameStep(GameStep gameStep) throws InvalidGameException {
        if (!GameData.getInstance().getGames().containsKey(gameStep.getGameId())) {
            throw new InvalidGameException(InvalidGameException.MESSAGE_GAME_NOT_FOUND);
        }
        Game game = GameData.getInstance().getGames().get(gameStep.getGameId());
        if (game.getStatus().equals(FINISHED)) {
            throw new InvalidGameException(InvalidGameException.MESSAGE_GAME_FINISHED);
        }
        int[][] board = game.getBoard();
        board[gameStep.getPosX()][gameStep.getPosY()] = gameStep.getType().getValue();
        GameData.getInstance().setGame(game);
        return game;
    }
}
