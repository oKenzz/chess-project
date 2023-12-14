package com.backend.chess_backend;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;
import com.backend.chess_backend.model.Square;
import com.backend.chess_backend.model.TranslatorService;

@SpringBootTest
class TranslatorTest {

    private final TranslatorService translatorService;

    @Autowired
    public TranslatorTest(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @Test
    public void translateBoardTest() {

        Square[][] board = new Square[8][8];

        PieceColor currentColor = PieceColor.WHITE;
        for (int y = 0; y < board[0].length; y++) {
            if (y < 4) {
                currentColor = PieceColor.WHITE;
            } else {
                currentColor = PieceColor.BLACK;
            }
            for (int x = 0; x < board.length; x++) {
                if (y == 1 || y == 6) {
                    board[x][y].setPiece(PieceFactory.makePawn(currentColor, x, y));
                } else if (y == 0 || y == 7) {
                    if (x == 0 || x == 7) {
                        board[x][y].setPiece(PieceFactory.makeRook(currentColor, x, y));
                    } else if (x == 1 || x == 6) {
                        board[x][y].setPiece(PieceFactory.makeKnight(currentColor, x, y));
                    } else if (x == 2 || x == 5) {
                        board[x][y].setPiece(PieceFactory.makeBishop(currentColor, x, y));
                    } else if (x == 3) {
                        board[x][y].setPiece(PieceFactory.makeQueen(currentColor, x, y));
                    } else if (x == 4) {
                        board[x][y].setPiece(PieceFactory.makeKing(currentColor, x, y));
                    }
                }
            }
        }
        String fen = translatorService.translateBoard(board, "w");
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w", fen);
    }

    @Test
    public void translatePosTestOneArgument() {

        String pos = "a4";

        // int[] coords = new int[2];
        ArrayList<Integer> coords = new ArrayList<Integer>();
        coords = translatorService.translatePos(pos);

        // int[] actual = new int[2];
        ArrayList<Integer> actual = new ArrayList<Integer>();
        actual.add(0);
        actual.add(3);

        assertTrue(coords.equals(actual));

    }

    @Test
    public void translatePosTestTwoArguments() {

        String pos1 = "a4";
        String pos2 = "b4";

        ArrayList<ArrayList<Integer>> coords = translatorService.translatePos(pos1, pos2);

        ArrayList<Integer> actual1 = new ArrayList<Integer>();
        actual1.add(0);
        actual1.add(3);

        assertTrue(coords.get(0).equals(actual1));

        ArrayList<Integer> actual2 = new ArrayList<Integer>();
        actual2.add(1);
        actual2.add(3);

        assertTrue(coords.get(1).equals(actual2));

    }

    // Method needs to be made public to run tests on it
    // @Test
    // public void translateCoordsTest(){
    // String pos = translatorService.translateCoords(0,3);
    // String actual = "a4";
    // assertEquals(pos, actual);
    // }
    @Test
    public void translatePossibleMovesTest() {

        boolean[][] bolBoard = new boolean[8][8];

        for (int x = 0; x < bolBoard.length; x++) {
            for (int y = 0; y < bolBoard.length; y++) {

                if (x == 3) {
                    bolBoard[x][y] = true;
                } else {
                    bolBoard[x][y] = false;
                }
            }
        }

        ArrayList<String> possitions = translatorService.translatePossibleMoves(bolBoard);

        ArrayList<String> actual = new ArrayList<String>(Arrays.asList("d1", "d2", "d3", "d4", "d5", "d6", "d7", "d8"));

        for (int i = 0; i < possitions.size(); i++) {
            assertEquals(possitions.get(i), actual.get(i));
        }
    }
}
