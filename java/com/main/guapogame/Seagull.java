package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Seagull extends Bird {

    Seagull(Resources res, int screenFactorX, int screenFactorY) {

        glass1 = BitmapFactory.decodeResource(res, R.drawable.seagull1_bitmap_cropped_new);
        glass2 = BitmapFactory.decodeResource(res, R.drawable.seagull2_bitmap_cropped_new);
        glass3 = BitmapFactory.decodeResource(res, R.drawable.seagull3_bitmap_cropped_new);

        width = screenFactorX;
        height = screenFactorY;

        glass1 = Bitmap.createScaledBitmap(glass1, width, height, false);
        glass2 = Bitmap.createScaledBitmap(glass2, width, height, false);
        glass3 = Bitmap.createScaledBitmap(glass3, width, height, false);

        y = -height;
    }
}
