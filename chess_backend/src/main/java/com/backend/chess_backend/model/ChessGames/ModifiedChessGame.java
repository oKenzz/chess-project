package com.backend.chess_backend.model.ChessGames;

import com.backend.chess_backend.model.Chat.Chat;

public class ModifiedChessGame extends SimpleChessGame {
    private boolean isPrivate;
    public Chat chat;

    public ModifiedChessGame(String gameId, boolean isPrivate) {
        super(gameId);
        this.isPrivate = isPrivate;
        this.chat = new Chat();
    }

    public boolean isPrivate() {
        return isPrivate;
    }
}