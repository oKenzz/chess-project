package com.backend.chess_backend.model.Chat;

import java.sql.Time;

public class Message {

    private String message;
    private String sentAt;
    private String sender;

    public Message(String message, String sendUuid) {
        this.sender = sendUuid;
        this.message = message;
        this.sentAt = new Time(System.currentTimeMillis()).toString().substring(0, 5);
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public String getSentAt() {
        return this.sentAt;
    }

}
