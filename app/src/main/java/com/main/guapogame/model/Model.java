package com.main.guapogame.model;

import static android.content.Context.MODE_PRIVATE;
import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.LIVES;
import static com.main.guapogame.parameters.Keys.getKey;
import static com.main.guapogame.model.state.BackgroundSpeed.setBackgroundSpeed;
import static com.main.guapogame.model.state.ScreenDimensions.setScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.setScreenWidth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;

import androidx.appcompat.app.AppCompatActivity;

import com.main.guapogame.presentation.activity.ContinueActivity;
import com.main.guapogame.presentation.activity.LevelActivity;
import com.main.guapogame.model.graphics.gameobjects.Graphics;
import com.main.guapogame.model.graphics.builders.GraphicsBuilder;
import com.main.guapogame.model.graphics.gameobjects.Hero;
import com.main.guapogame.model.interfaces.Draw;
import com.main.guapogame.model.interfaces.Update;
import com.main.guapogame.resources.Sounds;
import com.main.guapogame.state.GameScore;
import com.main.guapogame.resources.storage.Storage;

public class Model implements Update, Draw {
    private Graphics graphics;
    private final Storage storage;
    private final AppCompatActivity activity;
    private ModelUpdate modelUpdate;
    private ModelDraw modelDraw;

    public Model(AppCompatActivity activity) {
        this.activity = activity;
        this.storage = new Storage(activity);
        createModelData();
        setSessionIsNotActive();
    }

    @Override
    public void update() {
        modelUpdate.update();
    }

    @Override
    public void draw(Canvas canvas) {
        modelDraw.draw(canvas);
    }

    public Hero getHero() {
        return graphics.getHero();
    }

    public void saveHighScore() {
        storage.saveGame().saveHighScore(GameScore.getScore());
    }

    public void handleGameOver() {
        if(getLives() < 0) {
            saveHighScore();
            transition(LevelActivity.class);
        }
        if(getLives() >= 0) {
            saveHighScore();
            transition(ContinueActivity.class);
        }

        releaseSoundPool();
    }

    private int getLives() {
        return activity.getSharedPreferences(GAME, MODE_PRIVATE).getInt(getKey(getLevelId(), LIVES), 0);
    }

    private void releaseSoundPool() {
        Sounds.releaseSoundPool();
    }
    private String getLevelId() {
        return activity.getSharedPreferences(GAME, MODE_PRIVATE).getString(LEVEL, "");
    }

    private void createModelData() {
        getScreenParameters();

        createGraphics();
        createModelUpdate();
        createModelDraw();
    }

    private void createModelUpdate() {
        modelUpdate = new ModelUpdate.Builder()
                .graphics(graphics)
                .context(activity)
                .storage(storage)
                .build();
    }

    private void createModelDraw() {
        modelDraw = new ModelDraw.Builder()
                .graphics(graphics)
                .build();
    }

    private void createGraphics() {
        graphics = new GraphicsBuilder()
                .storage(storage)
                .context(activity)
                .build();
    }

    private void setSessionIsNotActive() {
        SharedPreferences prefs = activity.getSharedPreferences(GAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getKey(getLevelId(), GAMESTATE), false);
        editor.apply();
    }

    private void getScreenParameters() {
        int backgroundSpeed = (int) (getScreenWidth() / 400.0);

        setScreenWidth(getScreenWidth());
        setScreenHeight(getScreenHeight());
        setBackgroundSpeed(backgroundSpeed);
    }

    private int getScreenWidth() {
        return activity.getResources().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return activity.getResources().getDisplayMetrics().heightPixels;
    }

    private <T extends AppCompatActivity> void transition(Class<T> clazz) {
        Thread thread = new Thread();
        thread.start();
        try {
            Thread.sleep(2000);
            activity.startActivity(new Intent(activity, clazz));
            activity.finish();
        } catch (Exception _) {
            thread.interrupt();
        }
    }
}
