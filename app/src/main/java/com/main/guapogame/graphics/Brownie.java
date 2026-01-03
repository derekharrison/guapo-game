package com.main.guapogame.graphics;

import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;
import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.resources.Sounds;

public class Brownie extends Popup {

    private final Bitmap brownieImage;
    private final Bitmap brownieImageHit;
    private boolean isHit;
    private boolean playSoundHit = true;
    private boolean playSoundAppearing = true;
    private final Bubbles bubbles = new Bubbles(this);
    private float velY;

    protected Brownie(Builder builder) {
        super(builder);
        this.brownieImage = builder.image;
        this.brownieImageHit = builder.imageHit;
        this.velY = builder.velY;
        this.isHit = builder.isHit;
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
            canvas.drawBitmap(brownieImage, getPositionX(), getPositionY(), null);

        if(isHit)
            canvas.drawBitmap(brownieImageHit, getPositionX(), getPositionY(), null);

        if(isOnScreen())
            bubbles.drawBubbles(canvas);
    }

    public void playSoundHit() {
        if(playSoundHit) {
            Sounds.playSoundBarkBrownieHit();
            playSoundHit = false;
        }
    }

    public void playSoundAppearing() {
        if(playSoundAppearing && isOnScreen()) {
            Sounds.playSoundBrownieAppearing();
            playSoundAppearing = false;
        }
    }

    public void hitBrownie() {
        isHit = true;
    }

    public boolean getIsHit() {
        return isHit;
    }

    @Override
    public Bitmap getImage() {
        return brownieImage;
    }

    public Bitmap getImageHit() {
        return brownieImageHit;
    }

    public void setFrameCounter(int frameCounter) {
        this.frameCounter = frameCounter;
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    public float getVelY() {
        return velY;
    }

    public static class Builder extends Popup.Builder {
        private Bitmap image;
        private Bitmap imageHit;
        private boolean isHit;
        private int velY;

        @Override
        public Builder image(Bitmap image) {
            this.image = image;
            return this;
        }

        public Builder imageHit(Bitmap imageHit) {
            this.imageHit = imageHit;
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

        public Builder isHit(boolean isHit) {
            this.isHit = isHit;
            return this;
        }

        public Builder velY(int velY) {
            this.velY = velY;
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

    private boolean isOnScreen() {
        return getPositionX() > -getImage().getWidth() && getPositionX() < getScreenWidth();
    }

    private void updateBubbles() {
        if(isOnScreen()) {
            bubbles.updateBubbles(getFrameCounter());
        }
    }

    private void updatePosition() {
        positionX -= (getBackgroundSpeed() * 2);
        positionY += velY;
    }

    private void reflect() {
        int height = brownieImage.getHeight() / 2;

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
