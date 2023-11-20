package com.backend.chess_backend.model;

import java.beans.PersistenceDelegate;
import java.util.UUID;

import javax.swing.BorderFactory;

import com.backend.chess_backend.model.Pieces.Piece;
import com.backend.chess_backend.model.Pieces.PieceColor;

public class Game {
    
    private Board board;
    private Player playerWhite;
    private Player playerBlack;
    private int turnsMade;
    private String gameId;


    public Game(String gameid){
        this.gameId = gameid;
        this.turnsMade = 0;
        this.board = new Board();
       // this.playerWhite = new Player(WHITE);
       // this.playerBlack = new Player(BLACK);
    }

    public Boolean[][] possibleMoves(int x, int y){
        Piece[][] currentBoard = board.getBoard(); 
        int boardW = currentBoard.length;
        int boardH = currentBoard[0].length;
        Boolean[][] emptyList = new Boolean[boardW][boardH];
        
        for (int i = 0; i < boardW; i++) {
            for (int j = 0; j < boardH; j++) {
                emptyList[i][j] = false;
            }
        }  

        if(currentBoard[x][y].getColor() == PieceColor.WHITE && turnsMade % 2 == 0){
            return board.getPossibleMoves(x, y);
        }
        else if (currentBoard[x][y].getColor() == PieceColor.BLACK && turnsMade % 2 != 0){
            return board.getPossibleMoves(x, y);
        }
        
        return emptyList;
    }

    public Boolean attemptMove(int x, int y, int newX, int newY){
        Piece[][] currentBoard = board.getBoard(); 
        Boolean[][] possibleM = board.getPossibleMoves(x, y);
        if (board.checkIfallowed(newX, newY, possibleM)){
            board.updateCoords(currentBoard[x][y], newX, newY);
            IncrementTurn();
            return true;
        }

        return false;

    }
    private void IncrementTurn(){
        turnsMade++;
    }

    public Piece[][] getBoard(){
        return board.getBoard();
    }

    private void subtractTurn(){
        turnsMade--;
    }

    public void addPlayer(Player player) {
        if (playerWhite == null) {
            playerWhite = player;
        } else if (playerBlack == null) {
            playerBlack = player;
        }
    }

    public boolean isFull() {
        return playerWhite != null && playerBlack != null;
    }

    public String getId() {
        return gameId;
    }
}
