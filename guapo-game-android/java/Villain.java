package com.main.guapogame;

import static com.main.guapogame.Parameters.getBackgroundSpeed;
import static com.main.guapogame.Parameters.getScreenHeight;
import static com.main.guapogame.Parameters.getScreenWidth;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;
import java.util.Random;

public class Villain extends Character {
    private int speed = 20;
    private final static int NUM_FRAMES_DISPLAY = 3;

    protected Villain(Builder builder) {
        super(builder);
    }

    public static class Builder extends Character.Builder {
        public Builder positionX(float positionX) {
            super.positionX(positionX);
            return this;
        }

        public Builder images(List<Bitmap> images) {
            super.images(images);
            return this;
        }

        public Villain build() {
            super.build();
            return new Villain(this);
        }
    }

    public float getWidth() {
        return getImage().getWidth();
    }

    public float getHeight() {
        return getImage().getHeight();
    }

    @Override
    public void update() {
        super.update();
        Random random = new Random();
        if (getPositionX() + getWidth() < 0) {
            int bound = 3 * getBackgroundSpeed();
            this.speed = random.nextInt(bound);

            if (this.speed < (bound/2)) {
                this.speed = bound / 2;
            }

            setPositionX(((float) getScreenWidth() * 5) / 4);
            setPositionY(random.nextInt((int) (getScreenHeight() - getHeight())));
        }
        setPositionX(getPositionX() - speed);
    }

    @Override
    public Bitmap getImage() {
        if (getFrameCounter() >= 1 && getFrameCounter() <= NUM_FRAMES_DISPLAY) {
            return getImages().get(0);
        }

        if (getFrameCounter() > NUM_FRAMES_DISPLAY && getFrameCounter() <= 2 * NUM_FRAMES_DISPLAY) {
            return getImages().get(1);
        }

        if (getFrameCounter() > 2 * NUM_FRAMES_DISPLAY && getFrameCounter() <= 3 * NUM_FRAMES_DISPLAY) {
            return getImages().get(2);
        }

        resetFrameCounter();

        return getImages().get(0);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
    }
}
