package com.main.guapogame;

public class Keys {
    public static String HERO = "hero";
    public static String SNACK = "snack";
    public static String VILLAIN = "villain";
    public static String NUM_VILLAINS = "numVillains";
    public static String BACKGROUND = "background";
    public static String NUM_BACKGROUNDS = "numBackgrounds";
    public static String SNACK_POINTS = "snackPoints";
    public static String FRAME_COUNTER = "frameCounter";
    public static String POSITION_X = "positionX";
    public static String POSITION_Y = "positionY";
    public static String VELOCITY_X = "velocityX";
    public static String VELOCITY_Y = "velocityY";
    public static String GAMESTATE = "gameState";
    public static String LEVEL = "level";
    public static String ARUBA = "aruba";
    public static String BEACH = "beach";
    public static String TRIP = "trip";
    public static String OCEAN = "ocean";
    public static String UTREG = "utrecht";

    public static String getKey(String...key) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String k : key) {
            stringBuilder.append(k);
            stringBuilder.append("$");
        }

        return stringBuilder.toString();
    }
}
