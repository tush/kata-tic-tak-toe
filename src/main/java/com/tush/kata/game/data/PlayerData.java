package com.tush.kata.game.data;

import com.tush.kata.game.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * PlayerData
 * The singleton class used to hold Game data in memory
 */
public class PlayerData {
    private static Map<String, Player> players = new HashMap<>();
    private static PlayerData instance;

    public static synchronized PlayerData getInstance() {
        if (instance == null) {
            instance = new PlayerData();
        }
        return instance;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayer(Player player) {
        players.put(player.getId(), player);
    }

    public static void resetPlayers() { players = new HashMap<>(); }
}
