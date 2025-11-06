package com.main.guapogame.model;

import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.POSITION_X;
import static com.main.guapogame.definitions.Keys.POSITION_Y;
import static com.main.guapogame.definitions.Keys.getKey;
import static com.main.guapogame.definitions.Parameters.POINTS_BEGGIN_STRIPS;
import static com.main.guapogame.definitions.Parameters.getBackgroundSpeed;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;
import com.main.guapogame.definitions.Parameters;
import com.main.guapogame.graphics.Snack;
import com.main.guapogame.storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnacksBuilder {
    private Context context;
    private Storage storage;
    private Resources resources;


    public SnacksBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public SnacksBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public SnacksBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public List<Snack> build() {
        return  createSnacks();
    }

    private List<Snack> createSnacks() {
        if(!isActiveSession())
            return getSnacks();

        if(isActiveSession())
            return getSnacksFromActiveSession();

        return new ArrayList<>();
    }

    private List<Snack> getSnacks() {
        List<Snack> snacks1 = new ArrayList<>();
        snacks1.addAll(createSnacks(Parameters.NUM_CHEESY_BITES, Parameters.POINTS_CHEESY_BITES, R.drawable.cheesy_bite_resized));
        snacks1.addAll(createSnacks(Parameters.NUM_PAPRIKA, Parameters.POINTS_PAPRIKA, R.drawable.paprika_bitmap_cropped));
        snacks1.addAll(createSnacks(Parameters.NUM_CUCUMBERS, Parameters.POINTS_CUCUMBER, R.drawable.cucumber_bitmap_cropped));
        snacks1.addAll(createSnacks(Parameters.NUM_BROCCOLI, Parameters.POINTS_BROCCOLI, R.drawable.broccoli_bitmap_cropped));
        snacks1.addAll(createSnacks(1, POINTS_BEGGIN_STRIPS, R.drawable.beggin_strip_cropped));

        return snacks1;
    }

    private List<Snack> getSnacksFromActiveSession() {
        int numSnacks = getNumSnacks();
        int width = (int) (getScreenFactorX() - getScreenFactorX() / 3.0);
        int height = (int) (getScreenFactorY() - getScreenFactorY() / 3.0);
        List<Snack> snacks1 = new ArrayList<>();
        for(int snackId = 0; snackId < numSnacks; snackId++) {
            int assetId = storage.loadGame().getSnackAssetId(String.valueOf(snackId));
            snacks1.add(
                    new Snack.Builder()
                            .positionX((int) storage.loadGame().getSnackPosition(POSITION_X, String.valueOf(snackId)))
                            .positionY((int) storage.loadGame().getSnackPosition(POSITION_Y, String.valueOf(snackId)))
                            .velocityX(-getBackgroundSpeed())
                            .pointsForSnack(storage.loadGame().getSnackPoints(String.valueOf(snackId)))
                            .snackImage(getBitmapScaled(width, height, assetId))
                            .assetId(assetId)
                            .build()
            );
        }

        return snacks1;
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

    private int getScreenFactorX() {
        return (int) (getScreenWidth() / 10.0);
    }

    private int getScreenFactorY() {
        return (int) (getScreenHeight() / 5.0);
    }

    private int getNumSnacks() {
        if(isActiveSession()) {
            return storage.loadGame().getNumSnacks();
        }

        return 1;
    }

    private List<Snack> createSnacks(int numSnacks, int pointsForSnack, int assetId) {
        List<Snack> snacks = new ArrayList<>();
        int width = (int) (getScreenFactorX() - getScreenFactorX() / 3.0);
        int height = (int) (getScreenFactorY() - getScreenFactorY() / 3.0);
        Bitmap snackImage = getBitmapScaled(width, height, assetId);
        for (int snack = 0; snack < numSnacks; snack++) {
            snacks.add(
                    new Snack.Builder()
                            .positionX((int) getSnackPositionX(snackImage))
                            .positionY((int) getSnackPositionY(snackImage))
                            .velocityX(-getBackgroundSpeed())
                            .pointsForSnack(pointsForSnack)
                            .snackImage(snackImage)
                            .assetId(assetId)
                            .build()
            );
        }

        return snacks;
    }

    private float getSnackPositionX(Bitmap snackImage) {
        Random random = new Random();
        return random.nextInt(2 * getScreenWidth() - snackImage.getWidth() / 2);
    }

    private float getSnackPositionY(Bitmap snackImage) {
        Random random = new Random();
        return random.nextInt(getScreenHeight() - snackImage.getHeight() / 2);
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
