package com.backend.chess_backend.model.Chat;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    private List<Message> messages = new ArrayList<Message>();

    public Chat() {
    }

    public IMessage[] getMessages() {
        return messages.toArray(new Message[messages.size()]);
    }

    public IMessage[] postMessage(String message, String playerUuid) {
        Message messageObject = new Message(message, playerUuid);
        messages.add(messageObject);
        return getMessages();
    }
}
