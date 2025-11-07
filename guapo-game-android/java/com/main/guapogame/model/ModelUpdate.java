package com.main.guapogame.model;

import static com.main.guapogame.definitions.Keys.ARUBA;
import static com.main.guapogame.definitions.Keys.BEACH;
import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.OCEAN;
import static com.main.guapogame.definitions.Keys.TRIP;
import static com.main.guapogame.definitions.Keys.UTREG;
import static com.main.guapogame.definitions.Keys.getKey;
import static com.main.guapogame.definitions.Parameters.CHECK_POINT_INTERVAL;
import static com.main.guapogame.definitions.Parameters.FPS;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.main.guapogame.definitions.Parameters;
import com.main.guapogame.graphics.Background;
import com.main.guapogame.graphics.Bubble;
import com.main.guapogame.graphics.Graphics;
import com.main.guapogame.graphics.Hero;
import com.main.guapogame.graphics.Popup;
import com.main.guapogame.graphics.Snack;
import com.main.guapogame.graphics.Villain;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.resources.Sounds;
import com.main.guapogame.storage.Storage;
import com.main.guapogame.types.Level;

public class ModelUpdate implements Update {
    private int difficultyLevel = 0;
    private final Graphics graphics;
    private final Storage storage;
    private final Context context;
    private final Resources resources;
    private int checkpoint = 0;
    private int frameCounter = 0;

    protected ModelUpdate(Builder builder) {
        this.graphics = builder.graphics;
        this.context = builder.context;
        this.resources = builder.resources;
        this.storage = builder.storage;
        
        createSounds();
        getCheckpoint();
        getScore();
        getLevel();
    }

    @Override
    public void update() {
        updateBackground();
        updateVillains();
        updateHero();
        updateSnacks();
        updateCheckpointPopup();
        updateBubbles();
        updateFrameCounter();
        saveGame();
    }

    public static class Builder {
        private Graphics graphics;
        private Storage storage;
        private Context context;
        private Resources resources;

        public Builder graphics(Graphics graphics) {
            this.graphics = graphics;
            return this;
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder resources(Resources resources) {
            this.resources = resources;
            return this;
        }

        public Builder storage(Storage storage) {
            this.storage = storage;
            return this;
        }

        public ModelUpdate build() {
            return new ModelUpdate(this);
        }
    }

    private void saveGame() {
        if(reachedCheckpoint()) {
            Thread thread = new Thread(new SaveState());
            thread.start();
            advanceCheckpoint();
        }
    }

    private void getCheckpoint() {
        if(isActiveSession())
            checkpoint = storage.loadGame().getCheckpoint();
    }

    private void getScore() {
        if(isActiveSession())
            GameScore.score = storage.loadGame().getScore();
    }

    private void getLevel() {
        if(getLevelId().equals(ARUBA))
            GameState.setLevel(Level.ARUBA);

        if(getLevelId().equals(BEACH))
            GameState.setLevel(Level.BEACH);

        if(getLevelId().equals(OCEAN))
            GameState.setLevel(Level.OCEAN);

        if(getLevelId().equals(TRIP))
            GameState.setLevel(Level.TRIP);

        if(getLevelId().equals(UTREG))
            GameState.setLevel(Level.UTREG);
    }

    private boolean isActiveSession() {
        return getSharedPreferences().getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private String getLevelId() {
        return getSharedPreferences().getString(LEVEL, "");
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GAME, Context.MODE_PRIVATE);
    }
    private void advanceCheckpoint() {
        checkpoint++;
    }

    private void updateCheckpointPopup() {
        if(reachedCheckpoint() && isNotFirstCheckpoint()) {
            graphics.setCheckpointPopup(createCheckpointPopup());
            graphics.getCheckpointPopup().playSound();
        }

        graphics.getCheckpointPopup().update();
    }

