package com.backend.chess_backend.model.Pieces;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;

public class Knight extends Piece {

    public Knight(PieceColor color, int x, int y) {
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

        // Possible moves for a knight: 8 different positions in an L shape
        int[] xMoves = { -2, -1, 1, 2, 2, 1, -1, -2 };
        int[] yMoves = { 1, 2, 2, 1, -1, -2, -2, -1 };

        for (int i = 0; i < 8; i++) {
            int newX = this.getX() + xMoves[i];
            int newY = this.getY() + yMoves[i];

            // Check if the new position is within the board's bounds
            if (newX >= 0 && newX < boardW && newY >= 0 && newY < boardH) {
                moves[newX][newY] = true;
            }
        }

        return moves;
    }

    @Override
    public PieceTypeEnum getPieceType() {
        if (this.getColor() == PieceColor.BLACK) {
            return PieceTypeEnum.BLACK_KNIGHT;
        } else {
            return PieceTypeEnum.WHITE_KNIGHT;
        }
    }
}
