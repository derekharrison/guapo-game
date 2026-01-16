package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;

public class JellyFish extends Villain {
    protected static final int NUM_FRAMES = 6;

    protected JellyFish(Builder builder) {
        super(builder);
        setFrameCounter(NUM_FRAMES);
    }

    public static class Builder extends Villain.Builder {

        @Override
        public Villain build() {
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
}
