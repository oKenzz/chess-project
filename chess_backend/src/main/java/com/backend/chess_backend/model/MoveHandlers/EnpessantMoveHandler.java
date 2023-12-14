package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Square;

public class EnpessantMoveHandler {

    public static boolean isEnpessantMove(Piece piece, int x, int y, Board board) {
        Square[][] currentBoard = board.getBoard();
        if (piece != null) {
            if (piece.getPieceType() == PieceTypeEnum.WHITE_PAWN) {
                if (y == 5) {
                    if (currentBoard[x][y - 1].containsPiece()
                            && currentBoard[x][y - 1].getPiece().getPieceType() == PieceTypeEnum.BLACK_PAWN) {
                        if (currentBoard[x][y - 1].getPiece().getMovesMade() == 1) {
                            return true;
                        }
                    }
                }
            } else {
                if (y == 2) {
                    if (currentBoard[x][y + 1].containsPiece()
                            && currentBoard[x][y + 1].getPiece().getPieceType() == PieceTypeEnum.WHITE_PAWN) {
                        if (currentBoard[x][y + 1].getPiece().getMovesMade() == 1) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void validateEnpessantMove(Piece piece, boolean[][] movelist, Board board) {

        Square[][] currentBoard = board.getBoard();
        int x = piece.getX();
        int y = piece.getY();

        if (piece.getColor() == PieceColor.BLACK) {
            if (x > 0) {
                if (currentBoard[x - 1][y].containsPiece()
                        && currentBoard[x - 1][y].getPiece().getPieceType() == PieceTypeEnum.WHITE_PAWN) {
                    if (currentBoard[x - 1][y].getPiece().getMovesMade() == 1) {
                        movelist[x - 1][y - 1] = true;
                    }
                }
            }
            if (x < board.getBoardWidth() - 1) {
                if (currentBoard[x + 1][y].containsPiece()
                        && currentBoard[x + 1][y].getPiece().getPieceType() == PieceTypeEnum.WHITE_PAWN) {
                    if (currentBoard[x + 1][y].getPiece().getMovesMade() == 1) {
                        movelist[x + 1][y - 1] = true;
                    }
                }
            }
        } else {
            if (x > 0) {
                if (currentBoard[x - 1][y].containsPiece()
                        && currentBoard[x - 1][y].getPiece().getPieceType() == PieceTypeEnum.BLACK_PAWN) {
                    if (currentBoard[x - 1][y].getPiece().getMovesMade() == 1) {
                        movelist[x - 1][y + 1] = true;
                    }
                }
            }
            if (x < board.getBoardWidth() - 1) {
                if (currentBoard[x + 1][y].containsPiece()
                        && currentBoard[x + 1][y].getPiece().getPieceType() == PieceTypeEnum.BLACK_PAWN) {
                    if (currentBoard[x + 1][y].getPiece().getMovesMade() == 1) {
                        movelist[x + 1][y + 1] = true;
                    }
                }
            }
        }
    }

    public static void makeEnpessantMove(PieceColor color, int x, int y, Board board) {
        Square[][] currentBoard = board.getBoard();

        if (color == PieceColor.WHITE) {
            currentBoard[x][y - 1].removePiece();
        } else if (color == PieceColor.BLACK) {
            currentBoard[x][y + 1].removePiece();
        }
    }
}