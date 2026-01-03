package com.main.guapogame.graphics;

import android.content.Context;

import com.main.guapogame.interfaces.Position;

public class GameObject implements Position {
    protected final Context context;
    protected float positionX;
    protected float positionY;

    protected GameObject(Builder builder) {
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
        this.context = builder.context;
    }

    protected Context getContext() {
        return context;
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
        private Context context;
        private float positionX;
        private float positionY;

        public Builder positionX(float positionX) {
            this.positionX = positionX;
            return this;
        }

        public Builder positionY(float positionY) {
            this.positionY = positionY;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public GameObject build() {
            return new GameObject(this);
        }
    }
}
