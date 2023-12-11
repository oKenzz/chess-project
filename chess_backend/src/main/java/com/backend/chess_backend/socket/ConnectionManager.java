package com.backend.chess_backend.socket;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.backend.chess_backend.model.ChessGames.SimpleChessGame;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ConnectionManager {

    private final GameManager gameManager;
    private final SocketIOServer server;

    @Autowired
    public ConnectionManager(GameManager gameManager, SocketIOServer server) {
        this.gameManager = gameManager;
        this.server = server;
    }

    // Listener for client connection events
    public ConnectListener onConnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            var params = client.getHandshakeData().getUrlParams();

            // Handle existing client or new/specific room connection
            if (!handleExistingClient(client, sessionId)) {
                handleNewOrSpecificRoomConnection(client, sessionId, params);
            }
        };
    }

    private boolean handleExistingClient(SocketIOClient client, String sessionId) {
        if (gameManager.getGameByPlayerUuid(sessionId) != null) {
            client.joinRoom(gameManager.getGameIdByPlayerUuid(sessionId));
            return true;
        }
        return false;
    }

    private void handleNewOrSpecificRoomConnection(SocketIOClient client, String sessionId,
            Map<String, List<String>> params) {
        String room = params.containsKey("room") ? params.get("room").get(0) : null;
        if (room != null && !room.isEmpty() && !"null".equals(room)) {
            room = handleRoomJoining(client, sessionId, room);
        } else {
            room = handleNewConnection(client, sessionId);
        }
        alertPlayerJoined(sessionId, room);
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

    private String handleNewConnection(SocketIOClient client, String sessionId) {
        SimpleChessGame game = gameManager.createGame(sessionId, false);
        String room = game.getId();
        client.joinRoom(room);
        log.info("[CONNECTED] New connection, created room (" + room + ")");
        return room;
    }

    private String handleRoomJoining(SocketIOClient client, String sessionId, String room) {
        switch (room) {
            case "quickPlay":
                SimpleChessGame joinedGame = gameManager.joinRandomGame(sessionId);
                room = joinedGame.getId();
                log.info("[CONNECTED] Quickplay, joined room (" + room + ")");
                client.joinRoom(room);
                break;
            case "create":
                SimpleChessGame newGame = gameManager.createGame(sessionId, true);
                room = newGame.getId();
                log.info("[CONNECTED] Created room (" + room + ")");
                client.joinRoom(room);
                break;
            case "singlePlayer":
                SimpleChessGame singlePlayerGame = gameManager.createSoloGame(sessionId);
                room = singlePlayerGame.getId();
                log.info("[CONNECTED] SinglePlayer at (" + room + ")");
                client.joinRoom(room);
                break;
            default:
                handleCustomRoomJoining(client, sessionId, room);
                break;
        }
        return room;
    }

    private void handleCustomRoomJoining(SocketIOClient client, String sessionId, String room) {
        if (room.length() != 4 || !room.matches("[a-zA-Z0-9]+")) {
            log.error("Invalid room code: " + room);
            return;
        }
        room = room.toUpperCase();
        if (gameManager.roomExist(room)) {
            SimpleChessGame joinedGame = gameManager.joinRoom(room, sessionId);
            if (joinedGame != null) {
                client.joinRoom(room);
                log.info("[CONNECTED] Joined room (" + room + ")");
            } else {
                log.error("Join | Client:" + sessionId + " failed to join room: " + room);
            }
        } else {
            log.error("Join | Client:" + sessionId + " failed to join room: " + room);
        }
    }

    // Listener for client disconnection events
    public DisconnectListener onDisconnected() {
        return client -> handleClientDisconnection(client.getSessionId().toString());
    }

    private void handleClientDisconnection(String clientId) {
        String gameId = gameManager.getGameIdByPlayerUuid(clientId);
        if (gameId != null) {
            if (gameManager.disconnect(clientId)) {
                log.info(
                        "[DISCONNECTED] | Client: " + shortenClientId(clientId) + " disconnected from game: " + gameId);
                server.getRoomOperations(gameId).sendEvent("playerDisconnected", clientId);
            } else {
                log.error("Player disconnected from game: " + gameId
                        + " but was not removed from the game successfully.");
            }
        }
    }

    private String shortenClientId(String clientId) {
        return clientId.length() > 5 ? clientId.substring(0, 5) : clientId;
    }
}
