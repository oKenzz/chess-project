package com.backend.chess_backend.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
import com.google.gson.Gson;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ChessHandler {

    private final GameManager gameManager;
    private final SocketIOServer server; // SocketIOServer instance

    @Autowired
    public ChessHandler(GameManager gameManager, SocketIOServer server) {
        this.gameManager = gameManager;
        this.server = server;

    }

    // Listener for client connection events
    public ConnectListener onConnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            var params = client.getHandshakeData().getUrlParams();

            // if client already exists
            if (gameManager.getGameByPlayerUuid(sessionId) != null) {
                log.info("Client: " + sessionId + " is already in a game.");
                client.joinRoom(gameManager.getGameIdByPlayerUuid(sessionId));
                return;
            }

            // Attempt to retrieve the first 'room' parameter
            String room = params.containsKey("room") ? params.get("room").get(0) : null;
            if (room != null && !room.isEmpty() && !room.equals("null")) {
                switch (room) {
                    case "quickPlay":
                        room = quickPlay(client);
                        client.joinRoom(room);
                        log.info("QuickPlay | " + " Client:" + sessionId + " quick joined: " + room);
                        break;
                    case "create":
                        Game newGame = gameManager.createGame(sessionId, true);
                        room = newGame.getId();
                        client.joinRoom(room);
                        log.info("Create | " + " Client:" + sessionId + " created room: " + room);
                        break;

                    case "singlePlayer":
                        Game singlePlayerGame = gameManager.createSoloGame(sessionId);
                        room = singlePlayerGame.getId();
                        client.joinRoom(room);
                        log.info("SinglePlayer | " + " Client:" + sessionId + " created room: " + room);
                        break;
                    default:
                        if (room.length() != 4) {
                            log.error("Room code must be 4 characters long.");
                            return;
                        }
                        if (!room.matches("[a-zA-Z0-9]+")) {
                            log.info("Room code must be alphanumeric.");
                            return;
                        }
                        room = room.toUpperCase();
                        if (gameManager.roomExist(room)) {
                            Game joinedGame = gameManager.joinRoom(room, sessionId);
                            if (joinedGame != null) {
                                client.joinRoom(room);
                                log.info("Join | " + " Client:" + sessionId + " joined room: " + room);
                            } else {
                                log.info("Join | " + " Client:" + sessionId + " failed to join room: " + room);
                            }
                        } else {
                            log.info("Join | " + " Client:" + sessionId + " failed to join room: " + room);
                        }
                }
            } else {
                // Join a random room
                room = quickPlay(client);
            }
            alertPlayerJoined(sessionId, room);
        };
    }

    private void alertPlayerJoined(String sessionId, String roomCode) {
        Game game = gameManager.getGameByPlayerUuid(sessionId);
        if (game == null) {
            return;
        }
        for (SocketIOClient c : server.getRoomOperations(roomCode).getClients()) {
            if (!c.getSessionId().toString().equals(sessionId)) {
                c.sendEvent("playerJoined", sessionId);
            }
        }
    }

    private String quickPlay(SocketIOClient client) {
        String playerUuid = client.getSessionId().toString();
        Game joinedGame = gameManager.joinRandomGame(playerUuid);
        String room = joinedGame.getId();
        client.joinRoom(room);
        return room;
    }

    public void computerMoveListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        log.info("Computers turn");
        String playerUuid = client.getSessionId().toString();
        Game game = gameManager.getGameByPlayerUuid(playerUuid);
        if (game == null) {
            return;
        }
        game.makeRandomMove();
        server.getRoomOperations(game.getId()).sendEvent("boardState",
                Translator.translateBoard(game.getBoard(), game.getTurn()));
    }

    public void onChatMessage(SocketIOClient client, String message, AckRequest ackRequest) {
        log.info("Message: " + message + " From: " + client.getSessionId());
        // send message to global chat
        server.getBroadcastOperations().sendEvent("chat", message);

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData("Message has been rFeceieved");
        }
    }

    public void getGameStateListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuid = client.getSessionId().toString();
        Map<String, Object> gameState = getGameState(playerUuid);
        if (gameState == null) {
            return;
        }

        Gson gson = new Gson();
        String json = gson.toJson(gameState);
        client.sendEvent("gameState", json);
    }

    // Get Game State
    public Map<String, Object> getGameState(String playerUuid) {
        Game game = gameManager.getGameByPlayerUuid(playerUuid);
        if (game == null) {
            return null;
        }

        Map<String, Object> gameState = new HashMap<>();
        gameState.put("id", game.getId());
        gameState.put("gameCreatedAt", game.getGameStartedTime());
        gameState.put("fen", Translator.translateBoard(game.getBoard(), game.getTurn()));
        gameState.put("turn", game.getTurn());
        gameState.put("playerColor", game.getPlayerColor(playerUuid));
        gameState.put("players", game.getPlayers());

        return gameState;
    }

    public void moveListener(SocketIOClient client, String move, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        JSONObject jsonObject = new JSONObject(move);
        String sourceSquare = jsonObject.getString("from");
        String targetSquare = jsonObject.getString("to");
        log.info("Move: " + sourceSquare + " to " + targetSquare + " From: " + client.getSessionId());

        ArrayList<ArrayList<Integer>> coordinates = Translator.translatePos(sourceSquare, targetSquare);
        ArrayList<Integer> oldCord = coordinates.get(0);
        ArrayList<Integer> newCord = coordinates.get(1);
        log.info("Translated to " + coordinates);

        Game game = gameManager.getGameByPlayerUuid(playerUuID);
        Boolean hasMoved = game.attemptMove(oldCord.get(0), oldCord.get(1), newCord.get(0), newCord.get(1));

        Boolean inCheck = game.getIfCheck();
        if (inCheck) {
            log.info("Check");
        } else {
            log.info("Not Check");
        }

        log.info(game.checkGameOver());
        if (game.checkGameOver() != null) {
            String gameOverMsg = "Draw";
            if (game.checkGameOver().equals("w")) {
                gameOverMsg = "White wins";
            } else if (game.checkGameOver().equals("b")) {
                gameOverMsg = "Black wins";
            }
            server.getRoomOperations(game.getId()).sendEvent("gameOver", gameOverMsg);
        }
        if (hasMoved) {
            server.getRoomOperations(game.getId()).sendEvent("boardState",
                    Translator.translateBoard(game.getBoard(), game.getTurn()));
        }

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(hasMoved);

        }
    }

    public void possibleMoveListener(SocketIOClient client, String square, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        log.info("Square: " + square + " From: " + playerUuID);
        Game game = gameManager.getGameByPlayerUuid(playerUuID);
        ArrayList<Integer> coords = Translator.translatePos(square);
        log.info("Translated to " + coords);
        ArrayList<String> coordinates = Translator
                .translatePossibleMoves(game.possibleMoves(coords.get(0), coords.get(1)));

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(coordinates);
        }
    }

    public void restartGameListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        log.info("Restart Game From: " + playerUuID);
        Game game = gameManager.getGameByPlayerUuid(playerUuID);
        game.restartGame();
        server.getRoomOperations(game.getId()).sendEvent("boardState",
                Translator.translateBoard(game.getBoard(), game.getTurn()));
    }

    public void surrenderListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        playerSurrender(playerUuID);
    }

    private void playerSurrender(String playerUuID) {
        Game game = gameManager.getGameByPlayerUuid(playerUuID);
        if (game == null) {
            return;
        }
        game.surrender(playerUuID);
        log.info("Surrender From: " + playerUuID);
        String playerColor = game.getPlayerColor(playerUuID);
        String winner = playerColor.equals("white") ? "b" : "w";
        if (winner.equals("w")) {
            server.getRoomOperations(game.getId()).sendEvent("gameOver", "White wins");
        } else {
            server.getRoomOperations(game.getId()).sendEvent("gameOver", "Black wins");
        }
        gameManager.removeGame(game.getId());
    }

    // Listener for client disconnection events
    public DisconnectListener onDisconnected() {
        return client -> {
            UUID sessionId = client.getSessionId(); // getSessionId() returns a UUID
            String clientId = sessionId.toString(); // Convert UUID to String
            log.info("Client disconnected: " + clientId);

            // Retrieve the game ID associated with this player
            String gameId = gameManager.getGameIdByPlayerUuid(clientId);
            if (gameId != null) {
                // Broadcast to the room that the player has disconnected
                if (gameManager.disconnect(clientId)) {
                    log.info("Player disconnected from game: " + gameId);
                    server.getRoomOperations(gameId).sendEvent("playerDisconnected", clientId);

                } else {
                    log.info("Player disconnected from game: " + gameId
                            + " but was not removed from the game successfully.");
                }
            } else {
                log.info("Player: " + clientId + " disconnected from the server but was not in a game.");
            }

        };
    }
}