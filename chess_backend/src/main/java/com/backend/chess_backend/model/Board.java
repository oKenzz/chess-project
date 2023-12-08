package com.backend.chess_backend.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.backend.chess_backend.model.Pieces.Bishop;
import com.backend.chess_backend.model.Pieces.King;
import com.backend.chess_backend.model.Pieces.Knight;
import com.backend.chess_backend.model.Pieces.Pawn;
import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;
import com.backend.chess_backend.model.Pieces.Queen;
import com.backend.chess_backend.model.Pieces.Rook;

public class Board {

    // Will swap "Object to Piece"
    // ArrayList<ArrayList<Object>> board = new ArrayList<ArrayList<Object>>();

    Piece[][] board = new Piece[8][8];
    int[][] lastMove = new int[2][2];
    int[] bKingPosition = new int[2];
    int[] wKingPosition = new int[2];
    Boolean blackChecked;
    Boolean whiteChecked;
    Piece lastPiece;
    String gameOver;

    public Board() {

        createStartingBoard();
        this.gameOver = null;
        this.blackChecked = false;
        this.whiteChecked = false;
        this.lastPiece = null;
    }

    // returns the piece on that square (null if no piece there)
    public Piece getPiece(int xCord, int yCord) {
        return board[xCord][yCord];

    }

