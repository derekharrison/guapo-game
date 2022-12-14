package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Fish {
    public int speed = 20;
    public int x = -500, y, width, height, fish_counter = 1;
    public int num_frames_display = 6;
    public Bitmap fish1, fish2;

    Fish(Resources res, int screen_factor_x, int screen_factor_y, int fish_id1, int fish_id2) {

        fish1 = BitmapFactory.decodeResource(res, fish_id1);
        fish2 = BitmapFactory.decodeResource(res, fish_id2);

        width = screen_factor_x;
        height = screen_factor_y;

        fish1 = Bitmap.createScaledBitmap(fish1, width, height, false);
        fish2 = Bitmap.createScaledBitmap(fish2, width, height, false);

        y = -height;

    }

    public void update_fish_counter() {
        fish_counter++;
    }

    public Bitmap get_fish () {

        if (fish_counter >= 1 && fish_counter <= num_frames_display) {
            return fish1;
        }

        if (fish_counter > num_frames_display && fish_counter <= 2*num_frames_display) {
            return fish2;
        }

        fish_counter = 1;

        return fish1;
    }
}
