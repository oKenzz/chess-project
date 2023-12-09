package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
class KingTest {

    @Test
    public void TestGetPossibleMoves(){
        King king = new King(PieceColor.WHITE, 2, 2);
        Boolean[][] BoardList = new Boolean[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardList[i][j] = false;
            }
        } 

        BoardList[1][3] = true;
        BoardList[1][2] = true;
        BoardList[1][1] = true;

        BoardList[2][3] = true;
        BoardList[2][1] = true;

        BoardList[3][3] = true;
        BoardList[3][2] = true;
        BoardList[3][1] = true;

        assertArrayEquals(king.getPossibleMoves(8, 8), BoardList);
    }

}
