package com.backend.chess_backend.model.ChessGames;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Constants.GameOverEnum;
import com.backend.chess_backend.model.MoveHandlers.CheckGameState;
import com.backend.chess_backend.model.MoveHandlers.MoveHandler;
import com.backend.chess_backend.model.MoveHandlers.MoveValidator;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Player;
import com.backend.chess_backend.model.Square;

public class SimpleChessGame implements IChessGame {

    private Board board;
    private Player playerWhite;
    private Player playerBlack;
    private int turnsMade;
    private String gameId;
    private long gameStartedTime;
    private GameOverEnum gameOver;
    private final Integer defaultChessTime = 600;

    public SimpleChessGame(String gameid) {
        this.gameId = gameid;
        this.turnsMade = 0;
        this.board = new Board(8, 8);
        this.playerWhite = new Player(false, defaultChessTime);
        this.playerBlack = new Player(false, defaultChessTime);
        this.gameStartedTime = System.currentTimeMillis() / 1000L;
        this.gameOver = GameOverEnum.NOT_OVER;
    }

    public boolean[][] possibleMoves(int x, int y) {
        Square[][] currentBoard = board.getBoard();
        Piece currentPiece = currentBoard[x][y].getPiece();
        int boardW = currentBoard.length;
        int boardH = currentBoard[0].length;
        boolean[][] emptyList = new boolean[boardW][boardH];

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

    public boolean attemptMove(int x, int y, int newX, int newY) {

        Square[][] currentBoard = board.getBoard();
        boolean[][] possibleM = possibleMoves(x, y);

        if ((attemptToMoveWhite(x, y) && isWhitesTurn()) || attemptToMoveBlack(x, y) && isBlacksTurn()) {
            if (board.isMoveAllowed(newX, newY, possibleM)) {

                MoveHandler.makeUniqueMove(x, y, newX, newY, board, currentBoard);

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
            gameOver = GameOverEnum.WHITE;
        } else if (CheckGameState.blackStalemated(board)) {
            gameOver = GameOverEnum.DRAW;
        } else {
            gameOver = null;
        }
    }

    private void updateGameOverWhite() {
        if (CheckGameState.whiteCheckmated(board)) {
            gameOver = GameOverEnum.BLACK;
        } else if (CheckGameState.whiteStalemated(board)) {
            gameOver = GameOverEnum.DRAW;
        } else {
            gameOver = null;
        }
    }

    private boolean attemptToMoveWhite(int x, int y) {
        Square[][] currentBoard = board.getBoard();
        if (currentBoard[x][y].containsPiece() && currentBoard[x][y].getPiece().getColor() == PieceColor.WHITE) {
            return true;
        }

        return false;
    }

    private boolean attemptToMoveBlack(int x, int y) {
        Square[][] currentBoard = board.getBoard();
        if (currentBoard[x][y].containsPiece() && currentBoard[x][y].getPiece().getColor() == PieceColor.BLACK) {
            return true;
        }

        return false;
    }

    public void restartGame() {
        this.board = new Board();
        this.turnsMade = 0;
        this.gameStartedTime = System.currentTimeMillis() / 1000L;
    }

    private boolean isWhitesTurn() {
        if (turnsMade % 2 == 0) {
            return true;
        }
        return false;
    }

    private boolean isBlacksTurn() {
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

    public Square[][] getBoard() {
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

    public void addPlayer(String clinetId, boolean isBot) {

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

    public boolean attemptUndo(String clientId) {
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

    public boolean getCheck() {
        return CheckGameState.checked(board);
    }

    public boolean getWhiteCheck() {
        return CheckGameState.whiteChecked(board);
    }

    public boolean getBlackCheck() {
        return CheckGameState.blackChecked(board);
    }

    public boolean isFull() {
        return playerWhite.isOccupied() && playerBlack.isOccupied();
    }

    public String getId() {
        return gameId;
    }

    public GameOverEnum checkGameOver() {
        if (this.playerWhite.getTimeLeft() <= 0) {
            return GameOverEnum.BLACK;
        } else if (this.playerBlack.getTimeLeft() <= 0) {
            return GameOverEnum.WHITE;
        }
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

    public Player getWhitePlayer() {
        return this.playerWhite;
    }

    public Player getBlackPlayer() {
        return this.playerBlack;
    }

    public boolean hasBotPlayer() {
        for (Player player : this.getPlayers()) {
            if (player.isBot()) {
                return true;
            }
        }
        return false;
    }

}
