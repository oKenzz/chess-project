package com.backend.chess_backend.PieceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.Knight;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
public class KnightTest {

    @Test
    void testConstructor() {
        Knight knight = new Knight(PieceColor.WHITE, 1, 1);
        assertEquals(PieceColor.WHITE, knight.getColor());
        assertEquals(1, knight.getX());
        assertEquals(1, knight.getY());
    }

    @Test
    void testGetPossibleMoves() {
        Knight knight = new Knight(PieceColor.WHITE, 2, 2);
        boolean[][] possibleMoves = knight.getPossibleMoves(8, 8);
        assertFalse(possibleMoves[2][2]); // it can't move to its own position
        assertTrue(possibleMoves[0][1]); // it can move in L shape
        assertTrue(possibleMoves[3][4]); // it can move in L shape
        assertFalse(possibleMoves[2][3]); // it can't move straight
    }

    @Test
    void testGetPieceType() {
        Knight knightWhite = new Knight(PieceColor.WHITE, 1, 1);
        assertEquals(PieceTypeEnum.WHITE_KNIGHT, knightWhite.getPieceType());

        Knight knightBlack = new Knight(PieceColor.BLACK, 1, 1);
        assertEquals(PieceTypeEnum.BLACK_KNIGHT, knightBlack.getPieceType());
    }
}
