package com.backend.chess_backend.model;

import java.util.ArrayList;
import java.util.List;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceSetup;

public class Board {

    // Will swap "Object to Piece"
    // ArrayList<ArrayList<Object>> board = new ArrayList<ArrayList<Object>>();
    Square[][] board;
    int[][] lastMove = new int[2][2];
    int[] bKingPosition = new int[2];
    int[] wKingPosition = new int[2];
    Piece lastPiece;
    String gameOver;
    private int BoardHight;
    private int BoardWidth;

    public Board() {

        this.BoardHight = 8;
        this.BoardWidth = 8;
        this.board = new Square[BoardHight][BoardWidth];
        defineSquares();
        createStartingBoard();
        this.gameOver = null;
        this.lastPiece = null;

    }

    public Board(int hight, int width) {

        this.BoardHight = hight;
        this.BoardWidth = width;
        this.board = new Square[BoardHight][BoardWidth];
        defineSquares();
        createStartingBoard();
        this.gameOver = null;
        this.lastPiece = null;

    }

    // returns the piece on that square
    public Piece getPiece(int xCord, int yCord) {
        return board[xCord][yCord].getPiece();

    }

    // returns true if there is a piece on the square
    public boolean containsPiece(int xCord, int yCord) {
        return board[xCord][yCord].containsPiece();
    }

    public void reset() {
        createStartingBoard();
    }

    // checks if the move made is a move that is allowed for that piece
    public boolean isMoveAllowed(int newX, int newY, boolean[][] possibleM) {
        if (possibleM[newX][newY] == true) {
            return true;
        } else {
            return false;
        }
    }

    // updates the
    public void move(Piece piece, int newX, int newY) {
        lastMove[0][0] = piece.getX();
        lastMove[1][0] = newX;
        lastMove[0][1] = piece.getY();
        lastMove[1][1] = newY;

        lastPiece = board[newX][newY].getPiece();
        updateBoard(piece, newX, newY);
        piece.updateCoords(newX, newY);

        if (piece.getPieceType() == PieceTypeEnum.BLACK_KING) {
            this.bKingPosition[0] = newX;
            this.bKingPosition[1] = newY;
        } else if (piece.getPieceType() == PieceTypeEnum.WHITE_KING) {
            this.wKingPosition[0] = newX;
            this.wKingPosition[1] = newY;
        }

        piece.IncrementMovesMade();
    }

    public void undoMove() {
        int tmpx = lastMove[1][0];
        int tmpy = lastMove[1][1];
        Piece tmpp = lastPiece;
        move(board[lastMove[1][0]][lastMove[1][1]].getPiece(), lastMove[0][0], lastMove[0][1]);

        board[tmpx][tmpy].setPiece(tmpp);

        board[lastMove[1][0]][lastMove[1][1]].getPiece().DecrementMovesMade(2);

    }

    private void updateBoard(Piece piece, int newX, int newY) {

        board[newX][newY].setPiece(piece);
        board[piece.getX()][piece.getY()].removePiece();
    }

    // getter for the board itself
    public Square[][] getBoard() {
        return board;
    }

    private void createStartingBoard() {
        PieceSetup.setupStandardChessPieces(this.board);
        setKingsPositions();
    }

    private void defineSquares() {
        for (int x = 0; x < BoardWidth; x++) {
            for (int y = 0; y < BoardHight; y++) {
                this.board[x][y] = new Square(x, y);
            }
        }
    }

    private void setKingsPositions() {
        for (int x = 0; x < this.BoardWidth; x++) {
            for (int y = 0; y < this.BoardHight; y++) {
                if (this.board[x][y].containsPiece()) {
                    if (this.board[x][y].getPiece().getPieceType() == PieceTypeEnum.BLACK_KING) {
                        bKingPosition[0] = x;
                        bKingPosition[1] = y;
                    } else if (this.board[x][y].getPiece().getPieceType() == PieceTypeEnum.WHITE_KING) {
                        wKingPosition[0] = x;
                        wKingPosition[1] = y;
                    }
                }

            }
        }
    }

    public List<Piece> getAllPlayerPieces(PieceColor color) {
        List<Piece> pieces = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                Piece piece = board[x][y].getPiece();
                if (piece != null && piece.getColor() == color) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public List<Piece> getAllPieces() {
        List<Piece> pieces = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                Piece piece = board[x][y].getPiece();
                if (piece != null) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public int getBoardHeight() {
        return BoardHight;
    }

    public int getBoardWidth() {
        return BoardWidth;
    }

    public int[] getbKingPosition() {
        return bKingPosition;
    }

    public int[] getwKingPosition() {
        return wKingPosition;
    }
}
