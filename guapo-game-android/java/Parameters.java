package com.main.guapogame;
public class Parameters {
    // Level Parameters
    public static final int LEVEL_UNLOCK_SCORE = 100;
    public static final int CHECK_POINT_INTERVAL = 100;
    public static final int MAX_NUM_BIRDS = 12;
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
    public static final int NUM_FRAMES_POPUP = 120;
    public static final int NUM_LIVES = 3;

    // Additional parameters for level4
    public static final int NUM_FISH1 = 1;
    public static final int NUM_FISH2 = 1;
    public static final int NUM_FISH3 = 1;
    public static final int NUM_FISH4 = 3;
    public static final int NUM_FISH5 = 2;
    public static final int NUM_FISH6 = 2;
    public static final int NUM_PUFFER = 1;
    public static final int NUM_JELLY_FISH_INCREASE = 1;
    public static final int MAX_JELLY_FISH = 12;

    // Background image files
    public static final String BACKGROUND_IMAGES_LEVEL1 = "background_guapo_game_nr";
    public static final String BACKGROUND_IMAGES_LEVEL2 = "beach_background_slide";
    public static final String BACKGROUND_IMAGES_LEVEL3 = "background_guapo_game_level3_";
    public static final String BACKGROUND_IMAGES_LEVEL4 = "background_guapogame_underwaterlevel_";
    public static final String BACKGROUND_IMAGES_LEVEL5 = "background_tuttigame_nr";

    // Data names
    public static final String GAME_STATE_STR = "game_state";
    public static final String LEVEL1_STR = "level_1";
    public static final String LEVEL2_STR = "level_2";
    public static final String LEVEL3_STR = "level_3";
    public static final String LEVEL4_STR = "level_4";
    public static final String LEVEL5_STR = "level_5";
    public static final String NUM_LIVES_STR = "num_lives";
    public static final String NUM_JELLYFISH_STR = "num_jellyfish";
    public static final String BACKGROUNDS_STR = "backgrounds";
    public static final String BIRDS_STR = "birds";
    public static final String JELLYFISH_STR = "jellyfish";
    public static final String NUM_BIRDS_STR = "num_birds";
    public static final String FRITO_STR = "frito";
    public static final String MISTY_STR = "misty";
    public static final String MISTY_TOP_STR = "misty_top";
    public static final String BROWNIE_STR = "brownie";
    public static final String BROCCOLI_STR = "broccoli";
    public static final String CHEESY_BITES_STR = "cheesy_bites";
    public static final String PAPRIKA_STR = "paprika";
    public static final String CUCUMBERS_STR = "cucumbers";
    public static final String BEGGIN_STR = "beggin_strip";
    public static final String GUAPO_LOC_STR = "guapo_loc";
    public static final String SCORE_STR = "score";
    public static final String DIFFICULTY_LEVEL_STR = "difficulty_level";
    public static final String CHECKPOINT_STR = "checkpoint_num";
    public static final String FISH1 = "fish1";
    public static final String FISH2 = "fish2";
    public static final String FISH3 = "fish3";
    public static final String FISH4 = "fish4";
    public static final String FISH5 = "fish5";
    public static final String FISH7 = "fish7";
    public static final String PUFFERFISH_STR = "pufferfish";

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