    // returns true if there is a piece on the square
    public Boolean containsPiece(int xCord, int yCord) {
        Piece piece = board[xCord][yCord];
        if (piece == null) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean[][] getPossibleMoves(Piece piece) {
        Boolean[][] movelist = makePossibleMoves(piece);

        
        movelist = removeCheckMoves(piece, movelist);
        

        return movelist;
    }

    public void updateChecked(){
        if ( threatenedSquare(wKingPosition[0], wKingPosition[1], PieceColor.WHITE)){
            this.whiteChecked = true;
        }else if(threatenedSquare(bKingPosition[0], bKingPosition[1], PieceColor.BLACK)){
            this.blackChecked = true;
        }else{
            this.whiteChecked = false;
            this.blackChecked = false;
        }
    }
    

    public Boolean threatenedSquare(int coordx, int coordy, PieceColor color) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                if (board[x][y] != null) {
                    if (board[x][y].getColor() != color) {
                        Boolean[][] posMoves = makePossibleMoves(board[x][y]);
                        if (posMoves[coordx][coordy] == true) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // moves a piece to a square using get possible moves. Returns true if move
    // successful, false if piece cant move there.
    public Boolean[][] makePossibleMoves(Piece piece) {
        Boolean[][] movelist = piece.getPossibleMoves(8, 8);

        // removes squares if pices are in the way

        movelist = removeExcess(piece, movelist);
        // checks if the piece is a black pawn
        if (piece.getPieceType() == "p") {
            if (piece.getY() > 0 && board[piece.getX()][piece.getY() - 1] != null) {
                movelist[piece.getX()][piece.getY() - 1] = false;
                if (movelist[piece.getX()][piece.getY() - 2] == true) {
                    movelist[piece.getX()][piece.getY() - 2] = false;
                }

            } else if (piece.getMovesMade() == 0 && board[piece.getX()][piece.getY() - 2] != null) {
                movelist[piece.getX()][piece.getY() - 2] = false;
            }
            // removes the option from a black pawn to move diagonally down right when there
            // is not black peice to take
            if (piece.getX() != 7 && piece.getY() != 0 && board[piece.getX() + 1][piece.getY() - 1] == null) {
                movelist[piece.getX() + 1][piece.getY() - 1] = false;
            }
            // removes the option from a black pawn to move diagonally down left when there
            // is not black peice to take
            if (piece.getX() != 0 && piece.getY() != 0 && board[piece.getX() - 1][piece.getY() - 1] == null) {
                movelist[piece.getX() - 1][piece.getY() - 1] = false;
            }
            // adds en pessant moves to the left to possible moves
            if (piece.getX() != 0 && piece.getY() != 0 && board[piece.getX() - 1][piece.getY()] != null
                    && board[piece.getX() - 1][piece.getY()].getPieceType() == "P"
                    && board[piece.getX() - 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() - 1][piece.getY() - 1] = true;
            }
            // adds enpessant move to the right to possible moves
            if (piece.getX() != 7 && piece.getY() != 0 && board[piece.getX() + 1][piece.getY()] != null
                    && board[piece.getX() + 1][piece.getY()].getPieceType() == "P"
                    && board[piece.getX() + 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() + 1][piece.getY() - 1] = true;
            }

        } else if (piece.getPieceType() == "P") {
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
                    && board[piece.getX() - 1][piece.getY()].getPieceType() == "p"
                    && board[piece.getX() - 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() - 1][piece.getY() + 1] = true;
            }
            // enpessant to the right
            if (piece.getX() != 7 && piece.getY() != 7 && board[piece.getX() + 1][piece.getY()] != null
                    && board[piece.getX() + 1][piece.getY()].getPieceType() == "p"
                    && board[piece.getX() + 1][piece.getY()].getMovesMade() == 1) {
                movelist[piece.getX() + 1][piece.getY() + 1] = true;
            }

        }

        return movelist;
    }

    public Boolean[][] removeCheckMoves(Piece piece, Boolean[][] movelist) {
        for (int x = 0; x < movelist.length; x++) {
            for (int y = 0; y < movelist.length; y++) {
                if (movelist[x][y] == true) {
                    move(piece, x, y);
                    updateChecked();
                    if (piece.getColor() == PieceColor.BLACK && this.blackChecked) {
                        movelist[x][y] = false;
                    } else if (piece.getColor() == PieceColor.WHITE && this.whiteChecked) {
                        movelist[x][y] = false;
                    }
                    undoMove();
                    updateChecked();
                }
            }
        }

        return movelist;
    }

    public void reset() {
        createStartingBoard();
    }

    // checks if the move made is a move that is allowe for that piece
    public boolean checkIfallowed(int newX, int newY, Boolean[][] moveList) {
        if (moveList[newX][newY] == true) {
            return true;
        } else {
            return false;
        }
    }

    // updates the
    public void move(Piece piece, int newX, int newY) {
        lastMove[0][0] = piece.getX();
        lastMove[1][0] = newX;
        lastMove[0][1] = piece.getY();
        lastMove[1][1] = newY;
        lastPiece = board[newX][newY];
        updateBoard(piece, newX, newY);
        piece.updateCoords(newX, newY);
        if (piece.getPieceType() == "k") {
            this.bKingPosition[0] = newX;
            this.bKingPosition[1] = newY;
        } else if (piece.getPieceType() == "K") {
            this.wKingPosition[0] = newX;
            this.wKingPosition[1] = newY;
        }
        piece.movesMade++;
    }

    public void undoMove() {
        // board[lastMove[1][0]][lastMove[1][1]].updateCoords(lastMove[0][0], lastMove[0][1]);
        // updateBoard(board[lastMove[1][0]][lastMove[1][1]], lastMove[0][0], lastMove[0][1]);
        // if(lastPiece != null){
        //     lastPiece.movesMade--;
        // }
        // board[lastMove[1][0]][lastMove[1][1]] = lastPiece;
        int tmpx = lastMove[1][0];
        int tmpy = lastMove[1][1];
        Piece tmpp = lastPiece;
        move(board[lastMove[1][0]][lastMove[1][1]], lastMove[0][0], lastMove[0][1]);
        
        board[tmpx][tmpy] = tmpp;
        board[lastMove[1][0]][lastMove[1][1]].movesMade -= 2;

    }

    private void updateBoard(Piece piece, int newX, int newY) {
        // sets teh new position in board to the piece and changes the old one to null
        board[newX][newY] = piece;
        board[piece.getX()][piece.getY()] = null;
        // if the move made is an enpessant from a white piece it removes the piece that
        // was taken
        if (piece.getPieceType() == "P") {
            if (Math.abs(piece.getX() - newX) == 1 && board[newX][piece.getY()] != null
                    && board[newX][piece.getY()].movesMade == 1 && board[newX][piece.getY()].getPieceType() == "p") {
                board[newX][newY - 1] = null;
            }
            // removes the piece that was taken by enpessant from a black piece
            else if (piece.getPieceType() == "p") {
                if (Math.abs(piece.getX() - newX) == 1 && board[newX][piece.getY()] != null
                        && board[newX][piece.getY()].movesMade == 1
                        && board[newX][piece.getY()].getPieceType() == "P") {
                    board[newX][newY + 1] = null;
                }
            }

        }
    }

    // getter for the board itself
    public Piece[][] getBoard() {
        return board;
    }

    private void createStartingBoard() {
        for (int x = 0; x < board.length; x++) {
            // creates pawns at y=1 and 6
            board[x][6] = new Pawn(PieceColor.BLACK, x, 6);
            board[x][1] = new Pawn(PieceColor.WHITE, x, 1);
            // creates rooks in the corners
            if (x == 0 || x == 7) {
                board[x][7] = new Rook(PieceColor.BLACK, x, 7);
                board[x][0] = new Rook(PieceColor.WHITE, x, 0);
            }
            // creates knights
            else if (x == 1 || x == 6) {
                board[x][7] = new Knight(PieceColor.BLACK, x, 7);
                board[x][0] = new Knight(PieceColor.WHITE, x, 0);
            }
            // creates bishops
            else if (x == 2 || x == 5) {
                board[x][7] = new Bishop(PieceColor.BLACK, x, 7);
                board[x][0] = new Bishop(PieceColor.WHITE, x, 0);
            }
            // creates the queens
            else if (x == 3) {
                board[x][7] = new Queen(PieceColor.BLACK, x, 7);
                board[x][0] = new Queen(PieceColor.WHITE, x, 0);
            }
            // creates the kings and saves their positions in variables
            else if (x == 4) {
                board[x][7] = new King(PieceColor.BLACK, x, 7);
                board[x][0] = new King(PieceColor.WHITE, x, 0);
                bKingPosition[0] = x;
                bKingPosition[1] = 7;
                wKingPosition[0] = x;
                wKingPosition[1] = 0;
            }
        }

    }

    // removes moves that are false due to another piece being in the way
    private Boolean[][] removeExcess(Piece piece, Boolean[][] possibleMoves) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                // checks if there is a piece on one of the possible moves
                if (containsPiece(x, y) == true && possibleMoves[x][y] == true) {
                    int tmpx = x;
                    int tmpy = y;
                    // checks if it is at a diagonal
                    if (Math.abs(piece.getX() - x) - Math.abs(piece.getY() - y) == 0) {
                        // checks if the found piece is to the up and left of the original piece
                        if (x < piece.getX() && y < piece.getY()) {
                            while (tmpx >= 0 && tmpy >= 0) {
                                if (possibleMoves[tmpx][tmpy] == false) {
                                    break;
                                }
                                possibleMoves[tmpx][tmpy] = false;
                                tmpx--;
                                tmpy--;
                            }
                        }
                        // down left
                        else if (x < piece.getX() && y > piece.getY()) {
                            while (tmpx >= 0 && tmpy < board.length) {
                                if (possibleMoves[tmpx][tmpy] == false) {
                                    break;
                                }
                                possibleMoves[tmpx][tmpy] = false;
                                tmpx--;
                                tmpy++;
                            }

                        }
                        // up right
                        else if (x > piece.getX() && y < piece.getY()) {
                            while (tmpx < board.length && tmpy >= 0) {
                                if (possibleMoves[tmpx][tmpy] == false) {
                                    break;
                                }
                                possibleMoves[tmpx][tmpy] = false;
                                tmpx++;
                                tmpy--;
                            }

                        }
                        // down right
                        else if (x > piece.getX() && y > piece.getY()) {
                            while (tmpx < board.length && tmpy < board.length) {
                                possibleMoves[tmpx][tmpy] = false;
                                tmpx++;
                                tmpy++;
                            }

                        }

                    }
                    // checks if piece is on the same x-level
                    else if (piece.getX() == x) {
                        if (y < piece.getY()) {
                            while (tmpy >= 0) {
                                possibleMoves[x][tmpy] = false;
                                tmpy--;
                            }

                        } else {
                            if (y > piece.getY()) {
                                while (tmpy < board.length) {
                                    possibleMoves[x][tmpy] = false;
                                    tmpy++;
                                }

                            }

                        }
                    }
                    // checks if piece is on the same y-level
                    else if (piece.getY() == y) {
                        if (x < piece.getX()) {
                            while (tmpx >= 0) {
                                possibleMoves[tmpx][y] = false;
                                tmpx--;
                            }

                        }

                        else {
                            while (tmpx < board.length) {
                                possibleMoves[tmpx][y] = false;
                                tmpx++;
                            }

                        }

                    }
                    // for knights if there is a piece on their move
                    else if (Math.abs(x - piece.getX()) * Math.abs(y - piece.getY()) == 2) {
                        possibleMoves[x][y] = false;

                    }
                    // if it was a piece of the opposite color it ads that back since you can take
                    // it
                    if (board[x][y].getColor() != piece.getColor()) {
                        possibleMoves[x][y] = true;
                    }
                }

            }
        }
        return possibleMoves;
    }

    public Boolean ifCheck() {
        if(this.blackChecked || this.whiteChecked) {
            return true;
        } else {
            return false;
        }
    }

    public void updateGameOver() {
        
        List<Piece> blackPieces = getAllPlayerPieces(PieceColor.BLACK);
        Map<String, ArrayList<Integer>> blackMoves = getAllPossiblePlayerMoves(blackPieces);

        List<Piece> whitePieces = getAllPlayerPieces(PieceColor.WHITE);
        Map<String, ArrayList<Integer>> whiteMoves = getAllPossiblePlayerMoves(whitePieces);

        if (blackMoves.isEmpty()) {
            this.gameOver = "w";
        }else if(whiteMoves.isEmpty()){
            this.gameOver = "b";
        }else{
            this.gameOver = null;
        }
    }

    public String getGameOver() {
        updateGameOver();
        return this.gameOver;
    }

    public List<Piece> getAllPlayerPieces(PieceColor color){
        List<Piece> pieces = new ArrayList<>();
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                Piece piece = board[x][y];
                if (piece != null && piece.getColor() == color) {
                    pieces.add(piece);
                }
            }
        }
        return pieces;
    }

    public Map<String, ArrayList<Integer>> getAllPossiblePlayerMoves(List<Piece> pieces){
        Map<String, ArrayList<Integer>> moves = new HashMap<>();
        /*
         * {
         * from: [x, y],
         * to: [x, y]
         * }
         * 
         */
        for (Piece piece : pieces) {
            Boolean[][] possibleMoves = getPossibleMoves(piece);
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

    public int[][] getRandomMove(PieceColor color) {

        List<Piece> pieces = getAllPlayerPieces(color);
        
        Map<String, ArrayList<Integer>> moves = getAllPossiblePlayerMoves(pieces);
        // Get random move
        if (moves.size() > 0) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(moves.size());
            String from = (String) moves.keySet().toArray()[randomIndex];
            ArrayList<Integer> to = moves.get(from);
            int[] fromCoords = new int[2];
            int[] toCoords = new int[2];
            fromCoords[0] = Integer.parseInt(from.split(",")[0]);
            fromCoords[1] = Integer.parseInt(from.split(",")[1]);
            toCoords[0] = to.get(0);
            toCoords[1] = to.get(1);
            return new int[][] { fromCoords, toCoords };
        }
        return null; // No valid moves available
    }
}