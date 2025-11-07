package com.main.guapogame.model;

import static android.content.Context.MODE_PRIVATE;
import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.LIVES;
import static com.main.guapogame.definitions.Keys.getKey;
import static com.main.guapogame.definitions.Parameters.setBackgroundSpeed;
import static com.main.guapogame.definitions.Parameters.setScreenHeight;
import static com.main.guapogame.definitions.Parameters.setScreenWidth;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;

import androidx.appcompat.app.AppCompatActivity;

import com.main.guapogame.activity.ContinueActivity;
import com.main.guapogame.activity.LevelActivity;
import com.main.guapogame.graphics.Graphics;
import com.main.guapogame.graphics.Hero;
import com.main.guapogame.interfaces.Draw;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.storage.Storage;

public class Model implements Update, Draw {
    private Graphics graphics;
    private final Resources resources;
    private final Storage storage;
    private final AppCompatActivity activity;
    private ModelUpdate modelUpdate;
    private ModelDraw modelDraw;

    public Model(AppCompatActivity activity, Resources resources) {
        this.resources = resources;
        this.activity = activity;
        this.storage = new Storage(activity);
        createModelData();
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
        storage.saveGame().saveHighScore(GameScore.score);
    }

    public void handleGameOver() {
        if(getLives() < 0) {
            saveHighScore();
            transitionToActivity(LevelActivity.class);
        }
        if(getLives() >= 0) {
            saveHighScore();
            transitionToActivity(ContinueActivity.class);
        }
    }

    private int getLives() {
        return activity.getSharedPreferences(GAME, MODE_PRIVATE).getInt(getKey(getLevelId(), LIVES), 0);
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
                .resources(resources)
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
                .resources(getResources())
                .build();
    }
    
    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void getScreenParameters() {
        int backgroundSpeed = (int) (getScreenWidth() / 400.0);

        setScreenWidth(getScreenWidth());
        setScreenHeight(getScreenHeight());
        setBackgroundSpeed(backgroundSpeed);
    }

    private Resources getResources() {
        return resources;
    }

    private <T extends AppCompatActivity> void transitionToActivity(Class<T> clazz) {
        try {
            Thread.sleep(1000);
            activity.startActivity(new Intent(activity, clazz));
            activity.finish();
        } catch (Exception _) {
        }
    }
}
