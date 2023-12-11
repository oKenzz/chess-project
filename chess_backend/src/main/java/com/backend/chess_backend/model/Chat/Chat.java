package com.backend.chess_backend.model.Chat;

import java.util.ArrayList;
import java.util.List;

import com.backend.chess_backend.model.Player;
import com.backend.chess_backend.model.ChessGames.SimpleChessGame;

public class Chat {

    private String roomId;
    private Player whitePlayer;
    private Player blackPlayer;
    private List<Message> messages = new ArrayList<Message>();

    public Chat(SimpleChessGame game) {
        this.roomId = game.getId();
        this.whitePlayer = game.getWhitePlayer();
        this.blackPlayer = game.getBlackPlayer();
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isPlayerInChat(Player player) {
        return player.equals(whitePlayer) || player.equals(blackPlayer);
    }

    public Message[] getMessages() {
        return messages.toArray(new Message[messages.size()]);
    }

    public Message[] postMessage(String message, Player player) {
        Message messageObject = new Message(message, player);
        messages.add(messageObject);
        return getMessages();
    }

}
