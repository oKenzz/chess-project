package com.backend.chess_backend.socket;

import com.backend.chess_backend.model.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class GameManager {
    private class ChessGame extends Game {
        private boolean isPrivate;

        public ChessGame(String gameId, boolean isPrivate) {
            super(gameId);
            this.isPrivate = isPrivate;
        }

        public boolean isPrivate() {
            return isPrivate;
        }
    }

    private Map<String, ChessGame> games; // Keyed by game ID
    private Map<String, String> playerGameMap; // Keyed by player UUID, value is game ID

    public GameManager() {
        games = new ConcurrentHashMap<>();
        playerGameMap = new ConcurrentHashMap<>();
    }

    public synchronized ChessGame createGame(String clientId, boolean isPrivate) {
        if (!playerGameMap.containsKey(clientId)) {
            String newGameId = generateID();
            ChessGame newGame = new ChessGame(newGameId, isPrivate);
            newGame.addPlayer(clientId);
            games.put(newGameId, newGame);
            playerGameMap.put(clientId, newGameId);
            return newGame;
        }
        return this.getGameByPlayerUuid(clientId);
    }

    public synchronized boolean roomExist(String roomId) {
        return games.containsKey(roomId);
    }

    public synchronized ChessGame joinRoom(String gameId, String clientId) {
        if (playerGameMap.containsKey(clientId)) {
            return this.getGameByPlayerUuid(clientId);
        }

        ChessGame game = games.get(gameId);
        if (game != null && !game.isFull()) {
            game.addPlayer(clientId);
            playerGameMap.put(clientId, gameId);
            return game;
        }
        return null;
    }

    public synchronized ChessGame joinRandomGame(String clientId) {
        if (playerGameMap.containsKey(clientId)) {
            return this.getGameByPlayerUuid(clientId);
        }

        for (ChessGame game : games.values()) {
            if (!game.isFull() && !game.isPrivate()) {
                game.addPlayer(clientId);
                playerGameMap.put(clientId, game.getId());
                return game;
            }
        }
        return createGame(clientId, false);
    }

    public synchronized boolean disconnect(String clientId) {
        String gameId = playerGameMap.get(clientId);
        if (gameId != null) {
            ChessGame game = games.get(gameId);
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

    public ChessGame getGameByPlayerUuid(String playerUuid) {
        String gameId = playerGameMap.get(playerUuid);
        return gameId != null ? games.get(gameId) : null;
    }

    public String getGameIdByPlayerUuid(String playerUuid) {
        String gameId = playerGameMap.get(playerUuid);
        return gameId != null ? gameId : null;
    }

    public List<String> getGames() {
        List<String> games = new ArrayList<>(this.games.keySet());
        for (int i = 0; i < games.size(); i++) {
            ChessGame game = this.games.get(games.get(i));
            if (game.isPrivate()) {
                games.remove(i);
                i--; // Decrement i to account for the removed element
            }
        }
        return games;
    }

    public List<Map<String, Object>> getGamesInfo() {
        List<Map<String, Object>> allGamesInfo = new ArrayList<>();
        for (ChessGame game : games.values()) {
            Map<String, Object> gameInfo = new HashMap<>();
            gameInfo.put("gameId", game.getId());
            gameInfo.put("isPrivate", game.isPrivate());
            gameInfo.put("players", game.getPlayers());
            allGamesInfo.add(gameInfo);
        }
        return allGamesInfo;
    }

    public void kickPlayer(String uuid) {
        Game game = games.get(playerGameMap.get(uuid));
        game.removePlayer(uuid);
        playerGameMap.remove(uuid);
    }

    public String generateID() {
        return UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    public void reset() {
        games.clear();
        playerGameMap.clear();
    }
}
