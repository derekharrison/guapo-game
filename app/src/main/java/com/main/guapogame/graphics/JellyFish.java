package com.main.guapogame.graphics;

import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;

import android.graphics.Bitmap;

import java.util.List;

public class JellyFish extends Villain {
    protected static final int NUM_FRAMES = 6;

    protected JellyFish(Builder builder) {
        super(builder);
        setFrameCounter(NUM_FRAMES);
    }

    public static class Builder extends Villain.Builder {
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

        @Override
        public Builder velX(float velX) {
            super.velX(velX);
            return this;
        }

        @Override
        public Builder images(List<Bitmap> images) {
            super.images(images);
            return this;
        }

        @Override
        public Builder frameCounter(int frameCounter) {
            super.frameCounter(frameCounter);
            return this;
        }

        @Override
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

        updatePositionY();

        setPositionX(getPositionX() - getVelocityX());
    }

    private void updatePositionY() {
        if (displayFirstImage()) {
            setPositionY(getPositionY() + 5);
            return;
        }

        if (displaySecondImage()) {
            setPositionY(getPositionY() + 5);
            return;
        }

        if (displayThirdImage()) {
            setPositionY(getPositionY() - 10);
            return;
        }

        resetFrameCounter();
    }

    @Override
    protected boolean displayFirstImage() {
        return getFrameCounter() < NUM_FRAMES;
    }

    @Override
    protected boolean displaySecondImage() {
        return getFrameCounter() >= NUM_FRAMES && getFrameCounter() < 2 * NUM_FRAMES;
    }

    @Override
    protected boolean displayThirdImage() {
        return getFrameCounter() >= 2 * NUM_FRAMES && getFrameCounter() < 3 * NUM_FRAMES;
    }

    @Override
    protected int getY() {
        return random.nextInt(getScreenHeight());
    }
}
