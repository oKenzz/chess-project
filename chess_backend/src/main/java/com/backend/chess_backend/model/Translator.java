package com.backend.chess_backend.model;

import java.util.ArrayList;

import com.backend.chess_backend.model.Pieces.Piece;

public class Translator {

    private String alphabet;

    public Translator(){
        this.alphabet = "abcdefgh";
    }

    public ArrayList<Integer> translatePos(String pos){

        ArrayList<Integer> coordinates = new ArrayList<Integer>();
        
        int yCoord = 0;
        int xCoord = 0;


        for(int i = 0; i < alphabet.length(); i++){
            if(alphabet.charAt(i) == Character.toLowerCase(pos.charAt(0))){
                xCoord = i;
                break;
            }
        }


        yCoord = Character.getNumericValue(pos.charAt(1));
        yCoord--;
        coordinates.add(xCoord);
        coordinates.add(yCoord);
        
        return coordinates;
    }

    public ArrayList<ArrayList<Integer>> translatePos(String oldPos, String newPos){

        ArrayList<ArrayList<Integer>> allCoordinates = new ArrayList<>();

        allCoordinates.add(translatePos(oldPos));
        allCoordinates.add(translatePos(newPos));

        return allCoordinates;
    }

    public String translateBoard(Piece[][] board){
        String boardString = "";
        int emptyCount = 0;
        String rowStr = "";

        for(int y = board[0].length-1; y > -1; y--){
            for (int x = 0; x < board.length; x++ ){
                if(board[x][y] == null){
                    emptyCount++;
                }
                else{
                    if(emptyCount != 0){
                        rowStr += emptyCount + board[x][y].getPieceType();
                    }else{
                        rowStr += board[x][y].getPieceType();
                    }
                    emptyCount = 0;
                }
            }

            if(emptyCount != 0){
                rowStr += emptyCount;
            }
            if (y != 0){
                
                rowStr += "/";
            }
            boardString += rowStr;
            rowStr = "";
            emptyCount = 0;
        }
        return boardString;
    }

    private String translateCoords(int x, int y){
        String xpos = "" + alphabet.charAt(x);
        String ypos = "" + (y+1);
        return xpos + ypos;
    }

    public ArrayList<String> translatePossibleMoves(Boolean[][] bolBoard){
        ArrayList<String> posMoves = new ArrayList<>();

        for(int x = 0; x < bolBoard.length; x++){
            for(int y = 0; y < bolBoard.length; y++){
                if(bolBoard[x][y] == true){
                    posMoves.add(translateCoords(x, y));
                }
            }
        }
        return posMoves; 
    }
}
