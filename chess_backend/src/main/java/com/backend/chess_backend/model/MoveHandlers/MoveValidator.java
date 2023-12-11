package com.backend.chess_backend.model.MoveHandlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.backend.chess_backend.model.Board;
import com.backend.chess_backend.model.Move;
import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

public class MoveValidator {

    public boolean isValidMove(Move move, Board board) {

        return true;
    }

    public static Boolean[][] getPossibleMoves(Piece piece, Board currentBoard) {

        Boolean[][] movelist = primitivePossibleMoves(piece, currentBoard);

        if (piece instanceof King && CheckGameState.checked(currentBoard) == false && piece.getMovesMade() == 0) {
            CastlingMoveHandler.validateCastlingMoves(piece, movelist, currentBoard);
        }
        removeCheckMoves(piece, movelist, currentBoard);

        return movelist;
    }

    public static void removeCheckMoves(Piece piece, Boolean[][] movelist, Board board) {
        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (movelist[x][y] == true) {
                    board.move(piece, x, y);
                    if (piece.getColor() == PieceColor.BLACK && CheckGameState.blackChecked(board)) {
                        movelist[x][y] = false;
                    } else if (piece.getColor() == PieceColor.WHITE && CheckGameState.whiteChecked(board)) {
                        movelist[x][y] = false;
                    }
                    board.undoMove();
                }
            }
        }
    }

    public static Boolean[][] primitivePossibleMoves(Piece piece, Board currentBoard) {

        Boolean[][] movelist = piece.getPossibleMoves(currentBoard.getBoardWidth(), currentBoard.getBoardHeight());
        removeBlockedMoves(piece, movelist, currentBoard);
        if (piece instanceof Pawn) {
            handlePawnMoves(piece, movelist, currentBoard);
        }
        return movelist;

    }

    public static Boolean threatenedSquare(int coordx, int coordy, PieceColor color, Board board) {

        Piece[][] currentBoard = board.getBoard();
        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (currentBoard[x][y] != null) {
                    if (currentBoard[x][y].getColor() != color) {
                        Boolean[][] posMoves = null;
                        if (currentBoard[x][y] instanceof King) {
                            posMoves = primitivePossibleMoves(currentBoard[x][y], board);
                        } else {
                            posMoves = getPossibleMoves(currentBoard[x][y], board);
                        }
                        if (posMoves[coordx][coordy] == true) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Map<String, ArrayList<Integer>> getAllPossiblePlayerMoves(List<Piece> pieces, Board board) {
        Map<String, ArrayList<Integer>> moves = new HashMap<>();
        /*
         * {
         * from: [x, y],
         * to: [x, y]
         * }
         * 
         */
        for (Piece piece : pieces) {
            Boolean[][] possibleMoves = getPossibleMoves(piece, board);
            for (int x = 0; x < possibleMoves.length; x++) {
                for (int y = 0; y < possibleMoves[x].length; y++) {
                    if (possibleMoves[x][y]) {
                        String from = piece.getX() + "," + piece.getY();
                        String to = x + "," + y;
                        if (moves.containsKey(from)) {
                            moves.get(from).add(x);
                            moves.get(from).add(y);
                        } else {
                            ArrayList<Integer> toList = new ArrayList<>();
                            toList.add(x);
                            toList.add(y);
                            moves.put(from, toList);
                        }
                    }
                }
            }
        }
        return moves;
    }

    private static void handlePawnMoves(Piece piece, Boolean[][] movelist, Board currentBoard) {
        Piece[][] board = currentBoard.getBoard();

        // checks if the piece is a black pawn
        if (piece.getColor() == PieceColor.BLACK) {
            handleBlackPawnMoves(piece, movelist, board);
        } else if (piece.getColor() == PieceColor.WHITE) {
            handleWhitePawnMoves(piece, movelist, board);
        }
    }

    private static void handleWhitePawnMoves(Piece piece, Boolean[][] movelist, Piece[][] board) {
        if (piece.getY() < 7 && board[piece.getX()][piece.getY() + 1] != null) {
            movelist[piece.getX()][piece.getY() + 1] = false;
            if (movelist[piece.getX()][piece.getY() + 2] == true) {
                movelist[piece.getX()][piece.getY() + 2] = false;
            }

        } else if (piece.getMovesMade() == 0 && board[piece.getX()][piece.getY() + 2] != null) {
            movelist[piece.getX()][piece.getY() + 2] = false;
        }
        // removes the option from a white pawn to move diagonally up right when there
        // is not black peice to take
        if (piece.getX() != 7 && piece.getY() != 7 && board[piece.getX() + 1][piece.getY() + 1] == null) {
            movelist[piece.getX() + 1][piece.getY() + 1] = false;
        }
        // removes the option from a white pawn to move diagonally up left when there is
        // not black peice to take
        if (piece.getX() != 0 && piece.getY() != 7 && board[piece.getX() - 1][piece.getY() + 1] == null) {
            movelist[piece.getX() - 1][piece.getY() + 1] = false;
        }
        // enpessant to the left
        if (piece.getX() != 0 && piece.getY() != 7 && board[piece.getX() - 1][piece.getY()] != null
                && board[piece.getX() - 1][piece.getY()].getColor() == PieceColor.BLACK
                && board[piece.getX() - 1][piece.getY()].getMovesMade() == 1) {
            movelist[piece.getX() - 1][piece.getY() + 1] = true;
        }
        // enpessant to the right
        if (piece.getX() != 7 && piece.getY() != 7 && board[piece.getX() + 1][piece.getY()] != null
                && board[piece.getX() + 1][piece.getY()].getColor() == PieceColor.BLACK
                && board[piece.getX() + 1][piece.getY()].getMovesMade() == 1) {
            movelist[piece.getX() + 1][piece.getY() + 1] = true;
        }
    }

    public static Boolean isCastleMove(Piece piece, int x, int y, Board board) {
        if (piece instanceof King && piece.getMovesMade() == 0 && Math.abs(piece.getX() - x) == 2) {
            return true;
        }
        return false;
    }

    private static void handleBlackPawnMoves(Piece piece, Boolean[][] movelist, Piece[][] board) {
        if (piece.getY() > 0 && board[piece.getX()][piece.getY() - 1] != null) {
            movelist[piece.getX()][piece.getY() - 1] = false;
            if (movelist[piece.getX()][piece.getY() - 2] == true) {
                movelist[piece.getX()][piece.getY() - 2] = false;
            }

        } else if (piece.getMovesMade() == 0 && board[piece.getX()][piece.getY() - 2] != null) {
            movelist[piece.getX()][piece.getY() - 2] = false;
        }
        // removes the option from a black pawn to move diagonally down right when there
        // is not white peice to take
        if (piece.getX() != 7 && piece.getY() != 0 && board[piece.getX() + 1][piece.getY() - 1] == null) {
            movelist[piece.getX() + 1][piece.getY() - 1] = false;
        }
        // removes the option from a black pawn to move diagonally down left when there
        // is not white peice to take
        if (piece.getX() != 0 && piece.getY() != 0 && board[piece.getX() - 1][piece.getY() - 1] == null) {
            movelist[piece.getX() - 1][piece.getY() - 1] = false;
        }
        // adds en pessant moves to the left to possible moves
        if (piece.getX() != 0 && piece.getY() != 0 && board[piece.getX() - 1][piece.getY()] != null
                && board[piece.getX() - 1][piece.getY()].getColor() == PieceColor.WHITE
                && board[piece.getX() - 1][piece.getY()].getMovesMade() == 1) {
            movelist[piece.getX() - 1][piece.getY() - 1] = true;
        }
        // adds enpessant move to the right to possible moves
        if (piece.getX() != 7 && piece.getY() != 0 && board[piece.getX() + 1][piece.getY()] != null
                && board[piece.getX() + 1][piece.getY()].getColor() == PieceColor.WHITE
                && board[piece.getX() + 1][piece.getY()].getMovesMade() == 1) {
            movelist[piece.getX() + 1][piece.getY() - 1] = true;
        }
    }

    private static void removeBlockedMoves(Piece piece, Boolean[][] movelist, Board currentBoard) {
        Piece[][] board = currentBoard.getBoard();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                if (currentBoard.containsPiece(x, y) && movelist[x][y]) {
                    updateMoveListForBlockedPath(piece, movelist, x, y, currentBoard);
                }
            }
        }
    }

    private static void updateMoveListForBlockedPath(Piece piece, Boolean[][] movelist, int x, int y,
            Board currentBoard) {
        if (isBlockAtDiagonalDirection(piece, x, y)) {
            handleDiagonalBlockage(piece, movelist, x, y, currentBoard);
        } else if (isBlockAtStraightDirection(piece, x, y)) {
            handleStraightBlockage(piece, movelist, x, y, currentBoard);
        } else if (isBlockAtKnightMoveDirection(piece, x, y)) {
            movelist[x][y] = false;
        }

        if (isCaptureCase(piece, x, y, currentBoard)) {
            movelist[x][y] = true;
        }

    }

    private static boolean isBlockAtDiagonalDirection(Piece piece, int x, int y) {
        return Math.abs(piece.getX() - x) == Math.abs(piece.getY() - y);
    }

    private static boolean isBlockAtStraightDirection(Piece piece, int x, int y) {
        return piece.getX() == x || piece.getY() == y;
    }

    private static boolean isBlockAtKnightMoveDirection(Piece piece, int x, int y) {
        return Math.abs(x - piece.getX()) * Math.abs(y - piece.getY()) == 2;
    }

    private static boolean isCaptureCase(Piece piece, int x, int y, Board currentBoard) {
        return currentBoard.getBoard()[x][y].getColor() != piece.getColor();
    }

    private static void handleDiagonalBlockage(Piece piece, Boolean[][] movelist, int x, int y, Board curreBoard) {
        Piece[][] board = curreBoard.getBoard();
        int tmpx = x;
        int tmpy = y;
        // checks if the found piece is to the up and left of the original piece
        if (x < piece.getX() && y < piece.getY()) {
            while (tmpx >= 0 && tmpy >= 0) {
                if (movelist[tmpx][tmpy] == false) {
                    break;
                }
                movelist[tmpx][tmpy] = false;
                tmpx--;
                tmpy--;
            }
        }
        // down left
        else if (x < piece.getX() && y > piece.getY()) {
            while (tmpx >= 0 && tmpy < board.length) {
                if (movelist[tmpx][tmpy] == false) {
                    break;
                }
                movelist[tmpx][tmpy] = false;
                tmpx--;
                tmpy++;
            }

        }
        // up right
        else if (x > piece.getX() && y < piece.getY()) {
            while (tmpx < board.length && tmpy >= 0) {
                if (movelist[tmpx][tmpy] == false) {
                    break;
                }
                movelist[tmpx][tmpy] = false;
                tmpx++;
                tmpy--;
            }

        }
        // down right
        else if (x > piece.getX() && y > piece.getY()) {
            while (tmpx < board.length && tmpy < board.length) {
                movelist[tmpx][tmpy] = false;
                tmpx++;
                tmpy++;
            }
        }
    }

    private static void handleStraightBlockage(Piece piece, Boolean[][] movelist, int x, int y, Board currentBoard) {
        Piece[][] board = currentBoard.getBoard();
        int tmpx = x;
        int tmpy = y;
        // checks if piece is on the same x-level
        if (piece.getX() == x) {
            if (y < piece.getY()) {
                while (tmpy >= 0) {
                    movelist[x][tmpy] = false;
                    tmpy--;
                }

            } else {
                if (y > piece.getY()) {
                    while (tmpy < board.length) {
                        movelist[x][tmpy] = false;
                        tmpy++;
                    }

                }

            }
        }
        // checks if piece is on the same y-level
        else if (piece.getY() == y) {
            if (x < piece.getX()) {
                while (tmpx >= 0) {
                    movelist[tmpx][y] = false;
                    tmpx--;
                }

            }

            else {
                while (tmpx < board.length) {
                    movelist[tmpx][y] = false;
                    tmpx++;
                }

            }

        }
    }
}