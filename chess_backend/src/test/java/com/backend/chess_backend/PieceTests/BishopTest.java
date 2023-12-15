package com.backend.chess_backend.PieceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.Pieces.Bishop;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
public class BishopTest {

    @Test
    void testConstructor() {
        Bishop bishop = new Bishop(PieceColor.WHITE, 1, 1);
        assertEquals(PieceColor.WHITE, bishop.getColor());
        assertEquals(1, bishop.getX());
        assertEquals(1, bishop.getY());
    }

    @Test
    void testGetPossibleMoves() {
        Bishop bishop = new Bishop(PieceColor.WHITE, 1, 1);
        boolean[][] possibleMoves = bishop.getPossibleMoves(8, 8);
        assertFalse(possibleMoves[1][1]); // it can't move to its own position
        assertTrue(possibleMoves[0][0]); // it can move diagonally
        assertTrue(possibleMoves[2][2]); // it can move diagonally
        assertFalse(possibleMoves[1][2]); // it can't move straight
    }

    @Test
    void testGetPieceType() {
        Bishop bishopWhite = new Bishop(PieceColor.WHITE, 1, 1);
        assertEquals(PieceTypeEnum.WHITE_BISHOP, bishopWhite.getPieceType());

        Bishop bishopBlack = new Bishop(PieceColor.BLACK, 1, 1);
        assertEquals(PieceTypeEnum.BLACK_BISHOP, bishopBlack.getPieceType());
    }
}
