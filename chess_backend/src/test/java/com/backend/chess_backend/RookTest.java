package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.Rook;

@SpringBootTest
public class RookTest {
    
    @Test
    void initialisationTest(){

        Rook rook = new Rook(PieceColor.BLACK, 1, 7);

        assertEquals(rook.getX(), 1);        
        assertEquals(rook.getY(), 7);
        assertTrue(rook.getColor() == PieceColor.BLACK);

    }

    @Test
    void getPossibleMovesTest(){

        Rook rook = new Rook(PieceColor.BLACK, 1, 7);

    }
}
