package com.backend.chess_backend.model.ChessGames;

import com.backend.chess_backend.model.Chat.Chat;
import com.backend.chess_backend.model.Chat.IMessage;

public class ModifiedChessGame extends SimpleChessGame implements IModifiedChessGame  {
    private boolean isPrivate;
    private Chat chat;

    public ModifiedChessGame(String gameId, boolean isPrivate) {
        super(gameId);
        this.isPrivate = isPrivate;
        this.chat = new Chat();
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public IMessage[] getMessages() {
        return this.chat.getMessages();
    }

    public IMessage[] postMessage(String message, String playerUuid) {
        return  this.chat.postMessage(message, playerUuid);
    }
}