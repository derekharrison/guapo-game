package com.main.guapogame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.resources.Sounds;
import com.main.guapogame.interfaces.GameImage;
import com.main.guapogame.interfaces.Position;
import com.main.guapogame.interfaces.Update;

public class Popup implements Position, GameImage, Update {

    private final int duration;
    private final Bitmap image;
    private final float positionX;
    private final float positionY;
    private int frameCounter = 0;
    private boolean playSound = true;

    protected Popup(Builder builder) {
        this.duration = builder.duration;
        this.image = builder.image;
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
    }

    public void draw(Canvas canvas) {
        if(frameCounter < duration) {
            canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
        }
    }

    public void playSoundCheckpoint(Sounds sounds) {
        if(playSound) {
            sounds.playSoundCheckpoint();
            playSound = false;
        }
    }

    @Override
    public void update() {
        if(frameCounter < duration) {
            frameCounter++;
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

    public static class Builder {
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

        public Popup build() {
            return new Popup(this);
        }
    }
}
