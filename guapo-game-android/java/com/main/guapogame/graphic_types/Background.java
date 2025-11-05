package com.main.guapogame.graphic_types;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.interfaces.GameImage;
import com.main.guapogame.interfaces.Position;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.interfaces.Velocity;

public class Background implements Position, Velocity, Update, GameImage {

    private float positionX;
    private final float velocityX;
    private final Bitmap background;

    private Background(Builder builder) {
        this.positionX = builder.positionX;
        this.velocityX = builder.velocityX;
        this.background = builder.background;
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

        public Builder drawable(Bitmap background) {
            this.background = background;
            return this;
        }
        public Builder background(Bitmap background) {
            this.background = background;
            return this;
        }

        Background build() {
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
        return background;
    }

    public Bitmap getBackground() {
        return background;
    }

    @Override
    public void update() {
        this.positionX += this.velocityX;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(background, getPositionX(), 0, null);
    }
}
