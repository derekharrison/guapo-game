package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Pufferfish {
    public int speed = 20;
    int x = -500, y, width, height, fish_counter = 1, fish_hit_counter = 1;
    int num_frames_display = 3;
    Bitmap fish1, fish2;
    Bitmap fish1_hit, fish2_hit;
    public boolean play_sound_allowed = true;

    Pufferfish (Resources res, int screenFactorX, int screenFactorY) {

        fish1 = BitmapFactory.decodeResource(res, R.drawable.fish10_bitmap_cropped);
        fish2 = BitmapFactory.decodeResource(res, R.drawable.fish10b_bitmap_cropped);
        fish1_hit = BitmapFactory.decodeResource(res, R.drawable.fish11_bitmap_cropped);
        fish2_hit = BitmapFactory.decodeResource(res, R.drawable.fish11b_bitmap_cropped);

        width = screenFactorX;
        height = screenFactorY;

        fish1 = Bitmap.createScaledBitmap(fish1, width, height/2, false);
        fish2 = Bitmap.createScaledBitmap(fish2, width, height/2, false);
        fish1_hit = Bitmap.createScaledBitmap(fish1_hit, width, height, false);
        fish2_hit = Bitmap.createScaledBitmap(fish2_hit, width, height, false);

        y = -height;

    }

    int get_fish_height() {
        return fish1.getHeight();
    }

    int get_fish_width() {
        return fish1.getWidth();
    }


    Bitmap get_fish () {

        if (fish_counter >= 1 && fish_counter <= num_frames_display) {
            fish_counter++;
            return fish1;
        }

        if (fish_counter > num_frames_display && fish_counter <= 2*num_frames_display) {
            fish_counter++;
            return fish2;
        }

        fish_counter = 1;

        return fish1;
    }

    Bitmap get_fish_hit () {

        if (fish_hit_counter >= 1 && fish_hit_counter <= num_frames_display) {
            fish_hit_counter++;
            return fish1_hit;
        }

        if (fish_hit_counter > num_frames_display && fish_hit_counter <= 2*num_frames_display) {
            fish_hit_counter++;
            return fish2_hit;
        }

        fish_hit_counter = 1;

        return fish1_hit;
    }
}
