package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Bishop;
import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.Knight;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.Queen;
import com.backend.chess_backend.model.Pieces.Rook;
import com.backend.chess_backend.model.Translator;

@SpringBootTest
class ChessBackendApplicationTests {


    @Test
    public void translateBoardTest()
    {
        Translator translator = new Translator();
        Piece[][] board = new Piece[8][8];

        // for(int x = 0; x < board.length; x++){
        //     for(int y = 0; y < board[0].length; y++){

        //         if(x % 2 == 0){
        //             board[x][y] = new Rook(PieceColor.WHITE, x, y);
        //         }else{
        //             board[x][y] = null;
        //         }
        //     }
        // }
        // String fen = translator.translateBoard(board);
        // assertEquals("R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1/R1R1R1R1", fen);
        
        PieceColor currentColor = PieceColor.WHITE;
        for (int y = 0; y < board[0].length; y++){
            if(y < 4){
                currentColor = PieceColor.WHITE;
            }else{
                currentColor = PieceColor.BLACK;
            }
            for(int x = 0; x < board.length; x++){
                if(y == 1 || y == 6){
                    board[x][y] = new Pawn(currentColor, x, y);
                }else if(y == 0 || y == 7 ){
                    if (x==0 || x == 7){
                        board[x][y] = new Rook(currentColor, x, y);
                    }else if (x==1 || x == 6) {
                        board[x][y] = new Knight(currentColor, x, y);
                    }else if (x==2 || x == 5) {
                        board[x][y] = new Bishop(currentColor, x, y);
                    }else if (x==3) {
                        board[x][y] = new Queen(currentColor, x, y);
                    }else if (x==4) {
                        board[x][y] = new King(currentColor, x, y);
                    }
                }    
            }
        }
        String fen = translator.translateBoard(board);
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", fen);
    }
}
