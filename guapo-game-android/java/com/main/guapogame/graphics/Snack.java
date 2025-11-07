package com.main.guapogame.graphics;

import static com.main.guapogame.definitions.Parameters.getBackgroundSpeed;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.interfaces.Draw;
import com.main.guapogame.resources.Sounds;
import com.main.guapogame.interfaces.Image;
import com.main.guapogame.interfaces.Position;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.interfaces.Velocity;

import java.util.Random;

public class Snack implements Position, Velocity, Update, Image, Draw {
    private int positionX;
    private int positionY;
    private int velocityX;
    private final Bitmap snackImage;
    private final int assetId;
    private boolean playSound = true;
    private final int pointsSnack;

    protected Snack(Builder builder) {
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
        this.snackImage = builder.snackImage;
        this.pointsSnack = builder.pointsSnack;
        this.velocityX = builder.velocityX;
        this.assetId = builder.assetId;
    }

    public static class Builder {
        private int positionX = 0;
        private int velocityX;
        private int positionY;
        private Bitmap snackImage;
        private int pointsSnack;
        private int assetId;

        public Builder positionX(int positionX) {
            this.positionX = positionX;
            return this;
        }

        public Builder positionY(int positionY) {
            this.positionY = positionY;
            return this;
        }

        public Builder velocityX(int velocityX) {
            this.velocityX = velocityX;
            return this;
        }

        public Builder snackImage(Bitmap snackImage) {
            this.snackImage = snackImage;
            return this;
        }

        public Builder assetId(int assetId) {
            this.assetId = assetId;
            return this;
        }

        public Builder pointsForSnack(int pointsSnack) {
            this.pointsSnack = pointsSnack;
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

    public int getAssetId() {
        return assetId;
    }

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
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

        this.positionX += (int) getVelocityX();
        if(this.positionX + getSnackImage().getWidth() < 0) {
            playSound = true;
        }

        if (this.positionX + getSnackImage().getWidth() < 0) {
            Random rand = new Random();
            this.positionX = rand.nextInt(screenWidth + 1) + screenWidth;
            this.positionY = rand.nextInt(screenHeight - getSnackImage().getHeight());
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

    public void playSoundEat(Sounds sounds) {
        if(playSound) {
            sounds.playSoundEat();
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
