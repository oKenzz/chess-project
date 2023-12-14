package com.backend.chess_backend.model.ChessGames;

import com.backend.chess_backend.model.Chat.IMessage;

public interface IModifiedChessGame extends IChessGame {
    boolean isPrivate();
    IMessage[] getMessages();
    IMessage[] postMessage(String message, String playerUuid);
}
