package com.main.guapogame.resources;
public class Parameters {
    public static final int LEVEL_UNLOCK_SCORE = 25;
    public static final int CHECK_POINT_INTERVAL = 25;
    public static final int MAX_VILLAINS = 12;
    public static final int POINTS_BEGGIN_STRIPS = 5;
    public static final int NUM_CHEESY_BITES = 2;
    public static final int POINTS_CHEESY_BITES = 1;
    public static final int NUM_PAPRIKA = 1;
    public static final int POINTS_PAPRIKA = 1;
    public static final int NUM_CUCUMBERS = 1;
    public static final int POINTS_CUCUMBER = 1;
    public static final int NUM_BROCCOLI = 1;
    public static final int POINTS_BROCCOLI = 3;
    public static final int POINTS_WHEN_BEGGIN_STRIPS_APPEAR = 50;
    public static final int SCORE_INTERVAL_DIFFICULTY_LEVEL = 35;
    public static final int NUM_LIVES = 3;
    public static final int FPS = 30;
    public static final int START_NUM_OF_VILLAINS = 3;

    private static int BACKGROUND_SPEED;
    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;
    public static void setBackgroundSpeed(int backgroundSpeed) {
        BACKGROUND_SPEED = backgroundSpeed;
    }

    public static int getBackgroundSpeed() {
        return BACKGROUND_SPEED;
    }

    public static void setScreenWidth(int screenWidth) {
        SCREEN_WIDTH = screenWidth;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static void setScreenHeight(int screenHeight) {
        SCREEN_HEIGHT = screenHeight;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }
}
