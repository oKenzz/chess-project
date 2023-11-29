package com.backend.chess_backend.model;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

public class Game {

    private Board board;
    private Player playerWhite;
    private Player playerBlack;
    private int turnsMade;
    private String gameId;
    private long gameStartedTime;

    public Game(String gameid) {
        this.gameId = gameid;
        this.turnsMade = 0;
        this.board = new Board();
        this.playerWhite = null;
        this.playerBlack = null;
        this.gameStartedTime = System.currentTimeMillis() / 1000L;
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

        if ((attemptToMoveWhite(x, y) && isWhitesTurn()) || attemptToMoveBlack(x, y) && isBlacksTurn()) {
            return board.getPossibleMoves(currentPiece);
        }

        return board.getPossibleMoves(currentPiece);

    }

    public Boolean attemptMove(int x, int y, int newX, int newY) {
        Piece[][] currentBoard = board.getBoard();
        Boolean[][] possibleM = board.getPossibleMoves(currentBoard[x][y]);

        if ((attemptToMoveWhite(x, y) && isWhitesTurn()) || attemptToMoveBlack(x, y) && isBlacksTurn()) {
            if (board.checkIfallowed(newX, newY, possibleM)) {
                board.updateCoords(currentBoard[x][y], newX, newY);
                IncrementTurn();
                return true;
            }
        }

        return false;
    }

    private Boolean attemptToMoveWhite(int x, int y) {
        Piece[][] currentBoard = board.getBoard();
        if (currentBoard[x][y].getColor() == PieceColor.WHITE) {
            return true;
        }

        return false;
    }

    private Boolean attemptToMoveBlack(int x, int y) {
        Piece[][] currentBoard = board.getBoard();
        if (currentBoard[x][y].getColor() == PieceColor.BLACK) {
            return true;
        }

        return false;
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

    public void addPlayer(String clinetId) {
        Player player = new Player(clinetId);
        if (playerWhite == null) {
            playerWhite = player;
        } else if (playerBlack == null) {
            playerBlack = player;
        }
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

    public boolean isFull() {
        return playerWhite != null && playerBlack != null;
    }

    public String getId() {
        return gameId;
    }
}
