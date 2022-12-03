package com.main.guapogame;

import android.graphics.Bitmap;

public class Bird {
    public int speed = 20;
    public int x = -500, y, width, height, glass_counter = 1;
    public int num_frames_display = 10;
    public Bitmap glass1, glass2, glass3;
    public boolean play_sound_allowed = true;

    public Bitmap get_wine_glass () {

        if (glass_counter >= 1 && glass_counter <= num_frames_display) {
            glass_counter++;
            return glass1;
        }

        if (glass_counter > num_frames_display && glass_counter <= 2 * num_frames_display) {
            glass_counter++;
            return glass2;
        }

        if (glass_counter > 2 * num_frames_display && glass_counter <= 3 * num_frames_display) {
            glass_counter++;
            return glass3;
        }

        glass_counter = 1;

        return glass1;
    }
}
