package com.main.guapogame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.resources.Sounds;
import com.main.guapogame.model.State;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Character {
    private final Bitmap heroImage;
    private final Bitmap heroHitImage;
    private final List<Bitmap> capes;
    private boolean playSound = true;
    private final Villain.Heros hero;

    protected Hero(Builder builder) {
        super(builder);
        this.heroImage = builder.heroImage;
        this.heroHitImage = builder.heroHitImage;
        this.capes = builder.capes;
        this.hero = builder.hero;
        this.setPositionX(builder.x);
        this.setPositionY(builder.y);
    }

    public static class Builder extends Character.Builder {
        private float x = 0;
        private float y = 0;
        private Bitmap heroImage;
        private Bitmap heroHitImage;
        private final List<Bitmap> capes = new ArrayList<>();
        private Villain.Heros hero;

        public Builder positionX(float x) {
            this.x = x;
            return this;
        }

        public Builder positionY(float y) {
            this.y = y;
            return this;
        }

        public Builder heroImage(Bitmap heroImage) {
            this.heroImage = heroImage;
            super.image(heroImage);
            return this;
        }

        public Builder heroHitImage(Bitmap heroHit) {
            this.heroHitImage = heroHit;
            return this;
        }

        public Builder capes(Bitmap cape) {
            capes.add(cape);
            return this;
        }

        public Builder hero(Villain.Heros hero) {
            this.hero = hero;
            return this;
        }

        public Builder frameCounter(int frameCounter) {
            super.frameCounter(frameCounter);
            return this;
        }

        public Hero build() {
            return new Hero(this);
        }
    }

    @Override
    public void update() {
        super.update();
        this.setPositionX(getPositionX() + getVelocityX());
        this.setPositionY(getPositionY() + getVelocityY());
    }

    public void draw(Canvas canvas) {
        if(hero.equals(Villain.Heros.TUTTI)) {
            drawTutti(canvas);
        }
        if(hero.equals(Villain.Heros.GUAPO)) {
            drawGuapo(canvas);
        }
    }

    public float getWidth() {
        return getImage().getWidth();
    }

    public float getHeight() {
        return getImage().getHeight();
    }

    public Bitmap getImage() {
        if(State.getGameState().equals(com.main.guapogame.types.State.GAME_OVER)
        || State.getGameState().equals(com.main.guapogame.types.State.CONTINUE)) {
            return heroHitImage;
        }

        return heroImage;
    }

    public void playSoundInteractingWithVillain(Sounds sounds) {
        if(playSound) {
            sounds.playSoundBarkHit();
            playSound = false;
        }
    }

    private void drawGuapo(Canvas canvas) {
        canvas.drawBitmap(getCapeImage(), getPositionX(), getPositionY() + getHeight() / 5, null);
        canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
    }

    private void drawTutti(Canvas canvas) {
        canvas.drawBitmap(getCapeImage(), getPositionX(), getPositionY(), null);
        canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
    }

    private Bitmap getCapeImage() {
        if (getFrameCounter() <= 2) {
            return capes.get(1);
        }
        else if(getFrameCounter() > 2 && getFrameCounter() <= 4) {
            return capes.get(0);
        }

        resetFrameCounter();
        
        return capes.get(0);
    }
}
