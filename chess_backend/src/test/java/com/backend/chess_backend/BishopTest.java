package com.backend.chess_backend;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Bishop;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Translator;

@SpringBootTest
public class BishopTest {
    
    @Test
    void initialisationTest(){

        Bishop bishop = new Bishop(PieceColor.BLACK, 1, 7);

        assertEquals(bishop.getX(), 1);        
        assertEquals(bishop.getY(), 7);
        assertTrue(bishop.getColor() == PieceColor.BLACK);

    }

    @Test
    void getPossibleMovesTest(){

        Bishop bishop = new Bishop(PieceColor.BLACK, 1, 7);

        Boolean[][] posMoves = bishop.getPossibleMoves(8, 8);

        ArrayList<String> expected = new ArrayList<String>(Arrays.asList( "a7","c7","d6","e5","f4","g3","h2"));

        ArrayList<String> actual = Translator.translatePossibleMoves(posMoves);

        assertEquals(expected, actual);

        assertTrue(actual.size() == expected.size() && actual.containsAll(expected) && expected.containsAll(actual));
    }

    @Test
    void getPieceTypeTest(){
        Bishop bishop = new Bishop(PieceColor.BLACK, 1, 7);

        assertEquals(bishop.getPieceType(), "b");
    }
}
