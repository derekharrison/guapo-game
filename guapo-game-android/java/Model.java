package com.main.guapogame;

import static com.main.guapogame.Constants.FPS;
import static com.main.guapogame.Keys.BACKGROUND;
import static com.main.guapogame.Keys.GAMESTATE;
import static com.main.guapogame.Keys.NUM_BACKGROUNDS;
import static com.main.guapogame.Keys.POSITION_X;
import static com.main.guapogame.Keys.POSITION_Y;
import static com.main.guapogame.Keys.SNACK;
import static com.main.guapogame.Keys.VELOCITY_X;
import static com.main.guapogame.Parameters.CHECK_POINT_INTERVAL;
import static com.main.guapogame.Parameters.POINTS_BEGGIN_STRIPS;
import static com.main.guapogame.Parameters.getBackgroundSpeed;
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
import java.util.Random;

// TODO : create characters
// TODO : create beggin strip
// TODO : implement checkpoints
// TODO : inject game objects
// TODO : create model builder

public class Model {
    private final List<Background> backgrounds = new ArrayList<>();
    private final List<Snack> snacks = new ArrayList<>();
    private final List<Bitmap> lives = new ArrayList<>();
    private final List<Villain> villains = new ArrayList<>();
    private int difficultyLevel = 0;
    private final Paint paint;
    private int pauseRegionMaxX;
    private int pauseRegionMinY;
    private Bitmap playButton;
    private Bitmap pauseButton;
    private SharedPreferences prefs;
    private int score = 0;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private float screenFactorX, screenFactorY;
    private Hero hero;
    private Sounds sounds;
    private final Resources resources;
    private final GameState gameState;
    private final Context context;
    private int checkpoint = 0;
    private Popup checkpointPopup;

    public Model(Context context, Resources resources) {
        this.resources = resources;
        this.context = context;
        this.gameState = new GameState(context);

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);

        createModelData();

