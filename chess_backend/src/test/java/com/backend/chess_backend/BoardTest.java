package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Game;
import com.backend.chess_backend.model.Translator;
import com.backend.chess_backend.model.Pieces.Piece;

@SpringBootTest

public class BoardTest {
    @Test
    public void enpassantTest() {
        Board board = new Board();
        board.reset();
        Piece[][] tmp = board.getBoard();
        board.updateCoords(tmp[5][6], 5, 4);
        board.updateCoords(tmp[4][1], 4, 3);
        board.updateCoords(tmp[4][3], 4, 4);
        Boolean[][] testlist = board.getPossibleMoves(tmp[4][4]);
        assertTrue(board.checkIfallowed(5, 5, testlist));
    }

    @Test
    public void createBoardTest() {
        Board board = new Board();
        Piece[][] tmp = board.getBoard();
        board.reset();
        Translator translator = new Translator();
        String fen = translator.translateBoard(tmp, "w");
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w", fen);
    }

    @Test
    public void possiblemovetest() {
        Board board = new Board();
        board.reset();
        Piece[][] tmp = board.getBoard();
        // for (int i=0; i<7; i++){
        Boolean[][] moves = board.getPossibleMoves(tmp[5][6]);
        assertTrue(moves[5][5]);
        assertTrue(moves[5][4]);
        assertTrue(!moves[4][5]);
        assertTrue(!moves[6][5]);
        // for (int x=0; x<8;x++){
        // Boolean[][] moves=board.getPossibleMoves(tmp[x][7]);
        // assertEquals("p", tmp[x][6].getPieceType());

        // for(int y=0;y<8;y++){
        // assertTrue(!moves[x][y]);
        // }
        // }
        // }
    }

}
