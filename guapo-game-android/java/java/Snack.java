package com.main.guapogame;

import static com.main.guapogame.Parameters.getBackgroundSpeed;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Snack implements Position, Velocity, Update, GameImage {
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

    @Override
    public Bitmap getImage() {
        return this.snack_image;
    }

    @Override
    public float getPositionX() {
        return x;
    }

    @Override
    public float getPositionY() {
        return y;
    }

    @Override
    public void update() {
        this.x += (int) getVelocityX();
        if(this.x + get_snack_image().getWidth() < 0) {
            play_sound_allowed = true;
        }
    }

    @Override
    public float getVelocityX() {
        return -getBackgroundSpeed();
    }

    @Override
    public float getVelocityY() {
        return 0;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(get_snack_image(), x, y, null);
    }

    public void playSoundEat(Sounds sounds) {
        if(play_sound_allowed) {
            sounds.playSoundEat();
            play_sound_allowed = false;
        }
    }
}
