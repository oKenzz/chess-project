package com.backend.chess_backend.model;

public class Player {

    private long startTime;
    private int timeLeft;
    private String uuid;
    private boolean isBot;
    private boolean isTimerRunning;
    private boolean isOccupied;

    public Player(boolean isBot, int intialTime) {
        this.isBot = isBot;
        this.timeLeft = intialTime;
        this.isTimerRunning = false;
        this.isOccupied = false;
    }

    public void setPlayerID(String uuid) {
        this.uuid = uuid;
        this.isOccupied = true;
    }

    public void emptyPlayer() {
        this.uuid = null;
        this.isOccupied = false;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setBot(boolean isBot) {
        this.isBot = isBot;
    }

    public void startTimer() {
        if (!isTimerRunning) {
            this.startTime = System.currentTimeMillis() / 1000L;
            isTimerRunning = true;
        }
    }

    public void pauseTimer() {
        if (isTimerRunning) {
            long currentTime = System.currentTimeMillis() / 1000L;
            int timeElapsed = (int) (currentTime - startTime);
            timeLeft = Math.max(timeLeft - timeElapsed, 0);
            isTimerRunning = false;
        }
    }

    public int getTimeLeft() {
        if (isTimerRunning) {
            long currentTime = System.currentTimeMillis() / 1000L;
            int timeElapsed = (int) (currentTime - startTime);
            return Math.max(timeLeft - timeElapsed, 0);
        }
        return timeLeft;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isBot() {
        return isBot;
    }
}
