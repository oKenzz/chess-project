package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Player;

@SpringBootTest
public class PlayerTest {

    @Test
    void testConstructor() {
        Player player = new Player(true, 100);
        assertTrue(player.isBot());
        assertEquals(100, player.getTimeLeft());
    }

    @Test
    void testSetPlayerID() {
        Player player = new Player(true, 100);
        player.setPlayerID("1234");
        assertEquals("1234", player.getUuid());
        assertTrue(player.isOccupied());
    }

    @Test
    void testEmptyPlayer() {
        Player player = new Player(true, 100);
        player.setPlayerID("1234");
        player.emptyPlayer();
        assertNull(player.getUuid());
        assertFalse(player.isOccupied());
    }

    @Test
    void testSetBot() {
        Player player = new Player(true, 100);
        player.setBot(false);
        assertFalse(player.isBot());
    }

    @Test
    void testStartAndPauseTimer() throws InterruptedException {
        Player player = new Player(true, 100);
        player.startTimer();
        Thread.sleep(2000); // wait for 2 seconds
        player.pauseTimer();
        assertTrue(player.getTimeLeft() <= 98); // time should have decreased
    }

    @Test
    void testTimerNotRunningInitially() {
        Player player = new Player(true, 100);
        assertEquals(100, player.getTimeLeft());
    }

    @Test
    void testTimeLeftDecreasesWhenTimerIsRunning() throws InterruptedException {
        Player player = new Player(true, 100);
        player.startTimer();
        Thread.sleep(2000); // wait for 2 seconds
        assertTrue(player.getTimeLeft() < 100); // time should have decreased
    }

    @Test
    void testTimeLeftDoesNotDecreaseWhenTimerIsPaused() throws InterruptedException {
        Player player = new Player(true, 100);
        player.startTimer();
        Thread.sleep(2000); // wait for 2 seconds
        player.pauseTimer();
        int timeLeft = player.getTimeLeft();
        Thread.sleep(2000); // wait for 2 more seconds
        assertEquals(timeLeft, player.getTimeLeft()); // time should not have decreased
    }

    @Test
    void testTimeLeftDoesNotGoBelowZero() throws InterruptedException {
        Player player = new Player(true, 1);
        player.startTimer();
        Thread.sleep(2000); // wait for 2 seconds
        assertTrue(player.getTimeLeft() >= 0); // time should not go below zero
    }
}
