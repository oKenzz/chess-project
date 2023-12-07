package com.backend.chess_backend.schedulingtasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.backend.chess_backend.socket.GameManager;

@Component
public class ScheduledTasks {

    private final GameManager gameManager;

    @Autowired
    public ScheduledTasks(GameManager GameManager) {
        this.gameManager = GameManager;
    }

    @Scheduled(fixedRate = 1000)
    public void removeInactiveGames() {
        System.out.println("Removing inactive games");
        gameManager.removeInactiveGames();
    }
}