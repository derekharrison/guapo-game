package com.main.guapogame.graphics;

import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;
import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;
import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.getKey;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.state.GameState;
import com.main.guapogame.resources.BackgroundsLevel1;
import com.main.guapogame.resources.BackgroundsLevel2;
import com.main.guapogame.resources.BackgroundsLevel3;
import com.main.guapogame.resources.BackgroundsLevel4;
import com.main.guapogame.resources.BackgroundsLevel5;
import com.main.guapogame.storage.Storage;
import com.main.guapogame.enums.Level;

import java.util.ArrayList;
import java.util.List;

class BackgroundsBuilder {
    private Context context;
    private Storage storage;

    BackgroundsBuilder context(Context context) {
        this.context = context;
        return this;
    }

    BackgroundsBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    List<Background> build() {
        return createBackgrounds();
    }

    private List<Background> createBackgrounds() {
        List<Integer> assetIds = getBackgroundAssetIds();
        List<Background> backgrounds1 = new ArrayList<>();

        int background = 0;
        for(Integer assetId : assetIds) {
            backgrounds1.add(createBackground(assetId, background));
            background++;
        }

        return backgrounds1;
    }

    private Background createBackground(int assetId, int backgroundId) {
        return new Background.Builder()
                .positionX(getStartPositionBackground(backgroundId))
                .velocityX(-getBackgroundSpeed())
                .background(getBitmapScaled(getScreenWidth(), getScreenHeight(), assetId))
                .build();
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private float getStartPositionBackground(int backgroundId) {
        if(isActiveSession()) {
            return storage.loadGame().getBackgroundPosition(POSITION_X, String.valueOf(backgroundId));
        }

        return (float) getScreenWidth() * backgroundId - 10;
    }

    private List<Integer> getBackgroundAssetIds() {
        if(GameState.getLevel().equals(Level.ARUBA))
            return new BackgroundsLevel1().getAssetIds();

        if(GameState.getLevel().equals(Level.BEACH))
            return new BackgroundsLevel2().getAssetIds();

        if(GameState.getLevel().equals(Level.TRIP))
            return new BackgroundsLevel3().getAssetIds();

        if(GameState.getLevel().equals(Level.OCEAN))
            return new BackgroundsLevel4().getAssetIds();

        if(GameState.getLevel().equals(Level.UTREG))
            return new BackgroundsLevel5().getAssetIds();

        return new ArrayList<>();
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
}
