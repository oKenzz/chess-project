package com.backend.chess_backend.model.GameStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.MoveValidator;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

public class StaleMateState {

    public static boolean isWhiteStalemated(Board board) {
        List<Piece> pieces = board.getAllPlayerPieces(PieceColor.WHITE);
        Map<String, ArrayList<Integer>> moves = MoveValidator.getAllPossiblePlayerMoves(pieces, board);

        if (CheckState.isChecked(board) == false && moves.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isBlackStalemated(Board board) {
        List<Piece> pieces = board.getAllPlayerPieces(PieceColor.BLACK);
        Map<String, ArrayList<Integer>> moves = MoveValidator.getAllPossiblePlayerMoves(pieces, board);

        if (CheckState.isChecked(board) == false && moves.isEmpty()) {
            return true;
        }
        return false;
    }

    public static boolean isStalemate(Board board) {

        if (isWhiteStalemated(board) || isBlackStalemated(board)) {
            return true;
        }
        return false;
    }
}
