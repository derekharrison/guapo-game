package com.main.guapogame.graphic_types;

import android.graphics.Bitmap;

import com.main.guapogame.interfaces.GameImage;
import com.main.guapogame.interfaces.Position;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.interfaces.Velocity;

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

