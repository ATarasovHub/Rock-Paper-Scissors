package com.example.demo.controller;

import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/play")
    public Map<String, String> play(@RequestParam String choice) {
        return gameService.playGame(choice);
    }

}
