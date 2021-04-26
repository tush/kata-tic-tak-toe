package com.tush.kata.game.controller;

import com.tush.kata.game.exception.InvalidGameException;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Game;
import com.tush.kata.game.model.Player;
import com.tush.kata.game.model.enums.GameStatus;
import com.tush.kata.game.model.enums.PlayerType;
import com.tush.kata.game.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameService gameService;

    @Test
    void startAPI_whenValidInput_thenReturnsOK() throws Exception {
        String postData = "{\"player\": { \"name\": \"Player1\" }}";
        mockMvc.perform(post("/game/start")
                .contentType("application/json")
                .content(postData))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX.name").value("Player1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.board").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(GameStatus.NEW.toString()));
    }

    @Test
    void startAPI_whenNotValidInput_thenReturnsError() throws Exception {
        String postData = "{}";
        mockMvc.perform(post("/game/start")
                .contentType("application/json")
                .content(postData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidParamException))
                .andExpect(result -> assertEquals(InvalidParamException.MESSAGE_INVALID_PLAYER, result.getResolvedException().getMessage()));
    }

    @Test
    void connectAPI_whenValidInput_thenReturnsOK() throws Exception {
        Player player = new Player();
        player.setName("Player1");
        Game game = gameService.createGame(player);
        String postData = "{\"gameId\": \""+ game.getId() +"\",\"player\": { \"name\": \"Player2\" }}";

        mockMvc.perform(post("/game/connect")
                .contentType("application/json")
                .content(postData))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX.name").value(player.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO.name").value("Player2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.board").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(GameStatus.IN_PROGRESS.toString()));
    }

    @Test
    void connectAPI_whenNotValidInput_thenReturnsGameNotFoundError() throws Exception {
        String postData = "{}";
        mockMvc.perform(post("/game/connect")
                .contentType("application/json")
                .content(postData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidParamException))
                .andExpect(result -> assertEquals(InvalidParamException.MESSAGE_INVALID_GAME, result.getResolvedException().getMessage()));
    }

    @Test
    void connectAPI_whenNotValidInput_thenReturnsInvalidPlayerError() throws Exception {
        Player player = new Player();
        player.setName("Player1");
        Game game = gameService.createGame(player);
        String postData = "{\"gameId\": \""+ game.getId() +"\",\"player\": { \"name\": \"\" }}";

        mockMvc.perform(post("/game/connect")
                .contentType("application/json")
                .content(postData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidParamException))
                .andExpect(result -> assertEquals(InvalidParamException.MESSAGE_INVALID_PLAYER, result.getResolvedException().getMessage()));
    }

    @Test
    void connectAPI_whenNotValidInput_thenReturnsInvalidGameError() throws Exception {
        Player player = new Player();
        player.setName("Player1");

        Player player2 = new Player();
        player2.setName("Player2");

        Game game = gameService.createGame(player);
        game.setPlayerO(player2);
        String postData = "{\"gameId\": \""+ game.getId() +"\",\"player\": { \"name\": \"Player3\" }}";

        mockMvc.perform(post("/game/connect")
                .contentType("application/json")
                .content(postData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidGameException))
                .andExpect(result -> assertEquals(InvalidGameException.MESSAGE_INVALID_GAME, result.getResolvedException().getMessage()));
    }


    @Test
    void playAPI_whenNotValidInput_thenReturnsError() throws Exception {
        String postData = "{}";
        mockMvc.perform(post("/game/play")
                .contentType("application/json")
                .content(postData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidGameException))
                .andExpect(result -> assertEquals(InvalidGameException.MESSAGE_GAME_NOT_FOUND, result.getResolvedException().getMessage()));
    }

    @Test
    void playAPI_whenGameFinished_thenReturnsError() throws Exception {
        Player player = new Player();
        player.setName("Player1");

        Player player2 = new Player();
        player2.setName("Player2");

        Game game = gameService.createGame(player);
        game.setPlayerO(player2);

        game.setStatus(GameStatus.FINISHED);

        String postData = "{\"gameId\": \""+ game.getId() +"\",\n" +
                "    \"type\": \"X\",\n" +
                "    \"posX\": \"2\",\n" +
                "    \"posY\": \"0\"}";

        mockMvc.perform(post("/game/play")
                .contentType("application/json")
                .content(postData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidGameException))
                .andExpect(result -> assertEquals(InvalidGameException.MESSAGE_GAME_FINISHED, result.getResolvedException().getMessage()));
    }

    @Test
    void playAPI_whenValidInput_thenReturnsOK() throws Exception {
        Player player = new Player();
        player.setName("Player1");

        Player player2 = new Player();
        player2.setName("Player2");

        Game game = gameService.createGame(player);
        game.setPlayerO(player2);
        game.setStatus(GameStatus.IN_PROGRESS);

        String postData = "{\"gameId\": \""+ game.getId() +"\",\n" +
                "    \"type\": \"X\",\n" +
                "    \"posX\": \"2\",\n" +
                "    \"posY\": \"0\"}";

        mockMvc.perform(post("/game/play")
                .contentType("application/json")
                .content(postData))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX.name").value(player.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO.name").value(player2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.board").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(GameStatus.IN_PROGRESS.toString()));
    }

    @Test
    void playAPI_whenX_wins_thenReturns_X_as_winner() throws Exception {
        Player player = new Player();
        player.setName("Player1");

        Player player2 = new Player();
        player2.setName("Player2");

        Game game = gameService.createGame(player);
        game.setPlayerO(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        int[][] board = game.getBoard();
        board[0][0] = PlayerType.X.getValue();
        board[0][1] = PlayerType.X.getValue();

        String postData = "{\"gameId\": \""+ game.getId() +"\",\n" +
                "    \"type\": \"X\",\n" +
                "    \"posX\": \"0\",\n" +
                "    \"posY\": \"2\"}";

        mockMvc.perform(post("/game/play")
                .contentType("application/json")
                .content(postData))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX.name").value(player.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO.name").value(player2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").value(PlayerType.X.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(GameStatus.FINISHED.toString()));
    }

    @Test
    void playAPI_whenO_wins_thenReturns_O_as_winner() throws Exception {
        Player player = new Player();
        player.setName("Player1");

        Player player2 = new Player();
        player2.setName("Player2");

        Game game = gameService.createGame(player);
        game.setPlayerO(player2);
        game.setStatus(GameStatus.IN_PROGRESS);
        int[][] board = game.getBoard();
        board[0][0] = PlayerType.O.getValue();
        board[0][1] = PlayerType.O.getValue();

        String postData = "{\"gameId\": \""+ game.getId() +"\",\n" +
                "    \"type\": \"O\",\n" +
                "    \"posX\": \"0\",\n" +
                "    \"posY\": \"2\"}";

        mockMvc.perform(post("/game/play")
                .contentType("application/json")
                .content(postData))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerX.name").value(player.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerO.name").value(player2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.board").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.winner").value(PlayerType.O.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(GameStatus.FINISHED.toString()));
    }

}