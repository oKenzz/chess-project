package com.backend.chess_backend.model.ChessGames;

import com.backend.chess_backend.model.Constants.GameOverEnum;
import com.backend.chess_backend.model.Player;
import com.backend.chess_backend.model.Square;

public interface IChessGame {

    boolean[][] possibleMoves(int x, int y);

    boolean attemptMove(int x, int y, int newX, int newY);

    void restartGame();

    String getTurn();

    Square[][] getBoard();

    long getGameStartedTime();

    void surrender(String clientId);

    String getPlayerColor(String clientId);

    boolean attemptUndo(String clientId);

    boolean isEmpty();

    void addPlayer(String clientId, boolean isBot);

    void removePlayer(String clientId);

    Player getPlayer(String clientId);

    Player[] getPlayers();

    boolean getCheck();

    boolean getWhiteCheck();

    boolean getBlackCheck();

    boolean isFull();

    String getId();

    GameOverEnum checkGameOver();

    long getGameTime();

    void makeRandomMove();

    int[] getPlayerTimes();

    Player getWhitePlayer();

    Player getBlackPlayer();

    boolean hasBotPlayer();
}
