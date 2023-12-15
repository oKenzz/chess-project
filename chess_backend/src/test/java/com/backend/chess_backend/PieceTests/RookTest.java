package com.backend.chess_backend.PieceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.Rook;

@SpringBootTest
public class RookTest {
    @Test
    void testConstructor() {
        Rook rook = new Rook(PieceColor.WHITE, 1, 1);
        assertEquals(PieceColor.WHITE, rook.getColor());
        assertEquals(1, rook.getX());
        assertEquals(1, rook.getY());
    }

    @Test
    void testGetPossibleMoves() {
        Rook rook = new Rook(PieceColor.WHITE, 1, 1);
        boolean[][] possibleMoves = rook.getPossibleMoves(8, 8);
        assertFalse(possibleMoves[1][1]); // it can't move to its own position
        assertTrue(possibleMoves[0][1]); // it can move horizontally
        assertTrue(possibleMoves[2][1]); // it can move horizontally
        assertTrue(possibleMoves[1][0]); // it can move vertically
        assertTrue(possibleMoves[1][2]); // it can move vertically
        assertFalse(possibleMoves[0][0]); // it can't move diagonally
    }

    @Test
    void testGetPieceType() {
        Rook rookWhite = new Rook(PieceColor.WHITE, 1, 1);
        assertEquals(PieceTypeEnum.WHITE_ROOK, rookWhite.getPieceType());

        Rook rookBlack = new Rook(PieceColor.BLACK, 1, 1);
        assertEquals(PieceTypeEnum.BLACK_ROOK, rookBlack.getPieceType());
    }
}
