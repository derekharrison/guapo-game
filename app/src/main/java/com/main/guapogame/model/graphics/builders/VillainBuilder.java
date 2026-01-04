package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.parameters.Keys.BEACH;
import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.OCEAN;
import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Keys.VELOCITY_X;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.model.graphics.gameobjects.JellyFish;
import com.main.guapogame.model.graphics.gameobjects.Villain;
import com.main.guapogame.resources.assets.JellyFishAssets;
import com.main.guapogame.resources.assets.SeagullAssets;
import com.main.guapogame.resources.assets.WaraWaraAssets;
import com.main.guapogame.resources.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class VillainBuilder {
    private Storage storage;
    private Context context;

    public VillainBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public VillainBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public Villain build() {
        return createVillain();
    }

    public Villain build(String villainId) {
        return createVillain(villainId);
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private int getVillainFrameCounter(String villainId) {
        return storage.loadGame().getVillainFrameCounter(villainId);
    }

    private float getVillainPositionX(String villainId) {
        return storage.loadGame().getVillainPosition(POSITION_X, villainId);
    }

    private float getVillainPositionY(String villainId) {
        return storage.loadGame().getVillainPosition(POSITION_Y, villainId);
    }

    private float getVillainVelocityX(String villainId) {
        return storage.loadGame().getVillainVelocity(VELOCITY_X, villainId);
    }

    private Villain createVillain() {
        if(getLevelId().equals(OCEAN)) {
            return new JellyFish.Builder()
                    .positionX(-500)
                    .images(createVillainImages())
                    .build();
        }

        return new Villain.Builder()
                .positionX(-500)
                .images(createVillainImages())
                .build();
    }

    private List<Bitmap> createVillainImages() {
        if(getLevelId().equals(BEACH))
            return createVillainImages(new SeagullAssets().getAssetIds());

        if(getLevelId().equals(OCEAN))
            return createVillainImages(new JellyFishAssets().getAssetIds());

        return createVillainImages(new WaraWaraAssets().getAssetIds());
    }

    private String getLevelId() {
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }

    private List<Bitmap> createVillainImages(List<Integer> assetIds) {
        List<Bitmap> images = new ArrayList<>();

        int width =  (int) (getScreenWidth() / 10.0);
        int height = (int) (getScreenHeight() / 5.0);

        for(int assetId : assetIds)
            images.add(getBitmapScaled(width, height, assetId));

        return images;
    }

    private Villain createVillain(String villainId) {
        if(getLevelId().equals(OCEAN)) {
            return new JellyFish.Builder()
                    .positionX(getVillainPositionX(villainId))
                    .positionY(getVillainPositionY(villainId))
                    .velX(getVillainVelocityX(villainId))
                    .images(createVillainImages())
                    .frameCounter(getVillainFrameCounter(villainId))
                    .build();
        }

        return new Villain.Builder()
                .positionX(getVillainPositionX(villainId))
                .positionY(getVillainPositionY(villainId))
                .velX(getVillainVelocityX(villainId))
                .images(createVillainImages())
                .frameCounter(getVillainFrameCounter(villainId))
                .build();
    }
}
