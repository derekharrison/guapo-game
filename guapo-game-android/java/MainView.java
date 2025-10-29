package com.main.guapogame;

import static com.main.guapogame.Parameters.setBackgroundSpeed;
import static com.main.guapogame.Parameters.setScreenHeight;
import static com.main.guapogame.Parameters.setScreenWidth;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO : create characters
// TODO : create beggin strip
// TODO : implement checkpoints

public class MainView extends SurfaceView {
    private int backgroundSpeed;
    private Snack[] broccoli;
    private Snack[] cheesyBites;
    private Snack[] cucumbers;
    private int difficultyLevel = 0;
    private Bitmap[] lives;
    private int numLives = 3;
    private Paint paint;
    private Snack[] paprika;
    private Parameters parameters;
    private int pauseRegionMinX;
    private int pauseRegionMaxX;
    private int pauseRegionMinY;
    private int pauseRegionMaxY;
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

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
    }
    
     public MainView(Context activity) {
        super(activity);

        createParameters();
        initDisplay();
        getSharedPreferences(activity);
        getSounds(activity);
        createImagesAndPositions();
        createLives();
    }

    protected int getBackgroundSpeed() {
        return backgroundSpeed;
    }

    protected int getScreenWidth() {
        return screenWidth;
    }

    protected int getScreenHeight() {
        return screenHeight;
    }

    private void createParameters() {
        parameters = new Parameters();
    }

    private void createImagesAndPositions() {
        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);

        createImages();
    }

    private void createLives() {
        lives1 = new Lives();
    }

    private void getSharedPreferences(Context activity) {
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
    }

    private void getSounds(Context activity) {
        sounds = new Sounds(activity);
    }

    private void initDisplay() {
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private void initLives() {
        numLives = prefs.getInt("num_lives", parameters.NUM_LIVES);
        lives = new Bitmap[numLives];
        for(int i = 0; i < numLives; i++) {
            lives[i] = BitmapFactory.decodeResource(getResources(), R.drawable.heart1_bitmap_cropped);
            lives[i] = Bitmap.createScaledBitmap(lives[i], (int) screenFactorX / 4, (int) screenFactorY / 4, false);
        }
    }

    private void createImages() {
        getParameters();
        createSnacks();
        createPauseAndPlayButtons();
        getPauseRegion();
        initLives();
        createHero();
    }

    private void getParameters() {
        screenFactorX = (int) (((float) screenWidth) / 10);
        screenFactorY = (int) (((float) screenHeight) / 5);
        backgroundSpeed = (int) (((float) screenWidth) / 400);

        setScreenWidth(screenWidth);
        setScreenHeight(screenHeight);
        setBackgroundSpeed(backgroundSpeed);
    }

    private void getPauseRegion() {
        pauseRegionMaxX = screenWidth - screenWidth / 30;
        pauseRegionMinX = pauseRegionMaxX - pauseButton.getWidth() - 3 * screenWidth / 60;
        pauseRegionMaxY = 5 * screenHeight / 30;
        pauseRegionMinY = screenHeight / 15;
    }

    private void createPauseAndPlayButtons() {
        playButton = getBitmapScaled((int) ((int) screenFactorX / 3.0), (int) ((int) screenFactorY / 3.0), R.drawable.play_button_bitmap_cropped);
        pauseButton = getBitmapScaled((int) ((int) screenFactorX / 3.0), (int) ((int) screenFactorY / 3.0), R.drawable.pause_button_bitmap_cropped);
    }

    private void createSnacks() {
        cheesyBites = createSnacks(parameters.NUM_CHEESY_BITES, parameters.POINTS_CHEESY_BITES, R.drawable.cheesy_bite_resized);
        paprika = createSnacks(parameters.NUM_PAPRIKA, parameters.POINTS_PAPRIKA, R.drawable.paprika_bitmap_cropped);
        cucumbers = createSnacks(parameters.NUM_CUCUMBERS, parameters.POINTS_CUCUMBER, R.drawable.cucumber_bitmap_cropped);
        broccoli = createSnacks(parameters.NUM_BROCCOLI, parameters.POINTS_BROCCOLI, R.drawable.broccoli_bitmap_cropped);
    }

    private HeroId getHeroId() {
        int heroId = prefs.getInt("choose_character", 0);
        if(heroId == 1) {
            return HeroId.TUTTI;
        }

        return HeroId.GUAPO;
    }

    private void createHero() {
        if(getHeroId().equals(HeroId.TUTTI)) {
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

    protected Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
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

    public Snack[] createSnacks(int numSnacks, int pointsForSnack, int snackId) {
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

    public void updateSnack(Snack snack) {
        if (snack.x + snack.width < 0) {
            Random rand = new Random();
            snack.x = rand.nextInt(screenWidth + 1) + screenWidth;
            snack.y = random.nextInt(screenHeight - snack.height);
        }

        if(heroInteractsWithSnack(hero, snack)) {
            score += snack.points_snack;
            snack.set_x(-500);
            if (snack.play_sound_allowed) {
                sounds.playSoundEat();
                snack.play_sound_allowed = false;
            } else {
                snack.play_sound_allowed = true;
            }
        }
    }

    private void updateHero() {
        hero.update();
    }

    protected int getLives() {
        return lives1.getLives();
    }

    protected void takeLife() {
        lives1.takeLife();
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

    private void handleHeroActionClick(MotionEvent event) {
        if(!touchInPauseArea(event) && gameIsPlaying()){
            updatePositionHero(event);
            setVelocityHeroToZero();
        }
    }

    private void handleHeroActionMove(MotionEvent event) {
        if(gameIsPlaying() && !touchInPauseArea(event)) {
            updateVelocityHero(event);
        }
    }

    private void handlePauseAction(MotionEvent event) {
        if(touchInPauseArea(event)) {
            if(gameIsPlaying()) {
                setGameStateToPaused();
            }
            else if(gameIsPaused()) {
                setGameStateToPlay();
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleHeroActionClick(event);
                handlePauseAction(event);
                break;
            case MotionEvent.ACTION_MOVE:
                handleHeroActionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    private void updatePositionHero(MotionEvent event) {
        hero.setPositionX(event.getX());
        hero.setPositionY(event.getY());
    }

    private void updateVelocityHero(MotionEvent event) {
        float velX = event.getX() - hero.getPositionX();
        float velY = event.getY() - hero.getPositionY();
        hero.setVelX(velX);
        hero.setVelY(velY);
        setMaxVelocityHero();
    }

    private void setVelocityHeroToZero() {
        hero.setVelX(0);
        hero.setVelY(0);
    }

    private void setMaxVelocityHero() {
        float velX = hero.getVelocityX();
        float velY = hero.getVelocityY();

        float maxSpeed = 100;
        if(velX * velX + velY * velY > maxSpeed * maxSpeed) {
            float ratio = velY / (velX + 1e-8f);
            float xVelocityMax = (float) sqrt(maxSpeed * maxSpeed / (1 + ratio * ratio));
            float yVelocityMax = abs(ratio * xVelocityMax);
            if(velX < 0) {
                hero.setVelX(-xVelocityMax);
            }
            else {
                hero.setVelX(xVelocityMax);
            }
            if(velY < 0) {
                hero.setVelY(-yVelocityMax);
            }
            else {
                hero.setVelY(yVelocityMax);
            }
        }

        if(velX * velX + velY * velY < 20) {
            hero.setVelX(0);
            hero.setVelY(0);
        }
    }

    private void updateSnacks(Snack[] snacks) {
        for(Snack snack : snacks) {
            updateSnack(snack);
        }
    }

    public void updateBegginStrip() {
        // TODO : implement
    }

    protected void updateNumberOfVillains() {
        int scoreThatRequiresNumberOfVillainsIncreases =
                difficultyLevel * parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL;

        if(score >= scoreThatRequiresNumberOfVillainsIncreases
                && villains.size() < parameters.MAX_NUM_BIRDS) {
            addVillain(
                    new Villain.Builder()
                            .x(-500)
                            .images(createVillainImages())
                            .build()
            );
            difficultyLevel++;
        }
    }

    protected void updateVillains() {
        updateNumberOfVillains();
        for(Villain villain : villains) {
            villain.update();
        }
    }

    public void updateAll() {
        updateHero();
        updateBegginStrip();
        updateSnacks(cheesyBites);
        updateSnacks(paprika);
        updateSnacks(cucumbers);
        updateSnacks(broccoli);
    }

    protected boolean heroHitVillain() {
        for(Villain villain : villains) {
            if(heroInteractsWithVillan(hero, villain)) {
                return true;
            }
        }

        return false;
    }

    private boolean heroInteractsWithVillan(Hero hero, Villain villain) {
        float positionX = hero.getPositionX();
        float positionY = hero.getPositionY();
        float width = hero.getWidth();
        float height = hero.getHeight();

        float birdWidth = villain.getVillainImage().getWidth();
        float birdHeight = villain.getVillainImage().getHeight();

        Rect rect_1 = new Rect(
                (int) positionX, (int) positionY,
                (int) (positionX + width / 2.0),
                (int) (positionY + height / 2.0)
        );
        Rect rect_2 = new Rect(
                (int) villain.getPositionX() + (int) ((birdWidth * 35) / 100),
                (int) villain.getPositionY() + (int) ((birdHeight * 35) / 100),
                (int) villain.getPositionX() + (int) ((birdWidth * 65) / 100),
                (int) villain.getPositionY() + (int) ((birdHeight * 65) / 100)
        );

        return Rect.intersects(rect_1, rect_2);
    }

    private boolean heroInteractsWithSnack(Hero hero, Snack snack) {
        float positionX = hero.getPositionX();
        float positionY = hero.getPositionY();
        float width = hero.getWidth();
        float height = hero.getHeight();

        float birdWidth = snack.getImage().getWidth();
        float birdHeight = snack.getImage().getHeight();

        Rect rect_1 = new Rect(
                (int) positionX, (int) positionY,
                (int) (positionX + width / 2.0),
                (int) (positionY + height / 2.0)
        );
        Rect rect_2 = new Rect(
                (int) snack.getPositionX() + (int) ((birdWidth * 35) / 100),
                (int) snack.getPositionY() + (int) ((birdHeight * 35) / 100),
                (int) snack.getPositionX() + (int) ((birdWidth * 65) / 100),
                (int) snack.getPositionY() + (int) ((birdHeight * 65) / 100)
        );

        return Rect.intersects(rect_1, rect_2);
    }

    public void saveHighScore(String score_id) {
        if (prefs.getInt(score_id, 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(score_id, score);
            editor.apply();
        }
    }

    public int getNumVillains(String store_id) {
        return prefs.getInt(store_id, 2);
    }

    protected List<Bitmap> createVillainImages() {
        List<Bitmap> images = new ArrayList<>();

        int width =  (int) (((float) screenWidth) / 10);
        int height = (int) (((float) screenHeight) / 5);

        Bitmap bird_image1 = BitmapFactory.decodeResource(getResources(), R.drawable.warawara1_bitmap_custom_mod_cropped);
        bird_image1 = Bitmap.createScaledBitmap(bird_image1, width, height, false);

        Bitmap bird_image2 = BitmapFactory.decodeResource(getResources(), R.drawable.warawara2_bitmap_custom_mod_cropped);
        bird_image2 = Bitmap.createScaledBitmap(bird_image2, width, height, false);

        Bitmap bird_image3 = BitmapFactory.decodeResource(getResources(), R.drawable.warawara3_bitmap_custom_mod_cropped);
        bird_image3 = Bitmap.createScaledBitmap(bird_image3, width, height, false);

        images.add(bird_image1);
        images.add(bird_image2);
        images.add(bird_image3);

        return images;
    }

    protected void addVillain(Villain villain) {
        this.villains.add(villain);
    }

    public boolean isActiveSession(String store_id) {
        return prefs.getBoolean(store_id, false);
    }

    public void getNumLives(String store_id) {
        numLives = prefs.getInt(store_id, 3);
    }

    public void getScore(String store_id) {
        score = prefs.getInt(store_id, 0);
    }

    protected void drawSnacks(Canvas canvas, Snack [] snacks) {
        for (Snack snack : snacks) {
            snack.draw(canvas);
        }
    }

    protected void drawSnack(Canvas canvas, Snack snack) {
       snack.draw(canvas);
    }

    protected void drawScore(Canvas canvas) {
        canvas.drawText(" " + score, 30, (float) screenHeight / 6, paint);
    }

    public void drawCharacters(Canvas canvas) {
        // TODO : implement
    }

    public void drawVillains(Canvas canvas) {
        for(Villain villain : villains) {
            villain.draw(canvas);
        }
    }

    public void drawLives(Canvas canvas) {
        int left_most_x = screenWidth / 2 - 20;
        for (int i = 0; i < numLives; i++) {
            canvas.drawBitmap(lives[i], left_most_x, 20, null);
            left_most_x += lives[i].getWidth() + 5;
        }
    }

    public void drawPauseButton(Canvas canvas) {
        if(gameIsPaused()) {
            canvas.drawBitmap(playButton, pauseRegionMaxX - playButton.getWidth(), pauseRegionMinY, null);
        }
        else {
            canvas.drawBitmap(pauseButton, pauseRegionMaxX - pauseButton.getWidth(), pauseRegionMinY, null);
        }
    }

    protected void drawHero(Canvas canvas) {
        hero.draw(canvas);
    }

    public void drawAll(Canvas canvas) {
        drawSnacks(canvas, cheesyBites);
        drawSnacks(canvas, paprika);
        drawSnacks(canvas, cucumbers);
        drawSnacks(canvas, broccoli);
        drawScore(canvas);
        drawCharacters(canvas);
        drawPauseButton(canvas);
    }

    private boolean touchInPauseArea(MotionEvent event) {
        return (event.getX() >= pauseRegionMinX - pauseButton.getWidth() / 2.0)
                && (event.getX() <= screenWidth) && (event.getY() >= 0)
                && (event.getY() <=  (float) (pauseRegionMaxY + pauseButton.getHeight() / 2));
    }
}
