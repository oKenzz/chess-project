package com.backend.chess_backend.model.GameStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.MoveHandlers.MoveValidator;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

public class CheckMateState {
    
    

    public static Boolean isWhiteCheckmated(Board board){
        List<Piece> pieces = board.getAllPlayerPieces(PieceColor.WHITE);
        Map<String, ArrayList<Integer>> moves = MoveValidator.getAllPossiblePlayerMoves(pieces, board);

        if (moves.isEmpty() && CheckState.isWhiteChecked(board)) {
            return true;
        }
        return false;
    }

    public static Boolean isBlackCheckmated(Board board){
        List<Piece> pieces = board.getAllPlayerPieces(PieceColor.BLACK);
        Map<String, ArrayList<Integer>> moves = MoveValidator.getAllPossiblePlayerMoves(pieces, board);

        if (moves.isEmpty() && CheckState.isBlackChecked(board)) {
            return true;
        }
        return false;
    }
}