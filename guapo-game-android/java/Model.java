package com.main.guapogame;

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

public class Model {
    private final List<Background> backgrounds;
    private int backgroundSpeed;
    private Snack[] broccoli;
    private Snack[] cheesyBites;
    private Snack[] cucumbers;
    private int difficultyLevel = 0;
    private Bitmap[] lives;
    private int numLives = 3;
    private final Paint paint;
    private Snack[] paprika;
    private int pauseRegionMaxX;
    private int pauseRegionMinY;
    private Bitmap playButton;
    private Bitmap pauseButton;
    private SharedPreferences prefs;
    private Random random;
    private int score = 0;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    private float screenFactorX, screenFactorY;
    private Hero hero;
    private Lives lives1;
    private final List<Villain> villains = new ArrayList<>();
    private Sounds sounds;
    private final Resources resources;

    public Model(Context activity, Resources resources, List<Background> backgrounds) {
        this.resources = resources;

        this.backgrounds = backgrounds;

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);

        getSharedPreferences(activity);
        getScreenParameters();

        createSounds(activity);
        createSnacks();
        createPauseAndPlayButtons();
        createLives();
        createLives1();
        createHero();

        getPauseRegion();
    }

    public Hero getHero() {
        return hero;
    }

    private Resources getResources() {
        return resources;
    }

    private void getSharedPreferences(Context activity) {
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
    }

    private void createLives1() {
        lives1 = new Lives();
    }

    private void createSounds(Context activity) {
        sounds = new Sounds(activity);
    }

    private void createLives() {
        numLives = prefs.getInt("num_lives", Parameters.NUM_LIVES);
        lives = new Bitmap[numLives];
        for(int i = 0; i < numLives; i++) {
            lives[i] = BitmapFactory.decodeResource(getResources(), R.drawable.heart1_bitmap_cropped);
            lives[i] = Bitmap.createScaledBitmap(lives[i], (int) screenFactorX / 4, (int) screenFactorY / 4, false);
        }
    }

    private void getScreenParameters() {
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenFactorX = (int) (((float) screenWidth) / 10);
        screenFactorY = (int) (((float) screenHeight) / 5);
        backgroundSpeed = (int) (((float) screenWidth) / 400);

        setScreenWidth(screenWidth);
        setScreenHeight(screenHeight);
        setBackgroundSpeed(backgroundSpeed);
    }

    private void getPauseRegion() {
        pauseRegionMaxX = screenWidth - screenWidth / 30;
        pauseRegionMinY = screenHeight / 15;
    }

    private void createPauseAndPlayButtons() {
        playButton = getBitmapScaled((int) ((int) screenFactorX / 3.0), (int) ((int) screenFactorY / 3.0), R.drawable.play_button_bitmap_cropped);
        pauseButton = getBitmapScaled((int) ((int) screenFactorX / 3.0), (int) ((int) screenFactorY / 3.0), R.drawable.pause_button_bitmap_cropped);
    }

    private void createSnacks() {
        cheesyBites = createSnacks(Parameters.NUM_CHEESY_BITES, Parameters.POINTS_CHEESY_BITES, R.drawable.cheesy_bite_resized);
        paprika = createSnacks(Parameters.NUM_PAPRIKA, Parameters.POINTS_PAPRIKA, R.drawable.paprika_bitmap_cropped);
        cucumbers = createSnacks(Parameters.NUM_CUCUMBERS, Parameters.POINTS_CUCUMBER, R.drawable.cucumber_bitmap_cropped);
        broccoli = createSnacks(Parameters.NUM_BROCCOLI, Parameters.POINTS_BROCCOLI, R.drawable.broccoli_bitmap_cropped);
    }

    private Heros getHeroId() {
        int heroId = prefs.getInt("choose_character", 0);
        if(heroId == 1) {
            return Heros.TUTTI;
        }

        return Heros.GUAPO;
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
        int startPosX = (int) (((float) screenWidth) / 10);
        int startPosY = (int) (((float) screenHeight) / 2);
        int width = (int) screenFactorX * 3 / 2;
        int height = (int) screenFactorY * 3 / 2;

        Bitmap heroImage = getBitmapScaled(width, height, R.drawable.guapo_main_image_bitmap_cropped);
        Bitmap heroImageHit = getBitmapScaled(width, height, R.drawable.guapo_main_image_hit_bitmap_cropped);
        Bitmap capeImage1 = getBitmapScaled(width, height, R.drawable.cape1_bitmap_cropped1);
        Bitmap capeImage2 = getBitmapScaled(width, height, R.drawable.cape1_bitmap_cropped1);

        return new Hero.Builder()
                .x(startPosX)
                .y(startPosY)
                .heroImage(heroImage)
                .heroHitImage(heroImageHit)
                .capes(capeImage1)
                .capes(capeImage2)
                .build();
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap heroImage = BitmapFactory.decodeResource(getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(heroImage, scaleX, scaleY, false);
    }

    private Hero createTutti() {
        int startPosX = (int) (((float) screenWidth) / 10);
        int startPosY = (int) (((float) screenHeight) / 2);
        int width = (int) screenFactorX * 3 / 2;
        int height = (int) screenFactorY * 3 / 2;

        Bitmap heroImage = getBitmapScaled(width, height, R.drawable.tutti_bitmap_no_cape_cropped);
        Bitmap heroImageHit = getBitmapScaled(width, height, R.drawable.tutti_bitmap_hit_no_cape_cropped);
        Bitmap capeImage1 = getBitmapScaled(width, height, R.drawable.cape1_bitmap_cropped1);
        Bitmap capeImage2 = getBitmapScaled(width, height, R.drawable.cape2_bitmap_cropped);

        return new Hero.Builder()
                .x(startPosX)
                .y(startPosY)
                .heroImage(heroImage)
                .heroHitImage(heroImageHit)
                .capes(capeImage1)
                .capes(capeImage2)
                .build();
    }

    private Snack[] createSnacks(int numSnacks, int pointsForSnack, int snackId) {
        Snack[] snacks = new Snack[numSnacks];

        random = new Random();

        for (int i = 0; i < numSnacks; i++) {
            Snack snack = new Snack(getResources(), (int) screenFactorX, (int) screenFactorY, snackId);
            snack.x = random.nextInt(2 * screenWidth - snack.get_snack_image().getWidth()/2);
            snack.y = random.nextInt(screenHeight - snack.get_snack_image().getHeight()/2);
            snack.points_snack = pointsForSnack;
            snacks[i] = snack;
        }

        return snacks;
    }

    private void updateSnack(Snack snack) {
        if (snack.x + snack.width < 0) {
            Random rand = new Random();
            snack.x = rand.nextInt(screenWidth + 1) + screenWidth;
            snack.y = random.nextInt(screenHeight - snack.height);
            snack.setPlaySoundAllowed(true);
        }

        snack.x = snack.x - backgroundSpeed;

        if(heroInteractsWithSnack(hero, snack)) {
            score += snack.points_snack;
            snack.set_x(-500);
            snack.playSoundEat(sounds);
        }
    }

    private void updateHero() {
        hero.update();
    }

    public void saveHighScore() {
        int score = 0;
        if (prefs.getInt("high_score", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("high_score", score);
            editor.apply();
        }
    }

    private int getLives() {
        return lives1.getLives();
    }

    private void takeLife() {
        lives1.takeLife();
    }

    private void setGameStateToGameOver() {
        GameState.setGameStateToGameOver();
    }

    private boolean gameIsPaused() {
        return GameState.getGameState().equals(State.PAUSED);
    }

    private void updateSnacks(Snack[] snacks) {
        for(Snack snack : snacks) {
            updateSnack(snack);
        }
    }

    public void update() {
        updateBackground();
        updateVillains();
        updateHero();
        updateSnacks();

        if (heroHitVillain()) {
            setGameStateToGameOver();
        }
    }

    public void draw(Canvas canvas) {
        drawBackgrounds(canvas);
        drawLives(canvas);
        drawAll(canvas);
        drawVillains(canvas);
        drawHero(canvas);
    }

    private void drawBackgrounds(Canvas canvas) {
        for(Background background : backgrounds) {
            background.draw(canvas);
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

    private int getFollowingBackground(int id) {
        return id % backgrounds.size();
    }

    private void updateBegginStrip() {
        // TODO : implement
    }

    private void updateVillains() {
        updateNumberOfVillains();
        for(Villain villain : villains) {
            villain.update();
        }
    }

    private void updateSnacks() {
        updateBegginStrip();
        updateSnacks(cheesyBites);
        updateSnacks(paprika);
        updateSnacks(cucumbers);
        updateSnacks(broccoli);
    }

    private void updateNumberOfVillains() {
        int scoreThatRequiresNumberOfVillainsIncreases =
                difficultyLevel * Parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL;

        if(score >= scoreThatRequiresNumberOfVillainsIncreases
                && villains.size() < Parameters.MAX_NUM_BIRDS) {
            addVillain(
                    new Villain.Builder()
                            .x(-500)
                            .images(createVillainImages())
                            .build()
            );
            difficultyLevel++;
        }
    }

    private boolean heroHitVillain() {
        for(Villain villain : villains) {
            if(heroInteractsWithVillan(hero, villain)) {
                hero.playSoundInteractingWithVillain(sounds);
                return true;
            }
        }

        return false;
    }

    private Rect getHeroRectangle(Bitmap image, int x, int y) {
        return new Rect(
                x,
                y,
                (int) (x + image.getWidth() / 2.0),
                (int) (y + image.getHeight() / 2.0)
        );
    }

    private Rect getVillainRectangle(Bitmap image, int x, int y) {
        return new Rect(
                (int) (x + image.getWidth() * 35.0 / 100.0),
                (int) (y + image.getHeight() * 35.0 / 100.0),
                (int) (x + image.getWidth() * 65.0 / 100.0),
                (int) (y + image.getHeight() * 65.0 / 100.0)
        );
    }

    private Rect getSnackRectangle(Bitmap image, int x, int y) {
        return new Rect(
                (int) (x + image.getWidth() * 35.0 / 100.0),
                (int) (y + image.getHeight() * 35.0 / 100.0),
                (int) (x + image.getWidth() * 65.0 / 100.0),
                (int) (y + image.getHeight() * 65.0 / 100.0)
        );
    }

    private boolean heroInteractsWithVillan(Hero hero, Villain villain) {
        Rect heroArea = getHeroRectangle(hero.getImage(), (int) hero.getPositionX(),
                (int) hero.getPositionY());
        Rect villainArea = getVillainRectangle(villain.getImage(), (int) villain.getPositionX(),
                (int) villain.getPositionY());

        return Rect.intersects(heroArea, villainArea);
    }

    private boolean heroInteractsWithSnack(Hero hero, Snack snack) {
        Rect rect_1 = getHeroRectangle(hero.getImage(), (int) hero.getPositionX(),
                (int) hero.getPositionY());
        Rect rect_2 = getSnackRectangle(snack.getImage(), (int) snack.getPositionX(),
                (int) snack.getPositionY());

        return Rect.intersects(rect_1, rect_2);
    }

    private void saveHighScore(String score_id) {
        if (prefs.getInt(score_id, 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(score_id, score);
            editor.apply();
        }
    }

    private int getNumVillains(String store_id) {
        return prefs.getInt(store_id, 2);
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

    private boolean isActiveSession(String store_id) {
        return prefs.getBoolean(store_id, false);
    }

    private void getNumLives(String store_id) {
        numLives = prefs.getInt(store_id, 3);
    }

    private void getScore(String store_id) {
        score = prefs.getInt(store_id, 0);
    }

    private void drawSnacks(Canvas canvas, Snack [] snacks) {
        for (Snack snack : snacks) {
            snack.draw(canvas);
        }
    }

    private void drawSnack(Canvas canvas, Snack snack) {
        snack.draw(canvas);
    }

    private void drawScore(Canvas canvas) {
        canvas.drawText(" " + score, 30, (float) screenHeight / 6, paint);
    }

    private void drawCharacters(Canvas canvas) {
        // TODO : implement
    }

    private void drawVillains(Canvas canvas) {
        for(Villain villain : villains) {
            villain.draw(canvas);
        }
    }

    private void drawLives(Canvas canvas) {
        int left_most_x = screenWidth / 2 - 20;
        for (int i = 0; i < numLives; i++) {
            canvas.drawBitmap(lives[i], left_most_x, 20, null);
            left_most_x += lives[i].getWidth() + 5;
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

    private void drawAll(Canvas canvas) {
        drawSnacks(canvas, cheesyBites);
        drawSnacks(canvas, paprika);
        drawSnacks(canvas, cucumbers);
        drawSnacks(canvas, broccoli);
        drawScore(canvas);
        drawCharacters(canvas);
        drawPauseButton(canvas);
    }
}
