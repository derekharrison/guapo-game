package com.main.guapogame.graphics;

import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;

import android.content.Context;
import android.graphics.Bitmap;

import com.main.guapogame.resources.Sounds;

public class Brownie extends CharacterPopup {

    protected Brownie(Builder builder) {
        super(builder);
    }

    @Override
    public void update() {
        super.update();
        updatePosition();
        reflect();
        updateBubbles();
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

    public void hitBrownie() {
        isHit = true;
    }

    public void setFrameCounter(int frameCounter) {
        this.frameCounter = frameCounter;
    }
    
    public static class Builder extends CharacterPopup.Builder {
        @Override
        public Builder image(Bitmap image) {
            super.image(image);
            return this;
        }

        @Override
        public Builder imageHit(Bitmap imageHit) {
            super.imageHit(imageHit);
            return this;
        }

        @Override
        public Builder isHit(boolean isHit) {
            super.isHit(isHit);
            return this;
        }

        @Override
        public Builder duration(int duration) {
            super.duration(duration);
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
        public Builder context(Context context) {
            super.context(context);
            return this;
        }

        @Override
        public Builder velY(float velY) {
            super.velY(velY);
            return this;
        }

        @Override
        public Builder frameCounter(int frameCounter) {
            super.frameCounter(frameCounter);
            return this;
        }

        @Override
        public Brownie build() {
            return new Brownie(this);
        }
    }

    private void updatePosition() {
        positionX -= (getBackgroundSpeed() * 2);
        positionY += velY;
    }
}
