package com.main.guapogame.graphics;

import static com.main.guapogame.state.BackgroundSpeed.getBackgroundSpeed;
import static com.main.guapogame.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.resources.Sounds;

public class Frito extends Popup {

    private final Bitmap fritoImage;
    private final Bitmap fritoImageHit;
    private boolean isHit;
    private boolean playSoundHit = true;
    private boolean playSoundAppearing = true;
    private final Bubbles bubbles = new Bubbles(this);
    private float velY;

    protected Frito(Builder builder) {
        super(builder);
        this.fritoImage = builder.image;
        this.fritoImageHit = builder.imageHit;
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
            canvas.drawBitmap(fritoImage, getPositionX(), getPositionY(), null);

        if(isHit)
            canvas.drawBitmap(fritoImageHit, getPositionX(), getPositionY(), null);

        if(isOnScreen())
            bubbles.drawBubbles(canvas);
    }

    public void playSoundHit() {
        if(playSoundHit) {
            Sounds.playSoundBarkFritoHit();
            playSoundHit = false;
        }
    }

    public void playSoundAppearing() {
        if(playSoundAppearing && isOnScreen()) {
            Sounds.playSoundCatAppearing();
            playSoundAppearing = false;
        }
    }

    public void hitFrito() {
        isHit = true;
    }

    public boolean getIsHit() {
        return isHit;
    }

    @Override
    public Bitmap getImage() {
        return fritoImage;
    }

    public Bitmap getImageHit() {
        return fritoImageHit;
    }

    public float getVelY() {
        return velY;
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    public static class Builder extends Popup.Builder {
        private Bitmap image;
        private Bitmap imageHit;
        private boolean isHit;
        private float velY;

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
        public Frito build() {
            return new Frito(this);
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
        positionX += getBackgroundSpeed() * 2;
        positionY += velY;
    }

    private void reflect() {
        int height = fritoImage.getHeight() / 2;

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
