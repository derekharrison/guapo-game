package com.main.guapogame.presentation.view;

import static com.main.guapogame.enums.State.GAME_OVER;
import static com.main.guapogame.enums.State.PAUSED;
import static com.main.guapogame.enums.State.PLAY;
import static com.main.guapogame.parameters.Parameters.FPS;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import com.main.guapogame.R;
import com.main.guapogame.model.Model;
import com.main.guapogame.state.GameState;

public class View extends SurfaceView implements Runnable {
    private final int screenWidth;
    private final int screenHeight;
    private final Model model;
    private final Thread gameThread = new Thread(this);

    public View(AppCompatActivity activity) {
        super(activity);

        model = new Model(activity);

        screenWidth = activity.getResources().getDisplayMetrics().widthPixels;
        screenHeight = activity.getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isClickGesture(event)) {
            handleClick(event);
            return true;
        }
        if(isMoveGesture(event)) {
            handleMove(event);
            return true;
        }
        if(isReleaseGesture(event)) {
            handleRelease();
            return true;
        }

        return false;
    }

    @Override
    public void run() {
        runGame();
    }

    public void startGame() {
        setGameStateToPlay();
        gameThread.start();
    }

    public void stopGame() {
        try {
            gameThread.join();
        } catch (InterruptedException _) {
            gameThread.interrupt();
        }
    }

    private void runGame() {
        while (!gameIsOver()) {
            long startTime = System.nanoTime();
            update();
            draw();
            makeThreadSleepWithDelay(getDelay(startTime));
        }

        model.handleGameOver();
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            model.draw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void update() {
        if(gameIsPlaying()) {
            model.update();
        }
    }

    private void makeThreadSleepWithDelay(long delay) {
        Thread thread = new Thread();
        thread.start();
        try{
            Thread.sleep(delay);
        } catch(Exception _) {
            thread.interrupt();
        }
    }

    private Bitmap getPauseButton(int scaleX, int scaleY) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button_bitmap_cropped);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private boolean isClickGesture(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }

    private boolean isMoveGesture(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_MOVE;
    }

    private boolean isReleaseGesture(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_UP;
    }

    private Bitmap getPauseButton() {
        int screenFactorX = (int) (screenWidth / 10.0);
        int screenFactorY = (int) (screenHeight / 5.0);
        return getPauseButton(
                (int) (screenFactorX / 3.0),
                (int) (screenFactorY / 3.0)
        );
    }

    private void setGameStateToPaused() {
        GameState.setGameStateToPaused();
    }

    private void setGameStateToPlay() {
        GameState.setGameStateToPlay();
    }

    private boolean gameIsOver() {
        return GameState.getGameState().equals(GAME_OVER);
    }

    private boolean gameIsPaused() {
        return GameState.getGameState().equals(PAUSED);
    }

    private boolean gameIsPlaying() {
        return GameState.getGameState().equals(PLAY);
    }

    private void handleClick(MotionEvent event) {
        handlePause(event);
        if(heroUpdatesAllowed())
            handleUpdatePosition(event);
    }

    private void handleMove(MotionEvent event) {
        stopHeroUpdates();
        handleUpdatePosition(event);
    }

    private void allowHeroUpdates() {
        model.getHero().setAllowUpdate(true);
    }

    private void stopHeroUpdates() {
        model.getHero().setAllowUpdate(false);
    }

    private boolean heroUpdatesAllowed() {
        return model.getHero().getAllowUpdate();
    }

    private void handleRelease() {
        allowHeroUpdates();
    }

    private void handlePause(MotionEvent event) {
        if(touchInPauseArea(event)) {
            if(gameIsPlaying())
                setGameStateToPaused();

            else if(gameIsPaused())
                setGameStateToPlay();
        }
    }

    private void handleUpdatePosition(MotionEvent event) {
        if(!touchInPauseArea(event) && gameIsPlaying())
            updatePositionHero(event);
    }

    private void updatePositionHero(MotionEvent event) {
        model.getHero().setPositionX(event.getX() - model.getHero().getWidth() / 2);
        model.getHero().setPositionY(event.getY() - model.getHero().getHeight() / 2);
    }

    private boolean touchInPauseArea(MotionEvent event) {
        return event.getX() >= getMinXPauseRegion(getPauseButton())
                && event.getY() <= getMaxYPauseRegion(getPauseButton())
                && event.getX() <= screenWidth
                && event.getY() >= 0;
    }

    private int getMinXPauseRegion(Bitmap pauseButton) {
        int width = pauseButton.getWidth();
        return (int) (screenWidth * 55.0 / 60.0 - width * 3.0 / 2.0);
    }

    private int getMaxYPauseRegion(Bitmap pauseButton) {
        return (int) (screenHeight * 5.0 / 30.0 + pauseButton.getHeight() / 2.0);
    }

    private long getDelay(long startTime) {
        return getScreenTime() - getCurrentTimeLapse(startTime);
    }

    private long getCurrentTimeLapse(long startTime) {
        return (System.nanoTime() - startTime) / 1000000;
    }

    private long getScreenTime() {
        return 1000 / FPS;
    }
}
