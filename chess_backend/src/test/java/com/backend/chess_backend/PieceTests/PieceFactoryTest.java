package com.backend.chess_backend.PieceTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Bishop;
import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.Knight;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;
import com.backend.chess_backend.model.Pieces.Queen;
import com.backend.chess_backend.model.Pieces.Rook;

@SpringBootTest
public class PieceFactoryTest {
    @Test
    void testMakePawn() {
        Piece pawn = PieceFactory.makePawn(PieceColor.WHITE, 1, 1);
        assertTrue(pawn instanceof Pawn);
        assertEquals(PieceColor.WHITE, pawn.getColor());
        assertEquals(1, pawn.getX());
        assertEquals(1, pawn.getY());
    }

    @Test
    void testMakeRook() {
        Piece rook = PieceFactory.makeRook(PieceColor.WHITE, 1, 1);
        assertTrue(rook instanceof Rook);
        assertEquals(PieceColor.WHITE, rook.getColor());
        assertEquals(1, rook.getX());
        assertEquals(1, rook.getY());
    }

    @Test
    void testMakeKnight() {
        Piece knight = PieceFactory.makeKnight(PieceColor.WHITE, 1, 1);
        assertTrue(knight instanceof Knight);
        assertEquals(PieceColor.WHITE, knight.getColor());
        assertEquals(1, knight.getX());
        assertEquals(1, knight.getY());
    }

    @Test
    void testMakeBishop() {
        Piece bishop = PieceFactory.makeBishop(PieceColor.WHITE, 1, 1);
        assertTrue(bishop instanceof Bishop);
        assertEquals(PieceColor.WHITE, bishop.getColor());
        assertEquals(1, bishop.getX());
        assertEquals(1, bishop.getY());
    }

    @Test
    void testMakeQueen() {
        Piece queen = PieceFactory.makeQueen(PieceColor.WHITE, 1, 1);
        assertTrue(queen instanceof Queen);
        assertEquals(PieceColor.WHITE, queen.getColor());
        assertEquals(1, queen.getX());
        assertEquals(1, queen.getY());
    }

    @Test
    void testMakeKing() {
        Piece king = PieceFactory.makeKing(PieceColor.WHITE, 1, 1);
        assertTrue(king instanceof King);
        assertEquals(PieceColor.WHITE, king.getColor());
        assertEquals(1, king.getX());
        assertEquals(1, king.getY());
    }
}
