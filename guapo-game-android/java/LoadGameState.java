package com.main.guapogame;

import static com.main.guapogame.Keys.BACKGROUND;
import static com.main.guapogame.Keys.CHECKPOINT;
import static com.main.guapogame.Keys.FRAME_COUNTER;
import static com.main.guapogame.Keys.HERO;
import static com.main.guapogame.Keys.LEVEL;
import static com.main.guapogame.Keys.LIVES;
import static com.main.guapogame.Keys.NUM_SNACKS;
import static com.main.guapogame.Keys.NUM_VILLAINS;
import static com.main.guapogame.Keys.SCORE;
import static com.main.guapogame.Keys.SNACK;
import static com.main.guapogame.Keys.SNACK_ASSET_ID;
import static com.main.guapogame.Keys.SNACK_POINTS;
import static com.main.guapogame.Keys.VILLAIN;
import static com.main.guapogame.Keys.getKey;

import android.content.Context;
import android.content.SharedPreferences;


public class LoadGameState {

    SharedPreferences prefs;

    public LoadGameState(Context context) {
        prefs = context.getSharedPreferences("game", Context.MODE_PRIVATE);
    }

    public float getHeroPosition(String key) {
        return getPosition(getKey(getLevelId(), HERO, key));
    }

    public float getVillainPosition(String key, String villainId) {
        return getPosition(getKey(getLevelId(), VILLAIN, key, villainId));
    }

    public float getVillainVelocity(String key, String villainId) {
        return getVelocity(getKey(getLevelId(), VILLAIN, key, villainId));
    }

    public float getSnackPosition(String key, String snackId) {
        return getPosition(getKey(getLevelId(), SNACK, key, snackId));
    }

    public int getSnackAssetId(String snackId) {
        return prefs.getInt(getKey(getLevelId(), SNACK, SNACK_ASSET_ID, snackId), 1);
    }

    public int getSnackPoints(String snackId) {
        return prefs.getInt(getKey(getLevelId(), SNACK, SNACK_POINTS, snackId), 0);
    }

    public float getBackgroundPosition(String key, String backgroundId) {
        return prefs.getFloat(getKey(BACKGROUND, getLevelId(), key, backgroundId), 0);
    }

    public int getHeroFrameCounter() {
        return prefs.getInt(getKey(getLevelId(), HERO, FRAME_COUNTER), 0);
    }

    public int getScore() {
        return prefs.getInt(getKey(getLevelId(), SCORE), 0);
    }

    public int getNumLives() {
        return prefs.getInt(getKey(getLevelId(), LIVES), 0);
    }

    private String getLevelId() {
        return prefs.getString(LEVEL, "");
    }

    public int getCheckpoint() {
        return prefs.getInt(getKey(getLevelId(), CHECKPOINT), 0);
    }


    public int getNumVillains() {
        return prefs.getInt(getKey(getLevelId(), VILLAIN, NUM_VILLAINS), 3);
    }

    public int getNumSnacks() {
        return prefs.getInt(getKey(getLevelId(), SNACK, NUM_SNACKS), 1);
    }

    public int getVillainFrameCounter(String villainId) {
        return prefs.getInt(getKey(getLevelId(), VILLAIN, FRAME_COUNTER, villainId), 0);
    }

    private float getPosition(String key) {
        return prefs.getFloat(key, 0);
    }

    private float getVelocity(String key) {
        return prefs.getFloat(key, 0);
    }
}
