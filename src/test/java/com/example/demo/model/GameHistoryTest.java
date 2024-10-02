package com.example.demo.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameHistoryTest {

    @Test
    void testGameHistoryConstructorAndGetters() {
        String playerChoice = "rock";
        String computerChoice = "scissors";
        String result = "You win!";
        LocalDateTime gameDate = LocalDateTime.now();

        GameHistory gameHistory = new GameHistory(playerChoice, computerChoice, result, gameDate);

        assertEquals(playerChoice, gameHistory.getPlayerChoice());
        assertEquals(computerChoice, gameHistory.getComputerChoice());
        assertEquals(result, gameHistory.getResult());
        assertEquals(gameDate, gameHistory.getGameDate());
    }

    @Test
    void testGameHistorySetters() {
        GameHistory gameHistory = new GameHistory();

        String playerChoice = "paper";
        String computerChoice = "rock";
        String result = "You win!";
        LocalDateTime gameDate = LocalDateTime.now();

        gameHistory.setPlayerChoice(playerChoice);
        gameHistory.setComputerChoice(computerChoice);
        gameHistory.setResult(result);
        gameHistory.setGameDate(gameDate);

        assertEquals(playerChoice, gameHistory.getPlayerChoice());
        assertEquals(computerChoice, gameHistory.getComputerChoice());
        assertEquals(result, gameHistory.getResult());
        assertEquals(gameDate, gameHistory.getGameDate());
    }
}
