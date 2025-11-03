package com.main.guapogame;

import static com.main.guapogame.Constants.NUM_LIVES;

public class Lives {
    private int lives = NUM_LIVES;

    public int getLives() {
        return lives;
    }

    public void takeLife() {
        lives--;
    }
}
