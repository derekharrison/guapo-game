package com.main.guapogame.graphics;

import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;
import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.security.SecureRandom;
import java.util.List;

public class Villain extends Character {
    protected int numFramesDisplay = 3;
    protected final SecureRandom random = new SecureRandom();

    protected Villain(Builder builder) {
        super(builder);
    }

    public static class Builder extends Character.Builder {
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
            return getImages().get(0);

        if (displaySecondImage())
            return getImages().get(1);

        if (displayThirdImage())
            return getImages().get(2);

        resetFrameCounter();

        return getImages().get(0);
    }

    protected void setFrameCounter(int frameCounter) {
        this.numFramesDisplay = frameCounter;
    }

    protected int getX() {
        return (getScreenWidth() * 5) / 4;
    }

    protected int getY() {
        return random.nextInt((int) (getScreenHeight() - getHeight()));
    }

    protected int getSpeed() {
        int bound = 3 * getBackgroundSpeed();
        int speed = random.nextInt(bound);

        if (speed < bound / 2)
            speed = bound / 2;

        return speed;
    }

    protected boolean displayFirstImage() {
        return getFrameCounter() <= numFramesDisplay;
    }

    protected boolean displaySecondImage() {
        return getFrameCounter() > numFramesDisplay && getFrameCounter() <= 2 * numFramesDisplay;
    }

    protected boolean displayThirdImage() {
        return getFrameCounter() > 2 * numFramesDisplay && getFrameCounter() <= 3 * numFramesDisplay;
    }
}
