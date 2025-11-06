package com.main.guapogame.model;

import static com.main.guapogame.definitions.Keys.BEACH;
import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.OCEAN;
import static com.main.guapogame.definitions.Keys.POSITION_X;
import static com.main.guapogame.definitions.Keys.POSITION_Y;
import static com.main.guapogame.definitions.Keys.VELOCITY_X;
import static com.main.guapogame.definitions.Keys.getKey;
import static com.main.guapogame.definitions.Parameters.START_NUM_OF_VILLAINS;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.graphics.Villain;
import com.main.guapogame.resources.Seagulls;
import com.main.guapogame.resources.WaraWaras;
import com.main.guapogame.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class VillainsBuilder {

    List<Villain> villains = new ArrayList<>();
    private Context context;
    private Storage storage;
    private Resources resources;

    public VillainsBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public VillainsBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public VillainsBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public List<Villain> build() {
        createVillains();
        return villains;
    }

    private void createVillains() {
        int numVillains = getNumVillains();
        for(int villainId = 0; villainId < numVillains; villainId++) {
            if(isActiveSession())
                createVillain(String.valueOf(villainId));
            else
                addVillain(createVillain());
        }
    }

    private boolean isActiveSession() {
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private void addVillain(Villain villain) {
        this.villains.add(villain);
    }

    private int getNumVillains() {
        if(isActiveSession())
            return storage.loadGame().getNumVillains();

        return START_NUM_OF_VILLAINS;
    }


    private Villain createVillain() {
        return new Villain.Builder()
                .positionX(-500)
                .images(createVillainImages())
                .build();
    }

    private List<Bitmap> createVillainImages() {
        if(getLevelId().equals(BEACH) || getLevelId().equals(OCEAN))
            return createVillainImages(new Seagulls().getAssetIds());

        return createVillainImages(new WaraWaras().getAssetIds());
    }

    private List<Bitmap> createVillainImages(List<Integer> assetIds) {
        List<Bitmap> images = new ArrayList<>();

        int width =  (int) (getScreenWidth() / 10.0);
        int height = (int) (getScreenHeight() / 5.0);

        for(int assetId : assetIds)
            images.add(getBitmapScaled(width, height, assetId));

        return images;
    }

    private void createVillain(String villainId) {
        villains.add(
                new Villain.Builder()
                        .positionX(getVillainPositionX(villainId))
                        .positionY(getVillainPositionY(villainId))
                        .velX(getVillainVelocityX(villainId))
                        .images(createVillainImages())
                        .frameCounter(getVillainFrameCounter(villainId))
                        .build()
        );
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
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

    private String getLevelId() {
        return  context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