    private void updateBubbles() {
        if(GameState.getLevel().equals(Level.OCEAN)) {
            if((frameCounter % (6 * FPS)) == 0) {
                if(!graphics.getBubbles().isEmpty() && graphics.getBubbles().size() > 2)
                    graphics.getBubbles().removeLast();

                Bubble bubble = new BubbleBuilder()
                        .resources(resources)
                        .positionX((int) graphics.getHero().getPositionX())
                        .positionY((int) graphics.getHero().getPositionY())
                        .build();

                bubble.playSound();
                graphics.addBubble(bubble);
            }

            for(Bubble bubble : graphics.getBubbles()) {
                bubble.update();
            }
        }
    }

    private void createSounds() {
        Sounds.createSoundPool(context);
    }

    private boolean reachedCheckpoint() {
        return GameScore.score >= checkpoint * CHECK_POINT_INTERVAL;
    }

    private Popup createCheckpointPopup() {
        return new CheckpointPopupBuilder()
                .resources(resources)
                .duration(2 * FPS)
                .build();
    }

    private boolean isNotFirstCheckpoint() {
        return checkpoint > 0;
    }

    private void updateHero() {
        graphics.getHero().update();
    }

    private void updateSnack(Snack snack) {
        snack.update();
        if(heroInteractsWithSnack(graphics.getHero(), snack)) {
            updateScore(snack);
            snack.setPositionX(-500);
            snack.playSoundEat();
        }
    }

    private void updateScore(Snack snack) {
        GameScore.score += snack.getPointsForSnack();
    }

    private Villain createVillain() {
        return new VillainBuilder()
                .storage(storage)
                .resources(resources)
                .context(context)
                .build();
    }

    private void updateSnacks() {
        for(Snack snack : graphics.getSnacks())
            updateSnack(snack);
    }
    
    private void updateVillains() {
        updateNumberOfVillains();
        for(Villain villain : graphics.getVillains()) {
            villain.update();
            if(heroInteractsWithVillain(graphics.getHero(), villain)) {
                graphics.getHero().playSoundInteractingWithVillain();
                setGameStateToGameOver();
                storage.saveGame().saveNumLives(graphics.getLives().size() - 1);
            }
        }
    }

    private void setGameStateToGameOver() {
        GameState.setGameStateToGameOver();
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

    private void updateNumberOfVillains() {
        if (timeToIncreaseVillains()) {
            addVillain(createVillain());
            advanceDifficultyLevel();
        }
    }

    private void addVillain(Villain villain) {
        this.graphics.getVillains().add(villain);
    }

    private void advanceDifficultyLevel() {
        difficultyLevel++;
    }
    
    private boolean lessThanMaxVillains() {
        return graphics.getVillains().size() < Parameters.MAX_VILLAINS;
    }
    
    private boolean timeToIncreaseVillains() {
        return GameScore.score >= difficultyLevel * Parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL && lessThanMaxVillains();
    }

    private void updateBackground() {
        int backgroundId = 1;
        for(Background background : graphics.getBackgrounds()) {
            background.update();
            if(background.getPositionX() <= 0) {
                graphics.getBackgrounds()
                        .get(getFollowingBackground(backgroundId))
                        .setPositionX(background.getPositionX() + background.getBackground().getWidth() - 10);
            }
            backgroundId++;
        }
    }

    private int getFollowingBackground(int id) {
        return id % graphics.getBackgrounds().size();
    }

    private void save() {
        storage.saveGame().saveHero(graphics.getHero());
        storage.saveGame().saveSnacks(graphics.getSnacks());
        storage.saveGame().saveVillains(graphics.getVillains());
        storage.saveGame().saveBackgrounds(graphics.getBackgrounds());
        storage.saveGame().saveScore(GameScore.score);
        storage.saveGame().saveNumLives(graphics.getLives().size());
        storage.saveGame().saveCheckpoint(checkpoint);
    }

    private void updateFrameCounter() {
        frameCounter++;

        if(frameCounter >= Integer.MAX_VALUE - 100) {
            frameCounter = 0;
        }
    }

    private class SaveState implements Runnable {

        @Override
        public void run() {
            save();
        }
    }
}
