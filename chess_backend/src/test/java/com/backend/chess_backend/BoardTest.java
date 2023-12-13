package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Square;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Translator;

public class BoardTest {
    // @Test
    // public void enpassantTest() {
    //     Board board = new Board();
    //     board.reset();
    //     Piece[][] tmp = board.getBoard();
    //     board.move(tmp[5][6], 5, 4);
    //     board.move(tmp[4][1], 4, 3);
    //     board.move(tmp[4][3], 4, 4);
    //     Boolean[][] testlist = SimpleChessGame.possibleMoves();
    //     assertTrue(board.isMoveAllowed(5, 5, testlist));
    // }

    @Test
    public void createBoardTest() {
        Board board = new Board();
        Square[][] tmp = board.getBoard();
        board.reset();
        String fen = Translator.translateBoard(tmp, "w");
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w", fen);
    }

    // @Test
    // public void possiblemovetest() {
    //     Board board = new Board();
    //     board.reset();
    //     Piece[][] tmp = board.getBoard();
    //     // for (int i=0; i<7; i++){
    //     board.move(tmp[3][1], 3, 5);
    //     Boolean[][] moves = board.getPossibleMoves(tmp[3][6]);
    //     assertTrue(moves[3][4] == false);
    //     Boolean[][] moves2 = board.getPossibleMoves(tmp[0][0]);
    //     assertTrue(moves2[7][0] == false);
    //     Boolean[][] moves3 = board.getPossibleMoves(tmp[3][0]);
    //     assertTrue(moves2[3][7] == false);

    //     // for (int x=0; x<8;x++){
    //     // Boolean[][] moves=board.getPossibleMoves(tmp[x][7]);
    //     // assertEquals("p", tmp[x][6].getPieceType());

    //     // for(int y=0;y<8;y++){
    //     // assertTrue(!moves[x][y]);
    //     // }
    //     // }
    //     // }
    // }


    // @Test
    // public void updateCheckedTest(){
    //     Board board = new Board();
    //     board.reset();
    //     Piece[][] tmp = board.getBoard();
    //     board.move(tmp[4][1], 4, 3); // e2 to e4
    //     board.move(tmp[5][6], 5, 4); // f7 to f5
    //     board.move(tmp[3][0], 7, 4); // d1 to h5
    //     board.updateChecked();
    //     assertEquals(true, board.ifCheck());
    //     board.move(tmp[7][4], 6, 4); // h5 to g5
    //     board.updateChecked();
    //     assertEquals(false, board.ifCheck());
    // }

    // @Test
    // public void updateGameOverTest() {
    //     Board board = new Board();
    //     board.reset();
    //     Piece[][] tmp = board.getBoard();
    //     board.move(tmp[5][1], 5, 2); // f2 to f3
    //     assertEquals(null, board.getGameOver());
    //     board.move(tmp[4][6], 4, 4); // e7 to e5
    //     assertEquals(null, board.getGameOver());
    //     board.move(tmp[6][1], 6, 3); // g2 to g4
    //     assertEquals( null, board.getGameOver());
    //     board.move(tmp[3][7], 7, 3); // d8 to h4
    //     board.updateChecked();
    //     assertEquals("b", board.getGameOver());

    //     board.reset();
    //     tmp = board.getBoard();
    //     board.move(tmp[4][1], 4, 3); // e2 to e4
    //     board.move(tmp[5][6], 5, 4); // f7 to f5
    //     board.move(tmp[3][0], 7,4); // d1 to h5
    //     board.updateChecked();
    //     assertEquals(null, board.getGameOver());

    // }

    @Test
    public void undoMoveTest() {
        Board board = new Board();
        board.reset();
        Square[][] tmp = board.getBoard();
        board.move(tmp[4][1].getPiece(), 4, 3); // e2 to e4
        board.undoMove();
        assertEquals(tmp[4][1].getPiece().getPieceType(), "P");
        assertEquals(tmp[4][3], null);
        board.move(tmp[4][1].getPiece(), 4, 3); // e2 to e4
        board.move(tmp[5][6].getPiece(), 5, 4); // f7 to f5
        board.move(tmp[4][3].getPiece(), 5, 4); // e4 to f5
        board.undoMove();
        assertEquals(tmp[4][3].getPiece().getPieceType(), "P");
        assertEquals(tmp[5][4].getPiece().getPieceType(), "p");
        }

    // @Test
    // public void removeCheckMovesTest() {
    //     Board board = new Board();
    //     board.reset();
    //     Piece[][] tmp = board.getBoard();
    //     board.move(tmp[4][1], 4, 3); // e2 to e4
    //     board.move(tmp[5][6], 5, 4); // f7 to f5
    //     board.move(tmp[3][0], 7, 4); // d1 to h5
    //     Boolean[][] posM = board.makePossibleMoves(tmp[1][6]);
    //     assertTrue(posM[1][5] == true);
    //     assertTrue(posM[1][4] == true);
    //     board.updateChecked();
    //     posM = board.removeCheckMoves(tmp[1][6], posM);
    //     for (int x = 0; x < 8; x++) {
    //         for (int y = 0; y < 8; y++) {
    //             assertTrue(posM[x][y] == false);
    //         }
    //     }
    // }

    // @Test
    // public void threatenedSquare() {
    //     Board board = new Board();
    //     board.reset();
    //     Piece[][] tmp = board.getBoard();
    //     board.move(tmp[4][1], 4, 3); // e2 to e4
    //     board.move(tmp[5][6], 5, 4); // f7 to f5
    //     board.move(tmp[3][0], 7, 4); // d1 to h5
    //     assertEquals(board.threatenedSquare(4, 7, PieceColor.BLACK), true);
    //     assertEquals(board.threatenedSquare(7, 6, PieceColor.BLACK), true);
    //     assertEquals(board.threatenedSquare(4, 0, PieceColor.WHITE), false);
    // }

}
