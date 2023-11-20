package com.backend.chess_backend.model;

import com.backend.chess_backend.model.Pieces.PieceColor;

import io.netty.util.Timer;

public class Player {
    
    private PieceColor teamColor;
    private Timer timer;
    private String uuid;

    public Player(PieceColor color, String clientUUID){
        this.uuid = clientUUID;
        this.teamColor = color;
    }

    public void toggleTimer(){

    }

    // public double getTime(){
        
    // }

    public PieceColor getColor(){
        return teamColor;
    }
    public String getUuid() {
        return uuid;
    }
}
