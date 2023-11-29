package com.backend.chess_backend.socket;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Translator;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ChessHandler {

    private final GameManager gameManager;
    private final Translator translator;
    private final SocketIOServer server; // SocketIOServer instance

    @Autowired
    public ChessHandler(GameManager gameManager, Translator translator, SocketIOServer server) {
        this.gameManager = gameManager;
        this.translator = translator;
        this.server = server;

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
                    // Optionally, send a message to the client about not being able to join a new
                    // game.
                }
            }
        };
    }

    public void onChatMessage(SocketIOClient client, String message, AckRequest ackRequest) {
        log.info("Message: " + message + " From: " + client.getSessionId());
        // send message to global chat
        server.getBroadcastOperations().sendEvent("chat", message);

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData("Message has been receieved");
        }
    }

    // For starting a new game and reconnecting to a game
    public void getState(SocketIOClient client, AckRequest ackRequest) {
        // Return board state (FEN), player turn, and player color, time remaining, etc.
        JSONObject gameData = new JSONObject();

        String playerUuID = client.getSessionId().toString();
        Game game = gameManager.getGameByPlayerUuid(playerUuID);

        // {
        // "fen": "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR",
        // "turn": "w",
        // "playerColor": "w",
        // "gameStatus": "inProgress",
        // "gameCreatedAt": "2021-03-21T20:20:20.000Z",
        // "players":{
        // "w": {
        // "timeRemaining": 300,
        // },
        // "b": {
        // "timeRemaining": 300,
        // }
        // }
        // }

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData("Message has been receieved");
        }
    }

    public void moveListener(SocketIOClient client, String move, AckRequest ackRequest) {
        // Model do move and return board state
        String playerUuID = client.getSessionId().toString();
        JSONObject jsonObject = new JSONObject(move);
        String sourceSquare = jsonObject.getString("from");
        String targetSquare = jsonObject.getString("to");
        log.info("Move: " + sourceSquare + " to " + targetSquare + " From: " + client.getSessionId());

        ArrayList<ArrayList<Integer>> coordinates = translator.translatePos(sourceSquare, targetSquare);
        ArrayList<Integer> oldCord = coordinates.get(0);
        ArrayList<Integer> newCord = coordinates.get(1);

        log.info("Translated to " + coordinates);
        Game game = gameManager.getGameByPlayerUuid(playerUuID);

        Boolean hasMoved = game.attemptMove(oldCord.get(0), oldCord.get(1), newCord.get(0), newCord.get(1));

        if (hasMoved) {
            String fen = translator.translateBoard(game.getBoard());
            server.getRoomOperations(game.getId()).sendEvent("gameState", fen);
        }

    }

    public void newGamePostionListener(SocketIOClient client, String fen, AckRequest ackRequest) {
        log.info("Chess position: " + fen + " From: " + client.getSessionId());

        // GameManager gameManager = GameManager.getInstance();
        String roomID = gameManager.getGameIdByPlayerUuid(client.getSessionId().toString());
        log.info("Sending new game position to room: " + roomID);
        server.getRoomOperations(roomID).sendEvent("gameState", fen);

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