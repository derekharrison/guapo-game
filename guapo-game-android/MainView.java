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
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainView extends SurfaceView {

    public int background_speed;
    public Snack beggin_strip;
    public Snack[] broccoli;
    public CharacterImage brownie;
    public Snack[] cheesy_bites;
    public Bitmap continue_button_not_pressed;
    public Bitmap continue_button_pressed;
    public boolean continue_restart_pressed = false;
    public Snack[] cucumbers;
    public int difficulty_level = 0;

    public CharacterImage frito;
    public Bitmap[] lives;
    public int max_eat;
    public Misty misty;
    public boolean misty_comes_from_top = false;
    public Misty misty_top;
    public int num_lives = 3;
    public Paint paint;
    public Snack[] paprika;
    public Parameters parameters;
    public Bitmap pause_button;
    public int pause_region_min_x;
    public int pause_region_max_x;
    public int pause_region_min_y;
    public int pause_region_max_y;
    public Bitmap play_button;
    public SharedPreferences prefs;
    public Random random;
    public Bitmap restart_game_not_pressed;
    public Bitmap restart_game_pressed;
    public int score = 0;
    public int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public float screen_factor_x, screen_factor_y;
    public int sound1, sound3;
    public int sound_brownie_appearing;
    public int sound_brownie_hit;
    public int sound_bubbles;
    public int sound_frito_appearing;
    public int sound_frito_hit;
    public int sound_hit_bird;
    public int sound_misty_appearing;
    public int sound_misty_hit;
    public int sound_sun_popup;
    public int sound_tutti_eating_knaagstok;
    public int sound_tutti_eating_pathe;
    public int sound_tutti_eating_tosti;
    public SoundPool soundPool;
    public Sunpopup sun_popup;

    private Hero hero;
    private Lives lives1;
    private List<Villain> villains = new ArrayList<>();

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
    }
    
     public MainView(Context activity) {
        super(activity);

        setParameters();

        // Get display dimensions
        initDisplay();

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        // Initialize images and positions
        initImagesAndPositions();

        // Setup sound pool
        initSounds(activity);

        lives1 = new Lives();
    }

    public void setParameters() {
        parameters = new Parameters();
    }

    public void initImagesAndPositions() {
        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);

        // Initialize images
        initImages();
    }

    public void initDisplay() {
        screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public void initLives() {
        num_lives = prefs.getInt("num_lives", parameters.NUM_LIVES);

        lives = new Bitmap[num_lives];

        for(int i = 0; i < num_lives; i++) {
            lives[i] = BitmapFactory.decodeResource(getResources(), R.drawable.heart1_bitmap_cropped);
            lives[i] = Bitmap.createScaledBitmap(lives[i], (int) screen_factor_x / 4, (int) screen_factor_y / 4, false);
        }
    }

    public void initImages() {
        screen_factor_x = (int) (((float) screenWidth) / 10);
        screen_factor_y = (int) (((float) screenHeight) / 5);

        background_speed = (int) (((float) screenWidth) / 400);

        setScreenWidth(screenWidth);
        setScreenHeight(screenHeight);
        setBackgroundSpeed(background_speed);

        cheesy_bites = initSnacks(parameters.NUM_CHEESY_BITES, parameters.POINTS_CHEESY_BITES, R.drawable.cheesy_bite_resized);
        paprika = initSnacks(parameters.NUM_PAPRIKA, parameters.POINTS_PAPRIKA, R.drawable.paprika_bitmap_cropped);
        cucumbers = initSnacks(parameters.NUM_CUCUMBERS, parameters.POINTS_CUCUMBER, R.drawable.cucumber_bitmap_cropped);
        broccoli = initSnacks(parameters.NUM_BROCCOLI, parameters.POINTS_BROCCOLI, R.drawable.broccoli_bitmap_cropped);

        // Initialize beggin strip. There is only one beggin strip.
        beggin_strip = new Snack(getResources(), (int) screen_factor_x, (int) screen_factor_y, R.drawable.beggin_strip_cropped);
        beggin_strip.points_snack = parameters.POINTS_BEGGIN_STRIPS;

        // Initialize characters
        frito = initCharacter((int) screen_factor_x, (int) screen_factor_y, R.drawable.frito_bitmap_cropped, R.drawable.frito_bitmap_rotated_cropped, CharacterIds.FRITO);

        brownie = initCharacter((int) (6* screen_factor_x)/5, (int) (6* screen_factor_y)/5, R.drawable.brownie2_bitmap_cropped, R.drawable.brownie1_bitmap_cropped, CharacterIds.BROWNIE);

        // Initialize Misty
        initMisty();

        // Initialize level unlock popup
        sun_popup = new Sunpopup(getResources(), (int) screen_factor_x, (int) screen_factor_y, R.drawable.sun_popup_bitmap_cropped);

        // Initialize pause and play button images
        pause_button = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button_bitmap_cropped);
        pause_button = Bitmap.createScaledBitmap(pause_button, (int) screen_factor_x / 4, (int) screen_factor_y / 4, false);

        play_button = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_bitmap_cropped);
        play_button = Bitmap.createScaledBitmap(play_button, (int) screen_factor_x / 4, (int) screen_factor_y / 4, false);

        // Initialize game restart and continue buttons
        continue_button_not_pressed = BitmapFactory.decodeResource(getResources(), R.drawable.continue_button_not_pressed_bitmap_cropped);
        continue_button_not_pressed = Bitmap.createScaledBitmap(continue_button_not_pressed, (int) screen_factor_x, (int) screen_factor_y, false);

        continue_button_pressed = BitmapFactory.decodeResource(getResources(), R.drawable.continue_button_pressed_bitmap_cropped);
        continue_button_pressed = Bitmap.createScaledBitmap(continue_button_pressed, (int) screen_factor_x, (int) screen_factor_y, false);

        restart_game_not_pressed = BitmapFactory.decodeResource(getResources(), R.drawable.restart_button_not_pressed_bitmap_cropped);
        restart_game_not_pressed = Bitmap.createScaledBitmap(restart_game_not_pressed, (int) screen_factor_x, (int) screen_factor_y, false);

        restart_game_pressed = BitmapFactory.decodeResource(getResources(), R.drawable.restart_button_pressed_bitmap_cropped);
        restart_game_pressed = Bitmap.createScaledBitmap(restart_game_pressed, (int) screen_factor_x, (int) screen_factor_y, false);

        // Define pause region
        pause_region_max_x = screenWidth - screenWidth / 30;
        pause_region_min_x = pause_region_max_x - pause_button.getWidth() - 3 * screenWidth / 60;
        pause_region_max_y = 5 * screenHeight / 30;
        pause_region_min_y = screenHeight / 15;

        initLives();

        hero = createHero();
    }

    private HeroId getHeroId() {
        int heroId = prefs.getInt("choose_character", 0);
        if(heroId == 1) {
            return HeroId.TUTTI;
        }

        return HeroId.GUAPO;
    }

    private Hero createHero() {
        if(getHeroId().equals(HeroId.TUTTI)) {
            return createTutti();
        }

        return createGuapo();
    }

    private Hero createGuapo() {
        int startPosX = (int) (((float) screenWidth) / 10);
        int startPosY = (int) (((float) screenHeight) / 2);
        int width = (int) screen_factor_x * 3 / 2;
        int height = (int) screen_factor_y * 3 / 2;

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
        int width = (int) screen_factor_x * 3 / 2;
        int height = (int) screen_factor_y * 3 / 2;

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

    private void initSoundPool() {
        int num_sounds_eating = 3;
        max_eat = num_sounds_eating - 1;
        int min_number_of_sound_streams = 7;
        int num_sounds_cat = 2;
        int num_sounds_brownie = 1;
        int num_sounds_sun_popup = 1;
        int num_sounds_bubbles = 1;

        int tot_num_sounds = min_number_of_sound_streams + 1 + num_sounds_eating + num_sounds_cat + num_sounds_brownie + num_sounds_sun_popup + num_sounds_bubbles;

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(tot_num_sounds)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    public void initSounds(Context game_activity) {
        initSoundPool();

        sound_hit_bird = soundPool.load(game_activity, R.raw.tutti_0, 1);
        sound_misty_hit = soundPool.load(game_activity, R.raw.tutti_1, 1);
        sound_frito_hit = soundPool.load(game_activity, R.raw.tutti_4, 1);
        sound_brownie_hit = soundPool.load(game_activity, R.raw.tutti_5, 1);
        sound_tutti_eating_knaagstok = soundPool.load(game_activity, R.raw.tutti_eating_knaagstok, 1);
        sound_tutti_eating_pathe = soundPool.load(game_activity, R.raw.tuttu_eating_pathe, 1);
        sound_frito_appearing = soundPool.load(game_activity, R.raw.cat_sound1, 1);
        sound_brownie_appearing = soundPool.load(game_activity, R.raw.tutti_6, 1);
        sound_misty_appearing = soundPool.load(game_activity, R.raw.misty_sounds, 1);
        sound_sun_popup = soundPool.load(game_activity, R.raw.sun_popup_sound, 1);
        sound1 = soundPool.load(game_activity, R.raw.tutti_1, 1);
        sound3 = soundPool.load(game_activity, R.raw.tutti_3, 1);
        sound_bubbles = soundPool.load(game_activity, R.raw.bubble_sounds, 1);
        sound_tutti_eating_tosti = soundPool.load(game_activity, R.raw.tutti_eating_tosti, 1);
    }

    public Snack[] initSnacks(int num_cheesy_bites, int points_snack, int snack_id) {
        Snack[] snacks = new Snack[num_cheesy_bites];

        random = new Random();

        for (int i = 0;i < num_cheesy_bites;i++) {

            Snack snack = new Snack(getResources(), (int) screen_factor_x, (int) screen_factor_y, snack_id);
            snack.x = random.nextInt(2 * screenWidth - snack.get_snack_image().getWidth()/2);
            snack.y = random.nextInt(screenHeight - snack.get_snack_image().getHeight()/2);
            snack.points_snack = points_snack;
            snacks[i] = snack;

        }

        return snacks;
    }

    public CharacterImage initCharacter(int screenFactorX, int screenFactorY, int char_id, int char_id_hit, CharacterIds character_type) {
        random = new Random();

        CharacterImage character = new CharacterImage(getResources(), screenFactorX, screenFactorY, char_id, char_id_hit, character_type);

        character.x = random.nextInt(screenWidth - character.get_character_image().getWidth() / 2) + 15 * screenWidth;
        character.y = random.nextInt(screenHeight - character.get_character_image().getHeight() / 2);
        character.y_vel = 2 * background_speed;

        return character;
    }

    public void initMisty() {
        misty = new Misty(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.misty_bitmap_cropped,
                R.drawable.misty_hit_bitmap_cropped,
                CharacterIds.MISTY);

        misty.x = 23 * screenWidth;
        misty.pop_up_at_x = random.nextInt((screenWidth * 3) / 4 - screenWidth / 4 + 1) + screenWidth / 4;
        misty.y_vel = (2 * background_speed) / 4;
        misty.y = screenHeight;
        misty.is_top = false;

        misty_top = new Misty(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.misty_bitmap_cropped_rotated,
                R.drawable.misty_hit_bitmap_cropped_rotated,
                CharacterIds.MISTY_TOP);
        misty_top.x = misty.x;
        misty_top.pop_up_at_x = misty.pop_up_at_x;
        misty_top.y_vel = (2 * background_speed) / 4;
        misty_top.y = -misty_top.get_misty().getHeight();
        misty_top.is_top = true;
        updateMistyPos();
    }

    public void playSoundEat() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        Random rand = new Random();
        int randomNum = rand.nextInt(max_eat);
        if(!is_mute) {
            switch (randomNum) {
                case 0:
                    soundPool.play(sound_tutti_eating_knaagstok, 1, 1, 0, 0, 1);
                    break;
                case 1:
                    soundPool.play(sound_tutti_eating_pathe, (float) 0.7, (float) 0.7, 0, 0, 1);
                    break;
            }
        }
    }

    public void playSoundBarkHit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_hit_bird, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundCatAppearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_frito_appearing, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBrownieAppearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_brownie_appearing, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundMistyAppearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_misty_appearing, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void playSoundSunPopup() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_sun_popup, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void playSoundBarkFritoHit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_frito_hit, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBarkBrownieHit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_brownie_hit, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBarkMistyHit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_misty_hit, 1, 1, 0, 0, 1);
        }
    }

    public void updateMistyPos() {
        int randomNum = random.nextInt(2);
        switch (randomNum) {
            case 0:
                misty_comes_from_top = false;
                break;
            case 1:
                misty_comes_from_top = true;
                break;
        }
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
                playSoundEat();
                snack.play_sound_allowed = false;
            } else {
                snack.play_sound_allowed = true;
            }
        }
    }

    private void updateHero() {
        hero.update();
    }

    private boolean touchInPauseArea(MotionEvent event) {
        return (event.getX() >= pause_region_min_x - pause_button.getWidth() / 2.0)
                && (event.getX() <= screenWidth) && (event.getY() >= 0)
                && (event.getY() <=  (float) (pause_region_max_y + pause_button.getHeight() / 2));
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

    void play_sound_bark_animal_hit(CharacterIds char_id) {
        if(char_id == CharacterIds.FRITO) {
            playSoundBarkFritoHit();
        }
        if(char_id == CharacterIds.BROWNIE) {
            playSoundBarkBrownieHit();
        }
        if(char_id == CharacterIds.MISTY || char_id == CharacterIds.MISTY_TOP) {
            playSoundBarkMistyHit();
        }
    }

    void check_if_hit_character(CharacterImage animal) {
        Rect rect_1 = new Rect((int) (hero.getPositionX() - hero.getWidth() / 3), (int) (hero.getVelocityY() - hero.getHeight() / 3), (int) (hero.getPositionX() + hero.getWidth()), (int) (hero.getPositionY() - hero.getHeight() / 3 + hero.getHeight()));
        Rect rect_2 = new Rect(animal.x + animal.get_character_image().getWidth() / 5,
                animal.y + animal.get_character_image().getHeight() / 5,
                animal.x + ((animal.get_character_image().getWidth() * 4) / 5),
                animal.y + ((animal.get_character_image().getHeight()) * 4) / 5);

        if (Rect.intersects(rect_1, rect_2)) {
            if(animal.play_sound_allowed) {
                play_sound_bark_animal_hit(animal.character_id);
                animal.hit_character = true;
                animal.play_sound_allowed = false;
            }
        }
    }

    private void updateSnacks(Snack[] snacks) {
        for(Snack snack : snacks) {
            updateSnack(snack);
        }
    }

    public void updateBegginStrip() {
        if(score >= parameters.POINTS_WHEN_BEGGIN_STRIPS_APPEAR) {
            beggin_strip.x -= (int) beggin_strip.getVelocityX();
            if (beggin_strip.x + beggin_strip.width < 0) {
                Random rand = new Random();
                beggin_strip.x = rand.nextInt(2 * screenWidth + 1) + screenWidth;
                beggin_strip.y = random.nextInt(screenHeight - beggin_strip.height);
            }

            if (heroInteractsWithSnack(hero, beggin_strip)) {
                score = score + parameters.POINTS_BEGGIN_STRIPS;
                if(beggin_strip.play_sound_allowed) {
                    playSoundEat();
                    beggin_strip.play_sound_allowed = false;
                }
                beggin_strip.x = -500;
            }
            else {
                beggin_strip.play_sound_allowed = true;
            }
        }
    }

    public void updateFrito() {
        frito.speed = 2 * background_speed;
        frito.x -= frito.speed;

        // Frito goes out of bounds
        if (frito.x + frito.width < 0) {

            Random rand = new Random();

            frito.x = rand.nextInt(screenWidth + 1) + 23 * screenWidth;
            frito.y = random.nextInt(screenHeight - frito.height);

            frito.hit_character = false;
            frito.appeared = false;

            frito.play_sound_allowed = true;
        }

        // Frito appeared on screen
        if(frito.x < screenWidth && (frito.x + frito.width >= 0) && !frito.appeared) {
            playSoundCatAppearing();
            frito.appeared = true;
        }

        frito.y += frito.y_vel;

        if ((frito.y > screenHeight - frito.get_character_image().getHeight()) || (frito.y < 0)) {
            frito.y_vel = frito.y_vel * -1;
        }
        if(frito.y > screenHeight - frito.get_character_image().getHeight()) {
            frito.y = screenHeight - frito.get_character_image().getHeight();
        }
        if(frito.y < 0) {
            frito.y = 0;
        }

        check_if_hit_character(frito);
    }

    public void updateBrownie() {
        if ((brownie.x < screenWidth) && (brownie.x + brownie.width >= 0)) {
            brownie.speed = (3 * background_speed) / 2;
        }
        else {
            brownie.speed = (2 * background_speed);
        }

        brownie.x += brownie.speed;

        // Brownie went out of bounds
        if (brownie.x > screenWidth) {

            Random rand = new Random();

            brownie.x = rand.nextInt(screenWidth + 1) - 24 * screenWidth;
            brownie.y = random.nextInt(screenHeight - brownie.height);

            brownie.hit_character = false;
            brownie.appeared = false;

            brownie.play_sound_allowed = true;
        }

        // Brownie appeared on screen
        if(brownie.x < screenWidth && (brownie.x + brownie.width >= 0) && !brownie.appeared) {
            playSoundBrownieAppearing();
            brownie.appeared = true;
        }

        brownie.y += brownie.y_vel;

        if ((brownie.y > screenHeight - brownie.get_character_image().getHeight()) || (brownie.y < 0)) {
            brownie.y_vel = brownie.y_vel * -1;
        }
        if(brownie.y > screenHeight - brownie.get_character_image().getHeight()) {
            brownie.y = screenHeight - brownie.get_character_image().getHeight();
        }
        if(brownie.y < 0) {
            brownie.y = 0;
        }

        check_if_hit_character(brownie);
    }

    public void update_misty_fcn(Misty misty) {
        misty.speed = 2 * background_speed;
        misty.x -= misty.speed;

        // Misty went out of bounds
        if (misty.x + misty.width < 0) {

            Random rand = new Random();

            misty.x = 23 * screenWidth;
            misty.pop_up_at_x = rand.nextInt((screenWidth * 3) / 4 - screenWidth / 4 + 1) + screenWidth / 4;
            if(!misty.is_top) {
                misty.y = screenHeight;
            }
            else {
                misty.y = -misty.get_misty().getHeight();
            }
            misty.y_vel = misty.y_vel * -1;

            misty.hit_character = false;
            misty.appeared = false;

            misty.play_sound_allowed = true;

            updateMistyPos();

        }

        // Misty appeared on screen
        if(misty.x < screenWidth && (misty.x + misty.width >= 0) && !misty.appeared && misty.is_top == misty_comes_from_top) {
            playSoundMistyAppearing();
            misty.appeared = true;
        }

        // Update Misty position
        if(misty.x < screenWidth && (misty.x + misty.width >= 0) && !misty.is_top) {
            misty.y -= misty.y_vel;
        }
        if(misty.x < screenWidth && (misty.x + misty.width >= 0) && misty.is_top) {
            misty.y += misty.y_vel;
        }

        // Update Misty velocity
        if(misty.y < screenHeight - misty.get_misty().getHeight() && !misty.is_top) {
            misty.y_vel = misty.y_vel * -1;
        }
        if(misty.y > 0 && misty.is_top) {
            misty.y_vel = misty.y_vel * -1;
        }

        // Check if Misty and Guapo intersect
        float misty_width = (float) misty.get_misty().getWidth();
        float misty_height = (float) misty.get_misty().getHeight();

        if(misty.appeared && misty.is_top == misty_comes_from_top) {
            Rect rect_1 = new Rect((int) hero.getPositionX(),
                    (int) hero.getPositionY(),
                    (int) (hero.getPositionX() + hero.getWidth()),
                    (int) (hero.getPositionY() + hero.getHeight()));
            Rect rect_2 = new Rect(misty.pop_up_at_x, misty.y,
                    misty.pop_up_at_x + (int) ((misty_width * 5) / 5),
                    misty.y + (int) ((misty_height) * 5) / 5);

            if (Rect.intersects(rect_1, rect_2)) {
                if (misty.play_sound_allowed ) {
                    playSoundBarkMistyHit();
                    misty.hit_character = true;
                    misty.play_sound_allowed = false;
                }
            }
        }
    }

    public void updateMisty() {

        // Update Misty bottom
        update_misty_fcn(misty);

        // Update Misty top
        update_misty_fcn(misty_top);
    }

    protected void updateNumberOfVillains() {
        int scoreThatRequiresNumberOfVillainsIncreases =
                difficulty_level * parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL;

        if(score >= scoreThatRequiresNumberOfVillainsIncreases
                && villains.size() < parameters.MAX_NUM_BIRDS) {
            addVillain(
                    new Villain.Builder()
                            .x(-500)
                            .images(createVillainImages())
                            .build()
            );
            difficulty_level++;
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
        updateSnacks(cheesy_bites);
        updateSnacks(paprika);
        updateSnacks(cucumbers);
        updateSnacks(broccoli);

        updateFrito();
        updateBrownie();
        updateMisty();
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

    private float getXOfVillainFromStore(String storeId, int index) {
        return prefs.getFloat(storeId + "x" + index, -screenWidth);
    }

    private float getYOfVillainFromStore(String storeId, int index) {
        return prefs.getFloat(storeId + "y" + index, -screenHeight);
    }

    private float getVelXOfVillainFromStore(String storeId, int index) {
        return prefs.getFloat(storeId + "vx" + index, 0);
    }

    public void getVillains(String store_id, String level) {
        int numVillains = getNumVillains(parameters.NUM_BIRDS_STR + level);
        for(int index = 0; index < numVillains; index++) {
            villains.add(
                    new Villain.Builder()
                    .x(getXOfVillainFromStore(store_id, index))
                    .y(getYOfVillainFromStore(store_id, index))
                    .velX(getVelXOfVillainFromStore(store_id, index))
                    .images(createVillainImages())
                    .build()
            );
        }
    }

    protected void addVillains(List<Villain> villains) {
        this.villains.addAll(villains);
    }

    protected void addVillain(Villain villain) {
        this.villains.add(villain);
    }


    public boolean isActiveSession(String store_id) {
        return prefs.getBoolean(store_id, false);
    }

    public void getNumLives(String store_id) {
        num_lives = prefs.getInt(store_id, 3);
    }

    public void getScore(String store_id) {
        score = prefs.getInt(store_id, 0);
    }

    public void drawSnacks(Canvas canvas, Snack [] snacks) {
        for (Snack snack : snacks)
            canvas.drawBitmap(snack.get_snack_image(), snack.x, snack.y, null);
    }

    public void drawCharacters(Canvas canvas) {
        if(!frito.hit_character) {
            canvas.drawBitmap(frito.get_character_image(), frito.x, frito.y, null);
        }
        else if(frito.hit_character) {
            canvas.drawBitmap(frito.get_image_hit(), frito.x, frito.y, null);
        }

        if(!brownie.hit_character) {
            canvas.drawBitmap(brownie.get_character_image(), brownie.x, brownie.y, null);
        }
        else if(brownie.hit_character) {
            canvas.drawBitmap(brownie.get_image_hit(), brownie.x, brownie.y, null);
        }

        if(!misty.hit_character && !misty_comes_from_top) {
            canvas.drawBitmap(misty.get_misty(), misty.pop_up_at_x, misty.y, null);
        }
        else if(misty.hit_character && !misty_comes_from_top) {
            canvas.drawBitmap(misty.get_misty_hit(), misty.pop_up_at_x, misty.y, null);
        }

        if(!misty_top.hit_character && misty_comes_from_top) {
            canvas.drawBitmap(misty_top.get_misty(), misty_top.pop_up_at_x, misty_top.y, null);
        }
        else if(misty_top.hit_character && misty_comes_from_top) {
            canvas.drawBitmap(misty_top.get_misty_hit(), misty_top.pop_up_at_x, misty_top.y, null);
        }
    }

    public void drawVillains(Canvas canvas) {
        for(Villain villain : villains) {
            villain.draw(canvas);
        }
    }

    public void drawLives(Canvas canvas, int left_most_x, int y) {
        for (int i = 0; i < num_lives; i++) {
            canvas.drawBitmap(lives[i], left_most_x, y, null);
            left_most_x += lives[i].getWidth() + 5;
        }
    }

    public void drawPauseButton(Canvas canvas) {
        if(gameIsPaused()) {
            canvas.drawBitmap(play_button, pause_region_max_x - play_button.getWidth(), pause_region_min_y, null);
        }
        else {
            canvas.drawBitmap(pause_button, pause_region_max_x - pause_button.getWidth(), pause_region_min_y, null);
        }
    }

    protected void drawHero(Canvas canvas) {
        hero.draw(canvas);
    }

    protected void drawSunPopup(Canvas canvas, String score_id) {
        if((prefs.getInt(score_id, 0) < parameters.LEVEL_UNLOCK_SCORE) &&
                (score >= parameters.LEVEL_UNLOCK_SCORE) &&
                (sun_popup.popup_counter < parameters.NUM_FRAMES_POPUP)) {
            canvas.drawBitmap(sun_popup.get_image(), (float) (screenHeight * 2) / 6, (float) screenHeight / 35, null);
            if(!sun_popup.popped_up) {
                playSoundSunPopup();
                sun_popup.popped_up = true;
            }
            sun_popup.popup_counter++;
            if(sun_popup.popup_counter >= parameters.NUM_FRAMES_POPUP - 1) {
                saveHighScore(score_id);
            }
        }
    }

    // TODO : implement checkpoints

    public void drawAll(Canvas canvas) {
        drawSnacks(canvas, cheesy_bites);

        drawSnacks(canvas, paprika);

        drawSnacks(canvas, cucumbers);

        drawSnacks(canvas, broccoli);

        canvas.drawBitmap(beggin_strip.get_snack_image(), beggin_strip.x, beggin_strip.y, null);

        drawCharacters(canvas);

        // Draw score
        canvas.drawText(" " + score, 30, (float) screenHeight / 6, paint);

        drawPauseButton(canvas);
    }
}
