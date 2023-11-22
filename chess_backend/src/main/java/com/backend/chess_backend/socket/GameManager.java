package com.backend.chess_backend.socket;

import com.backend.chess_backend.model.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class GameManager {
    private Map<String, Game> games; // Keyed by game ID
    private Map<String, String> playerGameMap; // Keyed by player UUID, value is game ID

    public GameManager() {
        games = new HashMap<>();
        playerGameMap = new HashMap<>();
    }

    private Game createGame(String clientId){
        String newGameId = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        Game newGame = new Game(newGameId);
        newGame.addPlayer(clientId);
        games.put(newGameId, newGame);
        playerGameMap.put(clientId, newGameId);
        return newGame;
    }

    // Remove use joinRandomGame instead
    public Game joinOrCreateGame(String gameId, String clientId) {
        Game hasJoined  = join(gameId,clientId);
        if( hasJoined == null){
            return createGame(clientId);
        }
        return hasJoined;
    }

    private Game join(String gameId, String clientId){
        Game game = games.get(gameId);
        if (game != null){
            game.addPlayer(clientId);
                playerGameMap.put(clientId, game.getId());
                return game;
        };
        return null;
    }

    public String joinRandomGame(String clientId) {
        for (Game game : games.values()) {
            if (game.isFull() == false) {
                game.addPlayer(clientId);
                playerGameMap.put(clientId, game.getId());
                return game.getId();
            }
        }
        return createGame(clientId).getId();
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

}
