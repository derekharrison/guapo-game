package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;
import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Parameters.FPS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.enums.Level;
import com.main.guapogame.model.graphics.gameobjects.BoundaryPopup;
import com.main.guapogame.model.graphics.gameobjects.Chivaz;
import com.main.guapogame.model.state.Random;
import com.main.guapogame.resources.assets.ChivazAssets;
import com.main.guapogame.resources.storage.Storage;
import com.main.guapogame.state.GameState;

public class ChivazBuilder {

    private Context context;
    private Storage storage;

    public ChivazBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public ChivazBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public BoundaryPopup build() {
        return createMisty();
    }

    private BoundaryPopup createMisty() {
        return new Chivaz.Builder()
                .top(getTop())
                .topHit(getTopHit())
                .bottom(getBottom())
                .bottomHit(getBottomHit())
                .positionX(getPositionX())
                .positionY(getPositionY())
                .frameCounter(getFrameCounter())
                .isHit(getHit())
                .isTop(getIsTop())
                .duration(6 * FPS)
                .context(context)
                .build();
    }

    private Bitmap getTop() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, ChivazAssets.getTopOcean());

        return getBitmapScaled(width, height, ChivazAssets.getTop());
    }

    private Bitmap getTopHit() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, ChivazAssets.getTopHitOcean());

        return getBitmapScaled(width, height, ChivazAssets.getTopHit());
    }

    private Bitmap getBottom() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, ChivazAssets.getBottomOcean());

        return getBitmapScaled(width, height, ChivazAssets.getBottom());
    }

    private Bitmap getBottomHit() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, ChivazAssets.getBottomHitOcean());

        return getBitmapScaled(width, height, ChivazAssets.getBottomHit());
    }

    private int getFrameCounter() {
        if(isActiveSession())
            return storage.loadGame().getChivazFrameCounter();

        return 0;
    }

    private float getPositionX() {
        if(isActiveSession())
            return storage.loadGame().getChivazPosition(POSITION_X);

        return getStartPositionX();
    }

    private float getPositionY() {
        if(isActiveSession())
            return storage.loadGame().getChivazPosition(POSITION_Y);

        return -getScreenHeight();
    }

    private float getStartPositionX() {
        int pos = Random.getRandomNumber(getScreenWidth() / 2);
        return (float) getScreenWidth() / 4 + pos;
    }

    private boolean getHit() {
        if(isActiveSession())
            return storage.loadGame().getChivazIsHit();

        return false;
    }

    private boolean getIsTop() {
        if(isActiveSession())
            return storage.loadGame().getChivazIsTop();

        return Random.getRandomBoolean();
    }

    private boolean isActiveSession() {
        return storage.loadGame().getSessionIsActive();
    }

    private int getScreenFactorX() {
        return (int) (getScreenWidth() / 10.0);
    }

    private int getScreenFactorY() {
        return (int) (getScreenHeight() / 5.0);
    }

    private int getWidth() {
        return (int) (getScreenFactorX() * 4.0 / 2.0);
    }

    private int getHeight() {
        return (int) (getScreenFactorY() * 5.0 / 2.0);
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }
}
