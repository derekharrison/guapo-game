package com.main.guapogame;

import static com.main.guapogame.Parameters.getBackgroundSpeed;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.Random;

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

    public Bitmap getSnackImage() {
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
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        this.x += (int) getVelocityX();
        if(this.x + getSnackImage().getWidth() < 0) {
            play_sound_allowed = true;
        }

        if (this.x + getSnackImage().getWidth() < 0) {
            Random rand = new Random();
            this.x = rand.nextInt(screenWidth + 1) + screenWidth;
            this.y = rand.nextInt(screenHeight - getSnackImage().getHeight());
            this.setPlaySoundAllowed(true);
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
        canvas.drawBitmap(getSnackImage(), x, y, null);
    }

    public void playSoundEat(Sounds sounds) {
        if(play_sound_allowed) {
            sounds.playSoundEat();
            play_sound_allowed = false;
        }
    }

    public void setPlaySoundAllowed(boolean play_sound_allowed) {
        this.play_sound_allowed = play_sound_allowed;
    }
}
