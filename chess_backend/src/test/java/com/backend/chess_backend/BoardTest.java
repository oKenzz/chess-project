package com.backend.chess_backend;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
public class BoardTest {
    

        @Test
    void testDefaultConstructor() {
        Board board = new Board();
        assertNotNull(board.getBoard());
        assertEquals(8, board.getBoardHeight());
        assertEquals(8, board.getBoardWidth());
    }

    @Test
    void testParameterizedConstructor() {
        Board board = new Board(10, 10);
        assertNotNull(board.getBoard());
        assertEquals(10, board.getBoardHeight());
        assertEquals(10, board.getBoardWidth());
    }

    @Test
    void testGetPiece() {
        Board board = new Board();
        Piece piece = board.getPiece(0, 0);
        assertNotNull(piece);
    }

    @Test
    void testContainsPiece() {
        Board board = new Board();
        boolean contains = board.containsPiece(0, 0);
        assertTrue(contains);
    }

    @Test
    void testIsMoveAllowed() {
        Board board = new Board();
        boolean[][] possibleMoves = new boolean[8][8];
        possibleMoves[0][1] = true;
        assertTrue(board.isMoveAllowed(0, 1, possibleMoves));
    }

    @Test
    void testGetAllPlayerPieces() {
        Board board = new Board();
        List<Piece> pieces = board.getAllPlayerPieces(PieceColor.WHITE);
        assertEquals(pieces.size(), 16);
    }

    @Test
    void testGetAllPieces() {
        Board board = new Board();
        List<Piece> pieces = board.getAllPieces();
        assertFalse(pieces.isEmpty());
    }
    @Test
    void testMove() {
        Board board = new Board();
        Piece piece = board.getPiece(0, 0);
        board.move(piece, 0, 1);
        assertNull(board.getPiece(0, 0));
        assertEquals(piece, board.getPiece(0, 1));
    }

    @Test
    void testUndoMove() {
        Board board = new Board();
        Piece piece = board.getPiece(0, 0);
        board.move(piece, 0, 1);
        board.undoMove();
        assertEquals(piece, board.getPiece(0, 0));
    }

    @Test
    void testGetBoardHeight() {
        Board board = new Board();
        assertEquals(8, board.getBoardHeight());
    }

    @Test
    void testGetBoardWidth() {
        Board board = new Board();
        assertEquals(8, board.getBoardWidth());
    }

    @Test
    void testGetbKingPosition() {
        Board board = new Board();
        int[] position = board.getbKingPosition();
        assertNotNull(position);
        assertEquals(2, position.length);
    }

    @Test
    void testGetwKingPosition() {
        Board board = new Board();
        int[] position = board.getwKingPosition();
        assertNotNull(position);
        assertEquals(2, position.length);
    }

}


