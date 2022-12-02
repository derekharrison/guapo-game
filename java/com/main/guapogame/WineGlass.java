package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WineGlass extends Bird{

    WineGlass(Resources res, int screenFactorX, int screenFactorY) {

        glass1 = BitmapFactory.decodeResource(res, R.drawable.warawara1_bitmap_custom_mod_cropped);
        glass2 = BitmapFactory.decodeResource(res, R.drawable.warawara2_bitmap_custom_mod_cropped);
        glass3 = BitmapFactory.decodeResource(res, R.drawable.warawara3_bitmap_custom_mod_cropped);

        width = screenFactorX;
        height = screenFactorY;

        glass1 = Bitmap.createScaledBitmap(glass1, width, height, false);
        glass2 = Bitmap.createScaledBitmap(glass2, width, height, false);
        glass3 = Bitmap.createScaledBitmap(glass3, width, height, false);

        y = -height;
    }
}
