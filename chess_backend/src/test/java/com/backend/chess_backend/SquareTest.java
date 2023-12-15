package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class SquareTest {
    

    @Test
    void testConstructor() {
        Square square = new Square(0, 0);
        assertEquals(0, square.getX());
        assertEquals(0, square.getY());
        assertNull(square.getPiece());
    }

    @Test
    void testSetPiece() {
        Square square = new Square(0, 0);
        Piece piece = PieceFactory.makePawn(PieceColor.WHITE, 0, 0);
        square.setPiece(piece);
        assertEquals(piece, square.getPiece());
    }

    @Test
    void testRemovePiece() {
        Square square = new Square(0, 0);
        Piece piece = PieceFactory.makePawn(PieceColor.WHITE, 0, 0);        square.setPiece(piece);
        square.removePiece();
        assertNull(square.getPiece());
    }

    @Test
    void testContainsPiece() {
        Square square = new Square(0, 0);
        Piece piece = PieceFactory.makePawn(PieceColor.WHITE, 0, 0);
        square.setPiece(piece);
        assertTrue(square.containsPiece());
    }

    @Test
    void testContainsSpecificPiece() {
        Square square = new Square(0, 0);
        Piece piece = PieceFactory.makePawn(PieceColor.WHITE, 0, 0);
        square.setPiece(piece);
        assertTrue(square.containsPiece(piece));
    }

    @Test
    void testToString() {
        Square square = new Square(0, 0);
        String str = square.toString();
        assertTrue(str.contains("x=0"));
        assertTrue(str.contains("y=0"));
        assertTrue(str.contains("piece=None"));
    }
}
