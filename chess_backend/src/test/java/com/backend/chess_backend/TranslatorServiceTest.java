package com.backend.chess_backend;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;
import com.backend.chess_backend.model.Square;
import com.backend.chess_backend.model.TranslatorService;

@SpringBootTest
public class TranslatorServiceTest {
    
     private TranslatorService translatorService = new TranslatorService();

    @Test
    void testTranslatePos() {
        ArrayList<Integer> coordinates = translatorService.translatePos("e4");
        assertEquals(2, coordinates.size());
        assertEquals(4, coordinates.get(0));
        assertEquals(3, coordinates.get(1));
    }

    @Test
    void testTranslatePosTwoArgs() {
        ArrayList<ArrayList<Integer>> allCoordinates = translatorService.translatePos("e4", "e5");
        assertEquals(2, allCoordinates.size());
        assertEquals(4, allCoordinates.get(0).get(0));
        assertEquals(3, allCoordinates.get(0).get(1));
        assertEquals(4, allCoordinates.get(1).get(0));
        assertEquals(4, allCoordinates.get(1).get(1));
    }

    @Test
    void testTranslateCoords() {
        String coords = translatorService.translateCoords(4, 3);
        assertEquals("e4", coords);
    }

    @Test
    void testTranslatePossibleMoves() {
        boolean[][] bolBoard = new boolean[8][8];
        bolBoard[4][3] = true;
        ArrayList<String> posMoves = translatorService.translatePossibleMoves(bolBoard);
        assertEquals(1, posMoves.size());
        assertEquals("e4", posMoves.get(0));
    }

    @Test
    void testTranslateBoard() {
        Square[][] board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j);
                if ((i + j) % 2 == 0) {
                    Piece piece = PieceFactory.makePawn(PieceColor.WHITE, i, j);
                    board[i][j].setPiece(piece);
                }
            }
        }
        String boardString = translatorService.translateBoard(board, "w");
        assertNotNull(boardString);
        // Add more assertions based on your specific board setup and translateBoard logic
    }
}
