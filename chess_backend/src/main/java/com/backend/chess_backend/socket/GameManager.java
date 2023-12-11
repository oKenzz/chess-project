package com.backend.chess_backend.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.backend.chess_backend.model.ChessGames.ModifiedChessGame;
import com.backend.chess_backend.model.ChessGames.SimpleChessGame;
import com.backend.chess_backend.model.Player;

@Service
public class GameManager {

    private Map<String, ModifiedChessGame> games; // Keyed by game ID
    private Map<String, String> playerGameMap; // Keyed by player UUID, value is game ID

    public GameManager() {
        games = new ConcurrentHashMap<>();
        playerGameMap = new ConcurrentHashMap<>();
    }

    public synchronized ModifiedChessGame createGame(String clientId, boolean isPrivate) {
        if (!playerGameMap.containsKey(clientId)) {
            String newGameId = generateID();
            ModifiedChessGame newGame = new ModifiedChessGame(newGameId, isPrivate);
            newGame.addPlayer(clientId, false);
            games.put(newGameId, newGame);
            playerGameMap.put(clientId, newGameId);
            return newGame;
        }
        return this.getGameByPlayerUuid(clientId);
    }

    public synchronized ModifiedChessGame createSoloGame(String clientId) {
        String newGameId = generateID();
        ModifiedChessGame newGame = new ModifiedChessGame(newGameId, true);
        newGame.addPlayer(clientId, false);
        games.put(newGameId, newGame);
        playerGameMap.put(clientId, newGameId);

        // Bot player
        String botId = generateID();
        newGame.addPlayer(botId, true);
        playerGameMap.put(botId, newGameId);
        return newGame;
    }

    public synchronized boolean roomExist(String roomId) {
        return games.containsKey(roomId);
    }

    public synchronized ModifiedChessGame joinRoom(String gameId, String clientId) {
        if (playerGameMap.containsKey(clientId)) {
            return this.getGameByPlayerUuid(clientId);
        }
        ModifiedChessGame game = games.get(gameId);
        if (game != null && !game.isFull()) {
            game.addPlayer(clientId, false);
            playerGameMap.put(clientId, gameId);
            return game;
        }
        return null;
    }

    public synchronized ModifiedChessGame joinRandomGame(String clientId) {
        if (playerGameMap.containsKey(clientId)) {
            return this.getGameByPlayerUuid(clientId);
        }

        for (ModifiedChessGame game : games.values()) {
            if (!game.isFull() && !game.isPrivate()) {
                game.addPlayer(clientId, false);
                playerGameMap.put(clientId, game.getId());
                return game;
            }
        }
        return createGame(clientId, false);
    }

    public synchronized boolean disconnect(String clientId) {
        String gameId = playerGameMap.get(clientId);
        if (gameId != null) {
            ModifiedChessGame game = games.get(gameId);
            if (game != null) {
                game.removePlayer(clientId);
                playerGameMap.remove(clientId);
                removeGameIfEmpty(game);
                return true;
            }
            playerGameMap.remove(clientId);
            return true;
        }
        return false;
    }

    private synchronized boolean removeGameIfEmpty(SimpleChessGame game) {
        // if singile player game
        for (Player player : game.getPlayers()) {
            if (player.isBot()) {
                removeGame(game.getId());
                return true;
            }
        }
        if (game.isEmpty()) {
            removeGame(game.getId());
            return true;
        }
        return false;
    }

    public ModifiedChessGame getGameByPlayerUuid(String playerUuid) {
        String gameId = playerGameMap.get(playerUuid);
        return gameId != null ? games.get(gameId) : null;
    }

    public String getGameIdByPlayerUuid(String playerUuid) {
        String gameId = playerGameMap.get(playerUuid);
        return gameId != null ? gameId : null;
    }

    public synchronized Player getBotPlayer(String gameId) {
        ModifiedChessGame game = games.get(gameId);
        if (game != null) {
            Player[] players = game.getPlayers();
            for (Player player : players) {
                if (player.isBot()) {
                    return player;
                }
            }
        }
        return null;
    }

    public List<String> getGames() {
        List<String> games = new ArrayList<>(this.games.keySet());
        for (int i = 0; i < games.size(); i++) {
            ModifiedChessGame game = this.games.get(games.get(i));
            if (game.isPrivate()) {
                games.remove(i);
                i--; // Decrement i to account for the removed element
            }
        }
        return games;
    }

    public List<Map<String, Object>> getGamesInfo() {
        List<Map<String, Object>> allGamesInfo = new ArrayList<>();
        for (ModifiedChessGame game : games.values()) {
            Map<String, Object> gameInfo = new HashMap<>();
            gameInfo.put("gameId", game.getId());
            gameInfo.put("isPrivate", game.isPrivate());
            gameInfo.put("players", game.getPlayers());
            allGamesInfo.add(gameInfo);
        }
        return allGamesInfo;
    }

    public void kickPlayer(String uuid) {
        SimpleChessGame game = games.get(playerGameMap.get(uuid));
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

    public synchronized void removeGame(String gameId) {
        games.remove(gameId);
    }

    /**
     * The function removes inactive chess games that have been running for more
     * than 10 seconds.
     */
    public void removeInactiveGames() {
        for (ModifiedChessGame game : games.values()) {
            System.out.println(game.getGameTime());
            System.out.println(game.getGameTime() > 1800);
            if (game.getGameTime() > 1800) {
                removeGame(game.getId());
            }
        }
    }
}
