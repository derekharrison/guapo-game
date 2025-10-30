package com.main.guapogame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Character {
    private final Bitmap heroImage;
    private final Bitmap heroHitImage;
    private final List<Bitmap> capes;

    protected Hero(Builder builder) {
        super(builder);
        this.heroImage = builder.heroImage;
        this.heroHitImage = builder.heroHitImage;
        this.capes = builder.capes;
        this.setPositionX(builder.x);
        this.setPositionY(builder.y);
    }

    public static class Builder extends Character.Builder {
        private float x = 0;
        private float y = 0;
        private Bitmap heroImage;
        private Bitmap heroHitImage;
        private final List<Bitmap> capes = new ArrayList<>();

        public Builder x(float x) {
            this.x = x;
            return this;
        }

        public Builder y(float y) {
            this.y = y;
            return this;
        }

        public Builder heroImage(Bitmap heroImage) {
            this.heroImage = heroImage;
            super.characterImage(heroImage);
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

    public float getWidth() {
        return getImage().getWidth();
    }

    public float getHeight() {
        return getImage().getHeight();
    }

    public Bitmap getImage() {
        if(GameState.getGameState().equals(State.GAME_OVER)
        || GameState.getGameState().equals(State.CONTINUE)) {
            return heroHitImage;
        }

        return heroImage;
    }

    private Bitmap getHeroCapeImage() {
        if (getFrameCounter() > 2 && getFrameCounter() <= 4) {
            return capes.get(1);
        }
        resetFrameCounter();
        return capes.get(0);
    }

    public void draw(Canvas canvas) {
        float x = getPositionX();
        float y = getPositionY();
        float width = getImage().getWidth();
        float height = getImage().getHeight();
        canvas.drawBitmap(getHeroCapeImage(), x - width / 3, getPositionY() - height / 5, null);
        canvas.drawBitmap(getImage(), x - width / 3, y -height / 3, null);
    }
}
