package com.backend.chess_backend.repository;

import com.backend.chess_backend.model.Player;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
public class Game {

    @Id
    @Column(unique = true) 
    private String gameId;

    private Player player1;
    private Player player2;

    private Long turnsMade;

}

