package com.main.guapogame.model.graphics.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.model.interfaces.Image;
import com.main.guapogame.model.interfaces.Position;
import com.main.guapogame.model.interfaces.Update;
import com.main.guapogame.model.interfaces.Velocity;

public class Background implements Position, Velocity, Update, Image {

    private float positionX;
    private final float velocityX;
    private final Bitmap image;

    private Background(Builder builder) {
        this.positionX = builder.positionX;
        this.velocityX = builder.velocityX;
        this.image = builder.background;
    }

    public static class Builder {
        private float positionX;
        private float velocityX;
        private Bitmap background;

        public Builder positionX(float positionX) {
            this.positionX = positionX;
            return this;
        }

        public Builder velocityX(float velocityX) {
            this.velocityX = velocityX;
            return this;
        }

        public Builder background(Bitmap background) {
            this.background = background;
            return this;
        }

        public Background build() {
            return new Background(this);
        }
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    @Override
    public float getPositionX() {
        return positionX;
    }

    @Override
    public float getPositionY() {
        return 0;
    }

    @Override
    public float getVelocityX() {
        return velocityX;
    }

    @Override
    public float getVelocityY() {
        return 0;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public void update() {
        this.positionX += this.velocityX;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, getPositionX(), 0, null);
    }
}
