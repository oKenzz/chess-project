package com.backend.chess_backend.socket;

import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Translator;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GameManager {
    private Map<String, Game> games; // Keyed by game ID
    private Map<String, String> playerGameMap; // Keyed by player UUID, value is game ID

    public GameManager() {
        games = new ConcurrentHashMap<>();
        playerGameMap = new ConcurrentHashMap<>();
    }

    public synchronized Game createGame(String clientId) {
        String newGameId = generateID();
        Game newGame = new Game(newGameId);
        newGame.addPlayer(clientId);
        games.put(newGameId, newGame);
        playerGameMap.put(clientId, newGameId);
        return newGame;
    }

    public synchronized Game join(String gameId, String clientId) {
        Game game = games.get(gameId);
        System.out.println(game);
        System.out.println(game != null);
        if (game != null) {
            game.addPlayer(clientId);
            playerGameMap.put(clientId, game.getId());
            return game;
        }
        ;
        return null;
    }

    public synchronized Game joinRandomGame(String clientId) {
        if (playerGameMap.containsKey(clientId)) {
            return null;
        }

        for (Game game : games.values()) {
            if (!game.isFull()) {
                game.addPlayer(clientId);
                playerGameMap.put(clientId, game.getId());
                return game;
            }
        }
        return createGame(clientId);
    }

    public synchronized boolean disconnect(String clientId) {
        String gameId = playerGameMap.get(clientId);
        if (gameId != null) {
            Game game = games.get(gameId);
            if (game != null) {
                game.removePlayer(clientId);
                playerGameMap.remove(clientId);
                return true;
            }
            playerGameMap.remove(clientId);
            return true;
        }
        return false;
    }

    public Game getGameByPlayerUuid(String playerUuid) {
        String gameId = playerGameMap.get(playerUuid);
        return gameId != null ? games.get(gameId) : null;
    }

    public String getGameIdByPlayerUuid(String playerUuid) {
        String gameId = playerGameMap.get(playerUuid);
        return gameId != null ? gameId : null;
    }

    public String generateID() {
        return UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    public void reset() {
        games.clear();
        playerGameMap.clear();
    }
}
