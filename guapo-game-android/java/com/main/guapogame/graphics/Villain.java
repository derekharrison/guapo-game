package com.main.guapogame.graphics;

import static com.main.guapogame.definitions.Parameters.getBackgroundSpeed;
import static com.main.guapogame.definitions.Parameters.getScreenHeight;
import static com.main.guapogame.definitions.Parameters.getScreenWidth;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.List;
import java.util.Random;

public class Villain extends Character {

    private final static int NUM_FRAMES_DISPLAY = 3;

    protected Villain(Builder builder) {
        super(builder);
    }

    public static class Builder extends Character.Builder {
        public Builder positionX(float positionX) {
            super.positionX(positionX);
            return this;
        }

        public Builder positionY(float positionY) {
            super.positionY(positionY);
            return this;
        }

        public Builder velX(float velX) {
            super.velX(velX);
            return this;
        }

        public Builder images(List<Bitmap> images) {
            super.images(images);
            return this;
        }

        public Builder frameCounter(int frameCounter) {
            super.frameCounter(frameCounter);
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
        if (getPositionX() + getWidth() < 0) {
            super.setVelX(getSpeed());
            setPositionX(getX());
            setPositionY(getY());
        }

        setPositionX(getPositionX() - getVelocityX());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
    }

    @Override
    public Bitmap getImage() {
        if (displayFirstImage())
            return getImages().getFirst();

        if (displaySecondImage())
            return getImages().get(1);

        if (displayThirdImage())
            return getImages().get(2);

        resetFrameCounter();

        return getImages().getFirst();
    }

    private int getX() {
        return (getScreenWidth() * 5) / 4;
    }

    private int getY() {
        Random random = new Random();
        return random.nextInt((int) (getScreenHeight() - getHeight()));
    }

    private int getSpeed() {
        Random random = new Random();
        int bound = 3 * getBackgroundSpeed();
        int speed = random.nextInt(bound);

        if (speed < bound / 2)
            speed = bound / 2;

        return speed;
    }

    private boolean displayFirstImage() {
        return getFrameCounter() >= 1 && getFrameCounter() <= NUM_FRAMES_DISPLAY;
    }

    private boolean displaySecondImage() {
        return getFrameCounter() > NUM_FRAMES_DISPLAY && getFrameCounter() <= 2 * NUM_FRAMES_DISPLAY;
    }

    private boolean displayThirdImage() {
        return getFrameCounter() > 2 * NUM_FRAMES_DISPLAY && getFrameCounter() <= 3 * NUM_FRAMES_DISPLAY;
    }
}
