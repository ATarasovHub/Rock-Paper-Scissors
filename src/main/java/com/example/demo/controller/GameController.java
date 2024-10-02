package com.example.demo.controller;

import com.example.demo.model.GameHistory;
import com.example.demo.repository.GameHistoryRepository;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    @GetMapping("/play")
    public Map<String, String> play(@RequestParam String choice) {
        return gameService.playGame(choice);
    }

    @GetMapping("/history")
    public List<GameHistory> getHistory() {
        return gameHistoryRepository.findAll();
    }


}
