package com.backend.chess_backend.MoveHandlersTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.PromotionMoveHandler;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.PieceFactory;
import com.backend.chess_backend.model.Pieces.Queen;

@SpringBootTest
public class PromotionMoveHandlerTest {
    @Test
    void testIsPromotionMove() {
        Piece whitePawn = PieceFactory.makePawn(PieceColor.WHITE, 1, 6);
        Piece blackPawn = PieceFactory.makePawn(PieceColor.BLACK, 1, 1);

        assertTrue(PromotionMoveHandler.isPromotionMove(whitePawn, 1, 7));
        assertFalse(PromotionMoveHandler.isPromotionMove(whitePawn, 1, 6));
        assertTrue(PromotionMoveHandler.isPromotionMove(blackPawn, 1, 0));
        assertFalse(PromotionMoveHandler.isPromotionMove(blackPawn, 1, 1));
    }

    @Test
    void testPromoteToQueen() {
        Board board = new Board();
        Piece whitePawn = PieceFactory.makePawn(PieceColor.WHITE, 1, 6);
        board.getBoard()[1][6].setPiece(whitePawn);

        PromotionMoveHandler.promoteToQueen(whitePawn, 1, 7, board);

        assertTrue(board.getBoard()[1][7].getPiece() instanceof Queen);
        assertEquals(PieceColor.WHITE, board.getBoard()[1][7].getPiece().getColor());
        assertEquals(whitePawn.getMovesMade(), board.getBoard()[1][7].getPiece().getMovesMade());
    }
}
