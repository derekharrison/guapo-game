package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Image {
    int y, width, height, image_counter = 1;
    int cape_counter = 1;
    int num_frames_display = 2;
    Bitmap image;
    Bitmap cape1, cape2;

    Image(Resources res, int screenFactorX, int screenFactorY, int image_id, int cape1_id, int cape2_id) {

        image = BitmapFactory.decodeResource(res, image_id);

        cape1 = BitmapFactory.decodeResource(res, cape1_id);
        cape2 = BitmapFactory.decodeResource(res, cape2_id);

        width = screenFactorX;
        height = screenFactorY;

        image = Bitmap.createScaledBitmap(image, width, height, false);

        cape1 = Bitmap.createScaledBitmap(cape1, (width * 5) / 4, (height * 6) / 2, false);
        cape2 = Bitmap.createScaledBitmap(cape2, (width * 5) / 4, (height * 6) / 2, false);

        y = -height;

    }

    Bitmap get_tutti_cape () {

        if (cape_counter >= 1 && cape_counter <= num_frames_display) {
            cape_counter++;
            return cape1;
        }

        if (cape_counter > num_frames_display && cape_counter <= 2*num_frames_display) {
            cape_counter++;
            return cape2;
        }

        cape_counter = 1;

        return cape1;
    }

    Bitmap get_tutti_image () {

        return image;
    }
}
