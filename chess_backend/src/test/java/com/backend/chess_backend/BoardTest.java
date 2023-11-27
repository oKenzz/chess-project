package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Pieces.Piece;

@SpringBootTest


public class BoardTest {
    @Test
    public void enpassantTest(){
        Board board=new Board();
        board.reset();
        Piece[][] tmp=board.getBoard();
        board.updateCoords(tmp[5][6], 5, 4);
        board.updateCoords(tmp[4][1], 4, 3);
        board.updateCoords(tmp[4][3], 4, 4);
        Boolean[][] testlist=board.getPossibleMoves(tmp[4][4]);
        assertTrue(board.checkIfallowed(5, 5, testlist));
    }
    
}
