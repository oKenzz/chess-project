package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

public class EnPassantMoveHandler {

    public static void validateMoves(Piece piece, Boolean[][] movelist, Piece[][] board) {

        if (piece.getColor() == PieceColor.BLACK) {
            // adds en pessant moves to the left to possible moves
            if (piece.getX() != 0 && piece.getY() != 0 && board[piece.getX() - 1][piece.getY()] != null
                    && board[piece.getX() - 1][piece.getY()].getColor() == PieceColor.WHITE
                    && board[piece.getX() - 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() - 1][piece.getY() - 1] = true;
            }

            // adds enpessant move to the right to possible moves
            if (piece.getX() != 7 && piece.getY() != 0 && board[piece.getX() + 1][piece.getY()] != null
                    && board[piece.getX() + 1][piece.getY()].getColor() == PieceColor.WHITE
                    && board[piece.getX() + 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() + 1][piece.getY() - 1] = true;
            }

        } else if (piece.getColor() == PieceColor.WHITE) {
            // enpessant to the left
            if (piece.getX() != 0 && piece.getY() != 7 && board[piece.getX() - 1][piece.getY()] != null
                    && board[piece.getX() - 1][piece.getY()].getColor() == PieceColor.BLACK
                    && board[piece.getX() - 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() - 1][piece.getY() + 1] = true;
            }

            // enpessant to the right
            if (piece.getX() != 7 && piece.getY() != 7 && board[piece.getX() + 1][piece.getY()] != null
                    && board[piece.getX() + 1][piece.getY()].getColor() == PieceColor.BLACK
                    && board[piece.getX() + 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() + 1][piece.getY() + 1] = true;
            }
        }
    }

    public static void makeEnPassantMove(Piece piece, int newX, int newY, Board currentBoard){

        Piece[][] board = currentBoard.getBoard();
        Piece adjacentPiece = board[newX][piece.getY()];

        if (piece.getColor() == PieceColor.WHITE && adjacentPiece.getColor() == PieceColor.BLACK) {
            if (newY == piece.getY() - 1) {
                //currentBoard.move(piece, newX, newY);
                board[newX][piece.getY()] = null;
            }
        } else if (piece.getColor() == PieceColor.BLACK && adjacentPiece.getColor() == PieceColor.WHITE) {
            if (newY == piece.getY() + 1) {
                //currentBoard.move(piece, newX, newY);
                board[newX][piece.getY()] = null;
            }
        }
    }
}
