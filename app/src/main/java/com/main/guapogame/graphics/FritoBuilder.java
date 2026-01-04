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
import com.main.guapogame.resources.FritoAssets;
import com.main.guapogame.state.GameState;
import com.main.guapogame.storage.Storage;

import java.util.Random;

public class FritoBuilder {

    private Context context;
    private Storage storage;

    public FritoBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public FritoBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public Frito build() {
        return createFrito();
    }

    private Frito createFrito() {
        return new Frito.Builder()
                .image(getImage())
                .imageHit(getHitImage())
                .isHit(getHit())
                .positionX(getPositionX())
                .positionY(getPositionY())
                .velY(getVelocityY())
                .frameCounter(getFrameCounter())
                .context(context)
                .duration(6 * FPS)
                .build();
    }

    private int getFrameCounter() {
        if(isActiveSession())
            return storage.loadGame().getFritoFrameCounter();

        return 0;
    }

    private float getPositionX() {
        if(isActiveSession())
            return storage.loadGame().getFritoPosition(POSITION_X);

        int screenWidth = getScreenWidth();
        return (float) -screenWidth - getImage().getWidth() + new Random().nextInt(screenWidth);
    }

    private float getPositionY() {
        if(isActiveSession())
            return storage.loadGame().getFritoPosition(POSITION_Y);

        return (float) getScreenHeight() / 2;
    }

    private float getVelocityY() {
        if(isActiveSession())
            return storage.loadGame().getFritoVelocity(VELOCITY_Y);

        return (float) getBackgroundSpeed() * 2;
    }

    private boolean getHit() {
        if(isActiveSession())
            return storage.loadGame().getFritoIsHit();

        return false;
    }

    private Bitmap getImage() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, FritoAssets.getImageOcean());

        return getBitmapScaled(width, height, FritoAssets.getImage());
    }

    private Bitmap getHitImage() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, FritoAssets.getImageHitOcean());

        return getBitmapScaled(width, height, FritoAssets.getImageHit());
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
