package com.backend.chess_backend.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.chess_backend.model.ChessGames.SimpleChessGame;
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
                        // make green text but for room code make it blue
                        log.info("\u001B[32m" + "[CONNECTED] Quickplay, joined room (\u001B[34m" + room + "\u001B[0m"
                                + ")");
                        break;
                    case "create":
                        SimpleChessGame newGame = gameManager.createGame(sessionId, true);
                        room = newGame.getId();
                        client.joinRoom(room);
                        log.info("\u001B[32m" + "[CONNECTED]  Created room (\u001B[34m" + room + "\u001B[0m" + ")");
                        break;

                    case "singlePlayer":
                        SimpleChessGame singlePlayerGame = gameManager.createSoloGame(sessionId);
                        room = singlePlayerGame.getId();
                        client.joinRoom(room);
                        log.info("\u001B[32m" + "[CONNECTED]  SinglePlayer at (\u001B[34m" + room + "\u001B[0m" + ")");
                        break;
                    default:
                        if (room.length() != 4) {
                            return;
                        }
                        if (!room.matches("[a-zA-Z0-9]+")) {
                            return;
                        }
                        room = room.toUpperCase();
                        if (gameManager.roomExist(room)) {
                            SimpleChessGame joinedGame = gameManager.joinRoom(room, sessionId);
                            if (joinedGame != null) {
                                client.joinRoom(room);
                                log.info("\u001B[32m" + "[CONNECTED] Joined room (\u001B[34m" + room + "\u001B[0m"
                                        + ")");
                            } else {
                                log.error("\u001B[31m" + "Join |  Client:" + sessionId + " failed to join room: "
                                        + room + "\u001B[0m");
                            }
                        } else {
                            log.error("\u001B[31m" + "Join | " + " Client:" + sessionId + " failed to join room: "
                                    + room
                                    + "\u001B[0m");
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
        SimpleChessGame game = gameManager.getGameByPlayerUuid(sessionId);
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
        SimpleChessGame joinedGame = gameManager.joinRandomGame(playerUuid);
        String room = joinedGame.getId();
        client.joinRoom(room);
        return room;
    }

    public void computerMoveListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuid = client.getSessionId().toString();
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuid);
        if (game == null) {
            return;
        }
        game.makeRandomMove();
        server.getRoomOperations(game.getId()).sendEvent("boardState",
                Translator.translateBoard(game.getBoard(), game.getTurn()));
    }

    public void onChatMessage(SocketIOClient client, String message, AckRequest ackRequest) {
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
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuid);
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
        try {

            String playerUuID = client.getSessionId().toString();
            JSONObject jsonObject = new JSONObject(move);
            String sourceSquare = jsonObject.getString("from");
            String targetSquare = jsonObject.getString("to");

            ArrayList<ArrayList<Integer>> coordinates = Translator.translatePos(sourceSquare, targetSquare);
            ArrayList<Integer> oldCord = coordinates.get(0);
            ArrayList<Integer> newCord = coordinates.get(1);

            SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuID);
            Boolean hasMoved = game.attemptMove(oldCord.get(0), oldCord.get(1), newCord.get(0), newCord.get(1));

            if (game.checkGameOver() != null) {
                String gameOverMsg = "Chesshandler error";
                if (game.checkGameOver().equals("w")) {
                    gameOverMsg = "White wins";
                } else if (game.checkGameOver().equals("b")) {
                    gameOverMsg = "Black wins";
                } else if (game.checkGameOver().equals("d")) {
                    gameOverMsg = "Draw";
                }
                server.getRoomOperations(game.getId()).sendEvent("gameOver", gameOverMsg);
            }
            if (hasMoved) {
                server.getRoomOperations(game.getId()).sendEvent("boardState",
                        Translator.translateBoard(game.getBoard(), game.getTurn()));
            }

            // gameManager.syncGameTimer(game, playerUuID);
            server.getRoomOperations(game.getId()).sendEvent("syncTimers", game.getPlayerTimes());

            if (ackRequest.isAckRequested()) {
                ackRequest.sendAckData(hasMoved);

            }
        } catch (Exception e) {
            return;
        }
    }

    public void possibleMoveListener(SocketIOClient client, String square, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuID);
        ArrayList<Integer> coords = Translator.translatePos(square);
        ArrayList<String> coordinates = Translator
                .translatePossibleMoves(game.possibleMoves(coords.get(0), coords.get(1)));

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(coordinates);
        }
    }

    public void restartGameListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuID);
        game.restartGame();
        server.getRoomOperations(game.getId()).sendEvent("boardState",
                Translator.translateBoard(game.getBoard(), game.getTurn()));
    }

    public void surrenderListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        playerSurrender(playerUuID);
    }

    private void playerSurrender(String playerUuID) {
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuID);
        if (game == null) {
            return;
        }
        game.surrender(playerUuID);
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

            // Retrieve the game ID associated with this player
            String gameId = gameManager.getGameIdByPlayerUuid(clientId);
            if (gameId != null) {
                // Broadcast to the room that the player has disconnected
                if (gameManager.disconnect(clientId)) {
                    log.info(
                            "\u001B[31m" + "[DISCONNECTED] | Client: " +
                                    (clientId.length() > 5 ? clientId.substring(0, 5) : clientId) + "..."
                                    + " disconnected from game: " + gameId
                                    + "\u001B[0m");
                    server.getRoomOperations(gameId).sendEvent("playerDisconnected", clientId);

                } else {
                    log.error("Player disconnected from game: " + gameId
                            + " but was not removed from the game successfully.");
                }
            }

        };
    }
}