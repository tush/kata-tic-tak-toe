package com.tush.kata.game.model;

import com.tush.kata.game.model.enums.GameStatus;
import com.tush.kata.game.model.enums.PlayerType;

/**
 * Game Model
 * The class helps hold data related to a Game
 */
public class Game {
    private String id;
    private Player playerX;
    private Player playerO;
    private GameStatus status;
    private int[][] board;
    private PlayerType winner;
    private PlayerType nextPlayer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Player getPlayerX() {
        return playerX;
    }

    public void setPlayerX(Player playerX) {
        this.playerX = playerX;
    }

    public Player getPlayerO() {
        return playerO;
    }

    public void setPlayerO(Player playerO) {
        this.playerO = playerO;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public PlayerType getWinner() {
        return winner;
    }

    public void setWinner(PlayerType winner) {
        this.winner = winner;
        this.setStatus(GameStatus.FINISHED);
    }

    public PlayerType getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(PlayerType nextPlayer) {
        this.nextPlayer = nextPlayer;
    }
}
