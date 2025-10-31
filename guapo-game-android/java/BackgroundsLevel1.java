package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.LinkedList;

public class BackgroundsLevel1 {
    LinkedList<Background> backgrounds;
    private int screenWidth;
    private int screenHeight;
    private int backgroundSpeed;


    public BackgroundsLevel1(Resources resources) {
        screenHeight =resources.getDisplayMetrics().heightPixels;
        screenWidth = resources.getDisplayMetrics().widthPixels;
        backgroundSpeed = (int) (((float) screenWidth) / 400);
        createBackgrounds(resources);
    }

    public LinkedList<Background> getBackgrounds() {
        return backgrounds;
    }

    private void createBackgrounds(Resources resources) {
        backgrounds = new LinkedList<>();
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr1));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr2));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr3));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr4));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr5));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr6));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr7));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr8));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr9));
        backgrounds.add(createBackground(resources, R.drawable.background_guapo_game_nr10));
    }

    private Background createBackground(Resources resources, int backgroundId) {
        return new Background.Builder()
                .positionX(0)
                .velocityX(-backgroundSpeed)
                .background(getBitmapScaled(resources, screenWidth, screenHeight, backgroundId))
                .build();
    }

    protected Bitmap getBitmapScaled(Resources resources, int scaleX, int scaleY, int drawableIdentification) {
        Bitmap heroImage = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(heroImage, scaleX, scaleY, false);
    }

    private int getFollowingBackground(int id) {
        return id % backgrounds.size();
    }
}
