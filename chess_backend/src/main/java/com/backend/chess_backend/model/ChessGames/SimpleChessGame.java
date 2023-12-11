package com.backend.chess_backend.model.ChessGames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.CastlingMoveHandler;
import com.backend.chess_backend.model.MoveHandlers.CheckGameState;
import com.backend.chess_backend.model.MoveHandlers.MoveValidator;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Player;

public class SimpleChessGame {

    private Board board;
    private Player playerWhite;
    private Player playerBlack;
    private int turnsMade;
    private String gameId;
    private long gameStartedTime;
    private String gameOver;

    public SimpleChessGame(String gameid) {
        this.gameId = gameid;
        this.turnsMade = 0;
        this.board = new Board(8, 8);
        this.playerWhite = null;
        this.playerBlack = null;
        this.gameStartedTime = System.currentTimeMillis() / 1000L;
        this.gameOver = null;
    }

    public Boolean[][] possibleMoves(int x, int y) {
        Piece[][] currentBoard = board.getBoard();
        Piece currentPiece = currentBoard[x][y];
        int boardW = currentBoard.length;
        int boardH = currentBoard[0].length;
        Boolean[][] emptyList = new Boolean[boardW][boardH];

        for (int i = 0; i < boardW; i++) {
            for (int j = 0; j < boardH; j++) {
                emptyList[i][j] = false;
            }
        }

        if ((attemptToMoveWhite(x, y) && isWhitesTurn()) || (attemptToMoveBlack(x, y) && isBlacksTurn())) {
            return MoveValidator.getPossibleMoves(currentPiece, board);
        }

        return emptyList;

    }

    public Boolean attemptMove(int x, int y, int newX, int newY) {

        Piece[][] currentBoard = board.getBoard();
        Boolean[][] possibleM = possibleMoves(x, y);

        if ((attemptToMoveWhite(x, y) && isWhitesTurn()) || attemptToMoveBlack(x, y) && isBlacksTurn()) {
            if (board.isMoveAllowed(newX, newY, possibleM)) {

                if (MoveValidator.isCastleMove(currentBoard[x][y], newX, newY, board)) {
                    CastlingMoveHandler.makeCastleMove(x, y, newX, newY, board);
                }
                board.move(currentBoard[x][y], newX, newY);
                IncrementTurn();
                if (isWhitesTurn()) {
                    updateGameOverWhite();
                } else {
                    updateGameOverBlack();
                }
                return true;
            }
        }

        return false;
    }

    public int[][] getRandomMove(PieceColor color) {

        List<Piece> pieces = board.getAllPlayerPieces(color);

        Map<String, ArrayList<Integer>> moves = MoveValidator.getAllPossiblePlayerMoves(pieces, board);
        // Get random move
        if (moves.size() > 0) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(moves.size());
            String from = (String) moves.keySet().toArray()[randomIndex];
            ArrayList<Integer> to = moves.get(from);
            int[] fromCoords = new int[2];
            int[] toCoords = new int[2];
            fromCoords[0] = Integer.parseInt(from.split(",")[0]);
            fromCoords[1] = Integer.parseInt(from.split(",")[1]);
            toCoords[0] = to.get(0);
            toCoords[1] = to.get(1);
            return new int[][] { fromCoords, toCoords };
        }
        return null; // No valid moves available
    }

    private void updateGameOverBlack() {
        if (CheckGameState.blackCheckmated(board)) {
            gameOver = "w";
        } else if (CheckGameState.blackStalemated(board)) {
            gameOver = "d";
        } else {
            gameOver = null;
        }
    }

    private void updateGameOverWhite() {
        if (CheckGameState.whiteCheckmated(board)) {
            gameOver = "b";
        } else if (CheckGameState.whiteStalemated(board)) {
            gameOver = "d";
        } else {
            gameOver = null;
        }
    }

    private Boolean attemptToMoveWhite(int x, int y) {
        Piece[][] currentBoard = board.getBoard();
        if (currentBoard[x][y] != null && currentBoard[x][y].getColor() == PieceColor.WHITE) {
            return true;
        }

        return false;
    }

    private Boolean attemptToMoveBlack(int x, int y) {
        Piece[][] currentBoard = board.getBoard();
        if (currentBoard[x][y] != null && currentBoard[x][y].getColor() == PieceColor.BLACK) {
            return true;
        }

        return false;
    }

    public void restartGame() {
        this.board = new Board();
        this.turnsMade = 0;
        this.gameStartedTime = System.currentTimeMillis() / 1000L;
    }

    private Boolean isWhitesTurn() {
        if (turnsMade % 2 == 0) {
            return true;
        }
        return false;
    }

    private Boolean isBlacksTurn() {
        if (turnsMade % 2 != 0) {
            return true;
        }
        return false;
    }

    private void IncrementTurn() {
        turnsMade++;
    }

    public String getTurn() {
        if (isWhitesTurn()) {
            return "w";
        } else {
            return "b";
        }
    }

    public Piece[][] getBoard() {
        return board.getBoard();
    }

    public long getGameStartedTime() {
        return gameStartedTime;
    }

    private void subtractTurn() {
        turnsMade--;
    }

    public void surrender(String clientId) {
        String playerColor = getPlayerColor(clientId);
        if (playerColor != null) {
            if (playerColor.equals("white")) {
                gameOver = "b";
            } else {
                gameOver = "w";
            }
        }
    }

    public void addPlayer(String clinetId, Boolean isBot) {
        Player player = new Player(clinetId, isBot);
        if (playerWhite == null) {
            playerWhite = player;
        } else if (playerBlack == null) {
            playerBlack = player;
        }
    }

    public boolean isEmpty() {
        return playerWhite == null && playerBlack == null;
    }

    public void removePlayer(String clientId) {
        if (playerWhite != null && playerWhite.getUuid().equals(clientId)) {
            playerWhite = null;
        } else if (playerBlack != null && playerBlack.getUuid().equals(clientId)) {
            playerBlack = null;
        }
    }

    public String getPlayerColor(String clinetId) {
        if (playerWhite.getUuid().equals(clinetId)) {
            return "white";
        } else if (playerBlack.getUuid().equals(clinetId)) {
            return "black";
        }
        return null;
    }

    public Player getPlayer(String clinetId) {
        if (playerWhite.getUuid().equals(clinetId)) {
            return playerWhite;
        } else if (playerBlack.getUuid().equals(clinetId)) {
            return playerBlack;
        }
        return null;
    }

    public Player[] getPlayers() {
        Player[] players = { playerWhite, playerBlack };
        return players;
    }

    public Boolean getCheck() {
        return CheckGameState.checked(board);
    }

    public boolean isFull() {
        return playerWhite != null && playerBlack != null;
    }

    public String getId() {
        return gameId;
    }

    public String checkGameOver() {
        return gameOver;
    }

    public long getGameTime() {
        return System.currentTimeMillis() / 1000L - gameStartedTime;
    }

    public void makeRandomMove() {
        PieceColor currentColor = isWhitesTurn() ? PieceColor.WHITE : PieceColor.BLACK;
        int[][] move = getRandomMove(currentColor);
        if (move != null) {
            attemptMove(move[0][0], move[0][1], move[1][0], move[1][1]);
        }
    }
}