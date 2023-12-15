package com.backend.chess_backend.PieceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
public class PawnTest {
    @Test
    void testConstructor() {
        Pawn pawn = new Pawn(PieceColor.WHITE, 1, 1);
        assertEquals(PieceColor.WHITE, pawn.getColor());
        assertEquals(1, pawn.getX());
        assertEquals(1, pawn.getY());
    }

    @Test
    void testGetPossibleMoves() {
        Pawn pawn = new Pawn(PieceColor.WHITE, 1, 1);
        boolean[][] possibleMoves = pawn.getPossibleMoves(8, 8);
        assertFalse(possibleMoves[1][1]); // it can't move to its own position
        assertTrue(possibleMoves[1][2]); // it can move forward
        assertTrue(possibleMoves[0][2]); // it can capture diagonally
        assertTrue(possibleMoves[2][2]); // it can capture diagonally
        assertFalse(possibleMoves[1][0]); // it can't move backwards
    }

    @Test
    void testGetPieceType() {
        Pawn pawnWhite = new Pawn(PieceColor.WHITE, 1, 1);
        assertEquals(PieceTypeEnum.WHITE_PAWN, pawnWhite.getPieceType());

        Pawn pawnBlack = new Pawn(PieceColor.BLACK, 1, 1);
        assertEquals(PieceTypeEnum.BLACK_PAWN, pawnBlack.getPieceType());
    }
}
