package com.backend.chess_backend.model.Pieces;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;

public class Bishop extends Piece {

    public Bishop(PieceColor color, int xCoord, int yCoord) {
        super(color, xCoord, yCoord);

    }

    // returns all possible moves for that piece not taking the board pieces in
    // account. Returns in 2 dimentional array form;
    @Override
    public boolean[][] getPossibleMoves(int boardW, int boardH) {

        boolean[][] possibleMoves = new boolean[boardW][boardH];

        for (int x = 0; x < boardW; x++) {
            for (int y = 0; y < boardH; y++) {
                if ((Math.abs(x - this.getX()) - Math.abs(y - this.getY()) == 0) && x != this.getX()) { // if in
                    // diagonal but
                    // avoiding
                    // itself
                    possibleMoves[x][y] = true;
                } else {
                    possibleMoves[x][y] = false;
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public PieceTypeEnum getPieceType() {
        if (this.getColor() == PieceColor.BLACK) {
            return PieceTypeEnum.BLACK_BISHOP;
        } else {
            return PieceTypeEnum.WHITE_BISHOP;
        }
    }
}
