package com.backend.chess_backend.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;

import org.springframework.stereotype.Component;


@Log4j2
@Component
public class ChessHandler {
    

    // Listener for client connection events
    public ConnectListener onConnected() {
        return client -> {
            log.info("Client connected: " + client.getSessionId());
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

    public void moveListener(SocketIOClient client, String move, AckRequest ackRequest){
        log.info("Move: " + move + " From: " + client.getSessionId());

        //String movePossible backend.checkMove();
        // send random FEN string to client as test
        // client.getNamespace().getBroadcastOperations().sendEvent("gameState", move);
        String fen = "8/5k2/3p4/1p1Pp2p/pP2Pp1P/P4P1K/8/8 b - - 99 50";
        client.getNamespace().getBroadcastOperations().sendEvent("gameState", fen);

        if (ackRequest.isAckRequested()){
            ackRequest.sendAckData(true);
        }

    }

    public void newGamePostionListener(SocketIOClient client, String fen, AckRequest ackRequest){
        log.info("Chess position: " + fen + " From: " + client.getSessionId());
        client.getNamespace().getBroadcastOperations().sendEvent("gameState", fen);
        if (ackRequest.isAckRequested()){
            ackRequest.sendAckData(true);
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
