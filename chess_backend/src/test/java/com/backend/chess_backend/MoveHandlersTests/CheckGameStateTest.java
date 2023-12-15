package com.backend.chess_backend.MoveHandlersTests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.CheckGameState;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class CheckGameStateTest {
    
    @Test
    void testWhiteChecked() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if white is checked
        boolean result = CheckGameState.whiteChecked(board);
        assertFalse(result);

        board.move(currentBoard[5][1].getPiece(), 5, 3);
        board.move(currentBoard[4][6].getPiece(), 4, 4);
        board.move(currentBoard[6][0].getPiece(), 5, 2);
        board.move(currentBoard[3][7].getPiece(), 7, 3);

        result = CheckGameState.whiteChecked(board);
        assertTrue(result);
    }

    @Test
    void testBlackChecked() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if black is checked
        boolean result = CheckGameState.blackChecked(board);
        assertFalse(result);

        board.move(currentBoard[4][1].getPiece(), 4, 3);
        board.move(currentBoard[5][6].getPiece(), 5, 4);
        board.move(currentBoard[3][0].getPiece(), 7, 4);

        result = CheckGameState.blackChecked(board);
        assertTrue(result);
        
    }
}
