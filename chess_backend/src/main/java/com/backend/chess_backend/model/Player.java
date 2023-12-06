package com.backend.chess_backend.model;

import com.backend.chess_backend.model.Pieces.PieceColor;

import io.netty.util.Timer;

public class Player {

    private Timer timer;
    private String uuid;
    private Boolean isBot;

    public Player(String clientUUID, Boolean isBot) {
        this.uuid = clientUUID;
        this.isBot = isBot;
    }

    public void toggleTimer() {

    }

    // public double getTime(){

    // }

    public String getUuid() {
        return uuid;
    }

    public Boolean isBot() {
        return isBot;
    }
}
