package com.backend.chess_backend;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.Queen;
import com.backend.chess_backend.model.Translator;

@SpringBootTest
public class QueenTest {


    @Test
    void initialisationTest(){

        Queen queen = new Queen(PieceColor.BLACK, 1, 7);

        assertEquals(queen.getX(), 1);        
        assertEquals(queen.getY(), 7);
        assertTrue(queen.getColor() == PieceColor.BLACK);

    }

    @Test
    void getPossibleMovesTest(){

        Queen queen = new Queen(PieceColor.BLACK, 1, 7);

        Boolean[][] posMoves = queen.getPossibleMoves(8, 8);

        ArrayList<String> expected = new ArrayList<String>(Arrays.asList("b7","b6","b5","b4","b3","b2","b1","a8","c8","d8","e8", "f8","g8","h8","a7","c7","d6","e5","f4","g3","h2"));

        Translator translator = new Translator();

        ArrayList<String> actual = translator.translatePossibleMoves(posMoves);

        assertTrue(actual.size() == expected.size() && actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void getPieceTypeTest(){
        Queen queen = new Queen(PieceColor.BLACK, 1, 7);

        assertEquals(queen.getPieceType(), "q");
    }
}
