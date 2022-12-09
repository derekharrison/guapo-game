package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Seagull extends Bird {

    Seagull(Resources res, int screenFactorX, int screenFactorY) {

        bird_image1 = BitmapFactory.decodeResource(res, R.drawable.seagull1_bitmap_cropped_new);
        bird_image2 = BitmapFactory.decodeResource(res, R.drawable.seagull2_bitmap_cropped_new);
        bird_image3 = BitmapFactory.decodeResource(res, R.drawable.seagull3_bitmap_cropped_new);

        width = screenFactorX;
        height = screenFactorY;

        bird_image1 = Bitmap.createScaledBitmap(bird_image1, width, height, false);
        bird_image2 = Bitmap.createScaledBitmap(bird_image2, width, height, false);
        bird_image3 = Bitmap.createScaledBitmap(bird_image3, width, height, false);

        y = -height;
    }
}
