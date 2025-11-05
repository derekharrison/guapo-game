package com.main.guapogame.resources;

import static com.main.guapogame.resources.Keys.BACKGROUND;
import static com.main.guapogame.resources.Keys.CHECKPOINT;
import static com.main.guapogame.resources.Keys.FRAME_COUNTER;
import static com.main.guapogame.resources.Keys.HERO;
import static com.main.guapogame.resources.Keys.LEVEL;
import static com.main.guapogame.resources.Keys.LIVES;
import static com.main.guapogame.resources.Keys.NUM_SNACKS;
import static com.main.guapogame.resources.Keys.NUM_VILLAINS;
import static com.main.guapogame.resources.Keys.POSITION_X;
import static com.main.guapogame.resources.Keys.POSITION_Y;
import static com.main.guapogame.resources.Keys.SCORE;
import static com.main.guapogame.resources.Keys.SNACK;
import static com.main.guapogame.resources.Keys.SNACK_ASSET_ID;
import static com.main.guapogame.resources.Keys.SNACK_POINTS;
import static com.main.guapogame.resources.Keys.VELOCITY_X;
import static com.main.guapogame.resources.Keys.VELOCITY_Y;
import static com.main.guapogame.resources.Keys.VILLAIN;
import static com.main.guapogame.resources.Keys.getKey;

import android.content.Context;
import android.content.SharedPreferences;

import com.main.guapogame.graphic_types.Background;
import com.main.guapogame.graphic_types.Snack;
import com.main.guapogame.graphic_types.Villain;
import com.main.guapogame.graphic_types.Hero;

import java.util.List;

public class SaveGame {

    SharedPreferences prefs;

    public SaveGame(Context context) {
        prefs = context.getSharedPreferences("game", Context.MODE_PRIVATE);
    }

    public void saveHero(Hero hero) {
        savePosition(getKey(getLevelId(), HERO, POSITION_X), hero.getPositionX());
        savePosition(getKey(getLevelId(), HERO, POSITION_Y), hero.getPositionY());
        savePosition(getKey(getLevelId(), HERO, VELOCITY_X), hero.getVelocityX());
        savePosition(getKey(getLevelId(), HERO, VELOCITY_Y), hero.getVelocityY());
        saveFrameCounter(getKey(getLevelId(), HERO, FRAME_COUNTER), hero.getFrameCounter());
    }

    public void saveVillains(List<Villain> villains) {
        int villainId = 0;
        for(Villain villain : villains) {
            saveVillain(villain, String.valueOf(villainId));
            villainId++;
        }
        saveNum(getKey(getLevelId(), VILLAIN, NUM_VILLAINS), villains.size());
    }

    public void saveSnacks(List<Snack> snacks) {
        saveNum(getKey(getLevelId(), SNACK, NUM_SNACKS), snacks.size());
        int snackId = 0;
        for(Snack snack : snacks) {
            saveSnack(snack, String.valueOf(snackId));
            snackId++;
        }
    }

    public void saveBackgrounds(List<Background> backgrounds) {
        int backgroundId = 0;
        for(Background background : backgrounds) {
            saveBackground(background, String.valueOf(backgroundId));
            backgroundId++;
        }
    }

    public void saveScore(int score) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getKey(getLevelId(), SCORE), score);
        editor.apply();
    }

    public void saveCheckpoint(int checkpoint) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getKey(getLevelId(), CHECKPOINT), checkpoint);
        editor.apply();
    }

    public void saveNumLives(int numLives) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getKey(getLevelId(), LIVES), numLives);
        editor.apply();
    }

    private void saveBackground(Background background, String backgroundId) {
        String levelId = getLevelId();
        savePosition(getKey(BACKGROUND, levelId, POSITION_X, backgroundId), background.getPositionX());
    }

    private void saveSnack(Snack snack, String snackId) {
        savePosition(getKey(getLevelId(), SNACK, POSITION_X, snackId), snack.getPositionX());
        savePosition(getKey(getLevelId(), SNACK, POSITION_Y, snackId), snack.getPositionY());
        savePosition(getKey(getLevelId(), SNACK, VELOCITY_X, snackId), snack.getVelocityX());
        savePosition(getKey(getLevelId(), SNACK, VELOCITY_Y, snackId), snack.getVelocityY());
        saveSnackPoints(getKey(getLevelId(), SNACK, SNACK_POINTS, snackId), snack.getPointsForSnack());
        saveSnackAssetId(getKey(getLevelId(), SNACK, SNACK_ASSET_ID, snackId), snack.getAssetId());
    }

    private void saveVillain(Villain villain, String villainId) {
        savePosition(getKey(getLevelId(), VILLAIN, POSITION_X, villainId), villain.getPositionX());
        savePosition(getKey(getLevelId(), VILLAIN, POSITION_Y, villainId), villain.getPositionY());
        savePosition(getKey(getLevelId(), VILLAIN, VELOCITY_X, villainId), villain.getVelocityX());
        savePosition(getKey(getLevelId(), VILLAIN, VELOCITY_Y, villainId), villain.getVelocityY());
        saveFrameCounter(getKey(getLevelId(), VILLAIN, FRAME_COUNTER, villainId), villain.getFrameCounter());
    }

    private void savePosition(String key, float position) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, position);
        editor.apply();
    }

    private void saveNum(String key, int num) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, num);
        editor.apply();
    }

    private void saveSnackPoints(String key, int snackPoints) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, snackPoints);
        editor.apply();
    }

    private void saveSnackAssetId(String key, int snackId) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, snackId);
        editor.apply();
    }

    private void saveFrameCounter(String key, int frameCounter) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, frameCounter);
        editor.apply();
    }

    private String getLevelId() {
        return prefs.getString(LEVEL, "");
    }
}
