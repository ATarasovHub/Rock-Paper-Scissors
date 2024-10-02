package com.example.demo.service;

import com.example.demo.model.GameHistory;
import com.example.demo.repository.GameHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    @Mock
    private GameHistoryRepository gameHistoryRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void playGameTest() {
        // Задаем выбор компьютера
        String playerChoice = "rock";
        String computerChoice = "scissors";
        String expectedResult = "win";

        // Мокаем выбор компьютера с помощью частичного мока
        GameService gameServiceSpy = spy(gameService);
        doReturn(computerChoice).when(gameServiceSpy).getRandomChoice();

        // Выполняем игру
        Map<String, String> resultOfTheGame = gameServiceSpy.playGame(playerChoice);

        // Проверяем результаты игры
        assertEquals(expectedResult, resultOfTheGame.get("result"));
        assertEquals(computerChoice, resultOfTheGame.get("computerChoice"));
        assertEquals(playerChoice, resultOfTheGame.get("playerChoice"));

        // Проверяем, что сохранение игры в репозитории произошло
        verify(gameHistoryRepository, times(1)).save(any(GameHistory.class));
    }
    @Test
    void getRandomChoiceTest() {
        Set<String> resultOfTheGame = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String choice = gameService.getRandomChoice();
            resultOfTheGame.add(choice);
        }

        // Проверяем, что все варианты выбора доступны
        assertEquals(true, resultOfTheGame.contains("rock"));
        assertEquals(true, resultOfTheGame.contains("paper"));
        assertEquals(true, resultOfTheGame.contains("scissors"));
    }

    @Test
    void determineWinnerTest() {
        // Используем spy для создания шпионов на gameService
        GameService gameServiceSpy = spy(gameService);

        // Мы выигрываем
        doReturn("scissors").when(gameServiceSpy).getRandomChoice();
        Map<String, String> resultOfTheGame = gameServiceSpy.playGame("rock");
        assertEquals("win", resultOfTheGame.get("result")); // Обновлено на "win"
        assertEquals("scissors", resultOfTheGame.get("computerChoice"));
        assertEquals("rock", resultOfTheGame.get("playerChoice"));

        // Компьютер выигрывает
        doReturn("rock").when(gameServiceSpy).getRandomChoice();
        Map<String, String> resultOfTheGame2 = gameServiceSpy.playGame("scissors");
        assertEquals("lose", resultOfTheGame2.get("result")); // Обновлено на "lose"
        assertEquals("rock", resultOfTheGame2.get("computerChoice"));
        assertEquals("scissors", resultOfTheGame2.get("playerChoice"));

        // Ничья
        doReturn("scissors").when(gameServiceSpy).getRandomChoice();
        Map<String, String> resultOfTheGame3 = gameServiceSpy.playGame("scissors");
        assertEquals("draw", resultOfTheGame3.get("result")); // Обновлено на "draw"
        assertEquals("scissors", resultOfTheGame3.get("computerChoice"));
        assertEquals("scissors", resultOfTheGame3.get("playerChoice"));
    }
}
