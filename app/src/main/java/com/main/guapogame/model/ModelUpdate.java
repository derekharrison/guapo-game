package com.main.guapogame.model;

import static android.content.Context.MODE_PRIVATE;
import static com.main.guapogame.parameters.Keys.ARUBA;
import static com.main.guapogame.parameters.Keys.BEACH;
import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.HIGH_SCORE;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.TRIP;
import static com.main.guapogame.parameters.Keys.getKey;
import static com.main.guapogame.parameters.Parameters.CHECK_POINT_INTERVAL;
import static com.main.guapogame.parameters.Parameters.FPS;
import static com.main.guapogame.parameters.Parameters.LEVEL_UNLOCK_SCORE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Rect;

import com.main.guapogame.graphics.BrownieBuilder;
import com.main.guapogame.graphics.CharacterPopup;
import com.main.guapogame.graphics.CheckpointPopupBuilder;
import com.main.guapogame.graphics.FritoBuilder;
import com.main.guapogame.graphics.Misty;
import com.main.guapogame.graphics.MistyBuilder;
import com.main.guapogame.graphics.SunPopupBuilder;
import com.main.guapogame.graphics.VillainBuilder;
import com.main.guapogame.state.GameScore;
import com.main.guapogame.state.GameState;
import com.main.guapogame.parameters.Parameters;
import com.main.guapogame.graphics.Background;
import com.main.guapogame.graphics.Graphics;
import com.main.guapogame.graphics.Hero;
import com.main.guapogame.graphics.Popup;
import com.main.guapogame.graphics.Snack;
import com.main.guapogame.graphics.Villain;
import com.main.guapogame.interfaces.Update;
import com.main.guapogame.resources.Sounds;
import com.main.guapogame.storage.Storage;

class ModelUpdate implements Update {
    private int difficultyLevel = 0;
    private final Graphics graphics;
    private final Storage storage;
    private final Context context;
    private int checkpoint = 0;
    private int frameCounter = 0;

    protected ModelUpdate(Builder builder) {
        this.graphics = builder.graphics;
        this.context = builder.context;
        this.storage = builder.storage;
        
        createSounds();
        getCheckpoint();
        getDifficultyLevel();
        getScore();
    }

    @Override
    public void update() {
        updateBackground();
        updateVillains();
        updateHero();
        updateMisty();
        updateBrownie();
        updateFrito();
        updateSnacks();
        updateCheckpointPopup();
        updateSunPopup();
        updateFrameCounter();
        saveGame();
    }

    static class Builder {
        private Graphics graphics;
        private Storage storage;
        private Context context;

        Builder graphics(Graphics graphics) {
            this.graphics = graphics;
            return this;
        }

        Builder context(Context context) {
            this.context = context;
            return this;
        }

        Builder storage(Storage storage) {
            this.storage = storage;
            return this;
        }

        ModelUpdate build() {
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
            GameScore.setScore(storage.loadGame().getScore());
    }

    private boolean isActiveSession() {
        return getSharedPreferences().getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private String getLevelId() {
        return getSharedPreferences().getString(LEVEL, "");
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GAME, MODE_PRIVATE);
    }
    private void advanceCheckpoint() {
        checkpoint++;
    }

    private void getDifficultyLevel() {
        if(isActiveSession())
            difficultyLevel = storage.loadGame().getDifficultyLevel();
    }

    private void updateCheckpointPopup() {
        if(reachedCheckpoint() && isNotFirstCheckpoint()) {
            graphics.setCheckpointPopup(createCheckpointPopup());
            graphics.getCheckpointPopup().playSound();
        }

        graphics.getCheckpointPopup().update();
    }

    private void updateSunPopup() {
        if(nextLevelIslocked() && GameScore.getScore() > LEVEL_UNLOCK_SCORE) {
            graphics.setSunPopup(createSunPopup());
            graphics.getSunPopup().playSound();
            storage.saveGame().saveHighScore(GameScore.getScore());
        }

        graphics.getSunPopup().update();
    }

