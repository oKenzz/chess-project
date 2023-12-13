package com.backend.chess_backend.model;

import java.util.ArrayList;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;


public class Translator {

    private static String alphabet = "abcdefgh";

    public static ArrayList<Integer> translatePos(String pos) {

        ArrayList<Integer> coordinates = new ArrayList<Integer>();

        int yCoord = 0;
        int xCoord = 0;

        for (int i = 0; i < alphabet.length(); i++) {
            if (alphabet.charAt(i) == Character.toLowerCase(pos.charAt(0))) {
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

    public static ArrayList<ArrayList<Integer>> translatePos(String oldPos, String newPos) {

        ArrayList<ArrayList<Integer>> allCoordinates = new ArrayList<>();

        allCoordinates.add(translatePos(oldPos));
        allCoordinates.add(translatePos(newPos));

        return allCoordinates;
    }

    public static String translateBoard(Square[][] board, String color) {
        String boardString = "";
        int emptyCount = 0;
        String rowStr = "";

        for (int y = board[0].length - 1; y > -1; y--) {
            for (int x = 0; x < board.length; x++) {
                if (!board[x][y].containsPiece()) {
                    emptyCount++;
                } else {
                    if (emptyCount != 0) {
                        rowStr += emptyCount + translatePieceType(board[x][y]);
                    } else {
                        rowStr += translatePieceType(board[x][y]);
                    }
                    emptyCount = 0;
                }
            }

            if (emptyCount != 0) {
                rowStr += emptyCount;
            }
            if (y != 0) {

                rowStr += "/";
            }
            boardString += rowStr;
            rowStr = "";
            emptyCount = 0;
        }

        boardString += " " + color;
        return boardString;
    }

    public static String translateCoords(int x, int y) {
        String xpos = "" + alphabet.charAt(x);
        String ypos = "" + (y + 1);
        return xpos + ypos;
    }


    private static String translatePieceType(Square square) {
        return square.getPiece().getPieceType().getMessage();
    }

    public static ArrayList<String> translatePossibleMoves(Boolean[][] bolBoard) {
        ArrayList<String> posMoves = new ArrayList<>();

        for (int x = 0; x < bolBoard.length; x++) {
            for (int y = 0; y < bolBoard.length; y++) {
                if (bolBoard[x][y] == true) {
                    posMoves.add(translateCoords(x, y));
                }
            }
        }
        return posMoves;
    }
}
