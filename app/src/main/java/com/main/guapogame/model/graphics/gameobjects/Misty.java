package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.parameters.Parameters.FPS;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.resources.Sounds;

public class Misty extends Popup {
    private final Bitmap mistyTop;
    private final Bitmap mistyTopHit;
    private final Bitmap mistyBottom;
    private final Bitmap mistyBottomHit;
    private Bitmap mistyImage;
    private Bitmap mistyImageHit;
    private boolean isHit;
    private final boolean isTop;
    private boolean playSoundMistyHit = true;
    private final Bubbles bubbles = new Bubbles(this);

    protected Misty(Builder builder) {
        super(builder);
        this.mistyTop = builder.mistyTop;
        this.mistyTopHit = builder.mistyTopHit;
        this.mistyBottom = builder.mistyBottom;
        this.mistyBottomHit = builder.mistyBottomHit;
        this.isHit = builder.isHit;
        this.isTop = builder.isTop;
        setImages();
        setStartPositionY();
    }

    @Override
    public void update() {
        super.update();

        if (isTop)
            updateTop();

        if (!isTop)
            updateBottom();

        bubbles.updateBubbles(getFrameCounter());
    }

    @Override
    public void draw(Canvas canvas) {
        if(frameCounter < duration && !isHit && isTop)
            canvas.drawBitmap(mistyTop, getPositionX(), getPositionY(), null);

        if(frameCounter < duration && isHit && isTop)
            canvas.drawBitmap(mistyTopHit, getPositionX(), getPositionY(), null);

        if(frameCounter < duration && !isHit && !isTop)
            canvas.drawBitmap(mistyBottom, getPositionX(), getPositionY(), null);

        if(frameCounter < duration && isHit && !isTop)
            canvas.drawBitmap(mistyBottomHit, getPositionX(), getPositionY(), null);

        bubbles.drawBubbles(canvas);
    }

    @Override
    public void playSound() {
        if(playSound) {
            Sounds.playSoundMistyAppearing();
            playSound = false;
        }
    }

    public void playSoundHit() {
        if(playSoundMistyHit) {
            Sounds.playSoundBarkMistyHit();
            playSoundMistyHit = false;
        }
    }

    public void hitMisty() {
        isHit = true;
    }

    public boolean isHit() {
        return isHit;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setFrameCounter(int frameCounter) {
        this.frameCounter = frameCounter;
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    @Override
    public Bitmap getImage() {
        return mistyImage;
    }

    public Bitmap getImageHit() {
        return mistyImageHit;
    }

    public static class Builder extends Popup.Builder {
        private Bitmap mistyTop;
        private Bitmap mistyTopHit;
        private Bitmap mistyBottom;
        private Bitmap mistyBottomHit;
        private boolean isHit;
        private boolean isTop;

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
        public Builder context(Context context) {
            super.context(context);
            return this;
        }

        public Builder mistyTop(Bitmap mistyTop) {
            this.mistyTop = mistyTop;
            return this;
        }

        public Builder mistyTopHit(Bitmap mistyTopHit) {
            this.mistyTopHit = mistyTopHit;
            return this;
        }

        public Builder mistyBottom(Bitmap mistyBottom) {
            this.mistyBottom = mistyBottom;
            return this;
        }

        public Builder mistyBottomHit(Bitmap mistyBottomHit) {
            this.mistyBottomHit = mistyBottomHit;
            return this;
        }

        public Builder isHit(boolean isHit) {
            this.isHit = isHit;
            return this;
        }

        public Builder isTop(boolean isTop) {
            this.isTop = isTop;
            return this;
        }

        @Override
        public Builder frameCounter(int frameCounter) {
            super.frameCounter(frameCounter);
            return this;
        }

        @Override
        public Misty build() {
            return new Misty(this);
        }
    }

    private void updateTop() {
        if(getFrameCounter() < 3 * FPS)
            this.positionY += getVelY();

        if(getFrameCounter() >= 3 * FPS)
            this.positionY -= getVelY();
    }

    private void updateBottom() {
        if(getFrameCounter() < 3 * FPS)
            this.positionY -= getVelY();

        if(getFrameCounter() >= 3 * FPS)
            this.positionY += getVelY();
    }

    private int getVelY() {
        return getScreenHeight() / 150;
    }

    private void setImages() {
        if(isTop) {
            mistyImage = mistyTop;
            mistyImageHit = mistyTopHit;
        }

        if(!isTop) {
            mistyImage = mistyBottom;
            mistyImageHit = mistyBottomHit;
        }
    }

    private void setStartPositionY() {
        if(isTop)
            positionY = calcStartPositionTopY();

        if(!isTop)
            positionY = calcStartPositionBottomY();
    }

    private int calcStartPositionTopY() {
        return -2 * mistyImage.getHeight();
    }

    private int calcStartPositionBottomY() {
        return getScreenHeight() + mistyImage.getHeight();
    }
}
