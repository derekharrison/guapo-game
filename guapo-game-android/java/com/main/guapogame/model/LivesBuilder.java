package com.main.guapogame.model;

import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.getKey;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;
import com.main.guapogame.definitions.Parameters;
import com.main.guapogame.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class LivesBuilder {

    private final List<Bitmap> lives = new ArrayList<>();
    private Context context;
    private Storage storage;
    private Resources resources;

    public LivesBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public LivesBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public LivesBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public List<Bitmap> build() {
        createLives();
        return lives;
    }

    private void createLives() {
        for(int id = 0; id < getNumLives(); id++) {
            Bitmap life = getBitmapScaled(
                    getScreenFactorX() / 4,
                    getScreenFactorY() / 4,
                    R.drawable.heart1_bitmap_cropped);
            lives.add(life);
        }
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private int getNumLives() {
        if(isActiveSession()) {
            return storage.loadGame().getNumLives();
        }

        return Parameters.NUM_LIVES;
    }

    private boolean isActiveSession() {
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private String getLevelId() {
        return  context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private int getScreenFactorX() {
        return (int) (getScreenWidth() / 10.0);
    }

    private int getScreenFactorY() {
        return (int) (getScreenHeight() / 5.0);
    }
}
