package com.backend.chess_backend.model.ChessGames;

import com.backend.chess_backend.model.Chat.Chat;

public class ModifiedChessGame extends SimpleChessGame {
    private boolean isPrivate;
    private Chat chat;

    public ModifiedChessGame(String gameId, boolean isPrivate) {
        super(gameId);
        this.isPrivate = isPrivate;
        this.chat = new Chat(this);
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public Chat getChat() {
        return chat;
    }
}