package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Square;

public class CastlingMoveHandler {

    public static void validateCastlingMoves(Piece king, boolean[][] movelist, Board board) {

        Square[][] currentBoard = board.getBoard();
        PieceColor kingColor = king.getColor();
        if (king.getColor() == PieceColor.BLACK) {

            if (currentBoard[0][7].getPiece().getPieceType() == PieceTypeEnum.BLACK_ROOK
                    && currentBoard[0][7].getPiece().getMovesMade() == 0) {
                if (notThreatenedEmptySquare(1, 7, kingColor, board) && notThreatenedEmptySquare(2, 7, kingColor, board)
                        && notThreatenedEmptySquare(3, 7, kingColor, board)) {

                    movelist[2][7] = true;
                    movelist[3][7] = true;

                }
            }
            if (currentBoard[7][7].getPiece().getPieceType() == PieceTypeEnum.BLACK_ROOK
                    && currentBoard[7][7].getPiece().getMovesMade() == 0) {
                if (notThreatenedEmptySquare(5, 7, kingColor, board)
                        && notThreatenedEmptySquare(6, 7, kingColor, board)) {

                    movelist[6][7] = true;
                    movelist[5][7] = true;

                }
            }
        } else if (king.getColor() == PieceColor.WHITE) {
            if (currentBoard[0][0].getPiece().getPieceType() == PieceTypeEnum.WHITE_ROOK
                    && currentBoard[0][0].getPiece().getMovesMade() == 0) {
                if (notThreatenedEmptySquare(1, 0, kingColor, board) && notThreatenedEmptySquare(2, 0, kingColor, board)
                        && notThreatenedEmptySquare(3, 0, kingColor, board)) {

                    movelist[2][0] = true;
                    movelist[3][0] = true;

                }
            }
            if (currentBoard[7][0].getPiece().getPieceType() == PieceTypeEnum.WHITE_ROOK
                    && currentBoard[7][0].getPiece().getMovesMade() == 0) {
                if (notThreatenedEmptySquare(5, 0, kingColor, board)
                        && notThreatenedEmptySquare(6, 0, kingColor, board)) {

                    movelist[6][0] = true;
                    movelist[5][0] = true;

                }
            }
        }
    }

    public static void makeCastleMove(int x, int y, int newX, int newY, Board board) {
        Square[][] currentBoard = board.getBoard();

        if (currentBoard[x][y].getPiece().getColor() == PieceColor.BLACK) {
            if (newX == 2 && newY == 7) {
                board.move(currentBoard[0][7].getPiece(), 3, 7);
            } else if (newX == 6 && newY == 7) {
                board.move(currentBoard[7][7].getPiece(), 5, 7);
            }
        } else if (currentBoard[x][y].getPiece().getColor() == PieceColor.WHITE) {
            if (newX == 2 && newY == 0) {
                board.move(currentBoard[0][0].getPiece(), 3, 0);
            } else if (newX == 6 && newY == 0) {
                board.move(currentBoard[7][0].getPiece(), 5, 0);
            }
        }
    }

    public static boolean isCastleMove(Piece piece, int x, int y, Board board) {
        if (piece.getPieceType() == PieceTypeEnum.BLACK_KING || piece.getPieceType() == PieceTypeEnum.WHITE_KING
                && piece.getMovesMade() == 0 && Math.abs(piece.getX() - x) == 2) {
            return true;
        }
        return false;
    }

    private static boolean notThreatenedEmptySquare(int x, int y, PieceColor color, Board board) {

        if (!board.containsPiece(x, y) && !MoveValidator.threatenedSquare(x, y, color, board)) {
            return true;
        }
        return false;
    }
}
