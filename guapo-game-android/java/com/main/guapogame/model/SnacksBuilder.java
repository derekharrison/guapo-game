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
         if(isActiveSession())
            return getSnacksFromActiveSession();

        return getSnacks();
    }

    private List<Snack> getSnacks() {
        List<Snack> snacks = new ArrayList<>();
        snacks.addAll(createSnacks(Parameters.NUM_CHEESY_BITES, Parameters.POINTS_CHEESY_BITES, R.drawable.cheesy_bite_resized));
        snacks.addAll(createSnacks(Parameters.NUM_PAPRIKA, Parameters.POINTS_PAPRIKA, R.drawable.paprika_bitmap_cropped));
        snacks.addAll(createSnacks(Parameters.NUM_CUCUMBERS, Parameters.POINTS_CUCUMBER, R.drawable.cucumber_bitmap_cropped));
        snacks.addAll(createSnacks(Parameters.NUM_BROCCOLI, Parameters.POINTS_BROCCOLI, R.drawable.broccoli_bitmap_cropped));
        snacks.addAll(createSnacks(1, POINTS_BEGGIN_STRIPS, R.drawable.beggin_strip_cropped));

        return snacks;
    }

    private List<Snack> getSnacksFromActiveSession() {
        List<Snack> snacks = new ArrayList<>();

        for(int snackId = 0; snackId < getNumSnacks(); snackId++)
            snacks.add(createSnack(String.valueOf(snackId)));

        return snacks;
    }

    private List<Snack> createSnacks(int numSnacks, int pointsForSnack, int assetId) {
        List<Snack> snacks = new ArrayList<>();

        for (int snack = 0; snack < numSnacks; snack++)
            snacks.add(createSnack(assetId, pointsForSnack));

        return snacks;
    }

    private int getAssetId(String snackId) {
        return storage.loadGame().getSnackAssetId(String.valueOf(snackId));
    }

    private Snack createSnack(String snackId) {
        int width = (int) (getScreenFactorX() - getScreenFactorX() / 3.0);
        int height = (int) (getScreenFactorY() - getScreenFactorY() / 3.0);
        return new Snack.Builder()
                .positionX((int) getSnackPositionX(String.valueOf(snackId)))
                .positionY((int) getSnackPositionY(String.valueOf(snackId)))
                .velocityX(-getBackgroundSpeed())
                .pointsForSnack(getPointsForSnack(String.valueOf(snackId)))
                .snackImage(getBitmapScaled(width, height, getAssetId(snackId)))
                .assetId(getAssetId(snackId))
                .build();
    }

    private Snack createSnack(int assetId, int pointsForSnack) {
        int width = (int) (getScreenFactorX() - getScreenFactorX() / 3.0);
        int height = (int) (getScreenFactorY() - getScreenFactorY() / 3.0);
        Bitmap snackImage = getBitmapScaled(width, height, assetId);
        return new Snack.Builder()
                .positionX((int) getSnackPositionX(snackImage))
                .positionY((int) getSnackPositionY(snackImage))
                .velocityX(-getBackgroundSpeed())
                .pointsForSnack(pointsForSnack)
                .snackImage(snackImage)
                .assetId(assetId)
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

    private float getSnackPositionX(String snackId) {
        return storage.loadGame().getSnackPosition(POSITION_X, String.valueOf(snackId));
    }

    private float getSnackPositionY(String snackId) {
        return storage.loadGame().getSnackPosition(POSITION_Y, String.valueOf(snackId));
    }

    private int getPointsForSnack(String snackId) {
        return storage.loadGame().getSnackPoints(String.valueOf(snackId));
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
