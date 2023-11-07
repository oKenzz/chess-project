package chess_backend.backend.src.main;

public class Move {

    private Square startCell; 
    private Square endCell;

    public Move(Square startCell, Square endCell){
        this.startCell = startCell;
        this.endCell = endCell; 
    }
    
}
