package com.main.guapogame.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class BegginStrip extends Snack {

    protected BegginStrip(Builder builder) {
        super(builder);
    }

    public static class Builder extends Snack.Builder {

        @Override
        public Builder positionX(int positionX) {
            super.positionX(positionX);
            return this;
        }

        @Override
        public Builder positionY(int positionY) {
            super.positionY(positionY);
            return this;
        }

        @Override
        public Builder snackImage(Bitmap snackImage) {
            super.snackImage(snackImage);
            return this;
        }

        @Override
        public Builder pointsForSnack(int pointsSnack) {
            super.pointsForSnack(pointsSnack);
            return this;
        }

        @Override
        public Builder resources(Resources resources) {
            super.resources(resources);
            return this;
        }

        @Override
        public BegginStrip build() {
            return new BegginStrip(this);
        }
    }
}
