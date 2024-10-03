package com.example.demo.service;

import com.example.demo.model.GameHistory;
import com.example.demo.repository.GameHistoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GameService {

    @Autowired
    private GameHistoryRepository gameHistoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    private String getRandomChoice() {
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

    public Map<String, Object> calculateWinPercentage() {
        List<GameHistory> gameHistories = gameHistoryRepository.findAll();
        long totalGames = gameHistories.size();
        long wins = gameHistories.stream().filter(game -> game.getResult().equals("win")).count();
        long losses = gameHistories.stream().filter(game -> game.getResult().equals("lose")).count();
        long draws = gameHistories.stream().filter(game -> game.getResult().equals("draw")).count();

        double winPercentage = totalGames == 0 ? 0.0 : (double) wins / totalGames * 100;

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("totalGames", totalGames);
        statistics.put("wins", wins);
        statistics.put("losses", losses);
        statistics.put("draws", draws);
        statistics.put("winPercentage", winPercentage);

        return statistics;
    }
    public void generate100Matches() {
        for (int i = 0; i < 100; i++) {
            String playerChoice = getRandomChoice();
            String computerChoice = getRandomChoice();
            String result = determineWinner(playerChoice, computerChoice);


            GameHistory gameHistory = new GameHistory(playerChoice, computerChoice, result, LocalDateTime.now());
            gameHistoryRepository.save(gameHistory);
        }
    }

    @Transactional
    public void clearGameHistory() {
        gameHistoryRepository.deleteAll();

        entityManager.createNativeQuery("ALTER SEQUENCE game_history_id_seq RESTART WITH 1").executeUpdate();
    }
}