        setSessionIsActive(false);
    }

    private void createModelData() {
        getSharedPreferences();
        getScreenParameters();

        createSounds();
        createPauseAndPlayButtons();
        createSnacks();
        createLives();
        createHero();
        createVillains();
        createBackgrounds();
        createPopups();

        getPauseRegion();
        getScore();
    }

    public Hero getHero() {
        return hero;
    }

    public void saveHighScore() {
        if (prefs.getInt("high_score", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("high_score", score);
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

    private void saveGame() {
        if(reachedCheckpoint()) {
            Thread thread = new Thread(new SaveState());
            thread.start();
            advanceCheckpoint();
        }
    }

    private void updateCheckpointPopup() {
        if(reachedCheckpoint()) {
            checkpointPopup = createCheckpointPopup(2 * FPS);
            checkpointPopup.playSoundCheckpoint(sounds);
        }

        checkpointPopup.update();
    }

    private void createPopups() {
        checkpointPopup = createCheckpointPopup(0);
    }

    private Popup createCheckpointPopup(int duration) {
        int width = (int) (screenWidth / 10.0);
        int height = (int) (screenHeight / 5.0);
        Bitmap image = getBitmapScaled(width, height, R.drawable.flag_aruba_bitmap_cropped);
        return new Popup.Builder()
                .duration(duration)
                .positionX(screenWidth - screenWidth / 4)
                .positionY(image.getHeight() + screenHeight / 10)
                .image(image)
                .build();
    }

    private void drawPopUp(Canvas canvas) {
        checkpointPopup.draw(canvas);
    }

    private void save() {
        gameState.saveGameState().saveHero(hero);
        gameState.saveGameState().saveSnacks(snacks);
        gameState.saveGameState().saveVillains(villains);
        gameState.saveGameState().saveBackgrounds(backgrounds);
        gameState.saveGameState().saveScore(score);
        gameState.saveGameState().saveCheckpoint(checkpoint);
    }

    private void getCheckpoint() {
        // TODO : implement
        if(isActiveSession()) {
            checkpoint = gameState.getLoadGameState().getCheckpoint();
        }
    }

    private void getScore() {
        if(isActiveSession()) {
            score = gameState.getLoadGameState().getScore();
        }
    }

    private boolean reachedCheckpoint() {
        return score >= (checkpoint + 1) * CHECK_POINT_INTERVAL;
    }

    private void advanceCheckpoint() {
        checkpoint++;
    }

    private void updateSnack(Snack snack) {
        snack.update();
        if(heroInteractsWithSnack(hero, snack)) {
            score += snack.getPointsForSnack();
            snack.setPositionX(-500);
            snack.playSoundEat(sounds);
        }
    }

    private void updateHero() {
        hero.update();
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

    private void updateVillains() {
        updateNumberOfVillains();
        for(Villain villain : villains) {
            villain.update();
            if(heroInteractsWithVillain(hero, villain)) {
                hero.playSoundInteractingWithVillain(sounds);
                setGameStateToGameOver();
            }
        }
    }

    private void updateSnacks() {
        for(Snack snack : snacks) {
            updateSnack(snack);
        }
    }

    private void updateNumberOfVillains() {
        if (needToIncreaseVillains() && lessThanMaxVillains()) {
            addVillain(createVillain());
            advanceDifficultyLevel();
        }
    }

    private void advanceDifficultyLevel() {
        difficultyLevel++;
    }

    private boolean lessThanMaxVillains() {
        return villains.size() < Parameters.MAX_VILLAINS;
    }
    private boolean needToIncreaseVillains() {
        return score >= difficultyLevel * Parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL;
    }

    private void drawBackgrounds(Canvas canvas) {
        for(Background background : backgrounds) {
            background.draw(canvas);
        }
    }

    private void drawSnacks(Canvas canvas) {
        for (Snack snack : snacks) {
            snack.draw(canvas);
        }
    }

    private void drawScore(Canvas canvas) {
        canvas.drawText(" " + score, 30, (float) screenHeight / 6, paint);
    }

    private void drawVillains(Canvas canvas) {
        for(Villain villain : villains) {
            villain.draw(canvas);
        }
    }

    private void drawLives(Canvas canvas) {
        int lifeLocation = screenWidth / 2 - 20;
        for (Bitmap life : lives) {
            canvas.drawBitmap(life, lifeLocation, 20, null);
            lifeLocation += life.getWidth() + 5;
        }
    }

    private void drawPauseButton(Canvas canvas) {
        if(gameIsPaused()) {
            canvas.drawBitmap(playButton, pauseRegionMaxX - playButton.getWidth(), pauseRegionMinY, null);
        }
        else {
            canvas.drawBitmap(pauseButton, pauseRegionMaxX - pauseButton.getWidth(), pauseRegionMinY, null);
        }
    }

    private void drawHero(Canvas canvas) {
        hero.draw(canvas);
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

    private void createBackgrounds() {
        List<Integer> assetIds = getBackgroundAssetIds();
        int backgroundId = 0;
        for(Integer background : assetIds) {
            this.backgrounds.add(createBackground(background, String.valueOf(backgroundId)));
            backgroundId++;
        }
    }

    private List<Integer> getBackgroundAssetIds() {
        int numBackgrounds = prefs.getInt(NUM_BACKGROUNDS, 0);
        List<Integer> assetIds = new ArrayList<>();
        for(int id = 0; id < numBackgrounds; id++) {
            assetIds.add(prefs.getInt(BACKGROUND + id, 0));
        }

        return assetIds;
    }

    private Background createBackground(int assetId, String backgroundId) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        return new Background.Builder()
                .positionX(getStartPositionBackground(backgroundId))
                .velocityX(-getBackgroundSpeed())
                .background(getBitmapScaled(screenWidth, screenHeight, assetId))
                .build();
    }

    private float getStartPositionBackground(String backgroundId) {
        if(isActiveSession()) {
            return gameState.getLoadGameState().getBackgroundPosition(POSITION_X, backgroundId);
        }

        return 0;
    }

    private void createSounds() {
        sounds = new Sounds(context);
    }

    private void createLives() {
        int numLives = prefs.getInt("num_lives", Parameters.NUM_LIVES);
        for(int i = 0; i < numLives; i++) {
            Bitmap life = getBitmapScaled(
                    (int) screenFactorX / 4,
                    (int) screenFactorY / 4,
                    R.drawable.heart1_bitmap_cropped);
            lives.add(life);
        }
    }

    private void createPauseAndPlayButtons() {
        playButton = getBitmapScaled((int) ((int) screenFactorX / 3.0), (int) ((int) screenFactorY / 3.0), R.drawable.play_button_bitmap_cropped);
        pauseButton = getBitmapScaled((int) ((int) screenFactorX / 3.0), (int) ((int) screenFactorY / 3.0), R.drawable.pause_button_bitmap_cropped);
    }

    private void createSnacks() {
        snacks.addAll(createSnacks(Parameters.NUM_CHEESY_BITES, Parameters.POINTS_CHEESY_BITES, R.drawable.cheesy_bite_resized));
        snacks.addAll(createSnacks(Parameters.NUM_PAPRIKA, Parameters.POINTS_PAPRIKA, R.drawable.paprika_bitmap_cropped));
        snacks.addAll(createSnacks(Parameters.NUM_CUCUMBERS, Parameters.POINTS_CUCUMBER, R.drawable.cucumber_bitmap_cropped));
        snacks.addAll(createSnacks(Parameters.NUM_BROCCOLI, Parameters.POINTS_BROCCOLI, R.drawable.broccoli_bitmap_cropped));
        snacks.addAll(createSnacks(1, POINTS_BEGGIN_STRIPS, R.drawable.beggin_strip_cropped));
    }

    private void createHero() {
        if(getHeroId().equals(Heros.TUTTI)) {
            hero = createTutti();
        }
        else {
            hero = createGuapo();
        }
    }

    private Hero createGuapo() {
        int width = (int) (screenFactorX * 3 / 2.0);
        int height = (int) (screenFactorY * 3 / 2.0);

        Bitmap heroImage = getBitmapScaled(width, height, R.drawable.guapo_main_image_bitmap_cropped);
        Bitmap heroImageHit = getBitmapScaled(width, height, R.drawable.guapo_main_image_hit_bitmap_cropped);
        Bitmap capeImage1 = getBitmapScaled(width / 2, (3 * height) / 4, R.drawable.cape1_bitmap_cropped1);
        Bitmap capeImage2 = getBitmapScaled(width / 2, (3 * height) / 4, R.drawable.cape2_bitmap_cropped1);

        return new Hero.Builder()
                .positionX(getHeroPositionX())
                .positionY(getHeroPositionY())
                .heroImage(heroImage)
                .heroHitImage(heroImageHit)
                .capes(capeImage1)
                .capes(capeImage2)
                .hero(Heros.GUAPO)
                .frameCounter(getHeroFrameCounter())
                .build();
    }

    private Hero createTutti() {
        int width = (int) (screenFactorX * 3 / 2.0);
        int height = (int) (screenFactorY * 3 / 2.0);

        Bitmap heroImage = getBitmapScaled(width, height, R.drawable.tutti_bitmap_no_cape_cropped);
        Bitmap heroImageHit = getBitmapScaled(width, height, R.drawable.tutti_bitmap_hit_no_cape_cropped);
        Bitmap capeImage1 = getBitmapScaled(width / 2, (3 * height) / 4, R.drawable.cape1_bitmap_cropped1);
        Bitmap capeImage2 = getBitmapScaled(width / 2, (3 * height) / 4, R.drawable.cape2_bitmap_cropped);

        return new Hero.Builder()
                .positionX(getHeroPositionX())
                .positionY(getHeroPositionY())
                .heroImage(heroImage)
                .heroHitImage(heroImageHit)
                .frameCounter(getHeroFrameCounter())
                .capes(capeImage1)
                .capes(capeImage2)
                .hero(Heros.TUTTI)
                .build();
    }

    private int getHeroFrameCounter() {
        return gameState.getLoadGameState().getHeroFrameCounter();
    }

    private float getHeroPositionX() {
        if(isActiveSession()) {
            return gameState.getLoadGameState().getHeroPosition(POSITION_X);
        }

        return (float) (screenWidth / 10.0);
    }

    private float getHeroPositionY() {
        if(isActiveSession()) {
            return gameState.getLoadGameState().getHeroPosition(POSITION_Y);
        }

        return (float) (screenHeight / 2.0);
    }

    private List<Snack> createSnacks(int numSnacks, int pointsForSnack, int assetId) {
        List<Snack> snacks = new ArrayList<>();
        int width = (int) (screenFactorX - screenFactorX / 3.0);
        int height = (int) (screenFactorY - screenFactorY / 3.0);
        Bitmap snackImage = getBitmapScaled(width, height, assetId);
        for (int snackId = 0; snackId < numSnacks; snackId++) {
            snacks.add(
                    new Snack.Builder()
                            .positionX((int) getSnackPositionX(String.valueOf(snackId), snackImage))
                            .positionY((int) getSnackPositionY(String.valueOf(snackId), snackImage))
                            .velocityX(-getBackgroundSpeed())
                            .pointsForSnack(pointsForSnack)
                            .snackImage(snackImage)
                            .build()
            );
        }

        return snacks;
    }

    private float getSnackPositionX(String snackId, Bitmap snackImage) {
        Random random = new Random();
        if(isActiveSession()) {
            return gameState.getLoadGameState().getSnackPosition(SNACK, snackId);
        }

        return random.nextInt(2 * screenWidth - snackImage.getWidth() / 2);
    }

    private float getSnackPositionY(String snackId, Bitmap snackImage) {
        Random random = new Random();
        if(isActiveSession()) {
            return gameState.getLoadGameState().getSnackPosition(SNACK, snackId);
        }

        return random.nextInt(screenHeight - snackImage.getHeight() / 2);
    }

    private void createVillains() {
        int numVillains = getNumVillains();
        for(int villainId = 0; villainId < numVillains; villainId++) {
            if(isActiveSession()) {
                createVillain(String.valueOf(villainId));
            }
            else {
                addVillain(createVillain());
            }
        }
    }

    private Villain createVillain() {
        return new Villain.Builder()
                .positionX(-500)
                .images(createVillainImages())
                .build();
    }

    private void createVillain(String villainId) {
        villains.add(
                new Villain.Builder()
                        .positionX(getVillainPositionX(villainId))
                        .positionY(getVillainPositionY(villainId))
                        .velX(getVillainVelocityX(villainId))
                        .images(createVillainImages())
                        .frameCounter(getVillainFrameCounter(villainId))
                        .build()
        );
    }

    private int getVillainFrameCounter(String villainId) {
        return gameState.getLoadGameState().getVillainFrameCounter(villainId);
    }

    private float getVillainPositionX(String villainId) {
        return gameState.getLoadGameState().getVillainPosition(POSITION_X, villainId);
    }

    private float getVillainPositionY(String villainId) {
        return gameState.getLoadGameState().getVillainPosition(POSITION_Y, villainId);
    }

    private float getVillainVelocityX(String villainId) {
        return gameState.getLoadGameState().getVillainVelocity(VELOCITY_X, villainId);
    }

    private List<Bitmap> createVillainImages() {
        List<Bitmap> images = new ArrayList<>();

        int width =  (int) (((float) screenWidth) / 10);
        int height = (int) (((float) screenHeight) / 5);

        Bitmap bird_image1 = getBitmapScaled(width, height, R.drawable.warawara1_bitmap_custom_mod_cropped);
        Bitmap bird_image2 = getBitmapScaled(width, height, R.drawable.warawara2_bitmap_custom_mod_cropped);
        Bitmap bird_image3 = getBitmapScaled(width, height, R.drawable.warawara3_bitmap_custom_mod_cropped);

        images.add(bird_image1);
        images.add(bird_image2);
        images.add(bird_image3);

        return images;
    }

    private void addVillain(Villain villain) {
        this.villains.add(villain);
    }

    private boolean isActiveSession() {
        return prefs.getBoolean(GAMESTATE, false);
    }

    private void setSessionIsActive(boolean isActive) {
        // TODO : Implement
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(GAMESTATE, isActive);
        editor.apply();
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap heroImage = BitmapFactory.decodeResource(getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(heroImage, scaleX, scaleY, false);
    }

    private void getScreenParameters() {
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenFactorX = (int) (screenWidth / 10.0);
        screenFactorY = (int) (screenHeight / 5.0);
        int backgroundSpeed = (int) (screenWidth / 400.0);

        setScreenWidth(screenWidth);
        setScreenHeight(screenHeight);
        setBackgroundSpeed(backgroundSpeed);
    }

    private void getPauseRegion() {
        pauseRegionMaxX = screenWidth - screenWidth / 30;
        pauseRegionMinY = screenHeight / 15;
    }

    private Resources getResources() {
        return resources;
    }

    private void getSharedPreferences() {
        prefs = context.getSharedPreferences("game", Context.MODE_PRIVATE);
    }

    private int getNumVillains() {
        if(isActiveSession()) {
            return gameState.getLoadGameState().getNumVillains();
        }

        return 3;// TODO : Create constant
    }

    private void setGameStateToGameOver() {
        GameState.setGameStateToGameOver();
    }

    private boolean gameIsPaused() {
        return GameState.getGameState().equals(State.PAUSED);
    }

    private Heros getHeroId() {
        int heroId = prefs.getInt("choose_character", 0);
        if(heroId == 1) {
            return Heros.TUTTI;
        }

        return Heros.GUAPO;
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
        return id % backgrounds.size();
    }

    private class SaveState implements Runnable {

        @Override
        public void run() {
            save();
        }
    }
}
