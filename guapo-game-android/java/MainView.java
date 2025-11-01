package com.main.guapogame;

import static com.main.guapogame.Constants.FPS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainView extends SurfaceView implements Runnable {
    private Thread thread;
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private final Lives lives1 = new Lives();
    private AppCompatActivity gameActivity;
    private Model model;

    public MainView(AppCompatActivity activity, List<Background> backgrounds) {
        this(activity);

        gameActivity = activity;
        model = new Model(activity, getResources(), backgrounds);
    }

    public MainView(Context context) {
        super(context);

        Paint paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleClick(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleMove(event);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    @Override
    public void run() {
        runGame();
    }

    public void resume() {
        setGameStateToPlay();
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        try {
            setGameStateToGameOver();
            thread.join();
        } catch (InterruptedException _) {
        }
    }

    private void runGame() {
        while (!gameIsOver()) {
            long startTime = System.nanoTime();
            draw();
            update();
            makeThreadSleepWithDelay(getDelay(startTime));
        }

        handleGameOver();
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
        try{
            Thread.sleep(delay);
        } catch(Exception _) {}
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private Bitmap getPauseButton() {
        int screenFactorX = (int) (screenWidth / 10.0);
        int screenFactorY = (int) (screenHeight / 5.0);
        return getBitmapScaled((int) (screenFactorX / 3.0), (int) (screenFactorY / 3.0), R.drawable.pause_button_bitmap_cropped);
    }

    private int getLives() {
        return lives1.getLives();
    }

    private void setGameStateToPaused() {
        GameState.setGameStateToPaused();
    }

    private void setGameStateToPlay() {
        GameState.setGameStateToPlay();
    }

    private void setGameStateToGameOver() {
        GameState.setGameStateToGameOver();
    }

    private boolean gameIsOver() {
        return GameState.getGameState().equals(State.GAME_OVER);
    }

    private boolean gameIsPaused() {
        return GameState.getGameState().equals(State.PAUSED);
    }

    private boolean gameIsPlaying() {
        return GameState.getGameState().equals(State.PLAY);
    }

    private void handleClick(MotionEvent event) {
        handlePause(event);
        handleUpdatePosition(event);
    }

    private void handleMove(MotionEvent event) {
        if(gameIsPlaying() && !touchInPauseArea(event)) {
            updateVelocityHero(event);
        }
    }

    private void handlePause(MotionEvent event) {
        if(touchInPauseArea(event)) {
            if(gameIsPlaying()) {
                setGameStateToPaused();
            }
            else if(gameIsPaused()) {
                setGameStateToPlay();
            }
        }
    }

    private void handleUpdatePosition(MotionEvent event) {
        if(!touchInPauseArea(event) && gameIsPlaying()){
            updatePositionHero(event);
            setVelocityHeroToZero();
        }
    }

    private void updatePositionHero(MotionEvent event) {
        model.getHero().setPositionX(event.getX() - model.getHero().getWidth() / 2);
        model.getHero().setPositionY(event.getY() - model.getHero().getHeight() / 2);
    }

    private void updateVelocityHero(MotionEvent event) {
        float velX = event.getX() - model.getHero().getWidth() / 2 - model.getHero().getPositionX();
        float velY = event.getY() - model.getHero().getHeight() / 2 - model.getHero().getPositionY();
        model.getHero().setVelX(velX);
        model.getHero().setVelY(velY);
        setLowVelocitiesToZero();
    }

    private void setVelocityHeroToZero() {
        model.getHero().setVelX(0);
        model.getHero().setVelY(0);
    }

    private void setLowVelocitiesToZero() {
        float velX = model.getHero().getVelocityX();
        float velY = model.getHero().getVelocityY();

        if(velX * velX + velY * velY < 50) {
            model.getHero().setVelX(0);
            model.getHero().setVelY(0);
        }
    }

    private boolean touchInPauseArea(MotionEvent event) {
        return event.getX() >= getMinXPauseRegion(getPauseButton())
                && event.getY() <= getMaxYPauseRegion(getPauseButton())
                && event.getX() <= screenWidth
                && event.getY() >= 0;
    }

    private int getMinXPauseRegion(Bitmap pauseButton) {
        int width = pauseButton.getWidth();
        return (int) (screenWidth- width - 5.0 * screenWidth / 60.0 - width / 2.0);
    }

    private int getMaxYPauseRegion(Bitmap pauseButton) {
        return (int) (5.0 * screenHeight / 30.0 + pauseButton.getHeight() / 2.0);
    }
    
    private AppCompatActivity getGameActivity() {
        return gameActivity;
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

    private void handleGameOver() {
        if(getLives() <= 0) {
            model.saveHighScore();
            transitionToActivity(MainActivity.class);
        }
        if(getLives() > 0) {
            model.saveHighScore();
            transitionToActivity(LevelActivity.class);
        }
    }

    private <T extends AppCompatActivity> void transitionToActivity(Class<T> clazz) {
        try {
            Thread.sleep(1000);
            getGameActivity().startActivity(new Intent(getGameActivity(), clazz));
            getGameActivity().finish();
        } catch (Exception _) {
        }
    }
}
