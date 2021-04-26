package com.tush.kata.game.controller.dto;

import com.tush.kata.game.model.Player;

/**
 * ConnectionRequest
 * The DTO class to pass data from Request to Service for Game Engine
 */
public class ConnectionRequest {
    private Player player;
    private String gameId;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
