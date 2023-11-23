package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
class PawnTest {

    @Test
    public void TestGetPossibleMoves(){
        Pawn pawn = new Pawn(PieceColor.WHITE, 0, 1);
        Boolean[][] BoardList = new Boolean[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardList[i][j] = false;
            }
        } 
        BoardList[0][2] = true;
        BoardList[1][2] = true;
        BoardList[0][3] = true;

        assertArrayEquals(pawn.getPossibleMoves(8, 8), BoardList);
    }

}
