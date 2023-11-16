package com.backend.chess_backend.model.Pieces;

public class Pawn extends Piece{
    private boolean dead;
    private PieceColor color;
    private int xCoord;
    private int yCoord;
    private int hasmoved;


    public Pawn(PieceColor color, int xCoord, int yCoord){
        super(color, xCoord, yCoord);
    }

    //returns all possible moves for that piece not taking the board pieces in account. Returns a matrix of booleans, true where you can move, false otherwise; 
    @Override
    public  Boolean[][] getPossibleMoves(int boardW, int boardH){
        Boolean[][] possibleMoves = new Boolean[boardW][boardH];
        if (color==PieceColor.BLACK){
            if(hasmoved==0){
                possibleMoves[xCoord][yCoord-2]=true;
            }
            if(yCoord>0){
                possibleMoves[xCoord][yCoord-1]=true;
                if (xCoord>0){
                    possibleMoves[xCoord-1][yCoord-1]=true;
                }
                if (xCoord<7){
                    possibleMoves[xCoord+1][yCoord-1]=true;
                }
            }

        }
        else {
            if(hasmoved==0){
                possibleMoves[xCoord][yCoord+2]=true;
            }
            if(yCoord<7){
                possibleMoves[xCoord][yCoord+1]=true;
                if (xCoord>0){
                    possibleMoves[xCoord-1][yCoord+1]=true;
                }
                if (xCoord<7){
                    possibleMoves[xCoord+1][yCoord+1]=true;
                }
            }

        }
        return possibleMoves;
    }
    @Override
    public String getPieceType(){
        if(color == PieceColor.BLACK){
            return "p";
        }else{
            return "P";
        }    
    }
}
