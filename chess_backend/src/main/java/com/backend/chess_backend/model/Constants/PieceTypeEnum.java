package com.backend.chess_backend.model.Constants;

public enum PieceTypeEnum {
    WHITE_PAWN("P"),
    BLACK_PAWN("p"),
    WHITE_KNIGHT("N"),
    BLACK_KNIGHT("n"),
    WHITE_BISHOP("B"),
    BLACK_BISHOP("b"),
    WHITE_ROOK("R"),
    BLACK_ROOK("r"),
    WHITE_QUEEN("Q"),
    BLACK_QUEEN("q"),
    WHITE_KING("K"),
    BLACK_KING("k"),
    EMPTY(" ");
    
    private final String message;

    PieceTypeEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}