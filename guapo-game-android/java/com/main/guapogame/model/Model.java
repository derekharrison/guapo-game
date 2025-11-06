package com.main.guapogame.model;

import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.HIGH_SCORE;
import static com.main.guapogame.definitions.Keys.getKey;
import static com.main.guapogame.definitions.Parameters.CHECK_POINT_INTERVAL;
import static com.main.guapogame.definitions.Parameters.setBackgroundSpeed;
import static com.main.guapogame.definitions.Parameters.setScreenHeight;
import static com.main.guapogame.definitions.Parameters.setScreenWidth;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.main.guapogame.definitions.Parameters;
import com.main.guapogame.graphics.Popup;
import com.main.guapogame.graphics.Snack;
import com.main.guapogame.resources.Sounds;
import com.main.guapogame.graphics.Villain;
import com.main.guapogame.graphics.GraphicObjects;
import com.main.guapogame.graphics.Hero;
import com.main.guapogame.graphics.Background;
import com.main.guapogame.storage.Storage;

public class Model {
    private GraphicObjects graphicObjects;
    private int difficultyLevel = 0;
    private int score = 0;
    private Sounds sounds;
    private final Resources resources;
    private final Storage storage;
    private final Context context;
    private int checkpoint = 0;

    public Model(Context context, Resources resources) {
        this.resources = resources;
        this.context = context;
        this.storage = new Storage(context);

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
        return graphicObjects.getHero();
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
        createGameObjects();

        getScore();
        getCheckpoint();
    }

    private void createGameObjects() {
        graphicObjects = new GraphicObjectsBuilder()
                .storage(storage)
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
            graphicObjects.setCheckpointPopup(createCheckpointPopup());
            graphicObjects.getCheckpointPopup().playSoundCheckpoint(sounds);
        }

        graphicObjects.getCheckpointPopup().update();
    }

    private Popup createCheckpointPopup() {
        return new CheckpointPopupBuilder().resources(resources).build();
    }

    private boolean isNotFirstCheckpoint() {
        return checkpoint > 0;
    }


    private void drawPopUp(Canvas canvas) {
        graphicObjects.getCheckpointPopup().draw(canvas);
    }

    private void save() {
        storage.saveGame().saveHero(graphicObjects.getHero());
        storage.saveGame().saveSnacks(graphicObjects.getSnacks());
        storage.saveGame().saveVillains(graphicObjects.getVillains());
        storage.saveGame().saveBackgrounds(graphicObjects.getBackgrounds());
        storage.saveGame().saveScore(score);
        storage.saveGame().saveNumLives(graphicObjects.getLives().size());
        storage.saveGame().saveCheckpoint(checkpoint);
    }

    private void getCheckpoint() {
        if(isActiveSession())
            checkpoint = storage.loadGame().getCheckpoint();
    }

    private void getScore() {
        if(isActiveSession())
            score = storage.loadGame().getScore();
    }

    private boolean reachedCheckpoint() {
        return score >= checkpoint * CHECK_POINT_INTERVAL;
    }

    private void advanceCheckpoint() {
        checkpoint++;
    }

    private void updateSnack(Snack snack) {
        snack.update();
        if(heroInteractsWithSnack(graphicObjects.getHero(), snack)) {
            score += snack.getPointsForSnack();
            snack.setPositionX(-500);
            snack.playSoundEat(sounds);
        }
    }

    private void updateHero() {
        graphicObjects.getHero().update();
    }

    private void updateBackground() {
        int backgroundId = 1;
        for(Background background : graphicObjects.getBackgrounds()) {
            background.update();
            if(background.getPositionX() <= 0) {
                graphicObjects.getBackgrounds()
                        .get(getFollowingBackground(backgroundId))
                        .setPositionX(background.getPositionX() + background.getBackground().getWidth() - 10);
            }
            backgroundId++;
        }
    }

    private void updateVillains() {
        updateNumberOfVillains();
        for(Villain villain : graphicObjects.getVillains()) {
            villain.update();
            if(heroInteractsWithVillain(graphicObjects.getHero(), villain)) {
                graphicObjects.getHero().playSoundInteractingWithVillain(sounds);
                setGameStateToGameOver();
                storage.saveGame().saveNumLives(graphicObjects.getLives().size() - 1);
            }
        }
    }

    private void updateSnacks() {
        for(Snack snack : graphicObjects.getSnacks())
            updateSnack(snack);
    }

    private void updateNumberOfVillains() {
        if (timeToIncreaseVillains()) {
            addVillain(createVillain());
            advanceDifficultyLevel();
        }
    }

    private Villain createVillain() {
        return new VillainBuilder()
                .storage(storage)
                .resources(resources)
                .context(context)
                .build();
    }

    private void addVillain(Villain villain) {
        this.graphicObjects.getVillains().add(villain);
    }

    private void advanceDifficultyLevel() {
        difficultyLevel++;
    }

    private boolean lessThanMaxVillains() {
        return graphicObjects.getVillains().size() < Parameters.MAX_VILLAINS;
    }
    private boolean timeToIncreaseVillains() {
        return score >= difficultyLevel * Parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL && lessThanMaxVillains();
    }

    private void drawBackgrounds(Canvas canvas) {
        for(Background background : graphicObjects.getBackgrounds())
            background.draw(canvas);
    }

    private void drawSnacks(Canvas canvas) {
        for (Snack snack : graphicObjects.getSnacks())
            snack.draw(canvas);
    }

    private void drawScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
        canvas.drawText(" " + score, 30, (float) getScreenHeight() / 6, paint);
    }

    private void drawVillains(Canvas canvas) {
        for(Villain villain : graphicObjects.getVillains())
            villain.draw(canvas);
    }

    private void drawLives(Canvas canvas) {
        int lifeLocation = getScreenWidth() / 2 - 20;
        for (Bitmap life : graphicObjects.getLives()) {
            canvas.drawBitmap(life, lifeLocation, 20, null);
            lifeLocation += life.getWidth() + 5;
        }
    }

    private void drawPauseButton(Canvas canvas) {
        int pauseRegionMaxX = getScreenWidth() - getScreenWidth() / 30;
        int pauseRegionMinY = getScreenHeight() / 15;

        if(gameIsPaused())
            canvas.drawBitmap(graphicObjects.getPlayButton(), pauseRegionMaxX - graphicObjects.getPlayButton().getWidth(), pauseRegionMinY, null);
        else
            canvas.drawBitmap(graphicObjects.getPauseButton(), pauseRegionMaxX - graphicObjects.getPauseButton().getWidth(), pauseRegionMinY, null);
    }

    private void drawHero(Canvas canvas) {
        graphicObjects.getHero().draw(canvas);
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

    private boolean isActiveSession() {
        return getSharedPreferences().getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private void getScreenParameters() {
        int backgroundSpeed = (int) (getScreenWidth() / 400.0);

        setScreenWidth(getScreenWidth());
        setScreenHeight(getScreenHeight());
        setBackgroundSpeed(backgroundSpeed);
    }

    private Resources getResources() {
        return resources;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GAME, Context.MODE_PRIVATE);
    }

    private void setGameStateToGameOver() {
        State.setGameStateToGameOver();
    }

    private boolean gameIsPaused() {
        return State.getGameState().equals(com.main.guapogame.types.State.PAUSED);
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
        return id % graphicObjects.getBackgrounds().size();
    }

    private class SaveState implements Runnable {

        @Override
        public void run() {
            save();
        }
    }
}
