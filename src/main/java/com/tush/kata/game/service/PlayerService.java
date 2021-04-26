package com.tush.kata.game.service;

import com.tush.kata.game.data.PlayerData;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Player;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * PlayerService
 * The class hold business logic for Player Engine
 */
@Component
public class PlayerService {

    public Player addPlayer(String playerName) throws InvalidParamException {
        if (playerName == null || playerName.trim().isEmpty()) {
            throw new InvalidParamException(InvalidParamException.MESSAGE_INVALID_PLAYER);
        }
        Player player = new Player();
        player.setName(playerName);
        player.setId(UUID.randomUUID().toString());
        PlayerData.getInstance().setPlayer(player);
        return player;
    }

    public Map<String, Player> getPlayers() {
        return PlayerData.getInstance().getPlayers();
    }
}
