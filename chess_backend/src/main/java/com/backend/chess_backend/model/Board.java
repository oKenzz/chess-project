package com.backend.chess_backend.model;

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

    Piece[][] board = new Piece[8][8];
    int[][] lastMove = new int[2][2];
    int[] bKingPosition = new int[2];
    int[] wKingPosition = new int[2];
    String gameOver;

    public Board() {

        createStartingBoard();
        this.gameOver = null;
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

    // moves a piece to a square using get possible moves. Returns true if move
    // successful, false if piece cant move there.
    public Boolean[][] getPossibleMoves(Piece piece) {
        Boolean[][] movelist = piece.getPossibleMoves(8, 8);
        // removes squares if pices are in the way
        movelist = removeExcess(piece, movelist);
        // checks if the piece is a black pawn
        if (piece.getPieceType() == "p") {
            if (piece.getY() > 0 && board[piece.getX()][piece.getY() - 1] != null) {
                movelist[piece.getX()][piece.getY() - 1] = false;
                if (movelist[piece.getX()][piece.getY() - 2] == true) {
                    movelist[piece.getX()][piece.getY() - 2] = false;
                }

            } else if (piece.getMovesMade() == 0 && board[piece.getX()][piece.getY() - 2] != null) {
                movelist[piece.getX()][piece.getY() - 2] = false;
            }
            // removes the option from a black pawn to move diagonally down right when there
            // is not black peice to take
            if (piece.getX() != 7 && piece.getY() != 0 && board[piece.getX() + 1][piece.getY() - 1] == null) {
                movelist[piece.getX() + 1][piece.getY() - 1] = false;
            }
            // removes the option from a black pawn to move diagonally down left when there
            // is not black peice to take
            if (piece.getX() != 0 && piece.getY() != 0 && board[piece.getX() - 1][piece.getY() - 1] == null) {
                movelist[piece.getX() - 1][piece.getY() - 1] = false;
            }
            // adds en pessant moves to the left to possible moves
            if (piece.getX() != 0 && piece.getY() != 0 && board[piece.getX() - 1][piece.getY()] != null
                    && board[piece.getX() - 1][piece.getY()].getPieceType() == "P"
                    && board[piece.getX() - 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() - 1][piece.getY() - 1] = true;
            }
            // adds enpessant move to the right to possible moves
            if (piece.getX() != 7 && piece.getY() != 0 && board[piece.getX() + 1][piece.getY()] != null
                    && board[piece.getX() + 1][piece.getY()].getPieceType() == "P"
                    && board[piece.getX() + 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() + 1][piece.getY() - 1] = true;
            }

        } else if (piece.getPieceType() == "P") {
            if (piece.getY() < 7 && board[piece.getX()][piece.getY() + 1] != null) {
                movelist[piece.getX()][piece.getY() + 1] = false;
                if (movelist[piece.getX()][piece.getY() + 2] == true) {
                    movelist[piece.getX()][piece.getY() + 2] = false;
                }

            } else if (piece.getMovesMade() == 0 && board[piece.getX()][piece.getY() + 2] != null) {
                movelist[piece.getX()][piece.getY() + 2] = false;
            }
            // removes the option from a white pawn to move diagonally up right when there
            // is not black peice to take
            if (piece.getX() != 7 && piece.getY() != 7 && board[piece.getX() + 1][piece.getY() + 1] == null) {
                movelist[piece.getX() + 1][piece.getY() + 1] = false;
            }
            // removes the option from a white pawn to move diagonally up left when there is
            // not black peice to take
            if (piece.getX() != 0 && piece.getY() != 7 && board[piece.getX() - 1][piece.getY() + 1] == null) {
                movelist[piece.getX() - 1][piece.getY() + 1] = false;
            }
            // enpessant to the left
            if (piece.getX() != 0 && piece.getY() != 7 && board[piece.getX() - 1][piece.getY()] != null
                    && board[piece.getX() - 1][piece.getY()].getPieceType() == "p"
                    && board[piece.getX() - 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() - 1][piece.getY() + 1] = true;
            }
            // enpessant to the right
            if (piece.getX() != 7 && piece.getY() != 7 && board[piece.getX() + 1][piece.getY()] != null
                    && board[piece.getX() + 1][piece.getY()].getPieceType() == "p"
                    && board[piece.getX() + 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() + 1][piece.getY() + 1] = true;
            }

        }
        return movelist;
    }

    public void reset() {
        createStartingBoard();
    }

    // checks if the move made is a move that is allowe for that piece
    public boolean checkIfallowed(int newX, int newY, Boolean[][] moveList) {
        if (moveList[newX][newY] == true) {
            return true;
        } else {
            return false;
        }

    }

    // updates the
    public void updateCoords(Piece piece, int newX, int newY) {
        lastMove[0][0] = piece.getX();
        lastMove[1][0] = newX;
        lastMove[0][1] = piece.getY();
        lastMove[1][1] = newY;
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
        updateGameOver();
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
            //creates pawns at y=1 and 6
            board[x][6] = new Pawn(PieceColor.BLACK, x, 6);
            board[x][1] = new Pawn(PieceColor.WHITE, x, 1);
            //creates rooks in the corners 
            if (x == 0 || x == 7) {
                board[x][7] = new Rook(PieceColor.BLACK, x, 7);
                board[x][0] = new Rook(PieceColor.WHITE, x, 0);
            } 
            //creates knights
            else if (x == 1 || x == 6) {
                board[x][7] = new Knight(PieceColor.BLACK, x, 7);
                board[x][0] = new Knight(PieceColor.WHITE, x, 0);
            } 
            //creates bishops
            else if (x == 2 || x == 5) {
                board[x][7] = new Bishop(PieceColor.BLACK, x, 7);
                board[x][0] = new Bishop(PieceColor.WHITE, x, 0);
            } 
            //creates the queens 
            else if (x == 3) {
                board[x][7] = new Queen(PieceColor.BLACK, x, 7);
                board[x][0] = new Queen(PieceColor.WHITE, x, 0);
            } 
            //creates the kings and saves their positions in variables
            else if (x == 4) {
                board[x][7] = new King(PieceColor.BLACK, x, 7);
                board[x][0] = new King(PieceColor.WHITE, x, 0);
                bKingPosition[0] = x;
                bKingPosition[1] = 0;
                wKingPosition[0] = x;
                wKingPosition[1] = 7;
                
            }
        
        }

        // PieceColor currentColor = PieceColor.WHITE;
        // for (int y = 0; y < board[0].length; y++) {
        //     if (y < 4) {
        //         currentColor = PieceColor.WHITE;
        //     } else {
        //         currentColor = PieceColor.BLACK;
        //     }
        //     for (int x = 0; x < board.length; x++) {
        //         if (y == 1 || y == 6) {
        //             board[x][y] = new Pawn(currentColor, x, y);
        //         } else if (y == 0 || y == 7) {
        //             if (x == 0 || x == 7) {
        //                 board[x][y] = new Rook(currentColor, x, y);
        //             } else if (x == 1 || x == 6) {
        //                 board[x][y] = new Knight(currentColor, x, y);
        //             } else if (x == 2 || x == 5) {
        //                 board[x][y] = new Bishop(currentColor, x, y);
        //             } else if (x == 3) {
        //                 board[x][y] = new Queen(currentColor, x, y);
        //             } else if (x == 4) {
        //                 board[x][y] = new King(currentColor, x, y);

        //             }
        //         }
        //     }
        // }
        // bKingPosition[0] = 4;
        // bKingPosition[1] = 0;
        // wKingPosition[0] = 4;
        // wKingPosition[1] = 7;
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

    private void updateGameOver() {
        if(board[this.bKingPosition[0]][this.bKingPosition[1]].getPieceType() != "k"){
            this.gameOver = "w";
        }
        else if(board[this.wKingPosition[0]][this.wKingPosition[1]].getPieceType() != "K"){
            this.gameOver = "b";
        }
        else{
            this.gameOver = null;
        }
    }

    public String getGameOver() {
        return this.gameOver;
    }
}