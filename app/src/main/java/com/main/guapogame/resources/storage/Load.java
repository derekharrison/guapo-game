package com.main.guapogame.resources.storage;

import static com.main.guapogame.parameters.Keys.BACKGROUND;
import static com.main.guapogame.parameters.Keys.BEGGIN;
import static com.main.guapogame.parameters.Keys.BROWNIE;
import static com.main.guapogame.parameters.Keys.CHECKPOINT;
import static com.main.guapogame.parameters.Keys.DIFFICULTY_LEVEL;
import static com.main.guapogame.parameters.Keys.FRAME_COUNTER;
import static com.main.guapogame.parameters.Keys.FRITO;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.HERO;
import static com.main.guapogame.parameters.Keys.IS_HIT;
import static com.main.guapogame.parameters.Keys.IS_TOP;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.LIVES;
import static com.main.guapogame.parameters.Keys.MISTY;
import static com.main.guapogame.parameters.Keys.NUM_SNACKS;
import static com.main.guapogame.parameters.Keys.NUM_VILLAINS;
import static com.main.guapogame.parameters.Keys.ROCCO;
import static com.main.guapogame.parameters.Keys.SCORE;
import static com.main.guapogame.parameters.Keys.SNACK;
import static com.main.guapogame.parameters.Keys.SNACK_ASSET_ID;
import static com.main.guapogame.parameters.Keys.SNACK_POINTS;
import static com.main.guapogame.parameters.Keys.VILLAIN;
import static com.main.guapogame.parameters.Keys.getKey;

import android.content.Context;
import android.content.SharedPreferences;

public class Load {

    private final SharedPreferences prefs;

    public Load(Context context) {
        prefs = context.getSharedPreferences("game", Context.MODE_PRIVATE);
    }

    public float getHeroPosition(String key) {
        return getPosition(getKey(getLevelId(), HERO, key));
    }

    public float getMistyPosition(String key) {
        return getPosition(getKey(getLevelId(), MISTY, key));
    }

    public boolean getMistyIsHit() {
        return getHit(getKey(getLevelId(), MISTY, IS_HIT));
    }

    public int getMistyFrameCounter() {
        return getFrameCounter(getKey(getLevelId(), MISTY, FRAME_COUNTER));
    }

    public boolean getMistyIsTop() {
        return getTop(getKey(getLevelId(), MISTY, IS_TOP));
    }

    public float getBrowniePosition(String key) {
        return getPosition(getKey(getLevelId(), BROWNIE, key));
    }

    public float getBrownieVelocity(String key) {
        return getVelocity(getKey(getLevelId(), BROWNIE, key));
    }

    public boolean getBrownieIsHit() {
        return getHit(getKey(getLevelId(), BROWNIE, IS_HIT));
    }

    public int getBrownieFrameCounter() {
        return getFrameCounter(getKey(getLevelId(), BROWNIE, FRAME_COUNTER));
    }

    public float getFritoPosition(String key) {
        return getPosition(getKey(getLevelId(), FRITO, key));
    }

    public boolean getFritoIsHit() {
        return getHit(getKey(getLevelId(), FRITO, IS_HIT));
    }

    public int getFritoFrameCounter() {
        return getFrameCounter(getKey(getLevelId(), FRITO, FRAME_COUNTER));
    }

    public float getFritoVelocity(String key) {
        return getVelocity(getKey(getLevelId(), FRITO, key));
    }

    public float getRoccoPosition(String key) {
        return getPosition(getKey(getLevelId(), ROCCO, key));
    }

    public boolean getRoccoIsHit() {
        return getHit(getKey(getLevelId(), ROCCO, IS_HIT));
    }

    public int getRoccoFrameCounter() {
        return getFrameCounter(getKey(getLevelId(), ROCCO, FRAME_COUNTER));
    }

    public float getRoccoVelocity(String key) {
        return getVelocity(getKey(getLevelId(), ROCCO, key));
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

    public float getBegginPosition(String key) {
        return getPosition(getKey(getLevelId(), BEGGIN, key));
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

    public int getCheckpoint() {
        return prefs.getInt(getKey(getLevelId(), CHECKPOINT), 0);
    }

    public int getDifficultyLevel() {
        return prefs.getInt(getKey(getLevelId(), DIFFICULTY_LEVEL), 0);
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

    public boolean getSessionIsActive() {
        return prefs.getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private String getLevelId() {
        return prefs.getString(LEVEL, "");
    }

    private float getPosition(String key) {
        return prefs.getFloat(key, 0);
    }

    private boolean getHit(String key) {
        return prefs.getBoolean(key, false);
    }

    private boolean getTop(String key) {
        return prefs.getBoolean(key, false);
    }

    private int getFrameCounter(String key) {
        return prefs.getInt(key, 0);
    }

    private float getVelocity(String key) {
        return prefs.getFloat(key, 0);
    }
}
