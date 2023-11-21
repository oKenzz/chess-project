package com.backend.chess_backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Translator;
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

        Boolean[][] posMoves = rook.getPossibleMoves(8, 8);

        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("b7","b6","b5","b4","b3","b2","b1", "a8","c8","d8","e8", "f8","g8","h8"));

        Translator translator = new Translator();

        ArrayList<String> actual = translator.translatePossibleMoves(posMoves);

        assertTrue(actual.size() == expected.size() && actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void getPieceTypeTest(){
        Rook rook = new Rook(PieceColor.BLACK, 1, 7);

        assertEquals(rook.getPieceType(), "r");
    }
}
