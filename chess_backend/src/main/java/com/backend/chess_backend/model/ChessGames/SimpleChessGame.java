package com.backend.chess_backend.model.ChessGames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.CastlingMoveHandler;
import com.backend.chess_backend.model.MoveHandlers.CheckGameState;
import com.backend.chess_backend.model.MoveHandlers.EnPassantMoveHandler;
import com.backend.chess_backend.model.MoveHandlers.EnpessantMoveHandler;
import com.backend.chess_backend.model.MoveHandlers.MoveValidator;
import com.backend.chess_backend.model.MoveHandlers.PromotionMoveHandler;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Player;
import com.backend.chess_backend.model.Constants.GameOverEnum;

public class SimpleChessGame {
    private Board board;
    private Player playerWhite;
    private Player playerBlack;
    private int turnsMade;
    private String gameId;
    private long gameStartedTime;
    private GameOverEnum gameOver;
    private Integer defaultChessTime = 600;

    public SimpleChessGame(String gameid) {
        this.gameId = gameid;
        this.turnsMade = 0;
        this.board = new Board(8, 8);
        this.playerWhite = new Player(false, defaultChessTime);
        this.playerBlack = new Player(false, defaultChessTime);
        this.gameStartedTime = System.currentTimeMillis() / 1000L;
        this.gameOver = GameOverEnum.NOT_OVER;
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
                    board.move(currentBoard[x][y], newX, newY);
                } else if (PromotionMoveHandler.isPromotionMove(currentBoard[x][y], newX, newY)) {
                    board.move(currentBoard[x][y], newX, newY);
                    PromotionMoveHandler.promoteToQueen(currentBoard[newX][newY], newX, newY, board);
                } else if (EnpessantMoveHandler.isEnpessantMove(currentBoard[x][y], newX, newY, board)) {
                    board.move(currentBoard[x][y], newX, newY);
                    EnpessantMoveHandler.makeEnpessantMove(currentBoard[newX][newY].getColor(), newX, newY, board);
                } else if (MoveValidator.isEnPassantMove(currentBoard[x][y], newX, newY, board)) {
                    EnPassantMoveHandler.makeEnPassantMove(currentBoard[x][y], newX, newY, board);
                }
                board.move(currentBoard[x][y], newX, newY);
                toggleTimer();
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
            gameOver = GameOverEnum.BLACK;
        } else if (CheckGameState.blackStalemated(board)) {
            gameOver = GameOverEnum.DRAW;
        } else {
            gameOver = null;
        }
    }

    private void updateGameOverWhite() {
        if (CheckGameState.whiteCheckmated(board)) {
            gameOver = GameOverEnum.WHITE;
        } else if (CheckGameState.whiteStalemated(board)) {
            gameOver = GameOverEnum.DRAW;
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
                gameOver = GameOverEnum.BLACK;
            } else {
                gameOver = GameOverEnum.WHITE;
            }
        }
    }

    public void addPlayer(String clinetId, Boolean isBot) {

        if (!playerWhite.isOccupied()) {
            playerWhite.setPlayerID(clinetId);
        } else if (!playerBlack.isOccupied()) {
            playerBlack.setPlayerID(clinetId);
        }
        if (isBot) {
            playerBlack.setBot(isBot);
        }
        ;
        if (isFull()) {
            playerWhite.startTimer();
        }
    }

    public boolean isEmpty() {
        return !playerWhite.isOccupied() && !playerBlack.isOccupied();
    }

    public void removePlayer(String clientId) {
        if (playerWhite.getUuid().equals(clientId)) {
            playerWhite.emptyPlayer();
        } else if (playerBlack.getUuid().equals(clientId)) {
            playerBlack.emptyPlayer();
        }
    }

    public String getPlayerColor(String clinetId) {
        if (playerWhite.isOccupied() && playerWhite.getUuid().equals(clinetId)) {
            return "white";
        } else if (playerBlack.isOccupied() && playerBlack.getUuid().equals(clinetId)) {
            return "black";
        }
        return null;
    }

    public Boolean attemptUndo(String clientId) {
        if (playerWhite.isOccupied() && playerWhite.getUuid().equals(clientId)) {
            board.undoMove();
            subtractTurn();
            return true;
        } else if (playerBlack.isOccupied() && playerBlack.getUuid().equals(clientId)) {
            board.undoMove();
            subtractTurn();
            return true;
        }
        return false;
    }

    public Player getPlayer(String clinetId) {
        if (playerWhite.isOccupied() && playerWhite.getUuid().equals(clinetId)) {
            return playerWhite;
        } else if (playerBlack.isOccupied() && playerBlack.getUuid().equals(clinetId)) {
            return playerBlack;
        }
        return null;
    }

    public Player[] getPlayers() {
        return new Player[] { playerWhite, playerBlack };
    }

    public Boolean getCheck() {
        return CheckGameState.checked(board);
    }

    public Boolean getWhiteCheck() {
        return CheckGameState.whiteChecked(board);
    }

    public Boolean getBlackCheck() {
        return CheckGameState.blackChecked(board);
    }

    public boolean isFull() {
        return playerWhite.isOccupied() && playerBlack.isOccupied();
    }

    public String getId() {
        return gameId;
    }

    public GameOverEnum checkGameOver() {
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

    private void toggleTimer() {
        if (isWhitesTurn()) {
            playerWhite.pauseTimer(); // Pause white's timer
            playerBlack.startTimer(); // Resume black's timer
        } else {
            playerBlack.pauseTimer(); // Pause black's timer
            playerWhite.startTimer(); // Resume white's timer
        }
    }

    public int[] getPlayerTimes() {
        int[] timers = { playerWhite.getTimeLeft(), playerBlack.getTimeLeft() };
        return timers;
    }

}
