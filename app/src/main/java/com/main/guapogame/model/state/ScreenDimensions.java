package com.main.guapogame.model.state;

public class ScreenDimensions {
    private static int screenWidth;
    private static int screenHeight;

    public static void setScreenWidth(int screenWidth1) { screenWidth = screenWidth1; }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenHeight(int screenHeight1) {
        screenHeight = screenHeight1;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    private ScreenDimensions() {}
}
