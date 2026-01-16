package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.model.state.BackgroundSpeed.getBackgroundSpeed;

import com.main.guapogame.resources.assets.Sounds;

public class Frito extends CharacterPopup {

    protected Frito(Builder builder) {
        super(builder);
    }

    @Override
    public void update() {
        super.update();
        playSoundAppearing();
        updatePosition();
    }

    @Override
    public void playSoundHit() {
        if(playSoundHit) {
            Sounds.playSoundBarkFritoHit();
            playSoundHit = false;
        }
    }

    @Override
    public void playSoundAppearing() {
        if(playSoundAppearing && isOnScreen()) {
            Sounds.playSoundCatAppearing();
            playSoundAppearing = false;
        }
    }

    public static class Builder extends CharacterPopup.Builder {

        @Override
        public CharacterPopup build() {
            return new Frito(this);
        }
    }

    @Override
    protected void updatePosition() {
        positionX += getBackgroundSpeed() * 2;
        positionY += velY;
    }
}
