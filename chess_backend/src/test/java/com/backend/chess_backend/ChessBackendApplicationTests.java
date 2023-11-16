package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.Rook;
import com.backend.chess_backend.model.Translator;

@SpringBootTest
class ChessBackendApplicationTests {


    @Test
    public void translateBoardTest()
    {
        Translator translator = new Translator();
        Piece[][] board = new Piece[8][8];

        for(int x = 0; x < board.length; x++){
            for(int y = 0; y < board[0].length; y++){

                if(x % 2 == 0){
                    board[x][y] = new Rook(PieceColor.WHITE, x, y);
                }else{
                    board[x][y] = null;
                }
            }
        }
        String fen = translator.translateBoard(board);
        assertEquals(fen, "R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1");
        //assertTrue(true);
    }
}
