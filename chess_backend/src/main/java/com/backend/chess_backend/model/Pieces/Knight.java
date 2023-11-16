package com.backend.chess_backend.model.Pieces;

public class Knight extends Piece {

    public Knight(PieceColor color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public Boolean[][] getPossibleMoves(int boardW, int boardH) {
        Boolean[][] moves = new Boolean[boardW][boardH];

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
    public String getPieceType(){
        if(this.getColor() == PieceColor.BLACK){
            return "n";
        }else{
            return "N";
        }
    }
}
