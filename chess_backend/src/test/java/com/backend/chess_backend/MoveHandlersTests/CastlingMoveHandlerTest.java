package com.backend.chess_backend.MoveHandlersTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.CastlingMoveHandler;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class CastlingMoveHandlerTest {
    @Test
    void testValidateCastlingMoves() {
        // Set up a board and pieces in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();
        board.move(currentBoard[6][0].getPiece(), 5, 2);
        board.move(currentBoard[5][0].getPiece(), 2, 3);
        boolean[][] movelist = new boolean[8][8];

        // Validate castling moves
        CastlingMoveHandler.validateCastlingMoves(currentBoard[4][0].getPiece(), movelist, board);

        // Check that the movelist was updated correctly
        assertTrue(movelist[6][0]);

        // Repeat this process for different board and piece states
        // For example, you could set up a board where a castling move is not possible
        // Then call validateCastlingMoves and check that the movelist was not updated
    }

    @Test
    void testMakeCastleMove() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();
        board.move(currentBoard[6][0].getPiece(), 5, 2);
        board.move(currentBoard[5][0].getPiece(), 2, 3);
        
        // Make a castle move
        CastlingMoveHandler.makeCastleMove(4, 0, 6, 0, board);

        // Check that the move was made correctly
        assertNull(board.getBoard()[7][0].getPiece());
        assertNotNull(board.getBoard()[5][0].getPiece());

        // Repeat this process for different types of castle moves
    }

    @Test
    void testIsCastleMove() {
        // Set up a piece in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();
        // Check if a move is a castle move
        boolean result = CastlingMoveHandler.isCastleMove(currentBoard[4][0].getPiece(), 6, 0, board);
        assertTrue(result);

        // Repeat this process for different types of moves
        // For example, you could check if a non-castle move is not a castle move
    }
}
