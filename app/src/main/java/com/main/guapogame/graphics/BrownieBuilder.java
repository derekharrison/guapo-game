package com.main.guapogame.graphics;

import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Keys.VELOCITY_Y;
import static com.main.guapogame.parameters.Parameters.FPS;
import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;
import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.enums.Level;
import com.main.guapogame.resources.BrownieAssets;
import com.main.guapogame.state.GameState;
import com.main.guapogame.storage.Storage;

import java.security.SecureRandom;
import java.util.Random;

public class BrownieBuilder {

    private Context context;
    private Storage storage;
    private SecureRandom random = new SecureRandom();

    public BrownieBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public BrownieBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public CharacterPopup build() {
        return createBrownie();
    }

    private CharacterPopup createBrownie() {
        return new Brownie.Builder()
                .image(getImage())
                .imageHit(getHitImage())
                .positionX(getPositionX())
                .positionY(getPositionY())
                .frameCounter(getFrameCounter())
                .isHit(getHit())
                .velY((int) getVelY())
                .context(context)
                .duration(16 * FPS)
                .build();
    }

    private float getVelY() {
        if(isActiveSession())
            return storage.loadGame().getBrownieVelocity(VELOCITY_Y);

        return (float) getBackgroundSpeed() * 2;
    }

    private int getFrameCounter() {
        if(isActiveSession())
            return storage.loadGame().getBrownieFrameCounter();

        return 0;
    }

    private float getPositionX() {
        if(isActiveSession())
            return storage.loadGame().getBrowniePosition(POSITION_X);

        int screenWidth = getScreenWidth();
        return (float) screenWidth + getImage().getWidth() + random.nextInt(screenWidth);
    }

    private float getPositionY() {
        if(isActiveSession())
            return storage.loadGame().getBrowniePosition(POSITION_Y);

        return (float) getScreenHeight() / 2;
    }

    private boolean getHit() {
        if(isActiveSession())
            return storage.loadGame().getBrownieIsHit();

        return false;
    }

    private Bitmap getImage() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, BrownieAssets.getImageOcean());

        return getBitmapScaled(width, height, BrownieAssets.getImage());
    }

    private Bitmap getHitImage() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, BrownieAssets.getImageHitOcean());

        return getBitmapScaled(width, height, BrownieAssets.getImageHit());
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
