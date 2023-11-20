package com.backend.chess_backend.socket;

import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameManager {
    private static GameManager instance;
    private Map<String, Game> games; // Keyed by game ID
    private Map<String, String> playerGameMap; // Keyed by player UUID, value is game ID

    private GameManager() {
        games = new HashMap<>();
        playerGameMap = new HashMap<>();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private Game createGame(Player player){
        String newGameId = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        Game newGame = new Game(newGameId);
        newGame.addPlayer(player);
        games.put(newGameId, newGame);
        playerGameMap.put(player.getUuid(), newGameId);
        return newGame;
    }

    public Game joinOrCreateGame(Player player) {
        Game hasJoined  = join(player);
        if( hasJoined == null){
            return createGame(player);
        }
        return hasJoined;
    }

    private Game join(Player player){
        for (Game game : games.values()){
            if (!game.isFull()) {
                game.addPlayer(player);
                playerGameMap.put(player.getUuid(), game.getId());
                return game;
            }
        }
        return null;
    }


    public Game getGameByPlayerUuid(String playerUuid) {
        String gameId = playerGameMap.get(playerUuid);
        return gameId != null ? games.get(gameId) : null;
    }

    public static void reset() {
        instance = null;
    }

}
