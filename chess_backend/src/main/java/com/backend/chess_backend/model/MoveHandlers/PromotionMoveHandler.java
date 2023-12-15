package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;
import com.backend.chess_backend.model.Square;

public class PromotionMoveHandler {

    private static final PieceTypeEnum PieceTypeEnum = null;

    public static boolean isPromotionMove(Piece piece, int x, int y) {
        if (piece != null && (piece.getPieceType() == PieceTypeEnum.WHITE_PAWN
                || piece.getPieceType() == PieceTypeEnum.BLACK_PAWN)) {
            if (piece.getColor() == PieceColor.WHITE && y == 7) {
                return true;
            } else if (piece.getColor() == PieceColor.BLACK && y == 0) {
                return true;
            }
        }
        return false;
    }

    public static void promoteToQueen(Piece piece, int x, int y, Board board) {
        Square[][] currentBoard = board.getBoard();

        currentBoard[x][y].setPiece(PieceFactory.makeQueen(piece.getColor(), x, y));
        currentBoard[x][y].getPiece().setMovesMade(piece.getMovesMade()); 
    }
}
