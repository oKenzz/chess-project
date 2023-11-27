package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Game;

@SpringBootTest
class GameTest {

    @Test
    public void GameTest(){
        String testId = "ABC1";
        Game game = new Game(testId);

        Boolean[][] BoardList = new Boolean[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardList[i][j] = false;
            }
        } 

        // testing knight 
        BoardList[0][2] = true;
        BoardList[2][2] = true;

        assertArrayEquals(game.possibleMoves(1, 0), BoardList);


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardList[i][j] = false;
            }
        } 

        // testing pawn 
        BoardList[0][2] = true;
        BoardList[0][3] = true;

        assertArrayEquals(game.possibleMoves(0, 1), BoardList);

    }
}