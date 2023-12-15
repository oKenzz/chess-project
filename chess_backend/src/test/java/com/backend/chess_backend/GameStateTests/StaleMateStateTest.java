package com.backend.chess_backend.GameStateTests;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Constants.PieceTypeEnum;
import com.backend.chess_backend.model.GameStates.StaleMateState;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Square;

@SpringBootTest
public class StaleMateStateTest {

    @Test
    void testIsWhiteStalemated() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if the game is a stalemate
        boolean result = StaleMateState.isWhiteStalemated(board);
        assertFalse(result);
        List<Piece> whitePieces = board.getAllPlayerPieces(PieceColor.WHITE);
        for (Piece piece : whitePieces) {
            if (piece.getPieceType() != PieceTypeEnum.WHITE_KING){
                currentBoard[piece.getX()][piece.getY()].setPiece(null);
            }
        }
        board.move(currentBoard[4][0].getPiece(), 7, 0);
        board.move(currentBoard[3][7].getPiece(), 5, 1);

        result = StaleMateState.isWhiteStalemated(board);
        assertTrue(result);
    }

    @Test
    void testIsStalemate() {
        // Set up a board in a specific state
        Board board = new Board();
        Square[][] currentBoard = board.getBoard();

        // Check if the game is a stalemate
        boolean result = StaleMateState.isStalemate(board);
        assertFalse(result);
        List<Piece> whitePieces = board.getAllPlayerPieces(PieceColor.WHITE);
        for (Piece piece : whitePieces) {
            if (piece.getPieceType() != PieceTypeEnum.WHITE_KING){
                currentBoard[piece.getX()][piece.getY()].setPiece(null);
            }
        }
        board.move(currentBoard[4][0].getPiece(), 7, 0);
        board.move(currentBoard[3][7].getPiece(), 5, 1);

        result = StaleMateState.isStalemate(board);
        assertTrue(result);
    }
}
