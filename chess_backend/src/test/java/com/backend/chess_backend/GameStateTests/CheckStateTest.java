package com.backend.chess_backend.GameStateTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.GameStates.CheckState;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class CheckStateTest {
    @Test
    void testIsChecked() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if the board is in check
        boolean result = CheckState.isChecked(board);
        assertFalse(result);

        board.move(currentBoard[5][1].getPiece(), 5, 3);
        board.move(currentBoard[4][6].getPiece(), 4, 4);
        board.move(currentBoard[6][0].getPiece(), 5, 2);
        board.move(currentBoard[3][7].getPiece(), 7, 3);
        
        result = CheckState.isChecked(board);
        assertTrue(result);
    }

    @Test
    void testIsWhiteChecked() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if the board is in check
        boolean result = CheckState.isWhiteChecked(board);
        assertFalse(result);

        board.move(currentBoard[5][1].getPiece(), 5, 3);
        board.move(currentBoard[4][6].getPiece(), 4, 4);
        board.move(currentBoard[6][0].getPiece(), 5, 2);
        board.move(currentBoard[3][7].getPiece(), 7, 3);
        
        result = CheckState.isChecked(board);
        assertTrue(result);
    }

    @Test
    void testIsBlackChecked() {

        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if black is checked
        boolean result = CheckState.isBlackChecked(board);
        assertFalse(result);

        board.move(currentBoard[4][1].getPiece(), 4, 3);
        board.move(currentBoard[5][6].getPiece(), 5, 4);
        board.move(currentBoard[3][0].getPiece(), 7, 4);

        result = CheckState.isBlackChecked(board);
        assertTrue(result);
    }
}
