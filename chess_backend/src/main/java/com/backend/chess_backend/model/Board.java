package com.backend.chess_backend.model;

import java.util.ArrayList;
import java.util.List;

import com.backend.chess_backend.model.Pieces.Bishop;
import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.Knight;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
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
        if (piece.getPieceType() == "k") {
            this.bKingPosition[0] = newX;
            this.bKingPosition[1] = newY;
        } else if (piece.getPieceType() == "K") {
            this.wKingPosition[0] = newX;
            this.wKingPosition[1] = newY;
        }
        piece.movesMade++;
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
        // if the move made is an enpessant from a white piece it removes the piece that
        // was taken
        if (piece.getPieceType() == "P") {
            if (Math.abs(piece.getX() - newX) == 1 && board[newX][piece.getY()] != null
                    && board[newX][piece.getY()].movesMade == 1 && board[newX][piece.getY()].getPieceType() == "p") {
                board[newX][newY - 1] = null;
            }
            // removes the piece that was taken by enpessant from a black piece
            else if (piece.getPieceType() == "p") {
                if (Math.abs(piece.getX() - newX) == 1 && board[newX][piece.getY()] != null
                        && board[newX][piece.getY()].movesMade == 1
                        && board[newX][piece.getY()].getPieceType() == "P") {
                    board[newX][newY + 1] = null;
                }
            }

        }
    }

    // getter for the board itself
    public Piece[][] getBoard() {
        return board;
    }

    private void createStartingBoard() {
        for (int x = 0; x < board.length; x++) {
            // creates pawns at y=1 and 6
            board[x][6] = new Pawn(PieceColor.BLACK, x, 6);
            board[x][1] = new Pawn(PieceColor.WHITE, x, 1);
            // creates rooks in the corners
            if (x == 0 || x == 7) {
                board[x][7] = new Rook(PieceColor.BLACK, x, 7);
                board[x][0] = new Rook(PieceColor.WHITE, x, 0);
            }
            // creates knights
            else if (x == 1 || x == 6) {
                board[x][7] = new Knight(PieceColor.BLACK, x, 7);
                board[x][0] = new Knight(PieceColor.WHITE, x, 0);
            }
            // creates bishops
            else if (x == 2 || x == 5) {
                board[x][7] = new Bishop(PieceColor.BLACK, x, 7);
                board[x][0] = new Bishop(PieceColor.WHITE, x, 0);
            }
            // creates the queens
            else if (x == 3) {
                board[x][7] = new Queen(PieceColor.BLACK, x, 7);
                board[x][0] = new Queen(PieceColor.WHITE, x, 0);
            }
            // creates the kings and saves their positions in variables
            else if (x == 4) {
                board[x][7] = new King(PieceColor.BLACK, x, 7);
                board[x][0] = new King(PieceColor.WHITE, x, 0);
                bKingPosition[0] = x;
                bKingPosition[1] = 7;
                wKingPosition[0] = x;
                wKingPosition[1] = 0;
            }
        }

    }

    // removes moves that are false due to another piece being in the way
    private Boolean[][] removeExcess(Piece piece, Boolean[][] possibleMoves) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                // checks if there is a piece on one of the possible moves
                if (containsPiece(x, y) == true && possibleMoves[x][y] == true) {
                    int tmpx = x;
                    int tmpy = y;
                    // checks if it is at a diagonal
                    if (Math.abs(piece.getX() - x) - Math.abs(piece.getY() - y) == 0) {
                        // checks if the found piece is to the up and left of the original piece
                        if (x < piece.getX() && y < piece.getY()) {
                            while (tmpx >= 0 && tmpy >= 0) {
                                if (possibleMoves[tmpx][tmpy] == false) {
                                    break;
                                }
                                possibleMoves[tmpx][tmpy] = false;
                                tmpx--;
                                tmpy--;
                            }
                        }
                        // down left
                        else if (x < piece.getX() && y > piece.getY()) {
                            while (tmpx >= 0 && tmpy < board.length) {
                                if (possibleMoves[tmpx][tmpy] == false) {
                                    break;
                                }
                                possibleMoves[tmpx][tmpy] = false;
                                tmpx--;
                                tmpy++;
                            }

                        }
                        // up right
                        else if (x > piece.getX() && y < piece.getY()) {
                            while (tmpx < board.length && tmpy >= 0) {
                                if (possibleMoves[tmpx][tmpy] == false) {
                                    break;
                                }
                                possibleMoves[tmpx][tmpy] = false;
                                tmpx++;
                                tmpy--;
                            }

                        }
                        // down right
                        else if (x > piece.getX() && y > piece.getY()) {
                            while (tmpx < board.length && tmpy < board.length) {
                                possibleMoves[tmpx][tmpy] = false;
                                tmpx++;
                                tmpy++;
                            }

                        }

                    }
                    // checks if piece is on the same x-level
                    else if (piece.getX() == x) {
                        if (y < piece.getY()) {
                            while (tmpy >= 0) {
                                possibleMoves[x][tmpy] = false;
                                tmpy--;
                            }

                        } else {
                            if (y > piece.getY()) {
                                while (tmpy < board.length) {
                                    possibleMoves[x][tmpy] = false;
                                    tmpy++;
                                }

                            }

                        }
                    }
                    // checks if piece is on the same y-level
                    else if (piece.getY() == y) {
                        if (x < piece.getX()) {
                            while (tmpx >= 0) {
                                possibleMoves[tmpx][y] = false;
                                tmpx--;
                            }

                        }

                        else {
                            while (tmpx < board.length) {
                                possibleMoves[tmpx][y] = false;
                                tmpx++;
                            }

                        }

                    }
                    // for knights if there is a piece on their move
                    else if (Math.abs(x - piece.getX()) * Math.abs(y - piece.getY()) == 2) {
                        possibleMoves[x][y] = false;

                    }
                    // if it was a piece of the opposite color it ads that back since you can take
                    // it
                    if (board[x][y].getColor() != piece.getColor()) {
                        possibleMoves[x][y] = true;
                    }
                }

            }
        }
        return possibleMoves;
    }


    public List<Piece> getAllPlayerPieces(PieceColor color){
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
    public List<Piece> getAllPieces(){
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