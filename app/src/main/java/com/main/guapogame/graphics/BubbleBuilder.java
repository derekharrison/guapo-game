package com.main.guapogame.graphics;

import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;
import static com.main.guapogame.parameters.Parameters.FPS;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;

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

    public Popup build() {
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

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }
}
