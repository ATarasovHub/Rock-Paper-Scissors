package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "game_history")
public class GameHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "player_choice")
    private String playerChoice;

    @Column(name = "computer_choice")
    private String computerChoice;

    @Column(name = "result")
    private String result;

    @Column(name = "game_date")
    private LocalDateTime gameDate;

    public GameHistory() {
    }

    public GameHistory(String playerChoice, String computerChoice, String result, LocalDateTime gameDate) {
        this.playerChoice = playerChoice;
        this.computerChoice = computerChoice;
        this.result = result;
        this.gameDate = gameDate;
    }

}
