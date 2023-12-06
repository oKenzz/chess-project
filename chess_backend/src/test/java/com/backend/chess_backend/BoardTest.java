package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Translator;

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
        Piece[][] tmp=board.getBoard();
        //for (int i=0; i<7; i++){
        board.updateCoords(tmp[3][1], 3, 5);
        Boolean[][] moves=board.getPossibleMoves(tmp[3][6]);
        assertTrue(moves[3][4]==false);
        Boolean[][] moves2=board.getPossibleMoves(tmp[0][0]);
        assertTrue(moves2[7][0]==false);
        Boolean[][] moves3=board.getPossibleMoves(tmp[3][0]);
        assertTrue(moves2[3][7]==false);
    
        // for (int x=0; x<8;x++){
        // Boolean[][] moves=board.getPossibleMoves(tmp[x][7]);
        // assertEquals("p", tmp[x][6].getPieceType());

        // for(int y=0;y<8;y++){
        // assertTrue(!moves[x][y]);
        // }
        // }
        // }
    }
    @Test
    public void updateGameOverTest() {
        Board board = new Board();
        board.reset();
        Piece[][] tmp = board.getBoard();
        board.updateCoords(tmp[4][1], 4, 3); //e2 to e4
        assertEquals(board.getGameOver(),null);
        board.updateCoords(tmp[5][6], 5, 4); //f7 to f5
        assertEquals(board.getGameOver(),null);
        board.updateCoords(tmp[3][0], 7, 4); //d1 to h5
        assertEquals(board.getGameOver(),null);
        board.updateCoords(tmp[4][6], 4, 5); //e7 to e6
        assertEquals(board.getGameOver(),null);
        board.updateCoords(tmp[7][4], 4, 7); //h5 to e8
        assertEquals(board.getGameOver(),"w");
    }        
}
