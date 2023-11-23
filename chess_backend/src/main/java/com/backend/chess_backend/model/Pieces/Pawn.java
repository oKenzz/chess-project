package com.backend.chess_backend.model.Pieces;

public class Pawn extends Piece {
    private boolean dead;
    private PieceColor color;
    private int xCoord;
    private int yCoord;
    private int hasmoved;

    public Pawn(PieceColor color, int xCoord, int yCoord) {
        super(color, xCoord, yCoord);
    }

    // returns all possible moves for that piece not taking the board pieces in
    // account. Returns a matrix of booleans, true where you can move, false
    // otherwise;
    @Override
    public Boolean[][] getPossibleMoves(int boardW, int boardH) {
        Boolean[][] moves = new Boolean[boardW][boardH];

        // Initialize all moves to false
        for (int i = 0; i < boardW; i++) {
            for (int j = 0; j < boardH; j++) {
                moves[i][j] = false;
            }
        }

        // Determine the direction of movement based on the color of the pawn
        int direction = (this.color == PieceColor.WHITE) ? -1 : 1;

        // Check the square directly in front of the pawn
        int forwardX = this.xCoord;
        int forwardY = this.yCoord + direction;

        if (forwardY >= 0 && forwardY < boardH) {
            moves[forwardX][forwardY] = true;

            // If it's the first move, the pawn can move two squares forward
            if (this.getMovesMade() == 0 && (forwardY + direction >= 0 && forwardY + direction < boardH)) {
                moves[forwardX][forwardY + direction] = true;
            }
        }

        // Check the diagonal squares for capturing
        int[] captureX = { this.xCoord - 1, this.xCoord + 1 };

        for (int x : captureX) {
            if (x >= 0 && x < boardW && forwardY >= 0 && forwardY < boardH) {
                // In an actual game, we would also check if there is an opponent's piece on
                // this square
                moves[x][forwardY] = true;
            }
        }
        return moves;
    }

    @Override
    public String getPieceType() {
        if (this.getColor() == PieceColor.BLACK) {
            return "p";
        } else {
            return "P";
        }
    }
}
