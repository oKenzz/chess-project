package com.backend.chess_backend.GameStateTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.GameStates.CheckMateState;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class CheckMateStateTest {
    @Test
    void testIsWhiteCheckmated() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if white is checkmated
        boolean result = CheckMateState.isWhiteCheckmated(board);
        assertFalse(result);

        board.move(currentBoard[5][1].getPiece(), 5, 2);
        board.move(currentBoard[4][6].getPiece(), 4, 5);
        board.move(currentBoard[6][1].getPiece(), 6, 3);
        board.move(currentBoard[3][7].getPiece(), 7, 3);

        result = CheckMateState.isWhiteCheckmated(board);
        assertTrue(result);
    }

    @Test
    void testIsBlackCheckmated() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if black is checkmated
        boolean result = CheckMateState.isBlackCheckmated(board);
        assertFalse(result);

        board.move(currentBoard[4][1].getPiece(), 4, 3);
        board.move(currentBoard[5][6].getPiece(), 5, 3);
        board.move(currentBoard[6][6].getPiece(), 6, 4);
        board.move(currentBoard[3][0].getPiece(), 7, 4);

        result = CheckMateState.isBlackCheckmated(board);
        assertTrue(result);

    }
}
