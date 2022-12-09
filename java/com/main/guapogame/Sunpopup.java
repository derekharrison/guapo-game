package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Sunpopup {
    int y, width, height;
    Bitmap sun;

    Sunpopup(Resources res, int screenFactorX, int screenFactorY) {

        sun = BitmapFactory.decodeResource(res, R.drawable.sun_popup_bitmap_cropped);

        width = (3 * screenFactorX) / 2;
        height = (3 * screenFactorY) / 2;

        sun = Bitmap.createScaledBitmap(sun, width, height, false);

        y = -height;

    }

    public Bitmap get_sun () {
        return this.sun;
    }

}
