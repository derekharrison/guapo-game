package com.main.guapogame.parameters;

public class Keys {
    public static final String HERO = "hero";
    public static final String MISTY = "misty";
    public static final String BROWNIE = "brownie";
    public static final String FRITO = "frito";
    public static final String SNACK = "snack";
    public static final String BEGGIN = "beggin";
    public static final String VILLAIN = "villain";
    public static final String NUM_VILLAINS = "numVillains";
    public static final String NUM_SNACKS = "numSnacks";
    public static final String BACKGROUND = "background";
    public static final String SNACK_POINTS = "snackPoints";
    public static final String SNACK_ASSET_ID = "snackAssetId";
    public static final String FRAME_COUNTER = "frameCounter";
    public static final String POSITION_X = "positionX";
    public static final String POSITION_Y = "positionY";
    public static final String VELOCITY_X = "velocityX";
    public static final String VELOCITY_Y = "velocityY";
    public static final String GAMESTATE = "gameState";
    public static final String LEVEL = "level";
    public static final String ARUBA = "aruba";
    public static final String BEACH = "beach";
    public static final String TRIP = "trip";
    public static final String OCEAN = "ocean";
    public static final String UTREG = "utrecht";
    public static final String HIGH_SCORE = "highScore";
    public static final String SCORE = "score";
    public static final String CHECKPOINT = "checkpoint";
    public static final String GAME = "game";
    public static final String LIVES = "lives";
    public static final String IS_HIT = "hit";
    public static final String IS_TOP = "top";
    public static final String DIFFICULTY_LEVEL = "difficultyLevel";

    public static String getKey(String...key) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String k : key) {
            stringBuilder.append(k);
            stringBuilder.append("$");
        }

        return stringBuilder.toString();
    }

    private Keys() {}
}
