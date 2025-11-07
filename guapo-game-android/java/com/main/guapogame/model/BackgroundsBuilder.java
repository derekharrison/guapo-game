package com.main.guapogame.model;

import static com.main.guapogame.definitions.Keys.ARUBA;
import static com.main.guapogame.definitions.Keys.BEACH;
import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.OCEAN;
import static com.main.guapogame.definitions.Keys.POSITION_X;
import static com.main.guapogame.definitions.Keys.TRIP;
import static com.main.guapogame.definitions.Keys.UTREG;
import static com.main.guapogame.definitions.Keys.getKey;
import static com.main.guapogame.definitions.Parameters.getBackgroundSpeed;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.graphics.Background;
import com.main.guapogame.resources.BackgroundsLevel1;
import com.main.guapogame.resources.BackgroundsLevel2;
import com.main.guapogame.resources.BackgroundsLevel3;
import com.main.guapogame.resources.BackgroundsLevel4;
import com.main.guapogame.resources.BackgroundsLevel5;
import com.main.guapogame.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class BackgroundsBuilder {
    private Context context;
    private Storage storage;
    private Resources resources;

    public BackgroundsBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public BackgroundsBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public BackgroundsBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public List<Background> build() {
        return createBackgrounds();
    }

    private List<Background> createBackgrounds() {
        List<Integer> assetIds = getBackgroundAssetIds();
        List<Background> backgrounds1 = new ArrayList<>();

        int backgroundId = 0;
        for(Integer assetId : assetIds) {
            backgrounds1.add(createBackground(assetId, String.valueOf(backgroundId)));
            backgroundId++;
        }

        return backgrounds1;
    }

    private Background createBackground(int assetId, String backgroundId) {
        return new Background.Builder()
                .positionX(getStartPositionBackground(backgroundId))
                .velocityX(-getBackgroundSpeed())
                .background(getBitmapScaled(getScreenWidth(), getScreenHeight(), assetId))
                .build();
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private float getStartPositionBackground(String backgroundId) {
        if(isActiveSession()) {
            return storage.loadGame().getBackgroundPosition(POSITION_X, backgroundId);
        }

        return 0;
    }

    private List<Integer> getBackgroundAssetIds() {
        String levelId = getLevelId();
        if(levelId.equals(ARUBA)) {
            return new BackgroundsLevel1().getAssetIds();
        }
        if(levelId.equals(BEACH)) {
            return new BackgroundsLevel2().getAssetIds();
        }
        if(levelId.equals(TRIP)) {
            return new BackgroundsLevel3().getAssetIds();
        }
        if(levelId.equals(OCEAN)) {
            return new BackgroundsLevel4().getAssetIds();
        }
        if(levelId.equals(UTREG)) {
            return new BackgroundsLevel5().getAssetIds();
        }
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
