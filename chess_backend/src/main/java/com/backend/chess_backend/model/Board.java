package com.backend.chess_backend.model;

import java.util.ArrayList;
import java.util.List;

import com.backend.chess_backend.model.Pieces.Bishop;
import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.Knight;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceSetup;
import com.backend.chess_backend.model.Pieces.Queen;
import com.backend.chess_backend.model.Pieces.Rook;

public class Board {

    // Will swap "Object to Piece"
    // ArrayList<ArrayList<Object>> board = new ArrayList<ArrayList<Object>>();

    Piece[][] board;
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
        this.board = new Piece[BoardHight][BoardWidth];
        createStartingBoard();
        this.gameOver = null;
        this.lastPiece = null;

    }

    public Board(int hight, int width) {

        this.BoardHight = hight;
        this.BoardWidth = width;
        this.board = new Piece[BoardHight][BoardWidth];
        createStartingBoard();
        this.gameOver = null;
        this.lastPiece = null;

    }

    // returns the piece on that square (null if no piece there)
    public Piece getPiece(int xCord, int yCord) {
        return board[xCord][yCord];

    }

    // returns true if there is a piece on the square
    public Boolean containsPiece(int xCord, int yCord) {
        Piece piece = board[xCord][yCord];
        if (piece == null) {
            return false;
        } else {
            return true;
        }
    }

    public void reset() {
        createStartingBoard();
    }

    // checks if the move made is a move that is allowed for that piece
    public boolean isMoveAllowed(int newX, int newY, Boolean[][] moveList) {
        if (moveList[newX][newY] == true) {
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
        lastPiece = board[newX][newY];
        updateBoard(piece, newX, newY);
        piece.updateCoords(newX, newY);
        if (piece instanceof King && piece.getColor() == PieceColor.BLACK) {
            this.bKingPosition[0] = newX;
            this.bKingPosition[1] = newY;
        } else if (piece instanceof King && piece.getColor() == PieceColor.WHITE) {
            this.wKingPosition[0] = newX;
            this.wKingPosition[1] = newY;
        }
        piece.IncrementMovesMade();
    }

    public void undoMove() {
        int tmpx = lastMove[1][0];
        int tmpy = lastMove[1][1];
        Piece tmpp = lastPiece;
        move(board[lastMove[1][0]][lastMove[1][1]], lastMove[0][0], lastMove[0][1]);

        board[tmpx][tmpy] = tmpp;
        board[lastMove[1][0]][lastMove[1][1]].movesMade -= 2;

    }

    private void updateBoard(Piece piece, int newX, int newY) {
        // sets teh new position in board to the piece and changes the old one to null
        board[newX][newY] = piece;
        board[piece.getX()][piece.getY()] = null;

        // //if the move made is an enpessant from a white piece it removes the piece that was taken
        // if (piece.getPieceType()=="P"){
        //     if(Math.abs(piece.getX()-newX)==1 && board[newX][piece.getY()]!=null&& board[newX][piece.getY()].movesMade==1&&board[newX][piece.getY()].getPieceType() =="p"){
        //         board[newX][newY-1]=null;
        //     }
        // //removes the piece that was taken by enpessant from a black piece
        // else if (piece.getPieceType()=="p"){
        //     if(Math.abs(piece.getX()-newX)==1 && board[newX][piece.getY()]!=null&& board[newX][piece.getY()].movesMade==1&&board[newX][piece.getY()].getPieceType()=="P"){
        //         board[newX][newY+1]=null;
        //     }
        // }
    }


    // getter for the board itself
    public Piece[][] getBoard() {
        return board;
    }

    private void createStartingBoard() {
        PieceSetup.setupStandardChessPieces(this.board);
        setKingsPositions();
    }

    private void setKingsPositions() {
        for (int x = 0; x < this.BoardWidth; x++) {
            for (int y = 0; y < this.BoardHight; y++) {
                if (this.board[x][y] instanceof King) {
                    if (this.board[x][y].getColor() == PieceColor.BLACK) {
                        bKingPosition[0] = x;
                        bKingPosition[1] = y;
                    } else if (this.board[x][y].getColor() == PieceColor.WHITE) {
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
                Piece piece = board[x][y];
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
                Piece piece = board[x][y];
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