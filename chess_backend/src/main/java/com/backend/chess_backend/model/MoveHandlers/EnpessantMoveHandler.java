package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Square;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

public class EnpessantMoveHandler {

    public static Boolean isEnpessantMove(Piece piece, int x, int y, Board board) {
        Square[][] currentBoard = board.getBoard();
        if (piece instanceof Pawn) {
            if (piece.getColor() == PieceColor.WHITE) {
                if (y == 5) {
                    if (currentBoard[x][y - 1].getPiece() instanceof Pawn) {
                        if (currentBoard[x][y - 1].getPiece().getColor() == PieceColor.BLACK) {
                            if (currentBoard[x][y - 1].getPiece().getMovesMade() == 1) {
                                return true;
                            }
                        }
                    }
                }
            } else {
                if (y == 2) {
                    if (currentBoard[x][y + 1].getPiece() instanceof Pawn) {
                        if (currentBoard[x][y + 1].getPiece().getColor() == PieceColor.WHITE) {
                            if (currentBoard[x][y + 1].getPiece().getMovesMade() == 1) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void makeEnpessantMove(PieceColor color, int x, int y, Board board) {
        Square[][] currentBoard = board.getBoard();

        if (color == PieceColor.WHITE) {
            currentBoard[x][y - 1].removePiece();
        } else {
            currentBoard[x][y + 1].removePiece();
        }
    }
}