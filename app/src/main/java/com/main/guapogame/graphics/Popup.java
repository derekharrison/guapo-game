package com.main.guapogame.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.interfaces.Draw;
import com.main.guapogame.interfaces.Image;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.resources.Sounds;

public class Popup extends GameObject implements Image, Update, Draw {
    protected final int duration;
    protected final Bitmap image;
    protected int frameCounter;
    protected boolean playSound = true;

    protected Popup(Builder builder) {
        super(builder);
        this.duration = builder.duration;
        this.image = builder.image;
        this.frameCounter = builder.frameCounter;
    }

    @Override
    public void update() {
        if(frameCounter < duration) {
            frameCounter++;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if(frameCounter < duration) {
            canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
        }
    }

    public void playSound() {
        if(playSound) {
            Sounds.playSoundCheckpoint();
            playSound = false;
        }
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    public static class Builder extends GameObject.Builder {
        private int duration;
        private Bitmap image;
        private int frameCounter;

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder image(Bitmap image) {
            this.image = image;
            return this;
        }

        public Builder frameCounter(int frameCounter) {
            this.frameCounter = frameCounter;
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
        public Popup build() {
            return new Popup(this);
        }
    }
}
