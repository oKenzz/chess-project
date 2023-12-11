package com.backend.chess_backend.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.chess_backend.model.ChessGames.ModifiedChessGame;
import com.backend.chess_backend.model.ChessGames.SimpleChessGame;
import com.backend.chess_backend.model.Constants.GameOverEnum;
import com.backend.chess_backend.model.Translator;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.gson.Gson;

@Component
public class ChessHandler {

    private final GameManager gameManager;
    private final SocketIOServer server; // SocketIOServer instance

    @Autowired
    public ChessHandler(GameManager gameManager, SocketIOServer server) {
        this.gameManager = gameManager;
        this.server = server;
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

    public void getChatMessagesListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuid = client.getSessionId().toString();
        ModifiedChessGame game = gameManager.getGameByPlayerUuid(playerUuid);
        if (game == null) {
            return;
        }
        String response = new Gson().toJson(game.getChat().getMessages());
        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(response);
        }
    }

    public void onChatMessage(SocketIOClient client, String message, AckRequest ackRequest) {
        String playerUuid = client.getSessionId().toString();
        ModifiedChessGame game = gameManager.getGameByPlayerUuid(playerUuid);
        if (game == null) {
            return;
        }
        if (message.equals("surrender")) {
            playerSurrender(playerUuid);
            return;
        }
        if (message.length() > 100 || message.length() < 1) {
            return;
        }
        game.getChat().postMessage(message, game.getPlayer(playerUuid));
        String response = new Gson().toJson(game.getChat().getMessages());
        server.getRoomOperations(game.getId()).sendEvent("chatMessage", response);
        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData("Message has been receieved");
        }
    }

    public void getGameStateListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuid = client.getSessionId().toString();
        Optional<Map<String, Object>> gameState = getGameState(playerUuid);
        gameState.ifPresent(state -> {
            String json = new Gson().toJson(state);
            client.sendEvent("gameState", json);
        });
    }

    public Optional<Map<String, Object>> getGameState(String playerUuid) {
        return Optional.ofNullable(gameManager.getGameByPlayerUuid(playerUuid)).map(game -> {
            Map<String, Object> gameState = new HashMap<>();
            gameState.put("id", game.getId());
            gameState.put("gameCreatedAt", game.getGameStartedTime());
            gameState.put("fen", Translator.translateBoard(game.getBoard(), game.getTurn()));
            gameState.put("turn", game.getTurn());
            gameState.put("playerColor", game.getPlayerColor(playerUuid));
            gameState.put("players", game.getPlayers());

            return gameState;
        });

    }

    public void moveListener(SocketIOClient client, String move, AckRequest ackRequest) {
        JSONObject jsonObject = new JSONObject(move);
        if (!jsonObject.has("from") || !jsonObject.has("to")) {
            return; // Exit if either 'from' or 'to' values are not present
        }
        String from = jsonObject.getString("from");
        String to = jsonObject.getString("to");

        if (!from.matches("[a-hA-H][1-8]") || !to.matches("[a-hA-H][1-8]")) {
            return; // Exit if either 'from' or 'to' values are not valid chessboard coordinates
        }

        String playerUuid = client.getSessionId().toString();
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuid);

        ArrayList<ArrayList<Integer>> coordinates = Translator.translatePos(
                jsonObject.getString("from"), jsonObject.getString("to"));
        boolean hasMoved = game.attemptMove(
                coordinates.get(0).get(0), coordinates.get(0).get(1),
                coordinates.get(1).get(0), coordinates.get(1).get(1));

        updateGameState(game);

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(hasMoved);
        }
    }

    private void updateGameState(SimpleChessGame game) {
        String gameId = game.getId();
        server.getRoomOperations(gameId).sendEvent("boardState",
                Translator.translateBoard(game.getBoard(), game.getTurn()));
        server.getRoomOperations(gameId).sendEvent("syncTimers", game.getPlayerTimes());

        GameOverEnum gameOverStatus = game.checkGameOver();
        if (gameOverStatus != GameOverEnum.NOT_OVER && gameOverStatus != null) {
            server.getRoomOperations(gameId).sendEvent("gameOver", gameOverStatus.getMessage());
        }
    }

    public void possibleMoveListener(SocketIOClient client, String square, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuID);
        if (game == null) {
            return;
        }
        ArrayList<Integer> coords = Translator.translatePos(square);
        ArrayList<String> coordinates = Translator
                .translatePossibleMoves(game.possibleMoves(coords.get(0), coords.get(1)));

        if (ackRequest.isAckRequested()) {
            ackRequest.sendAckData(coordinates);
        }
    }

    public void restartBoardListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuID);
        game.restartGame();
        server.getRoomOperations(game.getId()).sendEvent("boardState",
                Translator.translateBoard(game.getBoard(), game.getTurn()));
    }

    public void undoMoveListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuID);
        if (game.attemptUndo(playerUuID)) {
            server.getRoomOperations(game.getId()).sendEvent("boardState",
                    Translator.translateBoard(game.getBoard(), game.getTurn()));
        }
    }

    public void surrenderListener(SocketIOClient client, Void data, AckRequest ackRequest) {
        String playerUuID = client.getSessionId().toString();
        playerSurrender(playerUuID);
    }

    private void playerSurrender(String playerUuID) {
        SimpleChessGame game = gameManager.getGameByPlayerUuid(playerUuID);
        if (game != null) {
            game.surrender(playerUuID);
            String playerColor = game.getPlayerColor(playerUuID);
            String winner = playerColor.equals("white") ? "b" : "w";
            String gameOverMsg = winner.equals("w") ? "White wins" : "Black wins";
            server.getRoomOperations(game.getId()).sendEvent("gameOver", gameOverMsg);
            gameManager.removeGame(game.getId());
        }
    }

}