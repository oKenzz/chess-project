package com.backend.chess_backend.PieceTests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceSetup;
import com.backend.chess_backend.model.Pieces.Rook;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class PieceSetupTest {
    @Test
    void testSetupStandardChessPieces() {
        Square[][] board = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j);
            }
        }

        PieceSetup.setupStandardChessPieces(board);

        // Test a few pieces to make sure they're set up correctly
        assertTrue(board[0][1].getPiece() instanceof Pawn);
        assertTrue(board[0][1].getPiece().getColor() == PieceColor.WHITE);
        assertTrue(board[0][6].getPiece() instanceof Pawn);
        assertTrue(board[0][6].getPiece().getColor() == PieceColor.BLACK);

        assertTrue(board[0][0].getPiece() instanceof Rook);
        assertTrue(board[0][0].getPiece().getColor() == PieceColor.WHITE);
        assertTrue(board[0][7].getPiece() instanceof Rook);
        assertTrue(board[0][7].getPiece().getColor() == PieceColor.BLACK);

        // Add more assertions to test other pieces...
    }
}
