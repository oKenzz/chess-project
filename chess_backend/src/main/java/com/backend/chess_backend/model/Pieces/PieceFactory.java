package com.backend.chess_backend.model.Pieces;

public class PieceFactory {

    public static Piece makePawn(PieceColor color, int x, int y){
        return new Pawn(color, x, y);
    }

    public static Piece makeRook(PieceColor color, int x, int y){
        return new Rook(color, x, y);
    }
    
    public static Piece makeKnight(PieceColor color, int x, int y){
        return new Knight(color, x, y);
    }

    public static Piece makeBishop(PieceColor color, int x, int y){
        return new Bishop(color, x, y);
    }

    public static Piece makeQueen(PieceColor color, int x, int y){
        return new Queen(color, x, y);
    }

    public static Piece makeKing(PieceColor color, int x, int y){
        return new King(color, x, y);
    }
}