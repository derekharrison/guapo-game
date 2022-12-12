package com.main.guapogame;

import android.graphics.Bitmap;

public class Bird {
    public int speed = 20;
    public int x = -500, y, width, height, bird_counter = 1;
    public int num_frames_display = 10;
    public Bitmap bird_image1, bird_image2, bird_image3;
    public boolean play_sound_allowed = true;

    public Bitmap get_bird_image() {

        if (bird_counter >= 1 && bird_counter <= num_frames_display) {
            bird_counter++;
            return bird_image1;
        }

        if (bird_counter > num_frames_display && bird_counter <= 2 * num_frames_display) {
            bird_counter++;
            return bird_image2;
        }

        if (bird_counter > 2 * num_frames_display && bird_counter <= 3 * num_frames_display) {
            bird_counter++;
            return bird_image3;
        }

        bird_counter = 1;

        return bird_image1;
    }
}
