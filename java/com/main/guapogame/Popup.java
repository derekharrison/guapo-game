package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Popup {

    public int x, y, width, height;
    public Bitmap image;
    public boolean popped_up = false;
    public int popup_counter = 0;

    Popup(Resources res, int screenFactorX, int screenFactorY, int image_id) {

        image = BitmapFactory.decodeResource(res, image_id);

        width = (3 * screenFactorX) / 2;
        height = (3 * screenFactorY) / 2;

        image = Bitmap.createScaledBitmap(image, width, height, false);

        y = -height;
        x = -width;
    }

    public Bitmap get_image () {
        return this.image;
    }

}
