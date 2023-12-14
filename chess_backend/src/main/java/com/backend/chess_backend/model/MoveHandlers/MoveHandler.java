package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Square;

public class MoveHandler {

    public static void makeUniqueMove(int x, int y, int newX, int newY, Board board, Square[][] currentBoard) {
        if (CastlingMoveHandler.isCastleMove(currentBoard[x][y].getPiece(), newX, newY, board)) {
            CastlingMoveHandler.makeCastleMove(x, y, newX, newY, board);
            board.move(currentBoard[x][y].getPiece(), newX, newY);
        } else if (PromotionMoveHandler.isPromotionMove(currentBoard[x][y].getPiece(), newX, newY)) {
            board.move(currentBoard[x][y].getPiece(), newX, newY);
            PromotionMoveHandler.promoteToQueen(currentBoard[newX][newY].getPiece(), newX, newY, board);
        } else if (EnpessantMoveHandler.isEnpessantMove(currentBoard[x][y].getPiece(), newX, newY, board)) {
            board.move(currentBoard[x][y].getPiece(), newX, newY);
            EnpessantMoveHandler.makeEnpessantMove(currentBoard[newX][newY].getPiece().getColor(), newX, newY,
                    board);
        } else {
            board.move(currentBoard[x][y].getPiece(), newX, newY);
        }
    }

}
