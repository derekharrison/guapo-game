package com.main.guapogame;

import static com.main.guapogame.Parameters.getBackgroundSpeed;
import static com.main.guapogame.Parameters.getScreenHeight;
import static com.main.guapogame.Parameters.getScreenWidth;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Villain extends Character {
    private int speed = 20;
    private final static int NUM_FRAMES_DISPLAY = 3;
    private final List<Bitmap> images;

    protected Villain(Builder builder) {
        super(builder);
        this.images = builder.images;
    }


    public static class Builder extends Character.Builder {
        private float x = 0;
        private float y = 0;
        private float velX;

        private List<Bitmap> images = new ArrayList<>();

        public Builder x(float x) {
            this.x = x;
            return this;
        }

        public Builder y(float y) {
            this.y = y;
            return this;
        }

        public Builder images(Bitmap image) {
            this.images.add(image);
            return this;
        }

        public Builder images(List<Bitmap> images) {
            this.images.addAll(images);
            return this;
        }

        public Builder velX(float velX) {
            this.velX = velX;
            return this;
        }

        public Villain build() {
            return new Villain(this);
        }
    }

    public float getWidth() {
        return getImage().getWidth();
    }

    public float getHeight() {
        return getImage().getHeight();
    }

    @Override
    public void update() {
        super.update();
        Random random = new Random();
        if (getPositionX() + getWidth() < 0) {
            int bound = 3 * getBackgroundSpeed();
            this.speed = random.nextInt(bound);

            if (this.speed < (bound/2)) {
                this.speed = bound / 2;
            }

            setPositionX(((float) getScreenWidth() * 5) / 4);
            setPositionY(random.nextInt(getScreenHeight()));
        }
        setPositionX(getPositionX() - speed);
    }

    @Override
    public Bitmap getImage() {
        if (getFrameCounter() >= 1 && getFrameCounter() <= NUM_FRAMES_DISPLAY) {
            return images.get(0);
        }

        if (getFrameCounter() > NUM_FRAMES_DISPLAY && getFrameCounter() <= 2 * NUM_FRAMES_DISPLAY) {
            return images.get(1);
        }

        if (getFrameCounter() > 2 * NUM_FRAMES_DISPLAY && getFrameCounter() <= 3 * NUM_FRAMES_DISPLAY) {
            return images.get(2);
        }

        resetFrameCounter();

        return images.get(0);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
    }
}
