package com.main.guapogame;

import static com.main.guapogame.Constants.FPS;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Character extends AbstractCharacter {
    private float x = 0;
    private float y = 0;
    private float velX = 0;
    private float velY = 0;
    private final Bitmap characterImage;
    private final List<Bitmap> characterImages;
    private int frameCounter = 0;

    protected Character(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.velX = builder.velX;
        this.velY = builder.velY;
        this.characterImage = builder.characterImage;
        this.characterImages = builder.characterImages;
    }

    public static class Builder {
        private float x = 0;
        private float y = 0;
        private float velX = 0;
        private float velY = 0;
        private Bitmap characterImage;
        private List<Bitmap> characterImages = new ArrayList<>();

        public Builder x(float x) {
            this.x = x;
            return this;
        }

        public Builder y(float y) {
            this.y = y;
            return this;
        }

        public Builder characterImage(Bitmap characterImage) {
            this.characterImage = characterImage;
            this.characterImages.add(characterImage);
            return this;
        }

        public Builder characterImages(List<Bitmap> characterImage) {
            this.characterImages.addAll(characterImage);
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

        public Character build() {
            return new Character(this);
        }
    }


    public void addCharacter(Bitmap character) {
        characterImages.add(character);
    }

    public Bitmap getCharacterImage() {
        return characterImage;
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
