package com.backend.chess_backend.model.GameStates;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Square;
import com.backend.chess_backend.model.MoveHandlers.MoveValidator;
import com.backend.chess_backend.model.Pieces.PieceColor;

public class CheckState {
    

    public static Boolean isChecked(Board board) {
        if (isWhiteChecked(board) == true || isBlackChecked(board) == true) {
            return true;
        }
        return false;
    }

    public static Boolean isWhiteChecked(Board board) {
        if (threatenedWhiteKing(board) == true) {
            return true;
        }
        return false;
    }

    public static Boolean isBlackChecked(Board board) {
        if (threatenedblackKing(board) == true) {
            return true;
        }
        return false;
    }

    public static Boolean threatenedWhiteKing(Board board) {

        Square[][] currentBoard = board.getBoard();

        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (currentBoard[x][y].containsPiece()) {
                    if (currentBoard[x][y].getPiece().getColor() != PieceColor.WHITE) {
                        Boolean[][] posMoves = MoveValidator.primitivePossibleMoves(currentBoard[x][y].getPiece(), board);
                        int[] whiteKingPos = board.getwKingPosition();
                        if (posMoves[whiteKingPos[0]][whiteKingPos[1]] == true) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Boolean threatenedblackKing(Board board) {

        Square[][] currentBoard = board.getBoard();

        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (currentBoard[x][y].containsPiece()) {
                    if (currentBoard[x][y].getPiece().getColor() != PieceColor.BLACK) {
                        Boolean[][] posMoves = MoveValidator.primitivePossibleMoves(currentBoard[x][y].getPiece(), board);
                        int[] blackKingPos = board.getbKingPosition();
                        if (posMoves[blackKingPos[0]][blackKingPos[1]] == true) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}