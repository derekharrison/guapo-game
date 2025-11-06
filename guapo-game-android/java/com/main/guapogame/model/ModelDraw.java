package com.main.guapogame.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.main.guapogame.graphics.Background;
import com.main.guapogame.graphics.GraphicObjects;
import com.main.guapogame.graphics.Snack;
import com.main.guapogame.graphics.Villain;
import com.main.guapogame.interfaces.Draw;

public class ModelDraw implements Draw {

    GraphicObjects graphics;

    protected ModelDraw(Builder builder) {
        this.graphics = builder.graphics;
    }

    public static class Builder {
        GraphicObjects graphics;

        public Builder graphics(GraphicObjects graphics) {
            this.graphics = graphics;
            return this;
        }

        public ModelDraw build() {
            return new ModelDraw(this);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        drawBackgrounds(canvas);
        drawLives(canvas);
        drawSnacks(canvas);
        drawScore(canvas);
        drawPauseButton(canvas);
        drawVillains(canvas);
        drawHero(canvas);
        drawPopUp(canvas);
    }

    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void drawPopUp(Canvas canvas) {
        graphics.getCheckpointPopup().draw(canvas);
    }

    private void drawBackgrounds(Canvas canvas) {
        for(Background background : graphics.getBackgrounds())
            background.draw(canvas);
    }

    private void drawSnacks(Canvas canvas) {
        for (Snack snack : graphics.getSnacks())
            snack.draw(canvas);
    }

    private void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
        canvas.drawText(" " + GameScore.score, 30, (float) getScreenHeight() / 6, paint);
    }

    private void drawVillains(Canvas canvas) {
        for(Villain villain : graphics.getVillains())
            villain.draw(canvas);
    }

    private void drawLives(Canvas canvas) {
        int lifeLocation = getScreenWidth() / 2 - 20;
        for (Bitmap life : graphics.getLives()) {
            canvas.drawBitmap(life, lifeLocation, 20, null);
            lifeLocation += life.getWidth() + 5;
        }
    }

    private void drawPauseButton(Canvas canvas) {
        int pauseRegionMaxX = getScreenWidth() - getScreenWidth() / 30;
        int pauseRegionMinY = getScreenHeight() / 15;

        if(gameIsPaused())
            canvas.drawBitmap(graphics.getPlayButton(), pauseRegionMaxX - graphics.getPlayButton().getWidth(), pauseRegionMinY, null);
        else
            canvas.drawBitmap(graphics.getPauseButton(), pauseRegionMaxX - graphics.getPauseButton().getWidth(), pauseRegionMinY, null);
    }

    private boolean gameIsPaused() {
        return GameState.getGameState().equals(com.main.guapogame.types.State.PAUSED);
    }

    private void drawHero(Canvas canvas) {
        graphics.getHero().draw(canvas);
    }

}
