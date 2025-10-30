package com.main.guapogame;

import static com.main.guapogame.Constants.FPS;
import static com.main.guapogame.Constants.START_NUM_OF_VILLAINS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.AttributeSet;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

// TODO : save game state
// TODO : support continue active session
// TODO : take life
// TODO : handle game is over
// TODO : handle game restart

public class GameViewLevel1 extends MainView implements Runnable {
    private Thread thread;
    private LinkedList<Background> backgrounds;
    private GameActivityLevel1 gameActivity;

    public GameViewLevel1(GameActivityLevel1 activity) {
        super(activity);
        gameActivity = activity;

        createVillains();
        createBackgrounds();
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

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            drawBackgrounds(canvas);
            drawLives(canvas);
            drawAll(canvas);
            drawVillains(canvas);
            drawHero(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void drawBackgrounds(Canvas canvas) {
        for(Background background : backgrounds) {
            background.draw(canvas);
        }
    }

    private void update() {
        if(gameIsPlaying()) {
            updateBackground();
            updateVillains();
            updateHero();
            updateSnacks();

            if (heroHitVillain()) {
                setGameStateToGameOver();
            }
        }
    }

    private void updateBackground() {
        int backgroundId = 1;
        for(Background background : backgrounds) {
            background.update();
            if(background.getPositionX() <= 0) {
                backgrounds
                        .get(getFollowingBackground(backgroundId))
                        .setPositionX(background.getPositionX() + background.getBackground().getWidth() - 10);
            }
            backgroundId++;
        }
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

    private void createVillains() {
        for(int villain = 0; villain < START_NUM_OF_VILLAINS; villain++) {
            addVillain(
                    new Villain.Builder()
                            .x(-500)
                            .images(createVillainImages())
                            .build()
            );
        }
    }

    private int getFollowingBackground(int id) {
        return id % backgrounds.size();
    }

    private void handleGameOver() {
        if(getLives() <= 0) {
            saveHighScore("high_score");
            transitionToActivity(MainActivity.class);
        }
        if(getLives() > 0) {
            saveHighScore("high_score");
            transitionToActivity(LevelActivity.class);
        }
    }

    private void createBackgrounds() {
        backgrounds = new LinkedList<>();
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr1));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr2));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr3));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr4));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr5));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr6));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr7));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr8));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr9));
        backgrounds.add(createBackground(R.drawable.background_guapo_game_nr10));
    }

    private Background createBackground(int backgroundId) {
        return new Background.Builder()
                .positionX(0)
                .velocityX(-getBackgroundSpeed())
                .background(getBitmapScaled(getScreenWidth(), getScreenHeight(), backgroundId))
                .build();
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

    private GameActivityLevel1 getGameActivity() {
        return gameActivity;
    }
}
