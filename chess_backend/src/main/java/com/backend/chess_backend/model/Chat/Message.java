package com.backend.chess_backend.model.Chat;

import java.sql.Time;

import com.backend.chess_backend.model.Player;

public class Message {

    private String message;
    private String sentAt;
    private Player sender;

    public Message(String message, Player sender) {
        this.sender = sender;
        this.message = message;
        this.sentAt = new Time(System.currentTimeMillis()).toString().substring(0, 5);
    }

    public String getMessage() {
        return message;
    }

    public Player getSender() {
        return sender;
    }

}
