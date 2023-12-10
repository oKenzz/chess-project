package com.backend.chess_backend.model.MoveHandlers;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.Rook;

public class CastlingMoveHandler {
    

    public static void validateCastlingMoves(Piece king, Boolean[][] movelist, Board board){
    
        Piece[][] currentBoard = board.getBoard();
        if(king.getColor() == PieceColor.BLACK && king.getMovesMade() == 0){
            
            if(currentBoard[0][7] instanceof Rook && currentBoard[0][7].getMovesMade() == 0){
                if(!ThreatenedAndPiece(currentBoard[1][7], board) && !ThreatenedAndPiece(currentBoard[2][7], board) && !ThreatenedAndPiece(currentBoard[3][7], board)){
                    
                    movelist[2][7] = true;
                    movelist[3][7] = true;
                    
                }
            }
            if(currentBoard[7][7] instanceof Rook && currentBoard[7][7].getMovesMade() == 0){
                if(!ThreatenedAndPiece(currentBoard[5][7], board) && !ThreatenedAndPiece(currentBoard[6][7], board)){
                    
                    movelist[6][7] = true;
                    movelist[5][7] = true;
                    
                }
            }
        }
        else if(king.getColor() == PieceColor.WHITE && king.getMovesMade() == 0){
            if(currentBoard[0][0] instanceof Rook && currentBoard[0][0].getMovesMade() == 0){
                if(!ThreatenedAndPiece(currentBoard[1][0], board) && !ThreatenedAndPiece(currentBoard[2][0], board) && !ThreatenedAndPiece(currentBoard[3][0], board)){
                    
                    movelist[2][0] = true;
                    movelist[3][0] = true;
                    
                }
            }
            if(currentBoard[7][0] instanceof Rook && currentBoard[7][0].getMovesMade() == 0){
                if(!ThreatenedAndPiece(currentBoard[5][7], board) && !ThreatenedAndPiece(currentBoard[5][7], board)){
                    
                    movelist[6][0] = true;
                    movelist[5][0] = true;
                    
                }
            }
        }
    }
    


    private static Boolean ThreatenedAndPiece(Piece piece, Board board){

        if(piece != null && MoveValidator.threatenedSquare(piece.getX(), piece.getY(), piece.getColor(), board)){
            return true;
        }
        return false;

    }



}
