package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Snack {
    public int speed = 20;
    public int x = 0, y, width, height;
    public Bitmap snack_image;
    public boolean play_sound_allowed = true;
    public int points_snack;

    Snack (Resources res, int screenFactorX, int screenFactorY, int id) {

        snack_image = BitmapFactory.decodeResource(res, id);

        width = screenFactorX - screenFactorX/3;
        height = screenFactorY - screenFactorY/3;

        snack_image = Bitmap.createScaledBitmap(snack_image, width, height, false);

        y = -height;

    }

    public Bitmap get_snack_image () {
        return this.snack_image;
    }

    public void set_x(int x) { this.x = x; }
}
