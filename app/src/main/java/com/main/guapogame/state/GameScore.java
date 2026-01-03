package com.main.guapogame.state;

public class GameScore {
    private static int score = 0;

    public static void setScore(int score1) {
        score = score1;
    }

    public static int getScore() {
        return score;
    }

    private GameScore() {}
}
