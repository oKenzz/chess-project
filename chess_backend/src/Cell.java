package chess_backend.src;

public class Cell {
    private int x; 
    private int y; 

    public Cell(int x, int y){
        this.x = x; 
        this.y = y; 
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
