package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;

class PlayButtonBuilder {
    private Resources resources;

    PlayButtonBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    Bitmap build() {
        return createPlayButton();
    }

    private Bitmap createPlayButton() {
        return getBitmapScaled((int) (getScreenFactorX() / 3.0), (int) (getScreenFactorY() / 3.0), R.drawable.play_button_bitmap_cropped);
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private int getScreenFactorX() {
        return (int) (getScreenWidth() / 10.0);
    }
    private int getScreenFactorY() {
        return (int) (getScreenHeight() / 5.0);
    }
}
