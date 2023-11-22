package com.backend.chess_backend.socket;

import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Player;
import com.backend.chess_backend.repository.GameRepository;

import java.util.List;
import java.util.UUID;


public class GameManager {
    private static GameRepository gameRepository;

    public static Game createGame(String clientId){
        String newGameId = GameManager.generateID();
        Game newGame = new Game(newGameId);

        // Add player to game
        Player player = new Player(clientId);
        newGame.addPlayer( player.getUuid() );

        // Save the game to the database
        gameRepository.save(newGame);
        return newGame;
    }

    public static Game joinOrCreateGame(String gameId, String clientId) {
        Game hasJoined = join(gameId, clientId);
        if( hasJoined == null){
            return createGame(clientId);
        }
        return hasJoined;
    }

    public static Game join(String gameId, String ClientId){
        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            if (game.getId().equals(gameId) && game.isFull() == false) {
                game.addPlayer(ClientId);
                gameRepository.save(game);
                return game;
            }
        }

        return null;
    }

    public static String joinRandomGame(String clientId) {
        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            if (game.isFull() == false) {
                game.addPlayer(clientId);
                gameRepository.save(game);
                return game.getId();
            }
        }
        return createGame(clientId).getId();
    }

    public Game getGameByPlayerUuid(String playerUuid) {
        return gameRepository.findByUserId(playerUuid);
    }
    public static String getGameIdByPlayerUuid(String playerUuid) {
        return gameRepository.findByUserId(playerUuid).getId();
    }
    public static String generateID() {
        return UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

}
