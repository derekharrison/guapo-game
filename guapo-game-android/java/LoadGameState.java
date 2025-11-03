package com.main.guapogame;

import static com.main.guapogame.Keys.BACKGROUND;
import static com.main.guapogame.Keys.FRAME_COUNTER;
import static com.main.guapogame.Keys.HERO;
import static com.main.guapogame.Keys.NUM_VILLAINS;
import static com.main.guapogame.Keys.SNACK;
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
        return getPosition(getKey(HERO, key));
    }

    public float getVillainPosition(String key, String villainId) {
        return getPosition(getKey(VILLAIN, key, villainId));
    }

    public float getVillainVelocity(String key, String villainId) {
        return getVelocity(getKey(VILLAIN, key, villainId));
    }

    public float getSnackPosition(String key, String snackId) {
        return getPosition(getKey(SNACK, key, snackId));
    }

    public float getBackgroundPosition(String key, String backgroundId) {
        return prefs.getFloat(getKey(BACKGROUND, key, backgroundId), 0);
    }

    public int getHeroFrameCounter() {
        return prefs.getInt(getKey(HERO, FRAME_COUNTER), 0);
    }

    public int getScore() {
        return prefs.getInt("score", 0);
    }

    public int getCheckpoint() {
        return prefs.getInt("checkpoint", 0);
    }


    public int getNumVillains() {
        return prefs.getInt(getKey(VILLAIN, NUM_VILLAINS), 3);
    }

    public int getVillainFrameCounter(String villainId) {
        return prefs.getInt(getKey(VILLAIN, FRAME_COUNTER, villainId), 0);
    }

    private float getPosition(String key) {
        return prefs.getFloat(key, 0);
    }

    private float getVelocity(String key) {
        return prefs.getFloat(key, 0);
    }

    // TODO getGameState of remaining parameters, e.g. fish
}
