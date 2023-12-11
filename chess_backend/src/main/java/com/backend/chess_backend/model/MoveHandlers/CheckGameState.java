package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.GameStates.CheckMateState;
import com.backend.chess_backend.model.GameStates.CheckState;
import com.backend.chess_backend.model.GameStates.StaleMateState;

public class CheckGameState {
    

    public static Boolean whiteChecked(Board board){
        if(CheckState.isWhiteChecked(board)){
            return true;
        }
        return false;
    }

    public static Boolean blackChecked(Board board){
        if(CheckState.isBlackChecked(board)){
            return true;
        }
        return false;
    }

    public static Boolean checked(Board board){
        if(CheckState.isChecked(board)){
            return true;
        }
        return false;
    }

    public static Boolean whiteCheckmated(Board board){
        if(CheckMateState.isWhiteCheckmated(board)){
            return true;
        }
        return false;
    }

    public static Boolean blackCheckmated(Board board){
        if(CheckMateState.isBlackCheckmated(board)){
            return true;
        }
        return false;
    }

    public static Boolean stalemate(Board board){
        if(StaleMateState.isStalemate(board)){
            return true;
        }
        return false;
    }

    public static Boolean whiteStalemated(Board board){
        if(StaleMateState.isWhiteStalemated(board)){
            return true;
        }
        return false;
    }

    public static Boolean blackStalemated(Board board){
        if(StaleMateState.isBlackStalemated(board)){
            return true;
        }
        return false;
    }
}
