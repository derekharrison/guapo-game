package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

public class BackgroundsLevel1 {
    List<Background> backgrounds;
    private final int screenWidth;
    private final int screenHeight;
    private final int backgroundSpeed;
    private List<Integer> backgroundIds;

    public BackgroundsLevel1(Resources resources) {
        screenHeight =resources.getDisplayMetrics().heightPixels;
        screenWidth = resources.getDisplayMetrics().widthPixels;
        backgroundSpeed = (int) (((float) screenWidth) / 400);
        createBackgrounds(resources);
    }

    public List<Background> getBackgrounds() {
        return backgrounds;
    }

    public List<Integer> getBackgroundIds() {
        return backgroundIds;
    }

    private void createBackgrounds(Resources resources) {
        backgrounds = new ArrayList<>();
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

        backgroundIds = new ArrayList<>();
        backgroundIds.add(R.drawable.background_guapo_game_nr1);
        backgroundIds.add(R.drawable.background_guapo_game_nr2);
        backgroundIds.add(R.drawable.background_guapo_game_nr3);
        backgroundIds.add(R.drawable.background_guapo_game_nr4);
        backgroundIds.add(R.drawable.background_guapo_game_nr5);
        backgroundIds.add(R.drawable.background_guapo_game_nr6);
        backgroundIds.add(R.drawable.background_guapo_game_nr7);
        backgroundIds.add(R.drawable.background_guapo_game_nr8);
        backgroundIds.add(R.drawable.background_guapo_game_nr9);
        backgroundIds.add(R.drawable.background_guapo_game_nr10);
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
}
