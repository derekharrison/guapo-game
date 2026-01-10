package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Parameters.FPS;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.enums.Level;
import com.main.guapogame.model.graphics.gameobjects.BoundaryPopup;
import com.main.guapogame.model.graphics.gameobjects.Misty;
import com.main.guapogame.model.state.Random;
import com.main.guapogame.resources.assets.MistyAssets;
import com.main.guapogame.state.GameState;
import com.main.guapogame.resources.storage.Storage;

import java.security.SecureRandom;

public class MistyBuilder {
    private Context context;
    private Storage storage;

    public MistyBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public MistyBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public BoundaryPopup build() {
        return createMisty();
    }

    private BoundaryPopup createMisty() {
        return new Misty.Builder()
                .top(getMistyTop())
                .topHit(getMistyTopHit())
                .bottom(getMistyBottom())
                .bottomHit(getMistyBottomHit())
                .positionX(getPositionX())
                .positionY(getPositionY())
                .frameCounter(getFrameCounter())
                .isHit(getHit())
                .isTop(getIsTop())
                .duration(6 * FPS)
                .context(context)
                .build();
    }

    private Bitmap getMistyTop() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, MistyAssets.getMistyTopOcean());

        return getBitmapScaled(width, height, MistyAssets.getMistyTop());
    }

    private Bitmap getMistyTopHit() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, MistyAssets.getMistyTopHitOcean());

        return getBitmapScaled(width, height, MistyAssets.getMistyTopHit());
    }

    private Bitmap getMistyBottom() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, MistyAssets.getMistyBottomOcean());

        return getBitmapScaled(width, height, MistyAssets.getMistyBottom());
    }

    private Bitmap getMistyBottomHit() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, MistyAssets.getMistyBottomHitOcean());

        return getBitmapScaled(width, height, MistyAssets.getMistyBottomHit());
    }

    private int getFrameCounter() {
        if(isActiveSession())
            return storage.loadGame().getMistyFrameCounter();

        return 0;
    }

    private float getPositionX() {
        if(isActiveSession())
            return storage.loadGame().getMistyPosition(POSITION_X);

        return getStartPositionX();
    }

    private float getPositionY() {
        if(isActiveSession())
            return storage.loadGame().getMistyPosition(POSITION_Y);

        return -getScreenHeight();
    }

    private float getStartPositionX() {
        int pos = Random.getRandomNumber(getScreenWidth() / 2);
        return (float) getScreenWidth() / 4 + pos;
    }

    private boolean getHit() {
        if(isActiveSession())
            return storage.loadGame().getMistyIsHit();

        return false;
    }

    private boolean getIsTop() {
        if(isActiveSession())
            return storage.loadGame().getMistyIsTop();

        return new SecureRandom().nextBoolean();
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
        return (int) (getScreenFactorX() * 3.0 / 2.0);
    }

    private int getHeight() {
        return (int) (getScreenFactorY() * 3.0 / 2.0);
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }
}
