package com.tush.kata.game.service;

import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Game;
import com.tush.kata.game.model.Player;
import com.tush.kata.game.model.enums.GameStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceTest {

    @Autowired
    private GameService gameService;

    Player getPLayer(String name) {
        Player player = new Player();
        player.setName(name);
        return player;
    }

    @Test
    void createGame_whenValidInput_thenReturnsGame() throws InvalidParamException {
        String playerName = "Player1";
        Game game = gameService.createGame(getPLayer(playerName));
        assertTrue(!game.getId().isEmpty());
        assertNotNull(game.getPlayerX());
        assertEquals(game.getPlayerX().getName(), playerName);
        assertNull(game.getPlayerO());
        assertNull(game.getWinner());
        assertNotNull(game.getBoard());
        assertEquals(game.getStatus().toString(), GameStatus.NEW.toString());
    }

    @Test
    void createGame_whenNotValidInput_thenThrowsException() {
        try {
            gameService.createGame(getPLayer(""));
        } catch (InvalidParamException e) {
            assertEquals(InvalidParamException.MESSAGE_INVALID_PLAYER, e.getMessage());
        }
    }
}