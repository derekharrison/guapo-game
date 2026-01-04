package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Keys.VELOCITY_Y;
import static com.main.guapogame.parameters.Parameters.FPS;
import static com.main.guapogame.model.state.BackgroundSpeed.getBackgroundSpeed;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.graphics.Bitmap;

import com.main.guapogame.enums.Level;
import com.main.guapogame.model.graphics.gameobjects.Brownie;
import com.main.guapogame.model.graphics.gameobjects.CharacterPopup;
import com.main.guapogame.resources.BrownieAssets;
import com.main.guapogame.state.GameState;

public class BrownieBuilder extends AbstractCharacterPopupBuilder {


    @Override
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
}
