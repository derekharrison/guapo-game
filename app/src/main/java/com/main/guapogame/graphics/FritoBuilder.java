package com.main.guapogame.graphics;

import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Keys.VELOCITY_Y;
import static com.main.guapogame.parameters.Parameters.FPS;
import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;
import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;

import android.graphics.Bitmap;

import com.main.guapogame.enums.Level;
import com.main.guapogame.resources.FritoAssets;
import com.main.guapogame.state.GameState;

public class FritoBuilder extends AbstractCharacterPopupBuilder {

    @Override
    public CharacterPopup build() {
        return createFrito();
    }

    private CharacterPopup createFrito() {
        return new Frito.Builder()
                .image(getImage())
                .imageHit(getHitImage())
                .isHit(getHit())
                .positionX(getPositionX())
                .positionY(getPositionY())
                .velY(getVelocityY())
                .frameCounter(getFrameCounter())
                .context(context)
                .duration(16 * FPS)
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
        return (float) -screenWidth - getImage().getWidth() + random.nextInt(screenWidth);
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
}
