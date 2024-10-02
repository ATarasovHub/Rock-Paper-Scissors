package com.example.demo.service;

import com.example.demo.model.GameHistory;
import com.example.demo.repository.GameHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GameService {

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    private static final String[] choices = {"rock", "paper", "scissors"};

    public Map<String, String> playGame(String playerChoice) {
        String computerChoice = getRandomChoice();
        String result = determineWinner(playerChoice, computerChoice);

        GameHistory gameHistory = new GameHistory(playerChoice, computerChoice, result, LocalDateTime.now());
        gameHistoryRepository.save(gameHistory);

        Map<String, String> response = new HashMap<>();
        response.put("playerChoice", playerChoice);
        response.put("computerChoice", computerChoice);
        response.put("result", result);
        return response;
    }

    String getRandomChoice() {
        Random random = new Random();
        return choices[random.nextInt(choices.length)];
    }

    private String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            return "draw";
        } else if (
                (playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                        (playerChoice.equals("scissors") && computerChoice.equals("paper")) ||
                        (playerChoice.equals("paper") && computerChoice.equals("rock"))) {
            return "win";
        } else {
            return "lose";
        }
    }
}
