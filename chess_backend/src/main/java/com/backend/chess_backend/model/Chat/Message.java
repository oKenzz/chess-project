package com.backend.chess_backend.model.Chat;
public class Message implements IMessage {
    private String message;
    private String sentAt;
    private String sender;

    public Message(String message, String sendUuid) {
        this.sender = sendUuid;
        this.message = message;
        // Your existing implementation for setting sentAt
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public String getSentAt() {
        return sentAt;
    }
}
