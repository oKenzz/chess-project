package com.backend.chess_backend.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

@Log4j2
@Component
public class TestHandler {

    // Listener for client connection events
    public ConnectListener onConnected() {
        return client -> {
            log.info("Client connected: " + client.getSessionId());
            // Additional logic for when a client connects
        };
    }

    public void onChatMessage(SocketIOClient client, String message, AckRequest ackRequest){
        log.info("Message: " + message + " From: " + client.getSessionId());
        // send message to global chat
        client.getNamespace().getBroadcastOperations().sendEvent("chat", message);

        if (ackRequest.isAckRequested()){
            ackRequest.sendAckData("Message has been receieved");
        }
    }

    // Listener for client disconnection events
    public DisconnectListener onDisconnected() {
        return client -> {
            log.info("Client disconnected: " + client.getSessionId());
            // Additional logic for when a client disconnects
        };
    }
}
