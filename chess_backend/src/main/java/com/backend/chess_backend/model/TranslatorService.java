package com.backend.chess_backend.model;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class TranslatorService {

    private static final String ALPHABET = "abcdefgh";

    public ArrayList<Integer> translatePos(String pos) {
        ArrayList<Integer> coordinates = new ArrayList<>();

        int xCoord = ALPHABET.indexOf(Character.toLowerCase(pos.charAt(0)));
        int yCoord = Character.getNumericValue(pos.charAt(1)) - 1;

        coordinates.add(xCoord);
        coordinates.add(yCoord);

        return coordinates;
    }

    public ArrayList<ArrayList<Integer>> translatePos(String oldPos, String newPos) {
        ArrayList<ArrayList<Integer>> allCoordinates = new ArrayList<>();
        allCoordinates.add(translatePos(oldPos));
        allCoordinates.add(translatePos(newPos));

        return allCoordinates;
    }

    public String translateBoard(Square[][] board, String color) {
        StringBuilder boardString = new StringBuilder();
        int emptyCount = 0;

        for (int y = board[0].length - 1; y >= 0; y--) {
            for (int x = 0; x < board.length; x++) {
                if (!board[x][y].containsPiece()) {
                    emptyCount++;
                } else {
                    if (emptyCount != 0) {
                        boardString.append(emptyCount);
                        emptyCount = 0;
                    }
                    boardString.append(translatePieceType(board[x][y]));
                }
            }

            if (emptyCount != 0) {
                boardString.append(emptyCount);
                emptyCount = 0;
            }
            if (y != 0) {
                boardString.append("/");
            }
        }

        boardString.append(" ").append(color);
        return boardString.toString();
    }

    public String translateCoords(int x, int y) {
        return ALPHABET.charAt(x) + String.valueOf(y + 1);
    }

    private String translatePieceType(Square square) {
        if (square.containsPiece()) {
            return square.getPiece().getPieceType().getMessage();
        }
        return "";
    }

    public ArrayList<String> translatePossibleMoves(boolean[][] bolBoard) {
        ArrayList<String> posMoves = new ArrayList<>();

        for (int x = 0; x < bolBoard.length; x++) {
            for (int y = 0; y < bolBoard[0].length; y++) {
                if (bolBoard[x][y]) {
                    posMoves.add(translateCoords(x, y));
                }
            }
        }
        return posMoves;
    }
}
