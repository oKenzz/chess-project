package com.backend.chess_backend.socket;

import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.socket.GameManager;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Log4j2
@Component
public class ChessHandler {

    @Autowired
    private GameManager gameManager;

    // Listener for client connection events
    public ConnectListener onConnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();

            // Attempt to retrieve the first 'room' parameter
            String room = params.containsKey("room") ? params.get("room").get(0) : null;
            if (room != null && !room.isEmpty()) {
                // Check if the room exists
                gameManager.joinOrCreateGame(room, client.getSessionId().toString());
                client.joinRoom(room);
                log.info("Socket ID[{}] - room[{}] - Connected to chess game", client.getSessionId().toString(), room);
            } else {
                log.info("No room was found. Joining a random room.");
                String roomCode = gameManager.joinRandomGame(client.getSessionId().toString());
                client.joinRoom(roomCode);
                log.info("Socket ID[{}] - room[{}] - Connected to chess game", client.getSessionId().toString(), roomCode);
            }

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
        
        // GameManager gameManager = GameManager.getInstance();
        String roomID = gameManager.getGameIdByPlayerUuid(client.getSessionId().toString());
        log.info("Sending new game position to room: " + roomID);
        client.getNamespace().getRoomOperations(roomID).sendEvent("gameState", fen);

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
