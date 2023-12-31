package com.backend.chess_backend.model.Pieces;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;

public abstract class Piece {
    private boolean dead;
    private PieceColor color;
    private int xCoord;
    private int yCoord;
    private int movesMade;

    protected Piece(PieceColor color, int xCoord, int yCoord) {
        this.color = color;
        this.dead = false;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.movesMade = 0;

    }

    public boolean isDead() {
        return dead;
    }

    public PieceColor getColor() {
        return color;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setMovesMade(int m) {
        this.movesMade = m;
    }

    public void IncrementMovesMade() {
        this.movesMade++;
    }

    public void DecrementMovesMade() {
        this.movesMade--;
    }

    public void DecrementMovesMade(int m) {
        for (int i = 0; i < m; i++) {
            this.movesMade--;
        }
    }

    // forcefully updates the coordinates, should only be called right before move
    // returns true
    public void updateCoords(int newX, int newY) {
        this.xCoord = newX;
        this.yCoord = newY;
    }

    abstract public PieceTypeEnum getPieceType();

    abstract public boolean[][] getPossibleMoves(int boardW, int boardH);

}
