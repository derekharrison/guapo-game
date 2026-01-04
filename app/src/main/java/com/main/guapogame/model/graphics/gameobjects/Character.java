package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.parameters.Parameters.FPS;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Character extends AbstractCharacter {
    private float velX;
    private float velY;
    private final List<Bitmap> images;
    private int frameCounter;
    private int bubbleFrameCounter = 0;

    protected Character(Builder builder) {
        super(builder);
        this.velX = builder.velX;
        this.velY = builder.velY;
        this.frameCounter = builder.frameCounter;
        this.images = builder.images;
    }

    public static class Builder extends GameObject.Builder {
        private float velX = 0;
        private float velY = 0;
        private int frameCounter = 0;
        private List<Bitmap> images = new ArrayList<>();

        @Override
        public Builder positionX(float positionX) {
            super.positionX(positionX);
            return this;
        }

        @Override
        public Builder positionY(float positionY) {
            super.positionY(positionY);
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

        @Override
        public Builder context(Context context) {
            super.context(context);
            return this;
        }

        @Override
        public Character build() {
            return new Character(this);
        }
    }

    public List<Bitmap> getImages() { return images; }


    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
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

    public int getBubbleFrameCounter() {
        return bubbleFrameCounter;
    }

    protected void resetFrameCounter() {
        frameCounter = 0;
    }

    protected void resetBubbleFrameCounter() {
        bubbleFrameCounter = 0;
    }

    @Override
    public void update() {
        advanceFrameCounter();
        advanceBubbleFrameCounter();
    }

    private void advanceFrameCounter() {
        frameCounter++;

        if(frameCounter >= 10000 * FPS) {
            frameCounter = 0;
        }
    }

    private void advanceBubbleFrameCounter() {
        bubbleFrameCounter++;

        if(bubbleFrameCounter >= 10000 * FPS) {
            bubbleFrameCounter = 0;
        }
    }
}
