package com.main.guapogame.graphics;

import static com.main.guapogame.definitions.Parameters.getBackgroundSpeed;
import static com.main.guapogame.definitions.Parameters.getScreenHeight;
import static com.main.guapogame.definitions.Parameters.getScreenWidth;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Random;

public class JellyFish extends Villain {
    protected JellyFish(Builder builder) {
        super(builder);
    }

    public static class Builder extends Villain.Builder {
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

        public JellyFish build() {
            super.build();
            return new JellyFish(this);
        }
    }

    @Override
    public void update() {
        super.update();
        if (getPositionX() + getWidth() < 0) {
            super.setVelX(getSpeed());
            setPositionX(getX());
            setPositionY(getY());
        }

        if (displayFirstImage())
            setPositionY(getPositionY() + 10);

        if (displaySecondImage())
            setPositionY(getPositionY() + 10);

        if (displayThirdImage())
            setPositionY(getPositionY() - 20);

        setPositionX(getPositionX() - getVelocityX());
    }

    private boolean displayFirstImage() {
        return getFrameCounter() < NUM_FRAMES_DISPLAY;
    }

    private boolean displaySecondImage() {
        return getFrameCounter() >= NUM_FRAMES_DISPLAY && getFrameCounter() < 2 * NUM_FRAMES_DISPLAY;
    }

    private boolean displayThirdImage() {
        return getFrameCounter() >= 2 * NUM_FRAMES_DISPLAY && getFrameCounter() < 3 * NUM_FRAMES_DISPLAY;
    }

    private int getX() {
        return (getScreenWidth() * 5) / 4;
    }

    private int getY() {
        return new Random().nextInt((int) (getScreenHeight()));
    }

    private int getSpeed() {
        int bound = 3 * getBackgroundSpeed();
        int speed = new Random().nextInt(bound);

        if (speed < bound / 2)
            speed = bound / 2;

        return speed;
    }
}
