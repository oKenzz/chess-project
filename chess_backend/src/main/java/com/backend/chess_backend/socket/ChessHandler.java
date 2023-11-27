package com.backend.chess_backend.socket;

import com.backend.chess_backend.model.Game;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class ChessHandler {

    private final GameManager gameManager;

    @Autowired
    public ChessHandler(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    // Listener for client connection events
    public ConnectListener onConnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            var params = client.getHandshakeData().getUrlParams();

            // Attempt to retrieve the first 'room' parameter
            String room = params.containsKey("room") ? params.get("room").get(0) : null;
            if (room != null && !room.isEmpty()) {
                // Check if the room exists
                gameManager.join(room, sessionId);
                client.joinRoom(room);
                log.info("Socket ID[{}] - room[{}] - Connected to chess game", sessionId, room);
            } else {
                log.info("No room was found. Attempting to join a random room.");
                Game joinedGame = gameManager.joinRandomGame(sessionId);
                if (joinedGame != null) {
                String roomCode = joinedGame.getId();
                client.joinRoom(roomCode);
                log.info("Socket ID[{}] - room[{}] - Connected to chess game", sessionId, roomCode);
                } else {
                log.info("Player is already in a game. Not joining a new game.");
                // Optionally, send a message to the client about not being able to join a new game.
                }
            }
        };
    }

    public void onChatMessage(SocketIOClient client, String message, AckRequest ackRequest) {
        log.info("Message: " + message + " From: " + client.getSessionId());
        // send message to global chat
        client.getNamespace().getBroadcastOperations().sendEvent("chat", message);

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData("Message has been receieved");
        }
    }

    public void moveListener(SocketIOClient client, String move, AckRequest ackRequest) {
        log.info("Move: " + move + " From: " + client.getSessionId());

        String fen = "8/5k2/3p4/1p1Pp2p/pP2Pp1P/P4P1K/8/8 b - - 99 50";
        client.getNamespace().getBroadcastOperations().sendEvent("gameState", fen);
        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(true);
        }

    }

    public void newGamePostionListener(SocketIOClient client, String fen, AckRequest ackRequest) {
        log.info("Chess position: " + fen + " From: " + client.getSessionId());

        // GameManager gameManager = GameManager.getInstance();
        String roomID = gameManager.getGameIdByPlayerUuid(client.getSessionId().toString());
        log.info("Sending new game position to room: " + roomID);
        client.getNamespace().getRoomOperations(roomID).sendEvent("gameState", fen);

        if (ackRequest.isAckRequested()) {
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
