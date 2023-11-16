package com.backend.chess_backend.model.Pieces;

public class King extends Piece {

    public King (PieceColor color, int x, int y){
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
    
        // Directions the King can move: horizontally, vertically, and diagonally
        int[] xDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] yDirections = {-1, 0, 1, -1, 1, -1, 0, 1};
    
        for (int i = 0; i < 8; i++) {
            int newX = this.getX() + xDirections[i];
            int newY = this.getY() + yDirections[i];
    
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
            return "k";
        }else{
            return "K";
        }
    }
    
}
