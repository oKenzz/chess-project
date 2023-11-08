package chess_backend.backend.src.main.Pieces;

import chess_backend.backend.src.main.*;

public abstract class Piece {
    private boolean dead = false;
    private PieceColor Color;
    
    protected Piece (PieceColor color){
        this.Color = color;
    }

    public boolean isDead(){
        return dead;
    }

    private void move(Square endCell){
        
    }

    public abstract boolean canMove(Move move);
}
