package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;
import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Parameters.FPS;

import android.graphics.Bitmap;

import com.main.guapogame.enums.Level;
import com.main.guapogame.model.graphics.gameobjects.CharacterPopup;
import com.main.guapogame.model.graphics.gameobjects.Rocco;
import com.main.guapogame.resources.assets.RoccoAssets;
import com.main.guapogame.state.GameState;

public class RoccoBuilder extends AbstractCharacterPopupBuilder {

    @Override
    public CharacterPopup build() {
        return createRocco();
    }

    private CharacterPopup createRocco() {
        int width = getRoccoWidth();
        int height = getRoccoHeight();
        return new Rocco.Builder()
                .capes(getBitmapScaled((2 * width) / 3, (2 * height) / 3, RoccoAssets.getCapeImage1()))
                .capes(getBitmapScaled((2 * width) / 3, (2 * height) / 3, RoccoAssets.getCapeImage2()))
                .image(getImage())
                .imageHit(getHitImage())
                .isHit(getHit())
                .positionX(getPositionX())
                .positionY(getPositionY())
                .frameCounter(getFrameCounter())
                .context(context)
                .duration(4 * FPS)
                .build();
    }

    private int getRoccoWidth() {
        return  (int) (getScreenFactorX() * 3 / 2.0);
    }

    private int getRoccoHeight() {
        return (int) (getScreenFactorY() * 3 / 2.0);
    }


    private int getFrameCounter() {
        if(isActiveSession())
            return storage.loadGame().getRoccoFrameCounter();

        return 0;
    }

    private float getPositionX() {
        if(isActiveSession())
            return storage.loadGame().getRoccoPosition(POSITION_X);

        int screenWidth = getScreenWidth();
        return (float) -screenWidth - getImage().getWidth() + random.nextInt(screenWidth);
    }

    private float getPositionY() {
        if(isActiveSession())
            return storage.loadGame().getRoccoPosition(POSITION_Y);

        return (float) getScreenHeight() / 2;
    }

    private boolean getHit() {
        if(isActiveSession())
            return storage.loadGame().getRoccoIsHit();

        return false;
    }

    private Bitmap getImage() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, RoccoAssets.getImageOcean());

        return getBitmapScaled(width, height, RoccoAssets.getImage());
    }

    private Bitmap getHitImage() {
        int width = getWidth();
        int height = getHeight();

        if(GameState.getLevel().equals(Level.OCEAN))
            return getBitmapScaled(width, height, RoccoAssets.getImageHitOcean());

        return getBitmapScaled(width, height, RoccoAssets.getImageHit());
    }
}
