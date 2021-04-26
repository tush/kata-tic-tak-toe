package com.tush.kata.game.controller;

import com.tush.kata.game.controller.dto.ConnectionRequest;
import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.model.Player;
import com.tush.kata.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * PlayerController
 * The Controller class to create API's for Player Engine
 */
@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/add")
    public Player add(@RequestBody ConnectionRequest request) throws InvalidParamException {
        return playerService.addPlayer(request.getPlayer().getName());
    }

}
