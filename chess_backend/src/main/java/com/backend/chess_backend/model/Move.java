package com.backend.chess_backend.model;

public class Move {
    private int[] startSpot;
    private int[] endSpot;

    public Move(int[] startSpot, int[] endSpot) {
        this.startSpot = startSpot;
        this.endSpot = endSpot;
    }

    public int[] getStartSpot() {
        return startSpot;
    }

    public int[] getEndSpot() {
        return endSpot;
    }
}