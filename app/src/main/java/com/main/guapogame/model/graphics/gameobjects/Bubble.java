package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.model.state.BackgroundSpeed.getBackgroundSpeed;

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
        public Popup build() {
            return new Bubble(this);
        }
    }
}
