package com.backend.chess_backend.model;

import com.backend.chess_backend.model.Pieces.Piece;

public class Square {
    private final int x;
    private final int y;
    private Piece piece;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
        this.piece = null; // Initially, no piece is on the square
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean containsPiece(Piece p) {
        
        return this.piece != null && this.piece.equals(p);
        
    }

    public boolean containsPiece(){
        if (this.piece != null){
            return true;
        }
        return false;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void removePiece(){
        this.piece = null;
    }

    @Override
    public String toString() {
        return "Square{" +
               "x=" + x +
               ", y=" + y +
               ", piece=" + (piece != null ? piece.toString() : "None") +
               '}';
    }
}
