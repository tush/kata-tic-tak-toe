package com.tush.kata.game.service;

import com.tush.kata.game.data.GameData;
import com.tush.kata.game.exception.InvalidGameException;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Game;
import com.tush.kata.game.model.GameStep;
import com.tush.kata.game.model.Player;
import com.tush.kata.game.model.enums.PlayerType;
import org.springframework.stereotype.Component;

import java.util.Map;
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
        game.setNextPlayer(PlayerType.X);
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
        if (game.getStatus().equals(FINISHED) || game.getStatus().equals(DRAW)) {
            throw new InvalidGameException(InvalidGameException.MESSAGE_GAME_FINISHED);
        }
        if(game.getNextPlayer() != gameStep.getType()) {
            throw new InvalidGameException(InvalidGameException.MESSAGE_INVALID_GAME_STEP);
        }
        int[][] board = game.getBoard();
        if(board[gameStep.getPosX()][gameStep.getPosY()] != 0) {
            throw new InvalidGameException(InvalidGameException.MESSAGE_INVALID_SQUARE);
        }
        board[gameStep.getPosX()][gameStep.getPosY()] = gameStep.getType().getValue();

        Boolean xWinner = checkWinner(game.getBoard(), PlayerType.X);
        Boolean oWinner = checkWinner(game.getBoard(), PlayerType.O);

        if (Boolean.TRUE.equals(xWinner)) {
            game.setWinner(PlayerType.X);
        } else if (Boolean.TRUE.equals(oWinner)) {
            game.setWinner(PlayerType.O);
        } else if(Boolean.TRUE.equals(checkIfDraw(game.getBoard()))){
            game.setStatus(DRAW);
        }

        if(game.getNextPlayer() == PlayerType.X) {
            game.setNextPlayer(PlayerType.O);
        } else {
            game.setNextPlayer(PlayerType.X);
        }

        GameData.getInstance().setGame(game);
        return game;
    }

    private Boolean checkIfDraw(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private Boolean checkWinner(int[][] board, PlayerType playerType) {
        int[][] winCombos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        int[] flatArray = new int[9];

        int counter = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                flatArray[counter] = board[i][j];
                counter++;
            }
        }
        for (int i = 0; i < winCombos.length; i++) {
            int winCounter = 0;
            for (int j = 0; j < winCombos[i].length; j++) {
                if (flatArray[winCombos[i][j]] == playerType.getValue()) {
                    winCounter++;
                    if (winCounter == 3) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Map<String, Game> getGames() {
        return GameData.getInstance().getGames();
    }
}