    private Misty createMisty() {
        return new MistyBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private CharacterPopup createBrownie() {
        return new BrownieBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private CharacterPopup createFrito() {
        return new FritoBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private void createSounds() {
        Sounds.createSoundPool(context);
    }

    private boolean reachedCheckpoint() {
        return GameScore.getScore() >= checkpoint * CHECK_POINT_INTERVAL;
    }

    private boolean nextLevelIslocked() {
        if(levelCanBeUnlocked()) {
            return getHighScore(context.getSharedPreferences(GAME, MODE_PRIVATE), getLevelId())
                    < LEVEL_UNLOCK_SCORE;
        }

        return false;
    }

    private boolean levelCanBeUnlocked() {
        return getLevelId().equals(ARUBA) || getLevelId().equals(BEACH)
                || getLevelId().equals(TRIP);
    }

    private int getHighScore(SharedPreferences prefs, String levelId) {
        return prefs.getInt(getKey(levelId, HIGH_SCORE), 0);
    }

    private Popup createCheckpointPopup() {
        return new CheckpointPopupBuilder()
                .resources(context.getResources())
                .duration(2 * FPS)
                .build();
    }

    private Popup createSunPopup() {
        return new SunPopupBuilder()
                .resources(context.getResources())
                .duration(2 * FPS)
                .build();
    }

    private boolean isNotFirstCheckpoint() {
        return checkpoint > 0;
    }

    private void updateHero() {
        graphics.getHero().update();
    }

    private void updateMisty() {
        if((frameCounter % (6 * FPS)) == 0) {
            graphics.setMisty(createMisty());
            graphics.getMisty().playSound();
        }

        if(heroInteractsWithPopup(graphics.getHero(), graphics.getMisty())) {
            graphics.getMisty().hitMisty();
            graphics.getMisty().playSoundHit();
        }

        graphics.getMisty().update();
    }

    private void updateBrownie() {
        if((frameCounter % (16 * FPS)) == 0) {
            graphics.setBrownie(createBrownie());
            graphics.getBrownie().playSound();
        }

        if(heroInteractsWithPopup(graphics.getHero(), graphics.getBrownie())) {
            graphics.getBrownie().hit();
            graphics.getBrownie().playSoundHit();
        }

        graphics.getBrownie().update();
    }

    private void updateFrito() {
        if((frameCounter % (16 * FPS)) == 0) {
            graphics.setFrito(createFrito());
            graphics.getFrito().playSound();
        }

        if(heroInteractsWithPopup(graphics.getHero(), graphics.getFrito())) {
            graphics.getFrito().hit();
            graphics.getFrito().playSoundHit();
        }

        graphics.getFrito().update();
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
        GameScore.setScore(GameScore.getScore() + snack.getPointsForSnack());
    }

    private Villain createVillain() {
        return new VillainBuilder()
                .storage(storage)
                .context(context)
                .build();
    }

    private void updateSnacks() {
        for(Snack snack : graphics.getSnacks())
            updateSnack(snack);
    }
    
    private void updateVillains() {
        for(Villain villain : graphics.getVillains()) {
            villain.update();
            if(heroInteractsWithVillain(graphics.getHero(), villain)) {
                graphics.getHero().playSoundInteractingWithVillain();
                setGameStateToGameOver();
                storage.saveGame().saveNumLives(graphics.getLives().size() - 1);
            }
        }
        updateNumberOfVillains();
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

    private boolean heroInteractsWithPopup(Hero hero, Popup popup) {
        Rect heroArea = getHeroArea(hero.getImage(), (int) hero.getPositionX(),
                (int) hero.getPositionY());
        Rect mistyArea = getArea(popup.getImage(), (int) popup.getPositionX(),
                (int) popup.getPositionY());

        return Rect.intersects(heroArea, mistyArea);
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

    private Rect getArea(Bitmap image, int x, int y) {
        return new Rect(x, y, x + image.getWidth(), y + image.getHeight());
    }

    private Rect getSnackArea(Bitmap image, int x, int y) {
        return new Rect(x, y, x + image.getWidth(), y + image.getHeight());
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
        return GameScore.getScore() >= difficultyLevel * Parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL
                && lessThanMaxVillains();
    }

    private void updateBackground() {
        int backgroundId = 0;
        for(Background background : graphics.getBackgrounds()) {
            if(background.getPositionX() + background.getImage().getWidth() <= 0) {
                Background precedingBackground = graphics.getBackgrounds().get(getPrecedingBackground(backgroundId));
                background.setPositionX(precedingBackground.getPositionX() + precedingBackground.getImage().getWidth() - 10);
            }
            background.update();
            backgroundId++;
        }
    }

    private int getPrecedingBackground(int id) {
        if(id == 0)
            return graphics.getBackgrounds().size() - 1;

        return id - 1;
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

        private void save() {
            storage.saveGame().saveHero(graphics.getHero());
            storage.saveGame().saveMisty(graphics.getMisty());
            storage.saveGame().saveBrownie(graphics.getBrownie());
            storage.saveGame().saveFrito(graphics.getFrito());
            storage.saveGame().saveSnacks(graphics.getSnacks());
            storage.saveGame().saveVillains(graphics.getVillains());
            storage.saveGame().saveBackgrounds(graphics.getBackgrounds());
            storage.saveGame().saveScore(GameScore.getScore());
            storage.saveGame().saveNumLives(graphics.getLives().size());
            storage.saveGame().saveCheckpoint(checkpoint);
            storage.saveGame().saveDifficulty(difficultyLevel);
        }
    }
}
