package com.example.demo.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GameService {

    private final String[] choices = {"rock", "paper", "scissors"};

    public Map<String, String> playGame(String playerChoice) {
        String computerChoice = getComputerChoice();
        String result = determineWinner(playerChoice, computerChoice);

        Map<String, String> response = new HashMap<>();
        response.put("playerChoice", playerChoice);
        response.put("computerChoice", computerChoice);
        response.put("result", result);

        return response;
    }

    public String getComputerChoice() {
        Random random = new Random();
        return choices[random.nextInt(choices.length)];
    }

    public String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            return "It's a tie!";
        } else if ((playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                (playerChoice.equals("paper") && computerChoice.equals("rock")) ||
                (playerChoice.equals("scissors") && computerChoice.equals("paper"))) {
            return "You win!";
        } else {
            return "Computer wins!";
        }
    }
}
