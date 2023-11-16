package com.backend.chess_backend.model;

import java.util.ArrayList;

import com.backend.chess_backend.model.Pieces.Piece;

public class Translator {

    private String alphabet;

    public Translator(){
        this.alphabet = "abcdefgh";
    }

    public int[] translatePos(String pos){
        int[] coordinates = new int[2];
        int yCoord = 0;
        int xCoord = 0;


        for(int i = 0; i < alphabet.length(); i++){
            if(alphabet.charAt(i) == Character.toLowerCase(pos.charAt(0))){
                xCoord = i;
                break;
            }
        }
        yCoord = pos.charAt(1);
        yCoord--;
        coordinates[0] = xCoord;
        coordinates[1] = yCoord;
        
        return coordinates;
    }

    public int[][] translatePos(String oldPos, String newPos){

        int[][] allCoordinates = new int[2][2];

        allCoordinates[0] = translatePos(oldPos);
        allCoordinates[1] = translatePos(newPos);

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
                    if(x != board.length-1){
                        emptyCount = 0;
                    }
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
        String pos = alphabet.charAt(x) + y+1 + "";
        return pos;
    }

    public ArrayList<String> translatePossibleMoves(Boolean[][] bolBoard){
        ArrayList<String> posMoves = new ArrayList<>();

        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if(bolBoard[x][y]){
                    posMoves.add(translateCoords(x, y));
                }
            }
        }
        return posMoves; 
    }
}
