package com.main.guapogame.model.graphics.gameobjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.model.state.Trajectory;
import com.main.guapogame.resources.assets.Sounds;

import java.util.ArrayList;
import java.util.List;

public class Rocco extends CharacterPopup {

    private final List<Bitmap> capes;
    private int capeCounter = 0;

    protected Rocco(Builder builder) {
        super(builder);
        this.capes = builder.capes;
    }

    @Override
    public void update() {
        super.update();
        if(getFrameCounter() < duration) {
            updatePosition();
            updateBubbles();
            playSoundAppearing();
            capeCounter++;
        }
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
            Sounds.playSoundCatAppearing();
            playSoundAppearing = false;
        }
    }

    public static class Builder extends CharacterPopup.Builder {
        private List<Bitmap> capes = new ArrayList<>();

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
