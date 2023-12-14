package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.GameStates.CheckMateState;
import com.backend.chess_backend.model.GameStates.CheckState;
import com.backend.chess_backend.model.GameStates.StaleMateState;

public class CheckGameState {

    public static boolean whiteChecked(Board board) {
        if (CheckState.isWhiteChecked(board)) {
            return true;
        }
        return false;
    }

    public static boolean blackChecked(Board board) {
        if (CheckState.isBlackChecked(board)) {
            return true;
        }
        return false;
    }

    public static boolean checked(Board board) {
        if (CheckState.isChecked(board)) {
            return true;
        }
        return false;
    }

    public static boolean whiteCheckmated(Board board) {
        if (CheckMateState.isWhiteCheckmated(board)) {
            return true;
        }
        return false;
    }

    public static boolean blackCheckmated(Board board) {
        if (CheckMateState.isBlackCheckmated(board)) {
            return true;
        }
        return false;
    }

    public static boolean stalemate(Board board) {
        if (StaleMateState.isStalemate(board)) {
            return true;
        }
        return false;
    }

    public static boolean whiteStalemated(Board board) {
        if (StaleMateState.isWhiteStalemated(board)) {
            return true;
        }
        return false;
    }

    public static boolean blackStalemated(Board board) {
        if (StaleMateState.isBlackStalemated(board)) {
            return true;
        }
        return false;
    }
}
