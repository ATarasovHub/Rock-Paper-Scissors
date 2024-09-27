package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    @Test
    void playGameTest() throws Exception {

        // Создаем частичный мок GameService
        GameService gameService = spy(new GameService());

        // Когда будет вызван метод getComputerChoice(),
        // верни "scissors" и не выполняй реальный код этого метода»."
        doReturn("scissors").when(gameService).getComputerChoice();

        // Вызываем playGame с выбором игрока "paper"
        Map<String, String> resultOfTheGame = gameService.playGame("rock");

        assertEquals("You win!", resultOfTheGame.get("result"));
        assertEquals("scissors", resultOfTheGame.get("computerChoice"));
        assertEquals("rock", resultOfTheGame.get("playerChoice"));
    }

    @Test
    void getComputerChoiceTest() {
        GameService gameService = new GameService();

        Set<String> resultOfTheGame = new HashSet<String>();
        for(int i = 0; i < 100; i++){
            String choice = gameService.getComputerChoice();
            resultOfTheGame.add(choice);
        }

        assertEquals(resultOfTheGame.contains("rock"),true);
        assertEquals(resultOfTheGame.contains("paper"),true);
        assertEquals(resultOfTheGame.contains("scissors"),true);
    }

    @Test
    void determineWinnerTest(){
        GameService gameService = spy(new GameService());

        //we win
        doReturn("scissors").when(gameService).getComputerChoice();
        Map<String, String> resultOfTheGame = gameService.playGame("rock");
        assertEquals("You win!", resultOfTheGame.get("result"));
        assertEquals("scissors", resultOfTheGame.get("computerChoice"));
        assertEquals("rock", resultOfTheGame.get("playerChoice"));

        //computer win
        doReturn("rock").when(gameService).getComputerChoice();
        Map<String, String> resultOfTheGame2 = gameService.playGame("scissors");
        assertEquals("Computer wins!", resultOfTheGame2.get("result"));
        assertEquals("rock", resultOfTheGame2.get("computerChoice"));
        assertEquals("scissors", resultOfTheGame2.get("playerChoice"));

        //tie
        doReturn("scissors").when(gameService).getComputerChoice();
        Map<String, String> resultOfTheGame3 = gameService.playGame("scissors");
        assertEquals("It's a tie!", resultOfTheGame3.get("result"));
        assertEquals("scissors", resultOfTheGame3.get("computerChoice"));
        assertEquals("scissors", resultOfTheGame3.get("playerChoice"));
    }
}

