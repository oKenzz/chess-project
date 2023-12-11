package com.backend.chess_backend.model;

public class Player {
    private long startTime;
    private int timeLeft;
    private String uuid;
    private Boolean isBot;
    private boolean isTimerRunning;

    public Player(String clientUUID, Boolean isBot, int intialTime) {
        this.uuid = clientUUID;
        this.isBot = isBot;
        this.timeLeft = intialTime;
        this.isTimerRunning = false;
    }

    public void startTimer() {
        if (!isTimerRunning) {
            this.startTime = System.currentTimeMillis() / 1000L;
            isTimerRunning = true;
        }
    }

    public Player(String clientUUID) {
        this.uuid = clientUUID;
        this.isBot = false;
    }

    public void toggleTimer() {

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

    public Boolean isBot() {
        return isBot;
    }
}
