package com.main.guapogame.model.graphics.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.model.state.Random;
import com.main.guapogame.model.state.Trajectory;
import com.main.guapogame.resources.assets.Sounds;

import java.util.ArrayList;
import java.util.List;

public class Rocco extends CharacterPopup {

    private final List<Bitmap> capes;
    private int capeCounter = 0;
    private float departureAngle;

    protected Rocco(Builder builder) {
        super(builder);
        this.capes = builder.capes;
    }

    @Override
    public void update() {
        if(getFrameCounter() < duration / 2) {
            updatePosition();
            updateBubbles();
            playSoundAppearing();
        }
        else if(getFrameCounter() < duration) {
            flyAway();
        }
        frameCounter++;
        capeCounter++;
    }

    @Override
    public void draw(Canvas canvas) {
        if(getFrameCounter() < duration) {
            canvas.drawBitmap(
                    getCapeImage(),
                    getPositionX(),
                    getPositionY() + getHeight() / 5,
                    null
            );

            if (!isHit)
                canvas.drawBitmap(image, getPositionX(), getPositionY(), null);

            if (isHit)
                canvas.drawBitmap(imageHit, getPositionX(), getPositionY(), null);

            if (isOnScreen())
                bubbles.drawBubbles(canvas);
        }
    }

    @Override
    public void playSoundHit() {
        if(playSoundHit) {
            Sounds.playSoundBarkFritoHit();
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

    @Override
    public void reset() {
        super.reset();
        departureAngle = Random.getRandomFloat((float) (2 * Math.PI));
    }

    public static class Builder extends CharacterPopup.Builder {
        private final List<Bitmap> capes = new ArrayList<>();

        public Builder capes(Bitmap cape) {
            capes.add(cape);
            return this;
        }

        @Override
        public CharacterPopup build() {
            return new Rocco(this);
        }
    }

    @Override
    protected void updatePosition() {
        if(Trajectory.getFirst() != null) {
            positionX = Trajectory.getFirst().getPositionX();
            positionY = Trajectory.getFirst().getPositionY();
        }
    }

    private void flyAway() {
        float speed = 100;
        float displacementX = (float) (speed * Math.cos(departureAngle));
        float displacementY = (float) (speed * Math.sin(departureAngle));

        positionX += displacementX;
        positionY += displacementY;
    }

    private Bitmap getCapeImage() {
        if (capeCounter < 2)
            return capes.get(1);

        else if(capeCounter < 4)
            return capes.get(0);

        capeCounter = 0;

        return capes.get(0);
    }

    private float getHeight() {
        return getImage().getHeight();
    }
}
