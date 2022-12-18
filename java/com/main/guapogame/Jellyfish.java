package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Jellyfish {
    public int speed = 20;
    int x = -500, y, width, height, jelly_counter = 1;
    int num_frames_display = 6;
    Bitmap jelly1, jelly2, jelly3;
    public boolean play_sound_allowed = true;

    Jellyfish (Resources res, int screenFactorX, int screenFactorY) {

        jelly1 = BitmapFactory.decodeResource(res, R.drawable.jelly_fish_bitmap_cropped1);
        jelly2 = BitmapFactory.decodeResource(res, R.drawable.jelly_fish_bitmap_cropped2);
        jelly3 = BitmapFactory.decodeResource(res, R.drawable.jelly_fish_bitmap_cropped3_2);

        width = screenFactorX;
        height = screenFactorY;

        jelly1 = Bitmap.createScaledBitmap(jelly1, width, height, false);
        jelly2 = Bitmap.createScaledBitmap(jelly2, width, height, false);
        jelly3 = Bitmap.createScaledBitmap(jelly3, width, height, false);

        y = -height;

    }

    public void update_jellycounter() {
        jelly_counter++;
        if (jelly_counter >= 1 && jelly_counter <= num_frames_display) {
            y = y + height / 100;
        }

        if (jelly_counter > num_frames_display && jelly_counter <= 2*num_frames_display) {
            y = y + height / 100;
        }

        if (jelly_counter > 2*num_frames_display && jelly_counter <= 3*num_frames_display) {
            y = y - height / 100;
        }

        if (jelly_counter > 3*num_frames_display && jelly_counter <= 4*num_frames_display) {
            y = y - height / 100;
        }
    }

    Bitmap get_jellyfish () {

        if (jelly_counter >= 1 && jelly_counter <= num_frames_display) {
            return jelly1;
        }

        if (jelly_counter > num_frames_display && jelly_counter <= 2*num_frames_display) {
            return jelly2;
        }

        if (jelly_counter > 2*num_frames_display && jelly_counter <= 3*num_frames_display) {
            return jelly3;
        }

        if (jelly_counter > 3*num_frames_display && jelly_counter <= 4*num_frames_display) {
            return jelly3;
        }

        jelly_counter = 1;

        return jelly1;
    }
}
