package com.main.guapogame.graphics;

import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.interfaces.Draw;
import com.main.guapogame.interfaces.Image;
import com.main.guapogame.interfaces.Position;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.interfaces.Velocity;
import com.main.guapogame.resources.Sounds;

import java.security.SecureRandom;

public class Snack implements Position, Velocity, Update, Image, Draw {
    private int positionX;
    private int positionY;
    private final Bitmap snackImage;
    private boolean playSound = true;
    private final int pointsSnack;
    private final SecureRandom random = new SecureRandom();
    private final Resources resources;

    protected Snack(Builder builder) {
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
        this.snackImage = builder.snackImage;
        this.pointsSnack = builder.pointsSnack;
        this.resources = builder.resources;
    }

    public static class Builder {
        private int positionX = 0;
        private int positionY;
        private Bitmap snackImage;
        private int pointsSnack;
        private Resources resources;

        public Builder positionX(int positionX) {
            this.positionX = positionX;
            return this;
        }

        public Builder positionY(int positionY) {
            this.positionY = positionY;
            return this;
        }

        public Builder snackImage(Bitmap snackImage) {
            this.snackImage = snackImage;
            return this;
        }

        public Builder pointsForSnack(int pointsSnack) {
            this.pointsSnack = pointsSnack;
            return this;
        }

        public Builder resources(Resources resources) {
            this.resources = resources;
            return this;
        }

        public Snack build() {
            return new Snack(this);
        }
    }

    public Bitmap getSnackImage() {
        return this.snackImage;
    }

    public void setPositionX(int x) { this.positionX = x; }

    @Override
    public Bitmap getImage() {
        return this.snackImage;
    }

    @Override
    public float getPositionX() {
        return positionX;
    }

    @Override
    public float getPositionY() {
        return positionY;
    }

    @Override
    public void update() {
        int screenWidth = resources.getDisplayMetrics().widthPixels;
        int screenHeight = resources.getDisplayMetrics().heightPixels;

        this.positionX += (int) getVelocityX();
        if(this.positionX + getSnackImage().getWidth() < 0) {
            playSound = true;
        }

        if (this.positionX + getSnackImage().getWidth() < 0) {
            this.positionX = random.nextInt(screenWidth + 1) + screenWidth;
            this.positionY = random.nextInt(screenHeight - getSnackImage().getHeight());
            setPlaySoundAllowed();
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

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getSnackImage(), positionX, positionY, null);
    }

    public void playSoundEat() {
        if(playSound) {
            Sounds.playSoundEat();
            setPlaySoundDisallowed();
        }
    }

    public int getPointsForSnack() {
        return pointsSnack;
    }

    private void setPlaySoundAllowed() {
        this.playSound = true;
    }

    private void setPlaySoundDisallowed() {
        this.playSound = false;
    }
}
