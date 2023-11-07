package chess_backend.backend.src.main;

import java.util.*;

public class Board {
    

    //Will swap "Object to Piece"
    //ArrayList<ArrayList<Object>> board = new ArrayList<ArrayList<Object>>();

    Object[][] board = new Object[8][8];

    public Board(){

        createStartingBoard();

    }

    //returns the piece on that square (null if no piece there)
    public Object getPiece(int xCord, int yCord){
        return board[xCord][yCord];
        
    }

    //returns true if there is a piece on the square
    public Boolean containsPiece(int xCord, int yCord){
        Object piece = board[xCord][yCord];
        if (piece == null){
            return false;
        }else{
            return true;
        }
    }

    // extremely simple move function which needs to be changed.
    public void move(int xCord, int yCord, int newxCord, int newyCord){
        Object piece = board[xCord][yCord];
        board[xCord][yCord] = null;  //needs to be changed when castling implemented
        board[newxCord][newyCord] = piece;
    }


    public void reset(){
        createStartingBoard();
    }

    //getter for the board itself
    public Object getBoard(){
        return board;
    }

    private void createStartingBoard(){
        //creates board
        for (int x = 0; x < board.length; x++){
            for (int y = 0; y < board[x].length; y++){
                this.board[x][y] = new Object(); 
            }
        }
    }
}
