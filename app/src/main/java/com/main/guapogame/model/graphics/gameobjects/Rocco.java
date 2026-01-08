package com.main.guapogame.model.graphics.gameobjects;

import com.main.guapogame.model.state.Trajectory;
import com.main.guapogame.resources.assets.Sounds;

public class Rocco extends CharacterPopup {

    protected Rocco(Builder builder) {
        super(builder);
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
            return new Rocco(this);
        }
    }

    @Override
    protected void updatePosition() {
        if(Trajectory.getFirst() != null) {
            positionX = Trajectory.getFirst().getPositionX();
            positionY = Trajectory.getFirst().getPositionY();
        }
    }
}
