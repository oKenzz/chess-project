package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.Knight;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

@SpringBootTest
class KnightTest {

    @Test
    public void TestGetPossibleMoves(){
        Knight knight = new Knight(PieceColor.WHITE, 2, 2);
        Boolean[][] BoardList = new Boolean[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardList[i][j] = false;
            }
        } 

        BoardList[0][3] = true;
        BoardList[1][4] = true;

        BoardList[3][4] = true;
        BoardList[4][3] = true;

        BoardList[4][1] = true; 
        BoardList[3][0] = true;

        BoardList[1][0] = true;
        BoardList[0][1] = true;

        assertArrayEquals(knight.getPossibleMoves(8, 8), BoardList);
    }

}
