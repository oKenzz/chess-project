package com.backend.chess_backend.model.Pieces;

public class Rook extends Piece{

    int x = 0;

    public Rook(PieceColor color, int xCoord, int yCoord){
        super(color, xCoord, yCoord);
    }

    //returns all possible moves for that piece not taking the board pieces in account. Returns in 2 dimentional array form; 
    @Override
    public  Boolean[][] getPossibleMoves(int boardW, int boardH){

        Boolean[][] possibleMoves = new Boolean[boardW][boardH];
        
        
        for (int x = 0; x < boardW; x++){
            for (int y = 0; y < boardH; y++){
                if (x == this.getX() ^ y == this.getY()){ //if in same row/column but not itself
                    possibleMoves[x][y] = true;
                }else{
                    possibleMoves[x][y] = false;
                }
            }
        }
        return possibleMoves;
    }

    @Override
    public String getPieceType(){
        if(this.getColor() == PieceColor.BLACK){
            return "r";
        }else{
            return "R";
        }
    }
}