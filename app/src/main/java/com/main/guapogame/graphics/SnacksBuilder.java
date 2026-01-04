package com.main.guapogame.graphics;

import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;
import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Keys.getKey;
import static com.main.guapogame.parameters.Parameters.POINTS_BEGGIN_STRIPS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;
import com.main.guapogame.parameters.Parameters;
import com.main.guapogame.storage.Storage;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SnacksBuilder {
    private Context context;
    private Storage storage;
    private final SecureRandom random = new SecureRandom();

    SnacksBuilder context(Context context) {
        this.context = context;
        return this;
    }

    SnacksBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    List<Snack> build() {
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
        snacks.add(createBegginStrip());

        return snacks;
    }

    private List<Snack> getSnacksFromActiveSession() {
        List<Snack> snacks = new ArrayList<>();

        for(int snackId = 0; snackId < getNumSnacks(); snackId++)
            snacks.add(createSnack(String.valueOf(snackId)));

        snacks.add(createBegginStrip());

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
                .pointsForSnack(getPointsForSnack(String.valueOf(snackId)))
                .snackImage(getBitmapScaled(width, height, getAssetId(snackId)))
                .resources(context.getResources())
                .build();
    }

    private Snack createSnack(int assetId, int pointsForSnack) {
        int width = (int) (getScreenFactorX() - getScreenFactorX() / 3.0);
        int height = (int) (getScreenFactorY() - getScreenFactorY() / 3.0);
        Bitmap snackImage = getBitmapScaled(width, height, assetId);
        return new Snack.Builder()
                .positionX((int) getSnackPositionX(snackImage))
                .positionY((int) getSnackPositionY(snackImage))
                .pointsForSnack(pointsForSnack)
                .snackImage(snackImage)
                .resources(context.getResources())
                .build();
    }

    private BegginStrip createBegginStrip() {
        int width = (int) (getScreenFactorX() - getScreenFactorX() / 3.0);
        int height = (int) (getScreenFactorY() - getScreenFactorY() / 3.0);
        Bitmap snackImage = getBitmapScaled(width, height, R.drawable.beggin_strip_cropped);
        return new BegginStrip.Builder()
                .positionX((int) getBegginPositionX())
                .positionY((int) getBegginPositionY(snackImage))
                .pointsForSnack(POINTS_BEGGIN_STRIPS)
                .snackImage(snackImage)
                .resources(context.getResources())
                .build();
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
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

    private float getBegginPositionX() {
        if(isActiveSession())
            return storage.loadGame().getBegginPosition(POSITION_X);

        return (float) 8 * getScreenWidth();
    }

    private float getBegginPositionY(Bitmap snackImage) {
        if(isActiveSession())
            return storage.loadGame().getBegginPosition(POSITION_Y);

        return getSnackPositionY(snackImage);
    }

    private float getSnackPositionY(String snackId) {
        return storage.loadGame().getSnackPosition(POSITION_Y, String.valueOf(snackId));
    }

    private int getPointsForSnack(String snackId) {
        return storage.loadGame().getSnackPoints(String.valueOf(snackId));
    }

    private float getSnackPositionX(Bitmap snackImage) {
        return random.nextInt(2 * getScreenWidth() - snackImage.getWidth() / 2);
    }

    private float getSnackPositionY(Bitmap snackImage) {
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
