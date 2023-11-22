package com.backend.chess_backend;
import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Player;
import com.backend.chess_backend.socket.GameManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {GameManager.class})
public class GameManagerTest {
    private GameManager gameManager;
    private Player playerOne;
    private Player playerTwo;
    
    @BeforeEach
    void setUp() {
        gameManager.generateID();
        playerOne = new Player("uuid-player-1");
        playerTwo = new Player("uuid-player-2");
    }

    @Test
    void testJoinOrCreateGame_NewGame() {
        Game game = gameManager.joinOrCreateGame("TEST", playerOne.getUuid());
        System.out.println(game.getId());

        assertNotNull(game, "Game should not be null");
        assertFalse(game.isFull(), "Newly created game should not be full");
    }

    @Test
    void testJoinOrCreateGame_ExistingGame() {
        Game firstGame = gameManager.joinOrCreateGame("TEST", playerOne.getUuid());
        Game secondGame = gameManager.joinOrCreateGame("TEST", playerTwo.getUuid());

        assertEquals(firstGame, secondGame, "Both players should be in the same game");
        assertTrue(secondGame.isFull(), "Game should be full after two players join");
    }

    // Additional tests as needed...
}
