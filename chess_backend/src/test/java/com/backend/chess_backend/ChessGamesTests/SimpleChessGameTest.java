package com.backend.chess_backend.ChessGamesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.ChessGames.SimpleChessGame;
import com.backend.chess_backend.model.Constants.GameOverEnum;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
public class SimpleChessGameTest {
    @Test
    void testAttemptMove() {
        SimpleChessGame game = new SimpleChessGame("testGame");

        // Test a valid move
        boolean result = game.attemptMove(1, 1, 1, 2);
        assertTrue(result);

        // Test an invalid move
        result = game.attemptMove(1, 0, 3, 0);
        assertFalse(result);
    }

    @Test
    void testGetRandomMove() {
        SimpleChessGame game = new SimpleChessGame("testGame");

        // Test getting a random move for white
        int[][] move = game.getRandomMove(PieceColor.WHITE);
        assertNotNull(move);

        // Test getting a random move for black
        move = game.getRandomMove(PieceColor.BLACK);
        assertNotNull(move);
    }

    @Test
    void testRestartGame() {
        SimpleChessGame game = new SimpleChessGame("testGame");

        // Make a move
        game.attemptMove(1, 0, 2, 0);

        // Restart the game
        game.restartGame();

        // The board should be in its initial state
        // You can add assertions here to check the state of the board
    }

    @Test
    void testSurrender() {
        SimpleChessGame game = new SimpleChessGame("testGame");

        // Add a player
        game.addPlayer("player1", false);

        // The player surrenders
        game.surrender("player1");

        // The game should be over
        assertEquals(GameOverEnum.BLACK, game.checkGameOver());
    }

    @Test
    void testAddPlayer() {
        SimpleChessGame game = new SimpleChessGame("testGame");

        // Add a player
        game.addPlayer("player1", false);
        assertNotNull(game.getPlayer("player1"));
    }

    @Test
    void testRemovePlayer() {
        SimpleChessGame game = new SimpleChessGame("testGame");

        // Add a player
        game.addPlayer("player1", false);

        // Remove the player
        game.removePlayer("player1");
        assertNull(game.getPlayer("player1"));

    }

    @Test
    void testCheckGameOver() {
        SimpleChessGame game = new SimpleChessGame("testGame");

        // The game should not be over at the start
        GameOverEnum result = game.checkGameOver();
        assertEquals(GameOverEnum.NOT_OVER, result);

        // Add more assertions here to test different game states
    }
}
