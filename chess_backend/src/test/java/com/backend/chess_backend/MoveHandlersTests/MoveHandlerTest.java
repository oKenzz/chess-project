package com.backend.chess_backend.MoveHandlersTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.MoveHandler;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class MoveHandlerTest {
    
    @Test
    void testMakeUniqueMove() {
        // Set up a board and squares in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Make a move
        MoveHandler.makeUniqueMove(1, 1, 1, 3, board, currentBoard);

        // Check that the move was made correctly
        assertNull(currentBoard[1][1].getPiece());
        assertNotNull(currentBoard[1][3].getPiece());

        MoveHandler.makeUniqueMove(1, 3, 1, 4, board, currentBoard);


        assertNull(currentBoard[1][3].getPiece());
        assertNotNull(currentBoard[1][4].getPiece());
        

        MoveHandler.makeUniqueMove(0, 6, 0, 4, board, currentBoard); 

        assertNull(currentBoard[0][6].getPiece());
        assertNotNull(currentBoard[0][4].getPiece());


        MoveHandler.makeUniqueMove(1, 4, 0, 5, board, currentBoard);

        assertNull(currentBoard[1][4].getPiece());
        assertNull(currentBoard[0][4].getPiece());
        assertNotNull(currentBoard[0][5].getPiece());
        //Enpessant tested above
    }
}
