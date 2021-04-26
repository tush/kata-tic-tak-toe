package com.tush.kata.game.service;

import com.tush.kata.game.data.PlayerData;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerServiceTest {

    @Autowired
    private PlayerService playerService;

    @Test
    void getGames_whenNoGames_thenReturns_EmptyArray() {
        PlayerData.resetPlayers();
        Map<String, Player> players = playerService.getPlayers();
        assertTrue(players.isEmpty());
    }

    @Test
    void getGames_whenHasGames_thenReturns_GamesArray() throws InvalidParamException {
        PlayerData.resetPlayers();
        String playerXName = "Player1";
        playerService.addPlayer(playerXName);
        Map<String, Player> players = playerService.getPlayers();
        assertEquals(1, players.size());
    }
}