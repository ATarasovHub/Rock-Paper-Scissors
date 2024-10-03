package com.example.demo.controller;

import com.example.demo.model.GameHistory;
import com.example.demo.repository.GameHistoryRepository;
import com.example.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/win-percentage")
    public ResponseEntity<Map<String, Object>> getWinPercentage() {
        Map<String, Object> statistics = gameService.calculateWinPercentage();
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/generate-100-matches")
    public ResponseEntity<String> generate100Matches() {
        gameService.generate100Matches();
        return ResponseEntity.ok("100 matches generated successfully!");
    }

    @DeleteMapping("/clear-history")
    public ResponseEntity<String> clearHistory() {
        gameService.clearGameHistory(); // Очищаем историю
        return ResponseEntity.ok("History cleared. Please refresh the page!"); // Возвращаем нужное сообщение
    }
}
