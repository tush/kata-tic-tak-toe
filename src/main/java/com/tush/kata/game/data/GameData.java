package com.tush.kata.game.data;

import com.tush.kata.game.model.Game;

import java.util.HashMap;
import java.util.Map;

/**
 * GameData
 * The singleton class used to hold Game data in memory
 */
public class GameData {
    private static Map<String, Game> games = new HashMap<>();
    private static GameData instance;

    public static synchronized GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }

    public Map<String, Game> getGames() {
        return games;
    }

    public void setGame(Game game) {
        games.put(game.getId(), game);
    }

    public void resetGames() { games = new HashMap<>(); }
}
