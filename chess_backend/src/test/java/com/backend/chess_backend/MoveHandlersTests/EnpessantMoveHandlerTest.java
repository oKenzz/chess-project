package com.backend.chess_backend.MoveHandlersTests;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.EnpessantMoveHandler;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;

@SpringBootTest
public class EnpessantMoveHandlerTest {
    @Test
    void testIsEnpessantMove() {
        // Set up a board and pieces in a specific state
        Board board = new Board();
        board.move(board.getBoard()[1][1].getPiece(), 1, 4);
        board.move(board.getBoard()[0][6].getPiece(), 0, 4);
        // Check if it's an en passant move
        boolean result = EnpessantMoveHandler.isEnpessantMove(board.getBoard()[1][4].getPiece(), 0, 5, board);
        assertTrue(result);

    }

    @Test
    void testValidateEnpessantMove() {
        // Set up a board and pieces in a specific state
        Board board = new Board();
        
        board.move(board.getBoard()[1][1].getPiece(), 1, 4);
        board.move(board.getBoard()[0][6].getPiece(), 0, 4);
        boolean[][] movelist = new boolean[8][8];

        // Validate the en passant move
        EnpessantMoveHandler.validateEnpessantMove(board.getBoard()[1][4].getPiece(), movelist, board);

        // Check that the move list was updated correctly
        assertTrue(movelist[0][5]);
    }

    @Test
    void testMakeEnpessantMove() {
        // Set up a board and pieces in a specific state
        Board board = new Board();
        Piece piece = PieceFactory.makePawn(PieceColor.WHITE, 1, 5);
        board.getBoard()[1][5].setPiece(piece);

        // Make the en passant move
        EnpessantMoveHandler.makeEnpessantMove(PieceColor.WHITE, 1, 5, board);

        // Check that the piece was removed from the correct square
        assertNull(board.getBoard()[1][4].getPiece());
    }
}
