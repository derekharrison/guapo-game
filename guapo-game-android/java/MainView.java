package com.main.guapogame;

import static com.main.guapogame.Constants.FPS;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

// TODO : create characters
// TODO : create beggin strip
// TODO : implement checkpoints

public class MainView extends SurfaceView implements Runnable {

    private Thread thread;
    private int pauseRegionMinX;
    private int pauseRegionMaxY;
    private Bitmap pauseButton;
    private SharedPreferences prefs;
    private final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private final Lives lives1 = new Lives();


    private AppCompatActivity gameActivity;

    private Model model;

    public MainView(AppCompatActivity activity, LinkedList<Background> backgrounds) {
        this(activity);

        gameActivity = activity;
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        model = new Model(activity, getResources(), backgrounds);

        createPauseAndPlayButtons();
        getPauseRegion();
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
                handlePause(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleMove(event);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    private void createPauseAndPlayButtons() {
        int screenFactorX = (int) (screenWidth / 10.0);
        int screenFactorY = (int) (screenHeight / 5.0);
        pauseButton = getBitmapScaled((int) (screenFactorX / 3.0), (int) (screenFactorY / 3.0), R.drawable.pause_button_bitmap_cropped);
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private void getPauseRegion() {
        int pauseRegionMaxX = screenWidth - screenWidth / 30;
        pauseRegionMinX = pauseRegionMaxX - pauseButton.getWidth() - 3 * screenWidth / 60;
        pauseRegionMaxY = 5 * screenHeight / 30;
    }

    protected void saveHighScore() {
        int score = 0;
        if (prefs.getInt("high_score", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("high_score", score);
            editor.apply();
        }
    }

    protected int getLives() {
        return lives1.getLives();
    }

    protected void setGameStateToPaused() {
        GameState.setGameStateToPaused();
    }

    protected void setGameStateToPlay() {
        GameState.setGameStateToPlay();
    }

    protected void setGameStateToGameOver() {
        GameState.setGameStateToGameOver();
    }

    protected boolean gameIsOver() {
        return GameState.getGameState().equals(State.GAME_OVER);
    }

    protected boolean gameIsPaused() {
        return GameState.getGameState().equals(State.PAUSED);
    }

    protected boolean gameIsPlaying() {
        return GameState.getGameState().equals(State.PLAY);
    }

    private void handleClick(MotionEvent event) {
        if(!touchInPauseArea(event) && gameIsPlaying()){
            updatePositionHero(event);
            setVelocityHeroToZero();
        }
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

    private void updatePositionHero(MotionEvent event) {
        model.getHero().setPositionX(event.getX());
        model.getHero().setPositionY(event.getY());
    }

    private void updateVelocityHero(MotionEvent event) {
        float velX = event.getX() - model.getHero().getPositionX();
        float velY = event.getY() - model.getHero().getPositionY();
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

        if(velX * velX + velY * velY < 25) {
            model.getHero().setVelX(0);
            model.getHero().setVelY(0);
        }
    }

    private boolean touchInPauseArea(MotionEvent event) {
        return (event.getX() >= pauseRegionMinX - pauseButton.getWidth() / 2.0)
                && (event.getX() <= screenWidth) && (event.getY() >= 0)
                && (event.getY() <=  (float) (pauseRegionMaxY + pauseButton.getHeight() / 2));
    }

    @Override
    public void run() {
        runGame();
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

    private AppCompatActivity getGameActivity() {
        return gameActivity;
    }

    private void makeThreadSleepWithDelay(long delay) {
        try{
            Thread.sleep(delay);
        } catch(Exception _) {}
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

    private void handleGameOver() {
        if(getLives() <= 0) {
            saveHighScore();
            transitionToActivity(MainActivity.class);
        }
        if(getLives() > 0) {
            saveHighScore();
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
}
