package com.example.demo.controller;

import com.example.demo.model.GameHistory;
import com.example.demo.repository.GameHistoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class GameControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    @BeforeEach
    void setUp() {
        gameHistoryRepository.deleteAll(); // Очищаем базу данных перед каждым тестом
    }

    @Test
    void playTest() throws Exception {
        // Выполняем запрос к эндпоинту /play с параметром 'rock'
        mockMvc.perform(get("/play")
                        .param("choice", "rock")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.playerChoice").value("rock"));

        // Проверяем, что последняя запись в базе данных соответствует выбору 'rock'
        GameHistory lastGame = gameHistoryRepository.findAll().get(gameHistoryRepository.findAll().size() - 1);
        assertThat(lastGame.getPlayerChoice()).isEqualTo("rock");
    }
    @Test
    void testGetGameHistory() throws Exception {
        // Подготавливаем фиктивные данные
        GameHistory gameHistory = new GameHistory("rock", "scissors", "win", java.time.LocalDateTime.now());
        gameHistoryRepository.save(gameHistory);

        // Выполняем запрос к эндпоинту /history и проверяем результат
        mockMvc.perform(get("/history")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].playerChoice").value("rock"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].computerChoice").value("scissors"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].result").value("win"));
    }
}
