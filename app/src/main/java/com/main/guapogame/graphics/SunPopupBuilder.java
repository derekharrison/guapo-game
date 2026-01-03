package com.main.guapogame.graphics;

import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;

public class SunPopupBuilder {

    private Resources resources;
    private int duration;

    public SunPopupBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public SunPopupBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public Popup build() {
        return createSunPopup();
    }

    private Popup createSunPopup() {
        int width = (int) (getScreenWidth() / 10.0);
        int height = (int) (getScreenHeight() / 5.0);
        Bitmap image = getBitmapScaled(width, height, R.drawable.sun_popup_bitmap_cropped);
        return new Popup.Builder()
                .duration(duration)
                .positionX((float) getScreenWidth() / 2)
                .positionY(image.getHeight() + (float) getScreenHeight() / 10)
                .image(image)
                .build();
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }
}
