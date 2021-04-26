package com.tush.kata.game.service;

import com.tush.kata.game.data.GameData;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Game;
import com.tush.kata.game.model.Player;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.tush.kata.game.model.enums.GameStatus.*;

/**
 * GameService
 * The class hold business logic for Game Engine
 */
@Component
public class GameService {

    public Game createGame(Player player) throws InvalidParamException {
        if (player == null || player.getName().isEmpty()) {
            throw new InvalidParamException(InvalidParamException.MESSAGE_INVALID_PLAYER);
        }
        Game game = new Game();
        game.setBoard(new int[3][3]);
        game.setId(UUID.randomUUID().toString());
        game.setPlayerX(player);
        game.setStatus(NEW);
        GameData.getInstance().setGame(game);
        return game;
    }
}
