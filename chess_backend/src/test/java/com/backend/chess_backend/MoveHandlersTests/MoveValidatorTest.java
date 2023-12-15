package com.backend.chess_backend.MoveHandlersTests;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.MoveValidator;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class MoveValidatorTest {

    @Test
    void testGetPossibleMoves() {
        Board board = new Board();
        Square square = board.getBoard()[1][1];
        Piece pawn = square.getPiece();
        boolean[][] posMoves = MoveValidator.getPossibleMoves(pawn, board);
        assertNotNull(posMoves);
        assertFalse(posMoves[1][1]);
        assertTrue(posMoves[1][2]);
        assertTrue(posMoves[1][3]);

    }

    @Test
    void testRemoveCheckMoves() {
        Piece piece = PieceFactory.makePawn(PieceColor.WHITE, 0, 0);
        Board board = new Board();
        boolean[][] movelist = new boolean[board.getBoardWidth()][board.getBoardHeight()];
        MoveValidator.removeCheckMoves(piece, movelist, board);
        // Add assertions to check if the method works as expected
    }

    @Test
    void testPrimitivePossibleMoves() {
        Board board = new Board();
        Square square = board.getBoard()[1][1];
        Piece pawn = square.getPiece();
        boolean[][] posMoves = MoveValidator.primitivePossibleMoves(pawn, board);
        assertNotNull(posMoves);
        assertFalse(posMoves[1][1]);
        assertTrue(posMoves[1][2]);
        assertTrue(posMoves[1][3]);
    }

    @Test
    void testThreatenedSquare() {
        Board board = new Board();
        assertFalse(MoveValidator.threatenedSquare(5, 4, PieceColor.BLACK, board));
        assertTrue(MoveValidator.threatenedSquare(5, 4, PieceColor.WHITE, board));
    }

    @Test
    void testGetAllPossiblePlayerMoves() {
        Board board = new Board();
        List<Piece> pieces = board.getAllPlayerPieces(PieceColor.WHITE);
        assertNotNull(MoveValidator.getAllPossiblePlayerMoves(pieces, board));
        assertEquals(10, MoveValidator.getAllPossiblePlayerMoves(pieces, board).size());
    }
}
