package com.backend.chess_backend.model;

import com.backend.chess_backend.model.Pieces.Piece;
import java.util.ArrayList;

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

    // public String translateBoard(Piece[][] board){
    //     String boardString = "";
    //     int emptyCount = 0;
    //     String rowStr = "";

    //     for(int x = 0; x < board.length(); x++){


    //         for (int y = board[0].lenth()-1; y > -1; y--){
    //             if(board[x][y] == null){
    //                 emptyCount++;
    //             }
    //             else{
    //                 rowStr = emptyCount + board[x][y].getPieceType();
    //             }
    //         }
    //         if (y != 0){
    //             rowStr += "/";
    //         }
    //         boardString += rowStr;
    //     }
    //     return boardString;
    // }

    // private String translateCoords(int x, int y){
    //     String pos = alphabet.charAt(x) + y+1;
    //     return pos;
    // }

    // public ArrayList<String> translatePossibleMoves(Boolean[][] bolBoard){
    //     ArrayList<String> posMoves = new ArrayList<>();

    //     for(int x = 0; x < board.length(); x++){
    //         for(int y = 0; y < board[0].length(); y++){
    //             if(bolBoard[x][y]){
    //                 posMoves.add(translateCoords(x, y));
    //             }
    //         }
    //     }
    //     return posMoves; 
    // }
}
