package com.backend.chess_backend.model.Pieces;

public class PieceSetup {

    public static void setupStandardChessPieces(Piece[][] board) {
        setupPawns(board);
        setupRooks(board);
        setupKnights(board);
        setupBishops(board);
        setupQueens(board);
        setupKings(board);
    }

    private static void setupPawns(Piece[][] board) {
        for (int x = 0; x < board.length; x++) {
            board[x][6] = PieceFactory.makePawn(PieceColor.BLACK, x, 6);
            board[x][1] = PieceFactory.makePawn(PieceColor.WHITE, x, 1);
        }
    }

    private static void setupRooks(Piece[][] board){
        board[0][7] = PieceFactory.makeRook(PieceColor.BLACK, 0, 7);
        board[7][7] = PieceFactory.makeRook(PieceColor.BLACK, 7, 7);
        board[0][0] = PieceFactory.makeRook(PieceColor.WHITE, 0, 0);
        board[7][0] = PieceFactory.makeRook(PieceColor.WHITE, 7, 0);
    }

    private static void setupKnights(Piece[][] board){
        board[1][7] = PieceFactory.makeKnight(PieceColor.BLACK, 1, 7);
        board[6][7] = PieceFactory.makeKnight(PieceColor.BLACK, 6, 7);
        board[1][0] = PieceFactory.makeKnight(PieceColor.WHITE, 1, 0);
        board[6][0] = PieceFactory.makeKnight(PieceColor.WHITE, 6, 0);
    }

    private static void setupBishops(Piece[][] board){
        board[2][7] = PieceFactory.makeBishop(PieceColor.BLACK, 2, 7);
        board[5][7] = PieceFactory.makeBishop(PieceColor.BLACK, 5, 7);
        board[2][0] = PieceFactory.makeBishop(PieceColor.WHITE, 2, 0);
        board[5][0] = PieceFactory.makeBishop(PieceColor.WHITE, 5, 0);
    }

    private static void setupQueens(Piece[][] board){
        board[3][7] = PieceFactory.makeQueen(PieceColor.BLACK, 3, 7);
        board[3][0] = PieceFactory.makeQueen(PieceColor.WHITE, 3, 0);
    }

    private static void setupKings(Piece[][] board){
        board[4][7] = PieceFactory.makeKing(PieceColor.BLACK, 4, 7);
        board[4][0] = PieceFactory.makeKing(PieceColor.WHITE, 4, 0);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void setupFourPlayerChessPieces(Piece[][] board) {
        // Set up pieces for four-player chess
        return;
    }
}