package com.main.guapogame.model;

import static com.main.guapogame.enums.State.PAUSED;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.main.guapogame.enums.Level;
import com.main.guapogame.model.graphics.gameobjects.Background;
import com.main.guapogame.model.graphics.gameobjects.Graphics;
import com.main.guapogame.model.graphics.gameobjects.Snack;
import com.main.guapogame.model.graphics.gameobjects.Villain;
import com.main.guapogame.model.interfaces.Draw;
import com.main.guapogame.state.GameScore;
import com.main.guapogame.state.GameState;

class ModelDraw implements Draw {
    private final Graphics graphics;

    protected ModelDraw(Builder builder) {
        this.graphics = builder.graphics;
    }

    static class Builder {
        Graphics graphics;

        Builder graphics(Graphics graphics) {
            this.graphics = graphics;
            return this;
        }

        ModelDraw build() {
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
        drawMisty(canvas);
        drawBrownie(canvas);
        drawFrito(canvas);
        drawPopUp(canvas);
        drawSunPopup(canvas);
    }

    private void drawPopUp(Canvas canvas) {
        graphics.getCheckpointPopup().draw(canvas);
    }

    private void drawSunPopup(Canvas canvas) {
        if(sunPopupInContext())
            graphics.getSunPopup().draw(canvas);
    }

    private boolean sunPopupInContext() {
        return GameState.getLevel().equals(Level.ARUBA) || GameState.getLevel().equals(Level.BEACH)
                || GameState.getLevel().equals(Level.TRIP);
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
        canvas.drawText(" " + GameScore.getScore(), 30, (float) getScreenHeight() / 6, paint);
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
            canvas.drawBitmap(
                    graphics.getPlayButton(),
                    (float) pauseRegionMaxX - graphics.getPlayButton().getWidth(),
                    pauseRegionMinY,
                    null);
        else
            canvas.drawBitmap(
                    graphics.getPauseButton(),
                    (float) pauseRegionMaxX - graphics.getPauseButton().getWidth(),
                    pauseRegionMinY,
                    null);
    }

    private boolean gameIsPaused() {
        return GameState.getGameState().equals(PAUSED);
    }

    private void drawHero(Canvas canvas) {
        graphics.getHero().draw(canvas);
    }

    private void drawMisty(Canvas canvas) {
        graphics.getMisty().draw(canvas);
    }

    private void drawBrownie(Canvas canvas) {
        graphics.getBrownie().draw(canvas);
    }

    private void drawFrito(Canvas canvas) {
        graphics.getFrito().draw(canvas);
    }
}
