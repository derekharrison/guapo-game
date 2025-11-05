package com.main.guapogame;

import static com.main.guapogame.Keys.GAME;
import static com.main.guapogame.Parameters.FPS;
import static com.main.guapogame.Keys.BEACH;
import static com.main.guapogame.Keys.GAMESTATE;
import static com.main.guapogame.Keys.LEVEL;
import static com.main.guapogame.Keys.OCEAN;
import static com.main.guapogame.Keys.HIGH_SCORE;
import static com.main.guapogame.Keys.getKey;
import static com.main.guapogame.Parameters.CHECK_POINT_INTERVAL;
import static com.main.guapogame.Parameters.setBackgroundSpeed;
import static com.main.guapogame.Parameters.setScreenHeight;
import static com.main.guapogame.Parameters.setScreenWidth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private GameObjects gameObjects;
    private final List<Bitmap> lives = new ArrayList<>();
    private Bitmap playButton;
    private Bitmap pauseButton;
    private int difficultyLevel = 0;
    private int score = 0;
    private Sounds sounds;
    private final Resources resources;
    private final GameState gameState;
    private final Context context;
    private int checkpoint = 0;

    public Model(Context context, Resources resources) {
        this.resources = resources;
        this.context = context;
        this.gameState = new GameState(context);

        createModelData();
    }

    public void saveHighScore() {
        String levelId = getLevelId();
        if (getHighScore(levelId) < score) {
            SharedPreferences.Editor editor = getSharedPreferences().edit();
            editor.putInt(getKey(levelId, HIGH_SCORE), score);
            editor.apply();
        }
    }

    public void update() {
        updateBackground();
        updateVillains();
        updateHero();
        updateSnacks();
        updateCheckpointPopup();
        saveGame();
    }

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

    public Hero getHero() {
        return gameObjects.getHero();
    }

    private void saveGame() {
        if(reachedCheckpoint()) {
            Thread thread = new Thread(new SaveState());
            thread.start();
            advanceCheckpoint();
        }
    }

    private void createModelData() {
        getSharedPreferences();
        getScreenParameters();

        createSounds();
        createPauseAndPlayButtons();
        createLives();
        createGameObjects();

        getScore();
        getCheckpoint();
    }

    private void createGameObjects() {
        gameObjects = new GameObjectsBuilder()
                .gameState(gameState)
                .context(context)
                .resources(getResources())
                .build();
    }
    
    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private int getHighScore(String levelId) {
        return getSharedPreferences().getInt(getKey(levelId, HIGH_SCORE), 0);
    }

    private void updateCheckpointPopup() {
        if(reachedCheckpoint() && isNotFirstCheckpoint()) {
            gameObjects.setCheckpointPopup(createCheckpointPopup());
            gameObjects.getCheckpointPopup().playSoundCheckpoint(sounds);
        }

        gameObjects.getCheckpointPopup().update();
    }

    private Popup createCheckpointPopup() {
        int width = (int) (getScreenWidth() / 10.0);
        int height = (int) (getScreenHeight() / 5.0);
        Bitmap image = getBitmapScaled(width, height, R.drawable.flag_aruba_bitmap_cropped);
        return new Popup.Builder()
                .duration(2 * FPS)
                .positionX(getScreenWidth() - getScreenWidth() / 4)
                .positionY(image.getHeight() + getScreenHeight() / 10)
                .image(image)
                .build();
    }

    private boolean isNotFirstCheckpoint() {
        return checkpoint > 0;
    }


    private void drawPopUp(Canvas canvas) {
        gameObjects.getCheckpointPopup().draw(canvas);
    }

    private void save() {
        gameState.saveGame().saveHero(gameObjects.getHero());
        gameState.saveGame().saveSnacks(gameObjects.getSnacks());
        gameState.saveGame().saveVillains(gameObjects.getVillains());
        gameState.saveGame().saveBackgrounds(gameObjects.getBackgrounds());
        gameState.saveGame().saveScore(score);
        gameState.saveGame().saveNumLives(lives.size());
        gameState.saveGame().saveCheckpoint(checkpoint);
    }

    private void getCheckpoint() {
        if(isActiveSession()) {
            checkpoint = gameState.loadGame().getCheckpoint();
        }
    }

    private void getScore() {
        if(isActiveSession()) {
            score = gameState.loadGame().getScore();
        }
    }

    private boolean reachedCheckpoint() {
        return score >= checkpoint * CHECK_POINT_INTERVAL;
    }

    private void advanceCheckpoint() {
        checkpoint++;
    }

    private void updateSnack(Snack snack) {
        snack.update();
        if(heroInteractsWithSnack(gameObjects.getHero(), snack)) {
            score += snack.getPointsForSnack();
            snack.setPositionX(-500);
            snack.playSoundEat(sounds);
        }
    }

    private void updateHero() {
        gameObjects.getHero().update();
    }

    private void updateBackground() {
        int backgroundId = 1;
        for(Background background : gameObjects.getBackgrounds()) {
            background.update();
            if(background.getPositionX() <= 0) {
                gameObjects.getBackgrounds()
                        .get(getFollowingBackground(backgroundId))
                        .setPositionX(background.getPositionX() + background.getBackground().getWidth() - 10);
            }
            backgroundId++;
        }
    }

    private void updateVillains() {
        updateNumberOfVillains();
        for(Villain villain : gameObjects.getVillains()) {
            villain.update();
            if(heroInteractsWithVillain(gameObjects.getHero(), villain)) {
                gameObjects.getHero().playSoundInteractingWithVillain(sounds);
                setGameStateToGameOver();
                gameState.saveGame().saveNumLives(lives.size() - 1);
            }
        }
    }

    private void updateSnacks() {
        for(Snack snack : gameObjects.getSnacks()) {
            updateSnack(snack);
        }
    }

    private void updateNumberOfVillains() {
        if (timeToIncreaseVillains()) {
            addVillain(createVillain());
            advanceDifficultyLevel();
        }
    }

    private Villain createVillain() {
        return new Villain.Builder()
                .positionX(-500)
                .images(createVillainImages())
                .build();
    }

    private List<Bitmap> createVillainImages() {
        if(getLevelId().equals(BEACH) || getLevelId().equals(OCEAN))
            return createSeagullImages();

        return createWaraWaraImages();
    }

    private List<Bitmap> createSeagullImages() {
        List<Bitmap> images = new ArrayList<>();

        int width =  (int) (((float) getScreenWidth()) / 10);
        int height = (int) (((float) getScreenHeight()) / 5);

        Bitmap bird_image1 = getBitmapScaled(width, height, R.drawable.seagull1_bitmap_cropped_new);
        Bitmap bird_image2 = getBitmapScaled(width, height, R.drawable.seagull2_bitmap_cropped_new);
        Bitmap bird_image3 = getBitmapScaled(width, height, R.drawable.seagull3_bitmap_cropped_new);

        images.add(bird_image1);
        images.add(bird_image2);
        images.add(bird_image3);

        return images;
    }

    private List<Bitmap> createWaraWaraImages() {
        List<Bitmap> images = new ArrayList<>();

        int width =  (int) (((float) getScreenWidth()) / 10);
        int height = (int) (((float) getScreenHeight()) / 5);

        Bitmap bird_image1 = getBitmapScaled(width, height, R.drawable.warawara1_bitmap_custom_mod_cropped);
        Bitmap bird_image2 = getBitmapScaled(width, height, R.drawable.warawara2_bitmap_custom_mod_cropped);
        Bitmap bird_image3 = getBitmapScaled(width, height, R.drawable.warawara3_bitmap_custom_mod_cropped);

        images.add(bird_image1);
        images.add(bird_image2);
        images.add(bird_image3);

        return images;
    }

    private void addVillain(Villain villain) {
        this.gameObjects.getVillains().add(villain);
    }

    private void advanceDifficultyLevel() {
        difficultyLevel++;
    }

    private boolean lessThanMaxVillains() {
        return gameObjects.getVillains().size() < Parameters.MAX_VILLAINS;
    }
    private boolean timeToIncreaseVillains() {
        return score >= difficultyLevel * Parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL && lessThanMaxVillains();
    }

    private void drawBackgrounds(Canvas canvas) {
        for(Background background : gameObjects.getBackgrounds()) {
            background.draw(canvas);
        }
    }

    private void drawSnacks(Canvas canvas) {
        for (Snack snack : gameObjects.getSnacks()) {
            snack.draw(canvas);
        }
    }

    private void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
        canvas.drawText(" " + score, 30, (float) getScreenHeight() / 6, paint);
    }

    private void drawVillains(Canvas canvas) {
        for(Villain villain : gameObjects.getVillains()) {
            villain.draw(canvas);
        }
    }

    private void drawLives(Canvas canvas) {
        int lifeLocation = getScreenWidth() / 2 - 20;
        for (Bitmap life : lives) {
            canvas.drawBitmap(life, lifeLocation, 20, null);
            lifeLocation += life.getWidth() + 5;
        }
    }

    private void drawPauseButton(Canvas canvas) {
        int pauseRegionMaxX = getScreenWidth() - getScreenWidth() / 30;
        int pauseRegionMinY = getScreenHeight() / 15;
        if(gameIsPaused()) {
            canvas.drawBitmap(playButton, pauseRegionMaxX - playButton.getWidth(), pauseRegionMinY, null);
        }
        else {
            canvas.drawBitmap(pauseButton, pauseRegionMaxX - pauseButton.getWidth(), pauseRegionMinY, null);
        }
    }

    private void drawHero(Canvas canvas) {
        gameObjects.getHero().draw(canvas);
    }

    private boolean heroInteractsWithVillain(Hero hero, Villain villain) {
        Rect heroArea = getHeroArea(hero.getImage(), (int) hero.getPositionX(),
                (int) hero.getPositionY());
        Rect villainArea = getVillainArea(villain.getImage(), (int) villain.getPositionX(),
                (int) villain.getPositionY());

        return Rect.intersects(heroArea, villainArea);
    }

    private boolean heroInteractsWithSnack(Hero hero, Snack snack) {
        Rect heroArea = getHeroArea(hero.getImage(), (int) hero.getPositionX(),
                (int) hero.getPositionY());
        Rect snackArea = getSnackArea(snack.getImage(), (int) snack.getPositionX(),
                (int) snack.getPositionY());

        return Rect.intersects(heroArea, snackArea);
    }




    private String getLevelId() {
        return getSharedPreferences().getString(LEVEL, "");
    }



    private void createSounds() {
        sounds = new Sounds(context);
    }

    private void createLives() {
        for(int id = 0; id < getNumLives(); id++) {
            Bitmap life = getBitmapScaled(
                    getScreenFactorX() / 4,
                    getScreenFactorY() / 4,
                    R.drawable.heart1_bitmap_cropped);
            lives.add(life);
        }
    }

    private int getNumLives() {
        if(isActiveSession()) {
            return gameState.loadGame().getNumLives();
        }

        return Parameters.NUM_LIVES;
    }

    private void createPauseAndPlayButtons() {
        playButton = getBitmapScaled((int) (getScreenFactorX() / 3.0), (int) (getScreenFactorY() / 3.0), R.drawable.play_button_bitmap_cropped);
        pauseButton = getBitmapScaled((int) (getScreenFactorX() / 3.0), (int) (getScreenFactorY() / 3.0), R.drawable.pause_button_bitmap_cropped);
    }


    private boolean isActiveSession() {
        return getSharedPreferences().getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private void getScreenParameters() {
        int backgroundSpeed = (int) (getScreenWidth() / 400.0);

        setScreenWidth(getScreenWidth());
        setScreenHeight(getScreenHeight());
        setBackgroundSpeed(backgroundSpeed);
    }
    
    private int getScreenFactorX() {
        return (int) (getScreenWidth() / 10.0);
    }

    private int getScreenFactorY() {
        return (int) (getScreenHeight() / 5.0);
    }

    private Resources getResources() {
        return resources;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GAME, Context.MODE_PRIVATE);
    }

    private void setGameStateToGameOver() {
        GameState.setGameStateToGameOver();
    }

    private boolean gameIsPaused() {
        return GameState.getGameState().equals(State.PAUSED);
    }

    private Rect getHeroArea(Bitmap image, int x, int y) {
        return new Rect(x, y, x + image.getWidth(), y + image.getHeight());
    }

    private Rect getVillainArea(Bitmap image, int x, int y) {
        return new Rect(
                (int) (x + image.getWidth() * 45.0 / 100.0),
                (int) (y + image.getHeight() * 45.0 / 100.0),
                (int) (x + image.getWidth() * 55.0 / 100.0),
                (int) (y + image.getHeight() * 55.0 / 100.0)
        );
    }

    private Rect getSnackArea(Bitmap image, int x, int y) {
        return new Rect(
                x + image.getWidth(),
                y + image.getHeight(),
                x + image.getWidth(),
                y + image.getHeight()
        );
    }

    private int getFollowingBackground(int id) {
        return id % gameObjects.getBackgrounds().size();
    }

    private class SaveState implements Runnable {

        @Override
        public void run() {
            save();
        }
    }
}
