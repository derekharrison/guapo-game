package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class CharacterPopup extends Popup {

    protected Bitmap imageHit;
    protected boolean isHit;
    protected boolean playSoundHit = true;
    protected boolean playSoundAppearing = true;
    protected final Bubbles bubbles = new Bubbles(this);
    protected float velY;

    protected CharacterPopup(Builder builder) {
        super(builder);
        this.imageHit = builder.imageHit;
        this.isHit = builder.isHit;
        this.velY = builder.velY;
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
    public void draw(Canvas canvas) {
        if(!isHit)
            canvas.drawBitmap(image, getPositionX(), getPositionY(), null);

        if(isHit)
            canvas.drawBitmap(imageHit, getPositionX(), getPositionY(), null);

        if(isOnScreen())
            bubbles.drawBubbles(canvas);
    }

    public void playSoundHit() {
        // Implement in child classes
    }

    public void playSoundAppearing() {
        // Implement in child classes
    }

    public void hit() {
        isHit = true;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public float getVelY() {
        return velY;
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    public void reset() {
        playSound = true;
        frameCounter = 0;
        isHit = false;
    }

    public static class Builder extends Popup.Builder {
        private Bitmap imageHit;
        private float velY;
        private boolean isHit;

        @Override
        public Builder image(Bitmap image) {
            super.image(image);
            return this;
        }

        public Builder imageHit(Bitmap imageHit) {
            this.imageHit = imageHit;
            return this;
        }

        public Builder isHit(boolean isHit) {
            this.isHit = isHit;
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

        public Builder velY(float velY) {
            this.velY = velY;
            return this;
        }

        @Override
        public Builder frameCounter(int frameCounter) {
            super.frameCounter(frameCounter);
            return this;
        }

        @Override
        public CharacterPopup build() {
            return new CharacterPopup(this);
        }
    }

    protected void updatePosition() {
        // Implement in child classes
    }

    protected boolean isOnScreen() {
        return getPositionX() > -getImage().getWidth() && getPositionX() < getScreenWidth();
    }

    protected void updateBubbles() {
        if(isOnScreen()) {
            bubbles.updateBubbles(getFrameCounter());
        }
    }

    protected void reflect() {
        int height = image.getHeight() / 2;

        if(positionY < -height) {
            velY = -velY;
            positionY = -height;
        }

        if(positionY > getScreenHeight() - height) {
            velY = -velY;
            positionY = (float) getScreenHeight() - height;
        }
    }
}

