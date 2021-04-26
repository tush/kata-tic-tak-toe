package com.tush.kata.game.service;

import com.tush.kata.game.exception.InvalidGameException;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Game;
import com.tush.kata.game.model.GameStep;
import com.tush.kata.game.model.Player;
import com.tush.kata.game.model.enums.GameStatus;
import com.tush.kata.game.model.enums.PlayerType;
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

    GameStep getGameStep(PlayerType type, String gameId, int posX, int posY) {
        GameStep gameStep = new GameStep();
        gameStep.setType(type);
        gameStep.setPosX(posX);
        gameStep.setPosY(posY);
        gameStep.setGameId(gameId);
        return gameStep;
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

    @Test
    void connectToGame_whenValidInput_thenReturnsGame() throws InvalidParamException, InvalidGameException {
        String playerXName = "Player1";
        String playerOName = "Player2";
        Game _game = gameService.createGame(getPLayer(playerXName));
        Game game = gameService.connectToGame(getPLayer(playerOName), _game.getId());
        assertTrue(!game.getId().isEmpty());
        assertNotNull(game.getPlayerX());
        assertEquals(game.getPlayerX().getName(), playerXName);
        assertNotNull(game.getPlayerO());
        assertEquals(game.getPlayerO().getName(), playerOName);
        assertNull(game.getWinner());
        assertNotNull(game.getBoard());
        assertEquals(game.getStatus().toString(), GameStatus.IN_PROGRESS.toString());
    }

    @Test
    void connectToGame_whenNotValidPlayerInput_thenThrowsException() {
        try {
            Game _game = gameService.createGame(getPLayer("Player1"));
            gameService.connectToGame(getPLayer(""), _game.getId());
        } catch (InvalidParamException | InvalidGameException e) {
            assertEquals(InvalidParamException.MESSAGE_INVALID_PLAYER, e.getMessage());
        }
    }

    @Test
    void connectToGame_whenNotValidGameIdInput_thenThrowsException() {
        try {
            gameService.createGame(getPLayer("Player1"));
            gameService.connectToGame(getPLayer("Player2"), "");
        } catch (InvalidParamException | InvalidGameException e) {
            assertEquals(InvalidParamException.MESSAGE_INVALID_GAME, e.getMessage());
        }
    }

    @Test
    void connectToGame_whenGameInProgress_thenThrowsException() {
        try {
            Game _game = gameService.createGame(getPLayer("Player1"));
            gameService.connectToGame(getPLayer("Player2"), _game.getId());
            gameService.connectToGame(getPLayer("Player3"), _game.getId());
        } catch (InvalidParamException | InvalidGameException e) {
            assertEquals(InvalidGameException.MESSAGE_INVALID_GAME, e.getMessage());
        }
    }

    @Test
    void playGameStep_whenNotValidInput_thenThrowsException() {
        try {
            GameStep gameStep = new GameStep();
            gameService.playGameStep(gameStep);
        } catch (InvalidGameException e) {
            assertEquals(InvalidGameException.MESSAGE_GAME_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    void playGameStep_whenGameFinished_thenThrowsException() throws InvalidParamException {
        Game game = gameService.createGame(getPLayer("Player1"));
        game.setPlayerO(getPLayer("Player2"));
        game.setStatus(GameStatus.FINISHED);
        try {
            GameStep gameStep = new GameStep();
            gameStep.setGameId(game.getId());
            gameService.playGameStep(gameStep);
        } catch (InvalidGameException e) {
            assertEquals(InvalidGameException.MESSAGE_GAME_FINISHED, e.getMessage());
        }
    }

    @Test
    void playGameStep_whenValidInput_thenReturnsGame() throws InvalidParamException, InvalidGameException {
        String playerXName = "Player1";
        String playerOName = "Player2";

        Game _game = gameService.createGame(getPLayer(playerXName));
        _game.setPlayerO(getPLayer(playerOName));

        GameStep gameStep = getGameStep(PlayerType.X, _game.getId(), 0, 0);
        Game game = gameService.playGameStep(gameStep);

        assertTrue(!game.getId().isEmpty());
        assertNotNull(game.getPlayerX());
        assertEquals(game.getPlayerX().getName(), playerXName);
        assertNotNull(game.getPlayerO());
        assertEquals(game.getPlayerO().getName(), playerOName);
        assertNull(game.getWinner());
        assertNotNull(game.getBoard());

        assertEquals(game.getBoard()[0][0], gameStep.getType().X.getValue());
    }
}