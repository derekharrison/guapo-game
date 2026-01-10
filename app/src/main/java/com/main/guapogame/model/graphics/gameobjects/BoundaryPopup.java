package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.parameters.Parameters.FPS;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.resources.assets.Sounds;

public class BoundaryPopup extends Popup {
    private final Bitmap top;
    private final Bitmap topHit;
    private final Bitmap bottom;
    private final Bitmap bottomHit;
    private Bitmap imageHit;
    private boolean isHit;
    private final boolean isTop;
    private boolean playSoundHit = true;
    private final Bubbles bubbles = new Bubbles(this);

    protected BoundaryPopup(Builder builder) {
        super(builder);
        this.top = builder.top;
        this.topHit = builder.topHit;
        this.bottom = builder.bottom;
        this.bottomHit = builder.bottomHit;
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
            canvas.drawBitmap(top, getPositionX(), getPositionY(), null);

        if(frameCounter < duration && isHit && isTop)
            canvas.drawBitmap(topHit, getPositionX(), getPositionY(), null);

        if(frameCounter < duration && !isHit && !isTop)
            canvas.drawBitmap(bottom, getPositionX(), getPositionY(), null);

        if(frameCounter < duration && isHit && !isTop)
            canvas.drawBitmap(bottomHit, getPositionX(), getPositionY(), null);

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
        if(playSoundHit) {
            Sounds.playSoundBarkMistyHit();
            playSoundHit = false;
        }
    }

    public void hit() {
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
        return image;
    }

    public Bitmap getImageHit() {
        return imageHit;
    }

    public static class Builder extends Popup.Builder {
        private Bitmap top;
        private Bitmap topHit;
        private Bitmap bottom;
        private Bitmap bottomHit;
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

        public Builder top(Bitmap top) {
            this.top = top;
            return this;
        }

        public Builder topHit(Bitmap topHit) {
            this.topHit = topHit;
            return this;
        }

        public Builder bottom(Bitmap bottom) {
            this.bottom = bottom;
            return this;
        }

        public Builder bottomHit(Bitmap bottomHit) {
            this.bottomHit = bottomHit;
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
        public BoundaryPopup build() {
            return new BoundaryPopup(this);
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
        return (getImage().getHeight() * 3) / (12 * FPS);
    }

    private void setImages() {
        if(isTop) {
            image = top;
            imageHit = topHit;
        }

        if(!isTop) {
            image = bottom;
            imageHit = bottomHit;
        }
    }

    private void setStartPositionY() {
        if(isTop)
            positionY = calcStartPositionTopY();

        if(!isTop)
            positionY = calcStartPositionBottomY();
    }

    private int calcStartPositionTopY() {
        return -image.getHeight();
    }

    private int calcStartPositionBottomY() {
        return getScreenHeight();
    }
}