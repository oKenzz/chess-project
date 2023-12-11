package com.backend.chess_backend.model.Constants;

public enum GameOverEnum {
    NOT_OVER(""),
    BLACK("Black wins"),
    WHITE("White wins"),
    DRAW("Draw");

    private final String message;

    GameOverEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
