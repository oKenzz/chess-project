package com.backend.chess_backend.PieceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
public class KingTest {
    
    @Test
    void testConstructor() {
        King king = new King(PieceColor.WHITE, 1, 1);
        assertEquals(PieceColor.WHITE, king.getColor());
        assertEquals(1, king.getX());
        assertEquals(1, king.getY());
    }

    @Test
    void testGetPossibleMoves() {
        King king = new King(PieceColor.WHITE, 1, 1);
        boolean[][] possibleMoves = king.getPossibleMoves(8, 8);
        assertFalse(possibleMoves[1][1]); // it can't move to its own position
        assertTrue(possibleMoves[0][0]); // it can move diagonally
        assertTrue(possibleMoves[0][1]); // it can move vertically
        assertTrue(possibleMoves[1][0]); // it can move horizontally
        assertTrue(possibleMoves[2][2]); // it can move diagonally
    }

    @Test
    void testGetPieceType() {
        King kingWhite = new King(PieceColor.WHITE, 1, 1);
        assertEquals(PieceTypeEnum.WHITE_KING, kingWhite.getPieceType());

        King kingBlack = new King(PieceColor.BLACK, 1, 1);
        assertEquals(PieceTypeEnum.BLACK_KING, kingBlack.getPieceType());
    }
}
