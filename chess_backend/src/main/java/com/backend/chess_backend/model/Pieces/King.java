package com.backend.chess_backend.model.Pieces;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;

public class King extends Piece {

    public King(PieceColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public boolean[][] getPossibleMoves(int boardW, int boardH) {
        boolean[][] moves = new boolean[boardW][boardH];

        // Initialize all moves to false
        for (int i = 0; i < boardW; i++) {
            for (int j = 0; j < boardH; j++) {
                moves[i][j] = false;
            }
        }

        // Directions the King can move: horizontally, vertically, and diagonally
        int[] xDirections = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] yDirections = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int i = 0; i < 8; i++) {
            int newX = this.getX() + xDirections[i];
            int newY = this.getY() + yDirections[i];

            // Check if the new position is within the board's bounds
            if (newX >= 0 && newX < boardW && newY >= 0 && newY < boardH) {
                moves[newX][newY] = true;
            }
        }

        // if (this.getColor() == PieceColor.WHITE && this.getMovesMade() == 0) {
        // moves[2][0] = true;
        // moves[6][0] = true;
        // } else if (this.getColor() == PieceColor.BLACK && this.getMovesMade() == 0) {
        // moves[2][7] = true;
        // moves[6][7] = true;

        // }

        return moves;
    }

    @Override
    public PieceTypeEnum getPieceType() {
        if (this.getColor() == PieceColor.BLACK) {
            return PieceTypeEnum.BLACK_KING;
        } else {
            return PieceTypeEnum.WHITE_KING;
        }
    }
}
