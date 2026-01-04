package com.main.guapogame.storage;

import static com.main.guapogame.parameters.Keys.BACKGROUND;
import static com.main.guapogame.parameters.Keys.BEGGIN;
import static com.main.guapogame.parameters.Keys.BROWNIE;
import static com.main.guapogame.parameters.Keys.CHECKPOINT;
import static com.main.guapogame.parameters.Keys.DIFFICULTY_LEVEL;
import static com.main.guapogame.parameters.Keys.FRAME_COUNTER;
import static com.main.guapogame.parameters.Keys.FRITO;
import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.HERO;
import static com.main.guapogame.parameters.Keys.HIGH_SCORE;
import static com.main.guapogame.parameters.Keys.IS_HIT;
import static com.main.guapogame.parameters.Keys.IS_TOP;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.LIVES;
import static com.main.guapogame.parameters.Keys.MISTY;
import static com.main.guapogame.parameters.Keys.NUM_SNACKS;
import static com.main.guapogame.parameters.Keys.NUM_VILLAINS;
import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Keys.SCORE;
import static com.main.guapogame.parameters.Keys.SNACK;
import static com.main.guapogame.parameters.Keys.SNACK_POINTS;
import static com.main.guapogame.parameters.Keys.VELOCITY_X;
import static com.main.guapogame.parameters.Keys.VELOCITY_Y;
import static com.main.guapogame.parameters.Keys.VILLAIN;
import static com.main.guapogame.parameters.Keys.getKey;

import android.content.Context;
import android.content.SharedPreferences;

import com.main.guapogame.graphics.Background;
import com.main.guapogame.graphics.BegginStrip;
import com.main.guapogame.graphics.CharacterPopup;
import com.main.guapogame.graphics.Hero;
import com.main.guapogame.graphics.Misty;
import com.main.guapogame.graphics.Snack;
import com.main.guapogame.graphics.Villain;

import java.util.List;

public class Save {

    private final SharedPreferences prefs;
    private final Context context;

    public Save(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("game", Context.MODE_PRIVATE);
    }

    public void saveHero(Hero hero) {
        savePosition(getKey(getLevelId(), HERO, POSITION_X), hero.getPositionX());
        savePosition(getKey(getLevelId(), HERO, POSITION_Y), hero.getPositionY());
        savePosition(getKey(getLevelId(), HERO, VELOCITY_X), hero.getVelocityX());
        savePosition(getKey(getLevelId(), HERO, VELOCITY_Y), hero.getVelocityY());
        saveFrameCounter(getKey(getLevelId(), HERO, FRAME_COUNTER), hero.getFrameCounter());
    }

    public void saveMisty(Misty misty) {
        savePosition(getKey(getLevelId(), MISTY, POSITION_X), misty.getPositionX());
        savePosition(getKey(getLevelId(), MISTY, POSITION_Y), misty.getPositionY());
        saveIsHit(getKey(getLevelId(), MISTY, IS_HIT), misty.isHit());
        saveMistyIsTop(getKey(getLevelId(), MISTY, IS_TOP), misty.isTop());
        saveFrameCounter(getKey(getLevelId(), MISTY, FRAME_COUNTER), misty.getFrameCounter());
    }

    public void saveBrownie(CharacterPopup brownie) {
        savePosition(getKey(getLevelId(), BROWNIE, POSITION_X), brownie.getPositionX());
        savePosition(getKey(getLevelId(), BROWNIE, POSITION_Y), brownie.getPositionY());
        saveIsHit(getKey(getLevelId(), BROWNIE, IS_HIT), brownie.getIsHit());
        saveVelocity(getKey(getLevelId(), BROWNIE, VELOCITY_Y), brownie.getVelY());
        saveFrameCounter(getKey(getLevelId(), BROWNIE, FRAME_COUNTER), brownie.getFrameCounter());
    }

    public void saveFrito(CharacterPopup frito) {
        savePosition(getKey(getLevelId(), FRITO, POSITION_X), frito.getPositionX());
        savePosition(getKey(getLevelId(), FRITO, POSITION_Y), frito.getPositionY());
        saveIsHit(getKey(getLevelId(), FRITO, IS_HIT), frito.getIsHit());
        saveVelocity(getKey(getLevelId(), FRITO, VELOCITY_Y), frito.getVelY());
        saveFrameCounter(getKey(getLevelId(), FRITO, FRAME_COUNTER), frito.getFrameCounter());
    }

    private void saveIsHit(String key, boolean isHit) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, isHit);
        editor.apply();
    }

    private void saveMistyIsTop(String key, boolean isTop) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, isTop);
        editor.apply();
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
        int snackId = 0;
        for(Snack snack : snacks) {
            if(snack instanceof BegginStrip begginStrip) {
                saveBegginStrip(begginStrip);
            }
            else {
                saveSnack(snack, String.valueOf(snackId));
                snackId++;
            }
        }
        saveNum(getKey(getLevelId(), SNACK, NUM_SNACKS), snackId);
    }

    public void saveDifficulty(int difficultyLevel) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getKey(getLevelId(), DIFFICULTY_LEVEL), difficultyLevel);
        editor.apply();
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

    public void saveHighScore(int score) {
        String levelId = getLevelId();
        if (getHighScore(levelId) < score) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putInt(getKey(levelId, HIGH_SCORE), score);
            editor.apply();
        }
    }

    private int getHighScore(String levelId) {
        return getSharedPreferences().getInt(getKey(levelId, HIGH_SCORE), 0);
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GAME, Context.MODE_PRIVATE);
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
    }

    private void saveBegginStrip(BegginStrip snack) {
        savePosition(getKey(getLevelId(), BEGGIN, POSITION_X), snack.getPositionX());
        savePosition(getKey(getLevelId(), BEGGIN, POSITION_Y), snack.getPositionY());
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

    private void saveVelocity(String key, float velocity) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, velocity);
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

    private void saveFrameCounter(String key, int frameCounter) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, frameCounter);
        editor.apply();
    }

    private String getLevelId() {
        return prefs.getString(LEVEL, "");
    }
}
