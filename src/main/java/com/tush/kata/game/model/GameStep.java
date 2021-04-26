package com.tush.kata.game.model;

import com.tush.kata.game.model.enums.PlayerType;

/**
 * GameStep Model
 * The class helps hold data related to a step in a Game
 */
public class GameStep {
    private PlayerType type;
    private Integer posX;
    private Integer posY;
    private String gameId;

    public PlayerType getType() {
        return type;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
