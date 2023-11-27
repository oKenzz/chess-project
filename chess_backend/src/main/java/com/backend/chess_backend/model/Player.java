package com.backend.chess_backend.model;

import com.backend.chess_backend.model.Pieces.PieceColor;

import io.netty.util.Timer;

public class Player {
    
    private Timer timer;
    private String uuid;

    public Player(String clientUUID){
        this.uuid = clientUUID;
    }

    public void toggleTimer(){

    }

    // public double getTime(){
        
    // }

    public String getUuid() {
        return uuid;
    }
}
