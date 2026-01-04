package com.main.guapogame.graphics;

import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.getKey;
import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;
import com.main.guapogame.parameters.Parameters;
import com.main.guapogame.storage.Storage;

import java.util.ArrayList;
import java.util.List;

class LivesBuilder {
    private Context context;
    private Storage storage;

    LivesBuilder context(Context context) {
        this.context = context;
        return this;
    }

    LivesBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    List<Bitmap> build() {
        return createLives();
    }

    private List<Bitmap> createLives() {
        List<Bitmap> lives = new ArrayList<>();
        for(int id = 0; id < getNumLives(); id++) {
            Bitmap life = getBitmapScaled(
                    getScreenFactorX() / 4,
                    getScreenFactorY() / 4
                    );
            lives.add(life);
        }

        return lives;
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart1_bitmap_cropped);
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
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }

    private int getScreenFactorX() {
        return (int) (getScreenWidth() / 10.0);
    }

    private int getScreenFactorY() {
        return (int) (getScreenHeight() / 5.0);
    }
}
