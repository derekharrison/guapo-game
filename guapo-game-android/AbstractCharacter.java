package com.main.guapogame;

import android.graphics.Bitmap;

public abstract class AbstractCharacter implements Position, Velocity, GameImage, Update {

    @Override
    public Bitmap getImage() {
        return null;
    }

    @Override
    public float getPositionX() {
        return 0;
    }

    @Override
    public float getPositionY() {
        return 0;
    }

    @Override
    public float getVelocityX() {
        return 0;
    }

    @Override
    public float getVelocityY() {
        return 0;
    }

    @Override
    public void update() {

    }
}

