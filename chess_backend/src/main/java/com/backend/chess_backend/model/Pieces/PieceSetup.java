package com.backend.chess_backend.model.Pieces;

import com.backend.chess_backend.model.Square;

public class PieceSetup {

    public static void setupStandardChessPieces(Square[][] board) {
        setupPawns(board);
        setupRooks(board);
        setupKnights(board);
        setupBishops(board);
        setupQueens(board);
        setupKings(board);
    }

    private static void setupPawns(Square[][] board) {
        for (int x = 0; x < board.length; x++) {
            board[x][6].setPiece(PieceFactory.makePawn(PieceColor.BLACK, x, 6));
            board[x][1].setPiece(PieceFactory.makePawn(PieceColor.WHITE, x, 1));
        }
    }

    private static void setupRooks(Square[][] board){
        board[0][7].setPiece(PieceFactory.makeRook(PieceColor.BLACK, 0, 7));
        board[7][7].setPiece(PieceFactory.makeRook(PieceColor.BLACK, 7, 7));
        board[0][0].setPiece(PieceFactory.makeRook(PieceColor.WHITE, 0, 0));
        board[7][0].setPiece(PieceFactory.makeRook(PieceColor.WHITE, 7, 0));
    }

    private static void setupKnights(Square[][] board){
        board[1][7].setPiece(PieceFactory.makeKnight(PieceColor.BLACK, 1, 7));
        board[6][7].setPiece(PieceFactory.makeKnight(PieceColor.BLACK, 6, 7));
        board[1][0].setPiece(PieceFactory.makeKnight(PieceColor.WHITE, 1, 0));
        board[6][0].setPiece(PieceFactory.makeKnight(PieceColor.WHITE, 6, 0));
    }
    
    private static void setupBishops(Square[][] board){
        board[2][7].setPiece(PieceFactory.makeBishop(PieceColor.BLACK, 2, 7));
        board[5][7].setPiece(PieceFactory.makeBishop(PieceColor.BLACK, 5, 7));
        board[2][0].setPiece(PieceFactory.makeBishop(PieceColor.WHITE, 2, 0));
        board[5][0].setPiece(PieceFactory.makeBishop(PieceColor.WHITE, 5, 0));
    }
    
    private static void setupQueens(Square[][] board){
        board[3][7].setPiece(PieceFactory.makeQueen(PieceColor.BLACK, 3, 7));
        board[3][0].setPiece(PieceFactory.makeQueen(PieceColor.WHITE, 3, 0));
    }
    
    private static void setupKings(Square[][] board){
        board[4][7].setPiece(PieceFactory.makeKing(PieceColor.BLACK, 4, 7));
        board[4][0].setPiece(PieceFactory.makeKing(PieceColor.WHITE, 4, 0));
    }
    

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void setupFourPlayerChessPieces(Square[][] board) {
        // Set up pieces for four-player chess
        return;
    }
}