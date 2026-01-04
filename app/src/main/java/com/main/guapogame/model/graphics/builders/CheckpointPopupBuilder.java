package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;
import com.main.guapogame.model.graphics.gameobjects.Popup;

public class CheckpointPopupBuilder {

    private Resources resources;
    private int duration;

    public CheckpointPopupBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public CheckpointPopupBuilder duration(int duration) {
        this.duration = duration;
        return this;
    }

    public Popup build() {
        return createCheckpointPopup();
    }

    private Popup createCheckpointPopup() {
        int width = (int) (getScreenWidth() / 10.0);
        int height = (int) (getScreenHeight() / 5.0);
        Bitmap image = getBitmapScaled(width, height, R.drawable.flag_aruba_bitmap_cropped);
        return new Popup.Builder()
                .duration(duration)
                .positionX(getScreenWidth() - (float) getScreenWidth() / 4)
                .positionY(image.getHeight() + (float) getScreenHeight() / 10)
                .image(image)
                .build();
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }
}
