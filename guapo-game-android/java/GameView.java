package com.main.guapogame;

import static android.content.Context.MODE_PRIVATE;
import static com.main.guapogame.Keys.GAME;
import static com.main.guapogame.Keys.LEVEL;
import static com.main.guapogame.Keys.LIVES;
import static com.main.guapogame.Keys.getKey;
import static com.main.guapogame.Parameters.FPS;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

public class GameView extends SurfaceView implements Runnable {
    private Thread thread;
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private final AppCompatActivity gameActivity;
    private final Model model;

    public GameView(AppCompatActivity activity) {
        super(activity);

        gameActivity = activity;
        model = new Model(activity, getResources());
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            handleClick(event);
            return true;
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            handleMove(event);
            return true;
        }

        return false;
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
            update();
            draw();
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
        return getBitmapScaled(
                (int) (screenFactorX / 3.0),
                (int) (screenFactorY / 3.0),
                R.drawable.pause_button_bitmap_cropped
        );
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
        if(gameIsPlaying() && !touchInPauseArea(event))
            updateVelocityHero(event);
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
        return (int) (screenWidth * 55.0 / 60.0 - width * 3.0 / 2.0);
    }

    private int getMaxYPauseRegion(Bitmap pauseButton) {
        return (int) (screenHeight * 5.0 / 30.0 + pauseButton.getHeight() / 2.0);
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
        if(getLives() < 0) {
            model.saveHighScore();
            transitionToActivity(LevelActivity.class);
        }
        if(getLives() >= 0) {
            model.saveHighScore();
            transitionToActivity(ContinueActivity.class);
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

    private int getLives() {
        return gameActivity.getSharedPreferences(GAME, MODE_PRIVATE).getInt(getKey(getLevelId(), LIVES), 0);
    }

    private String getLevelId() {
        return gameActivity.getSharedPreferences(GAME, MODE_PRIVATE).getString(LEVEL, "");
    }
}
