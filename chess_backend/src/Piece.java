package chess_backend.src;

public abstract class Piece {

    private boolean dead = false;
    private PieceColor Color;


    protected Piece (PieceColor color){
        this.Color = color;
    }

    public boolean isDead(){
        return dead;
    }

    private void move(Cell endCell){
        
    }

    public abstract boolean canMove(Move move);

}
