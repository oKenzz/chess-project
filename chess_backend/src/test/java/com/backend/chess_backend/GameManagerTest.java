package com.backend.chess_backend;
import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Player;
import com.backend.chess_backend.socket.GameManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest {
    private GameManager gameManager;
    private Player playerOne;
    private Player playerTwo;

    @BeforeEach
    void setUp() {
        GameManager.reset(); // Reset GameManager to its initial state before each test
        gameManager = GameManager.getInstance();
        playerOne = new Player(null, "uuid-player-1");
        playerTwo = new Player(null, "uuid-player-2");
    }

    @Test
    void testJoinOrCreateGame_NewGame() {
        Game game = gameManager.joinOrCreateGame(playerOne);
        System.out.println(game.getId());

        assertNotNull(game, "Game should not be null");
        assertFalse(game.isFull(), "Newly created game should not be full");
    }

    @Test
    void testJoinOrCreateGame_ExistingGame() {
        Game firstGame = gameManager.joinOrCreateGame(playerOne);
        Game secondGame = gameManager.joinOrCreateGame(playerTwo);

        assertEquals(firstGame, secondGame, "Both players should be in the same game");
        assertTrue(secondGame.isFull(), "Game should be full after two players join");
    }

    // Additional tests as needed...
}
