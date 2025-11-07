package com.main.guapogame.model;

import static com.main.guapogame.definitions.Parameters.setBackgroundSpeed;
import static com.main.guapogame.definitions.Parameters.setScreenHeight;
import static com.main.guapogame.definitions.Parameters.setScreenWidth;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import com.main.guapogame.graphics.Graphics;
import com.main.guapogame.graphics.Hero;
import com.main.guapogame.interfaces.Draw;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.storage.Storage;

public class Model implements Update, Draw {
    private Graphics graphics;
    private final Resources resources;
    private final Storage storage;
    private final Context context;
    private ModelUpdate modelUpdate;
    private ModelDraw modelDraw;

    public Model(Context context, Resources resources) {
        this.resources = resources;
        this.context = context;
        this.storage = new Storage(context);

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
                .context(context)
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
                .context(context)
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
}
