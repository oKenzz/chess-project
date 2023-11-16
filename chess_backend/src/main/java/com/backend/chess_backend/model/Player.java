package com.backend.chess_backend.model;

import com.backend.chess_backend.model.Pieces.PieceColor;

import io.netty.util.Timer;

public class Player {
    
    private PieceColor teamColor;
    private Timer timer;

    public Player(PieceColor color){

        this.teamColor = color;
    }

    public void toggleTimer(){

    }

    // public double getTime(){
        
    // }

    public PieceColor getColor(){
        return teamColor;
    }
}
