package com.main.guapogame.model.state;

public class BackgroundSpeed {

    private static int speed;

    public static void setBackgroundSpeed(int backgroundSpeed1) {
        speed = backgroundSpeed1;
    }

    public static int getBackgroundSpeed() {
        return speed;
    }

    private BackgroundSpeed() {}
}
