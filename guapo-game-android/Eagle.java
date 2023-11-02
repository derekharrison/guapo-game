package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Eagle extends Bird{

    Eagle(Resources res, int screenFactorX, int screenFactorY) {

        bird_image1 = BitmapFactory.decodeResource(res, R.drawable.warawara1_bitmap_custom_mod_cropped);
        bird_image2 = BitmapFactory.decodeResource(res, R.drawable.warawara2_bitmap_custom_mod_cropped);
        bird_image3 = BitmapFactory.decodeResource(res, R.drawable.warawara3_bitmap_custom_mod_cropped);

        width = screenFactorX;
        height = screenFactorY;

        bird_image1 = Bitmap.createScaledBitmap(bird_image1, width, height, false);
        bird_image2 = Bitmap.createScaledBitmap(bird_image2, width, height, false);
        bird_image3 = Bitmap.createScaledBitmap(bird_image3, width, height, false);

        y = -height;
    }
}
