package com.backend.chess_backend.model.Pieces;


public abstract class Piece {
    public boolean dead;
    public PieceColor color;
    public int xCoord;
    public int yCoord;
    public int movesMade;

    protected Piece (PieceColor color, int xCoord, int yCoord){
        this.color = color;
        this.dead = false;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.movesMade = 0;
        
    }

    public boolean isDead(){
        return dead;
    }

    public PieceColor getColor(){
        return color;
    }

    public int getX(){
        return xCoord;
    }

    public int getY(){
        return yCoord;
    }

    public int getMovesMade(){
        return movesMade;
    }

    public void IncrementMovesMade(){
        this.movesMade++;
    }

    //forcefully updates the coordinates, should only be called right before move returns true
    public void updateCoords(int newX, int newY){
        this.xCoord = newX;
        this.yCoord = newY;
    }

    
    abstract public String getPieceType();
    abstract public Boolean[][] getPossibleMoves(int boardW, int boardH);

}
