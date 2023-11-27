package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
class PawnTest {

    @Test
    public void TestGetPossibleMoves(){
        Pawn pawn = new Pawn(PieceColor.BLACK, 5, 6);
        Boolean[][] BoardList = new Boolean[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardList[i][j] = false;
            }
        } 
        BoardList[5][5] = true;
        BoardList[5][4] = true;
        BoardList[4][5] = true;
        BoardList[6][5] = true;

        assertArrayEquals(pawn.getPossibleMoves(8, 8), BoardList);
    }

}
