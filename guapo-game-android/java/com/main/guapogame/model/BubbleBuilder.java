package com.main.guapogame.model;

import static com.main.guapogame.definitions.Parameters.FPS;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;
import com.main.guapogame.graphics.Bubble;

public class BubbleBuilder {

    private Resources resources;
    private int positionX;
    private int positionY;

    public BubbleBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public BubbleBuilder positionX(int positionX) {
        this.positionX = positionX;
        return this;
    }

    public BubbleBuilder positionY(int positionY) {
        this.positionY = positionY;
        return this;
    }

    public Bubble build() {
        int width = (int) (getScreenWidth() / 20.0);
        int height = (int) (getScreenHeight() / 10.0);
        Bitmap image = getBitmapScaled(width, height, R.drawable.bubble_bitmap_cropped);
        return new Bubble.Builder()
                .positionX(positionX)
                .positionY(positionY)
                .duration(6 * FPS)
                .image(image)
                .build();
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }
}
