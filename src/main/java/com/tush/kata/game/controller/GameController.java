package com.tush.kata.game.controller;

import com.tush.kata.game.controller.dto.ConnectionRequest;
import com.tush.kata.game.exception.InvalidGameException;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Game;
import com.tush.kata.game.model.GameStep;
import com.tush.kata.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * GameController
 * The Controller class to create API's for Game Engine
 */
@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public Game start(@RequestBody ConnectionRequest request) throws InvalidParamException {
        return gameService.createGame(request.getPlayer());
    }

    @PostMapping("/connect")
    public Game connect(@RequestBody ConnectionRequest request) throws InvalidParamException, InvalidGameException {
        return gameService.connectToGame(request.getPlayer(), request.getGameId());
    }

    @PostMapping("/play")
    public Game play(@RequestBody GameStep request) throws InvalidGameException {
        return gameService.playGameStep(request);
    }

    @GetMapping("/games")
    public List<Game> games() {
        return new ArrayList<>(gameService.getGames().values());
    }

}
