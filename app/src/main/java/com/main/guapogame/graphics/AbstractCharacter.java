package com.main.guapogame.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.interfaces.Draw;
import com.main.guapogame.interfaces.Image;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.interfaces.Velocity;

public abstract class AbstractCharacter extends GameObject implements Velocity, Image, Update, Draw {

    protected AbstractCharacter(Builder builder) {
        super(builder);
    }

    @Override
    public Bitmap getImage() {
        return null;
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

    @Override
    public void draw(Canvas canvas) {

    }
}

