package com.main.guapogame;

import static com.main.guapogame.Keys.BACKGROUND;
import static com.main.guapogame.Keys.HERO;
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

    public float getSnackVelocity(String key, String snackId) {
        return getVelocity(getKey(SNACK, key, snackId));
    }

    public float getBackgroundPosition(String key, String backgroundId) {
        return prefs.getFloat(getKey(BACKGROUND, key, backgroundId), 0);
    }

    public float getBackgroundVelocity(String key, String backgroundId) {
        return prefs.getFloat(getKey(BACKGROUND, key, backgroundId), 0);
    }

    private float getPosition(String key) {
        return prefs.getFloat(key, 0);
    }

    private float getVelocity(String key) {
        return prefs.getFloat(key, 0);
    }

    // TODO getGameState of remaining parameters, e.g. fish
}
