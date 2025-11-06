package com.main.guapogame.graphics;

import static com.main.guapogame.definitions.Parameters.FPS;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Character extends AbstractCharacter {
    private float x;
    private float y;
    private float velX;
    private float velY;
    private final List<Bitmap> images;
    private int frameCounter;

    protected Character(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.velX = builder.velX;
        this.velY = builder.velY;
        this.frameCounter = builder.frameCounter;
        this.images = builder.images;
    }

    public static class Builder {
        private float x = 0;
        private float y = 0;
        private float velX = 0;
        private float velY = 0;
        private int frameCounter = 0;
        private List<Bitmap> images = new ArrayList<>();

        public Builder positionX(float x) {
            this.x = x;
            return this;
        }

        public Builder positionY(float y) {
            this.y = y;
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

        public Builder images(List<Bitmap> images) {
            this.images = images;
            return this;
        }

        public Builder frameCounter(int frameCounter) {
            this.frameCounter = frameCounter;
            return this;
        }

        public Character build() {
            return new Character(this);
        }
    }

    public List<Bitmap> getImages() { return images; }

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

    public int getFrameCounter() {
        return frameCounter;
    }

    protected void resetFrameCounter() {
        frameCounter = 0;
    }

    @Override
    public void update() {
        advanceFrameCounter();
    }

    private void advanceFrameCounter() {
        frameCounter++;

        if(frameCounter >= 2 * FPS) {
            frameCounter = 0;
        }
    }
}
