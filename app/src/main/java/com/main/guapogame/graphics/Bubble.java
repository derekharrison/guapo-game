package com.main.guapogame.graphics;

import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;

import android.graphics.Bitmap;

public class Bubble extends Popup {
    protected Bubble(Builder builder) {
        super(builder);
    }

    @Override
    public void update() {
        super.update();
        if(frameCounter < duration) {
            frameCounter++;
        }

        this.positionY = this.positionY - 5;
        this.positionX -= getBackgroundSpeed();
    }

    public static class Builder extends Popup.Builder {
        @Override
        public Builder duration(int duration) {
            super.duration(duration);
            return this;
        }

        @Override
        public Builder image(Bitmap image) {
            super.image(image);
            return this;
        }

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
        public Bubble build() {
            return new Bubble(this);
        }
    }
}
