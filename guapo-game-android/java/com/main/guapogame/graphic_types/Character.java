package com.main.guapogame.graphic_types;

import static com.main.guapogame.resources.Parameters.FPS;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Character extends AbstractCharacter {
    private float x;
    private float y;
    private float velX;
    private float velY;
    private final Bitmap image;
    private final List<Bitmap> images;
    private int frameCounter = 0;

    protected Character(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.velX = builder.velX;
        this.velY = builder.velY;
        this.image = builder.image;
        this.frameCounter = builder.frameCounter;
        this.images = builder.images;
    }

    public static class Builder {
        private float x = 0;
        private float y = 0;
        private float velX = 0;
        private float velY = 0;
        private Bitmap image;
        private int frameCounter = 0;
        private final List<Bitmap> images = new ArrayList<>();

        public Builder positionX(float x) {
            this.x = x;
            return this;
        }

        public Builder positionY(float y) {
            this.y = y;
            return this;
        }

        public Builder image(Bitmap image) {
            this.image = image;
            this.images.add(image);
            return this;
        }

        public Builder images(List<Bitmap> characterImage) {
            this.images.addAll(characterImage);
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

        public Builder frameCounter(int frameCounter) {
            this.frameCounter = frameCounter;
            return this;
        }

        public Character build() {
            return new Character(this);
        }
    }


    public void addCharacter(Bitmap character) {
        images.add(character);
    }

    public Bitmap getCharacterImage() {
        return image;
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

    public void setFrameCounter(int frameCounter) {
        this.frameCounter = frameCounter;
    }

    private void advanceFrameCounter() {
        frameCounter++;

        if(frameCounter >= 2 * FPS) {
            frameCounter = 0;
        }
    }
}
