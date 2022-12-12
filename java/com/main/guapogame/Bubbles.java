package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bubbles {
    public int bubble_counter = 1;
    public int x_b1 = -500, y_b1, width1, height1;
    public int x_b2 = -500, y_b2, width2, height2;
    public int x_b3 = -500, y_b3, width3, height3;
    public int width, height;
    public int num_frames_display = 20;
    public Bitmap bubble1, bubble2, bubble3;
    public boolean b2_can_be_produced = false;
    public boolean b3_can_be_produced = false;
    public boolean bubble1_is_just_in_bounds_again;
    public boolean bubble2_is_just_in_bounds_again;
    public boolean bubble3_is_just_in_bounds_again;

    Bubbles (Resources res, int screenFactorX, int screenFactorY) {

        bubble1 = BitmapFactory.decodeResource(res, R.drawable.bubble_bitmap_cropped);
        bubble2 = BitmapFactory.decodeResource(res, R.drawable.bubble_bitmap_cropped);
        bubble3 = BitmapFactory.decodeResource(res, R.drawable.bubble_bitmap_cropped);

        width = screenFactorX;
        height = screenFactorY;

        width1 = screenFactorX/3;
        height1 = screenFactorY/3;

        width2 = screenFactorX/2;
        height2 = screenFactorY/2;

        width3 = screenFactorX/3;
        height3 = screenFactorY/3;

        bubble1 = Bitmap.createScaledBitmap(bubble1, width1, height1, false);
        bubble2 = Bitmap.createScaledBitmap(bubble2, width2, height2, false);
        bubble3 = Bitmap.createScaledBitmap(bubble3, width3, height3, false);

        y_b1 = -height - 1;
        y_b2 = -height - 1;
        y_b3 = -height - 1;

    }

    public Bitmap get_bubble1() {
        bubble_counter++;

        if(y_b1 > -2*height) {
            y_b1 = y_b1 - height / 30;
        }

        if(bubble_counter > num_frames_display) {
            b2_can_be_produced = true;
        }

        if(bubble_counter > 2*num_frames_display) {
            b3_can_be_produced = true;
        }

        return bubble1;
    }

    public Bitmap get_bubble2() {

        if(y_b2 > -2*height && b2_can_be_produced) {
            y_b2 = y_b2 - height / 30;
        }

        return bubble2;
    }

    public Bitmap get_bubble3() {

        if(y_b3 > -2*height && b3_can_be_produced) {
            y_b3 = y_b3 - height / 30;
        }

        return bubble3;
    }

    public boolean are_bubbles_out_of_bounds() {
        boolean bubs_out_of_bounds = false;
        if(y_b1 < -height && y_b2 < -height && y_b3 < -height) {
            b2_can_be_produced = false;
            b3_can_be_produced = false;
            bubs_out_of_bounds = true;
            bubble_counter = 1;
        }
        return bubs_out_of_bounds;
    }
}
