package com.main.guapogame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.resources.Sounds;

public class Bubble extends Popup {

    private final int duration;
    private final Bitmap image;
    private final float positionX;
    private float positionY;
    private int frameCounter = 0;
    private boolean playSound = true;

    protected Bubble(Builder builder) {
        super(builder);
        this.duration = builder.duration;
        this.image = builder.image;
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
    }

    @Override
    public void update() {
        super.update();
        if(frameCounter < duration) {
            frameCounter++;
        }

        this.positionY = this.positionY - 5;
    }

    @Override
    public void draw(Canvas canvas) {
        if(frameCounter < duration) {
            canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
        }
    }

    public void playSound() {
        if(playSound) {
            Sounds.playBubbles();
            playSound = false;
        }
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public float getPositionX() {
        return positionX;
    }

    @Override
    public float getPositionY() {
        return positionY;
    }

    public static class Builder extends Popup.Builder {
        private int duration;
        private Bitmap image;
        private float positionX;
        private float positionY;

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder image(Bitmap image) {
            this.image = image;
            return this;
        }

        public Builder positionX(int positionX) {
            this.positionX = positionX;
            return this;
        }

        public Builder positionY(int positionY) {
            this.positionY = positionY;
            return this;
        }

        public Bubble build() {
            return new Bubble(this);
        }
    }
}
