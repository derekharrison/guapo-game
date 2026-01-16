package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.model.state.BackgroundSpeed.getBackgroundSpeed;

import com.main.guapogame.resources.assets.Sounds;

public class Brownie extends CharacterPopup {

    protected Brownie(Builder builder) {
        super(builder);
    }

    @Override
    public void update() {
        super.update();
        updatePosition();
        playSoundAppearing();
    }

    @Override
    public void playSoundHit() {
        if(playSoundHit) {
            Sounds.playSoundBarkBrownieHit();
            playSoundHit = false;
        }
    }

    @Override
    public void playSoundAppearing() {
        if(playSoundAppearing && isOnScreen()) {
            Sounds.playSoundBrownieAppearing();
            playSoundAppearing = false;
        }
    }

    public void setFrameCounter(int frameCounter) {
        this.frameCounter = frameCounter;
    }
    
    public static class Builder extends CharacterPopup.Builder {

        @Override
        public CharacterPopup build() {
            return new Brownie(this);
        }
    }

    @Override
    protected void updatePosition() {
        positionX -= (getBackgroundSpeed() * 2);
        positionY += velY;
    }
}
