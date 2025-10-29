package com.main.guapogame;

import static com.main.guapogame.Constants.FPS;
import static com.main.guapogame.Parameters.getScreenHeight;
import static com.main.guapogame.Parameters.getScreenWidth;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Frito extends Character {
    private float x = 0;
    private float y = 0;
    private float velX = 0;
    private float velY = 0;
    private Bitmap fritoImage;
    private Bitmap fritoImageRotated;
    private boolean hasInteractedWithHero = false;
    private boolean hasAppearedOnScreen = false;
    private boolean play_sound_allowed = false;

    private int frameCounter = 0;

    protected Frito(Builder builder) {
        super(builder);
        this.x = builder.x;
        this.y = builder.y;
        this.velX = builder.velX;
        this.velY = builder.velY;

    }

    public static class Builder extends Character.Builder {
        private float x = 0;
        private float y = 0;
        private float velX = 0;
        private float velY = 0;
        private Bitmap fritoImage;
        private Bitmap fritoImageRotated;


        public Builder x(float x) {
            this.x = x;
            return this;
        }

        public Builder y(float y) {
            this.y = y;
            return this;
        }

        public Builder fritoImage(Bitmap characterImage) {
            this.fritoImage = characterImage;
            return this;
        }

        public Builder fritoImageRotated(Bitmap characterImage) {
            this.fritoImageRotated = characterImage;
            return this;
        }

        public Builder velX(float velX) {
            this.velX = velX;
            return this;
        }

        public Builder velY(float velY) {
            this.velY = velY;
            return this;
        }

        public Frito build() {
            return new Frito(this);
        }
    }

    @Override
    public float getPositionX() {
        return x;
    }

    public void setPositionX(float x) {
        this.x = x;
    }

    @Override
    public float getPositionY() {
        return y;
    }

    public void setPositionY(float y) {
        this.y = y;
    }

    @Override
    public float getVelocityX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    @Override
    public float getVelocityY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    protected int getFrameCounter() {
        return frameCounter;
    }

    @Override
    public void update() {
        super.update();
        // TODO : implement
    }
}
