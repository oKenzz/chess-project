package com.backend.chess_backend.PieceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.Queen;

@SpringBootTest
public class QueenTest {
    @Test
    void testConstructor() {
        Queen queen = new Queen(PieceColor.WHITE, 1, 1);
        assertEquals(PieceColor.WHITE, queen.getColor());
        assertEquals(1, queen.getX());
        assertEquals(1, queen.getY());
    }

    @Test
    void testGetPossibleMoves() {
        Queen queen = new Queen(PieceColor.WHITE, 1, 1);
        boolean[][] possibleMoves = queen.getPossibleMoves(8, 8);
        assertFalse(possibleMoves[1][1]); // it can't move to its own position
        assertTrue(possibleMoves[0][1]); // it can move horizontally
        assertTrue(possibleMoves[2][1]); // it can move horizontally
        assertTrue(possibleMoves[1][0]); // it can move vertically
        assertTrue(possibleMoves[1][2]); // it can move vertically
        assertTrue(possibleMoves[0][0]); // it can move diagonally
        assertTrue(possibleMoves[2][2]); // it can move diagonally
    }

    @Test
    void testGetPieceType() {
        Queen queenWhite = new Queen(PieceColor.WHITE, 1, 1);
        assertEquals(PieceTypeEnum.WHITE_QUEEN, queenWhite.getPieceType());

        Queen queenBlack = new Queen(PieceColor.BLACK, 1, 1);
        assertEquals(PieceTypeEnum.BLACK_QUEEN, queenBlack.getPieceType());
    }
}
