package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;

public class PromotionMoveHandler {

    public static boolean isPromotionMove(Piece piece, int x, int y) {
        if (piece != null && piece instanceof Pawn) {
            if (piece.getColor() == PieceColor.WHITE && y == 7) {
                return true;
            } else if (piece.getColor() == PieceColor.BLACK && y == 0) {
                return true;
            }
        }
        return false;
    }

    public static void promoteToQueen(Piece piece, int x, int y, Board board) {
        Piece[][] currentBoard = board.getBoard();

        currentBoard[x][y] = PieceFactory.makeQueen(piece.getColor(), x, y);
        currentBoard[x][y].movesMade = piece.movesMade;
    }
}
