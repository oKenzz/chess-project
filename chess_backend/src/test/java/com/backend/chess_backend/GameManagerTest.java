package com.backend.chess_backend;
import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Player;
import com.backend.chess_backend.socket.GameManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = GameManager.class)
public class GameManagerTest {
    @Autowired
    private GameManager gameManager;
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private Player playerFive;

    
    @BeforeEach
    void setUp() {
        gameManager.reset();
        playerOne = new Player("uuid-player-1");
        playerTwo = new Player("uuid-player-2");
        playerThree = new Player("uuid-player-3");
        playerFour = new Player("uuid-player-4");
        playerFive = new Player("uuid-player-4");
    }

    @Test
    void testCreateGame() {
        Game game = gameManager.createGame(playerOne.getUuid());

        assertNotNull(game, "Game should not be null");
        assertFalse(game.isFull(), "Newly created game should not be full");
    }

    @Test
    void testJoinGame() {
        Game firstGame = gameManager.createGame(playerOne.getUuid());
        String gameId = gameManager.getGameIdByPlayerUuid(playerOne.getUuid());
        Game secondGame = gameManager.join(gameId, playerTwo.getUuid());

        assertEquals(firstGame, secondGame, "Both players should be in the same game");
        assertTrue(secondGame.isFull(), "Game should be full after two players join");
    }

    @Test
    void testJoinRandomGame(){
        Game firstGame = gameManager.createGame(playerOne.getUuid());
        Game secondGame = gameManager.joinRandomGame(playerTwo.getUuid());
        Game thirdGame = gameManager.joinRandomGame(playerThree.getUuid());
        Game fourthGame = gameManager.joinRandomGame(playerThree.getUuid());
        Game fifthGame = gameManager.joinRandomGame(playerThree.getUuid());


        assertEquals(firstGame, secondGame, "Both players should be in the same game");
        assertEquals(thirdGame, fourthGame, "Should be the same game");
        assertTrue(!fifthGame.isFull(), "Should not be full");
        assertTrue(secondGame.isFull(), "Game should be full after two players join");

    }

    // Additional tests as needed...
}
