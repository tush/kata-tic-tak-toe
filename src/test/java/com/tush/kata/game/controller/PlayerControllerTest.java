package com.tush.kata.game.controller;

import com.tush.kata.game.exception.InvalidParamException;
import com.tush.kata.game.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlayerService playerService;

    @Test
    void addPlayerAPI_whenValidInput_thenReturnsOK() throws Exception {
        String postData = "{\"player\": { \"name\": \"Player1\" }}";
        mockMvc.perform(post("/player/add")
                .contentType("application/json")
                .content(postData))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Player1"));
    }

    @Test
    void addPlayerAPI_whenNotValidInput_thenReturnsError() throws Exception {
        String postData = "{\"player\": { \"name\": \"\" }}";
        mockMvc.perform(post("/player/add")
                .contentType("application/json")
                .content(postData))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidParamException))
                .andExpect(result -> assertEquals(InvalidParamException.MESSAGE_INVALID_PLAYER, result.getResolvedException().getMessage()));
    }
}