package com.main.guapogame.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;

public class PauseButtonBuilder {
    private Resources resources;

    public PauseButtonBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public Bitmap build() {
        return createPauseButton();
    }

    private Bitmap createPauseButton() {
        return getBitmapScaled((int) (getScreenFactorX() / 3.0), (int) (getScreenFactorY() / 3.0), R.drawable.pause_button_bitmap_cropped);
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
}
