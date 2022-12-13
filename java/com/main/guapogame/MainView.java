package com.main.guapogame;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;

public class MainView extends SurfaceView {

    public int action;
    public int background_speed;
    public Snack beggin_strip;
    public Snack[] broccoli;
    public CharacterImage brownie;
    public Bitmap cape_image;
    public int checkpoint_num = 0;
    public Snack[] cheesy_bites;
    public Snack[] cucumbers;
    public int difficulty_level = 0;
    public Fish[] fishes1;
    public Fish[] fishes2;
    public Fish[] fishes3;
    public Fish[] fishes4;
    public Fish[] fishes5;
    public Fish[] fishes7;
    public Pufferfish[] pufferfishes;
    public Flag flag_checkpoint;
    public CharacterImage frito;
    public boolean game_paused = false;
    public Bitmap guapo_body;
    public Bitmap guapo_head;
    public Bitmap guapo_head_hit;
    public GuapoImage guapo_image;
    public int guapo_loc_x;
    public int guapo_loc_x_o;
    public int guapo_loc_y;
    public int guapo_loc_y_o;
    public boolean hit_bird = false;
    public boolean is_playing;
    public Bitmap[] lives;
    public float loc_x;
    public float loc_y;
    public int max_eat;
    public Misty misty;
    public boolean misty_comes_from_top = false;
    public Misty misty_top;
    public int num_birds = 2; // Initial number of birds - 1
    public int num_jelly_fish = 2; // Initial number of jelly_fish - 1
    public int num_lives = 3;
    public Paint paint;
    public Snack[] paprika;
    public Parameters parameters;
    public Bitmap pause_button;
    public boolean paused_is_set = false;
    public int pause_region_min_x;
    public int pause_region_max_x;
    public int pause_region_min_y;
    public int pause_region_max_y;
    public Bitmap play_button;
    public SharedPreferences prefs;
    public Random random;
    public int score = 0;
    public int screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;
    public int screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;
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
    public int x_velocity = 0;
    public int x_velocity_o = 0;
    public int y_velocity = 0;
    public int y_velocity_o = 0;
    public Bitmap continue_button_not_pressed;
    public Bitmap continue_button_pressed;
    public Bitmap restart_game_not_pressed;
    public Bitmap restart_game_pressed;
    public boolean saved_state = false;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
    }
    
     public MainView(Context activity) {
        super(activity);

        set_parameters();

        // Get display dimensions
        init_display();

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        // Initialize images and positions
        init_images_and_positions();

        // Setup sound pool
        init_sounds(activity);
    }

    public void set_parameters() {

        parameters = new Parameters();
    }

    public void init_images_and_positions() {

        paint = new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);

        loc_x = 100;
        loc_y = 100;
        guapo_loc_x = 100;
        guapo_loc_y = 100;
        guapo_loc_x_o = 100;
        guapo_loc_y_o = 100;
        action = 3;

        // Initialize images
        init_images();
    }

    public void init_display() {
        screen_height = Resources.getSystem().getDisplayMetrics().heightPixels;
        screen_width = Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public void init_lives() {
        num_lives = prefs.getInt("num_lives", parameters.NUM_LIVES);

        lives = new Bitmap[num_lives];

        for(int i = 0; i < num_lives; i++) {
            lives[i] = BitmapFactory.decodeResource(getResources(), R.drawable.heart1_bitmap_cropped);
            lives[i] = Bitmap.createScaledBitmap(lives[i], (int) screen_factor_x / 4, (int) screen_factor_y / 4, false);
        }
    }

    public void init_images() {

        screen_factor_x = (int) (((float) screen_width) / 10);
        screen_factor_y = (int) (((float) screen_height) / 5);

        background_speed = (int) (((float) screen_width) / 200);

        cheesy_bites = init_snacks(parameters.NUM_CHEESY_BITES, parameters.POINTS_CHEESY_BITES, R.drawable.cheesy_bite_resized);

        paprika = init_snacks(parameters.NUM_PAPRIKA, parameters.POINTS_PAPRIKA, R.drawable.paprika_bitmap_cropped);

        cucumbers = init_snacks(parameters.NUM_CUCUMBERS, parameters.POINTS_CUCUMBER, R.drawable.cucumber_bitmap_cropped);

        broccoli = init_snacks(parameters.NUM_BROCCOLI, parameters.POINTS_BROCCOLI, R.drawable.broccoli_bitmap_cropped);

        // Initialize beggin strip. There is only one beggin strip.
        beggin_strip = new Snack(getResources(), (int) screen_factor_x, (int) screen_factor_y, R.drawable.beggin_strip_cropped);
        beggin_strip.points_snack = parameters.POINTS_BEGGIN_STRIPS;

        // Initialize characters
        frito = init_character((int) screen_factor_x, (int) screen_factor_y, R.drawable.frito_bitmap_cropped, R.drawable.frito_bitmap_rotated_cropped, CharacterIds.FRITO);

        brownie = init_character((int) (6* screen_factor_x)/5, (int) (6* screen_factor_y)/5, R.drawable.brownie2_bitmap_cropped, R.drawable.brownie1_bitmap_cropped, CharacterIds.BROWNIE);

        // Initialize Misty
        init_misty();

        // Initialize level unlock popup
        sun_popup = new Sunpopup(getResources(), (int) screen_factor_x, (int) screen_factor_y, R.drawable.sun_popup_bitmap_cropped);

        // Initialize checkpoint flag
        flag_checkpoint = new Flag(getResources(), (int) screen_factor_x / 2, (int) screen_factor_y, R.drawable.flag_bitmap_cropped);

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
        pause_region_max_x = screen_width - screen_width / 30;
        pause_region_min_x = pause_region_max_x - pause_button.getWidth() - 3 * screen_width / 60;
        pause_region_max_y = 5 * screen_height / 30;
        pause_region_min_y = screen_height / 15;

        // Initialize images related to Guapo
        guapo_image = new GuapoImage(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.guapo_head_bitmap_cropped,
                R.drawable.cape1_guapo_bitmap_cropped,
                R.drawable.cape2_guapo_bitmap_cropped);

        guapo_head = guapo_image.get_image();
        cape_image = guapo_image.get_cape();

        guapo_head_hit = BitmapFactory.decodeResource(getResources(), R.drawable.guapo_hit_bitmap_cropped);
        guapo_head_hit = Bitmap.createScaledBitmap(guapo_head_hit, (int) screen_factor_x, (int) screen_factor_y, false);

        guapo_body = BitmapFactory.decodeResource(getResources(), R.drawable.guapo_body_bitmap_cropped);
        guapo_body = Bitmap.createScaledBitmap(guapo_body, (int) ((screen_factor_x * 4) / 3), (int) ((screen_factor_y * 2) / 3), false);

        init_lives();
    }

    public void init_sound_pool() {

        int num_sounds_eating = 3;
        max_eat = num_sounds_eating - 1;
        int min_number_of_sound_streams = 7;
        int num_sounds_cat = 2;
        int num_sounds_brownie = 1;
        int num_sounds_sun_popup = 1;
        int num_sounds_bubbles = 1;

        int tot_num_sounds = min_number_of_sound_streams + 1 + num_sounds_eating + num_sounds_cat + num_sounds_brownie + num_sounds_sun_popup + num_sounds_bubbles;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(tot_num_sounds)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            soundPool = new SoundPool(tot_num_sounds, AudioManager.STREAM_MUSIC, 0);
        }
    }

    public void init_sounds(Context game_activity) {

        init_sound_pool();

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

    public Snack[] init_snacks(int num_cheesy_bites, int points_snack, int snack_id) {
        Snack[] snacks = new Snack[num_cheesy_bites];

        random = new Random();

        for (int i = 0;i < num_cheesy_bites;i++) {

            Snack snack = new Snack(getResources(), (int) screen_factor_x, (int) screen_factor_y, snack_id);
            snack.x = random.nextInt(2 * screen_width - snack.get_snack_image().getWidth()/2);
            snack.y = random.nextInt(screen_height - snack.get_snack_image().getHeight()/2);
            snack.points_snack = points_snack;
            snacks[i] = snack;

        }

        return snacks;
    }

    public CharacterImage init_character(int screenFactorX, int screenFactorY, int char_id, int char_id_hit, CharacterIds character_type) {
        random = new Random();

        CharacterImage character = new CharacterImage(getResources(), screenFactorX, screenFactorY, char_id, char_id_hit, character_type);

        character.x = random.nextInt(screen_width - character.get_character_image().getWidth() / 2) + 15 * screen_width;
        character.y = random.nextInt(screen_height - character.get_character_image().getHeight() / 2);
        character.y_vel = 2 * background_speed;

        return character;
    }

    public void init_misty() {

        misty = new Misty(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.misty_bitmap_cropped,
                R.drawable.misty_hit_bitmap_cropped,
                CharacterIds.MISTY);

        misty.x = 23 * screen_width;
        misty.pop_up_at_x = random.nextInt((screen_width * 3) / 4 - screen_width / 4 + 1) + screen_width / 4;
        misty.y_vel = (2 * background_speed) / 4;
        misty.y = screen_height;
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
        update_misty_pos();
    }

    public void play_sound_eat() {
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

    public void play_sound_bark_hit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_hit_bird, 1, 1, 0, 0, 1);
        }
    }

    public void play_sound_cat_appearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_frito_appearing, 1, 1, 0, 0, 1);
        }
    }

    public void play_sound_brownie_appearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_brownie_appearing, 1, 1, 0, 0, 1);
        }
    }

    public void play_sound_misty_appearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_misty_appearing, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void play_sound_sun_popup() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_sun_popup, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void play_sound_bark_frito_hit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_frito_hit, 1, 1, 0, 0, 1);
        }
    }

    public void play_sound_bark_brownie_hit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_brownie_hit, 1, 1, 0, 0, 1);
        }
    }

    public void play_sound_bark_misty_hit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_misty_hit, 1, 1, 0, 0, 1);
        }
    }

    public void update_misty_pos() {
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

    public int[] update_sprite(float loc_x, float loc_y, int action) {

        guapo_head = guapo_image.get_image();
        cape_image = guapo_image.get_cape();

        if((action == MotionEvent.ACTION_MOVE) && !game_paused) {
            guapo_loc_x_o = guapo_loc_x;
            guapo_loc_x = (int) loc_x - guapo_head.getWidth() / 2;
            if((abs(guapo_loc_x - guapo_loc_x_o) < guapo_head.getWidth())) {
                x_velocity = guapo_loc_x - guapo_loc_x_o;
            }
            guapo_loc_y_o = guapo_loc_y;
            guapo_loc_y = (int) loc_y - guapo_head.getHeight() / 2;
            if((abs(guapo_loc_y - guapo_loc_y_o) < guapo_head.getHeight())) {
                y_velocity = guapo_loc_y - guapo_loc_y_o;
            }
            x_velocity_o = x_velocity;
            y_velocity_o = y_velocity;
        }

        else if((action == MotionEvent.ACTION_DOWN) && !game_paused) {
            guapo_loc_x_o = guapo_loc_x;
            guapo_loc_x = (int) loc_x - guapo_head.getWidth() / 2;
            guapo_loc_y_o = guapo_loc_y;
            guapo_loc_y = (int) loc_y - guapo_head.getHeight() / 2;
        }
        else if(action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_MOVE && !game_paused){
            guapo_loc_x += x_velocity_o;
            if ((guapo_loc_x > screen_width - guapo_head.getWidth()) || (guapo_loc_x < 0)) {
                x_velocity_o = x_velocity_o * -1;
            }
            if(guapo_loc_x > screen_width - guapo_head.getWidth()) {
                guapo_loc_x = screen_width - guapo_head.getWidth();
            }
            if(guapo_loc_x < 0) {
                guapo_loc_x = 0;
            }

            guapo_loc_y += y_velocity_o;
            if ((guapo_loc_y > screen_height - guapo_head.getHeight()) || (guapo_loc_y < 0)) {
                y_velocity_o = y_velocity_o * -1;
            }
            if(guapo_loc_y > screen_height - guapo_head.getHeight()) {
                guapo_loc_y = screen_height - guapo_head.getHeight();
            }
            if(guapo_loc_y < 0) {
                guapo_loc_y = 0;
            }
        }

        int maxSpeed = 1500;
        if(y_velocity_o * y_velocity_o + x_velocity_o * x_velocity_o > maxSpeed * maxSpeed) {
            int xVelocityMax = maxSpeed / (int) sqrt(1 + ((float) y_velocity_o * y_velocity_o) / (x_velocity_o * x_velocity_o + 1));
            int yVelocityMax = abs(y_velocity_o / (abs(x_velocity_o) + 1)) * xVelocityMax;
            if(x_velocity_o < 0) {
                x_velocity_o = -xVelocityMax;
            }
            else {
                x_velocity_o = xVelocityMax;
            }
            if(y_velocity_o < 0) {
                y_velocity_o = -yVelocityMax;
            }
            else {
                y_velocity_o = yVelocityMax;
            }
        }

        //return x and y
        int[] coordinates = new int[2];
        coordinates[0] = guapo_loc_x;
        coordinates[1] = guapo_loc_y;

        return coordinates;

    }

    void play_sound_bark_animal_hit(CharacterIds char_id) {
        if(char_id == CharacterIds.FRITO) {
            play_sound_bark_frito_hit();
        }
        if(char_id == CharacterIds.BROWNIE) {
            play_sound_bark_brownie_hit();
        }
        if(char_id == CharacterIds.MISTY || char_id == CharacterIds.MISTY_TOP) {
            play_sound_bark_misty_hit();
        }
    }

    void check_if_hit_character(CharacterImage animal) {
        Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + guapo_head.getWidth(), guapo_loc_y + guapo_head.getHeight());
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

    public void update_snacks(Snack[] snack) {
        for (Snack snack_inst : snack) {

            snack_inst.speed = background_speed;
            snack_inst.x -= snack_inst.speed;

            if (snack_inst.x + snack_inst.width < 0) {

                Random rand = new Random();

                snack_inst.x = rand.nextInt(screen_width + 1) + screen_width;
                snack_inst.y = random.nextInt(screen_height - snack_inst.height);

            }

            float snack_inst_width = (float) snack_inst.get_snack_image().getWidth();
            float snack_inst_height = (float) snack_inst.get_snack_image().getHeight();

            Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + guapo_head.getWidth(), guapo_loc_y + guapo_head.getHeight());
            Rect rect_2 = new Rect(snack_inst.x + (int) snack_inst_width/5,
                    snack_inst.y + (int) snack_inst_height/5,
                    snack_inst.x + (int) ((snack_inst_width*4)/5),
                    snack_inst.y + (int) ((snack_inst_height)*4)/5);

            if (Rect.intersects(rect_1, rect_2)) {
                score = score + snack_inst.points_snack;
                if(snack_inst.play_sound_allowed) {
                    play_sound_eat();
                    snack_inst.play_sound_allowed = false;
                }
                snack_inst.x = -500;
            }
            else {
                snack_inst.play_sound_allowed = true;
            }

        }
    }

    public void update_beggin_strip() {

        if(score >= parameters.POINTS_WHEN_BEGGIN_STRIPS_APPEAR) {
            beggin_strip.speed = background_speed;
            beggin_strip.x -= beggin_strip.speed;
            if (beggin_strip.x + beggin_strip.width < 0) {

                Random rand = new Random();

                beggin_strip.x = rand.nextInt(2 * screen_width + 1) + screen_width;
                beggin_strip.y = random.nextInt(screen_height - beggin_strip.height);

            }

            float beggin_strip_width = (float) beggin_strip.get_snack_image().getWidth();
            float beggin_strip_height = (float) beggin_strip.get_snack_image().getHeight();

            Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + guapo_head.getWidth(), guapo_loc_y + guapo_head.getHeight());
            Rect rect_2 = new Rect(beggin_strip.x + (int) beggin_strip_width / 5,
                    beggin_strip.y + (int) beggin_strip_height / 5,
                    beggin_strip.x + (int) ((beggin_strip_width * 4) / 5),
                    beggin_strip.y + (int) ((beggin_strip_height) * 4) / 5);

            if (Rect.intersects(rect_1, rect_2)) {
                score = score + parameters.POINTS_BEGGIN_STRIPS;
                if(beggin_strip.play_sound_allowed) {
                    play_sound_eat();
                    beggin_strip.play_sound_allowed = false;
                }
                beggin_strip.x = -500;
            }
            else {
                beggin_strip.play_sound_allowed = true;
            }
        }
    }

    public void update_frito() {

        frito.speed = 2 * background_speed;
        frito.x -= frito.speed;

        // Frito goes out of bounds
        if (frito.x + frito.width < 0) {

            Random rand = new Random();

            frito.x = rand.nextInt(screen_width + 1) + 23 * screen_width;
            frito.y = random.nextInt(screen_height - frito.height);

            frito.hit_character = false;
            frito.appeared = false;

            frito.play_sound_allowed = true;
        }

        // Frito appeared on screen
        if(frito.x < screen_width && (frito.x + frito.width >= 0) && !frito.appeared) {
            play_sound_cat_appearing();
            frito.appeared = true;
        }

        frito.y += frito.y_vel;

        if ((frito.y > screen_height - frito.get_character_image().getHeight()) || (frito.y < 0)) {
            frito.y_vel = frito.y_vel * -1;
        }
        if(frito.y > screen_height - frito.get_character_image().getHeight()) {
            frito.y = screen_height - frito.get_character_image().getHeight();
        }
        if(frito.y < 0) {
            frito.y = 0;
        }

        check_if_hit_character(frito);
    }

    public void update_brownie() {
        if ((brownie.x < screen_width) && (brownie.x + brownie.width >= 0)) {
            brownie.speed = (3 * background_speed) / 2;
        }
        else {
            brownie.speed = (2 * background_speed);
        }

        brownie.x += brownie.speed;

        // Brownie went out of bounds
        if (brownie.x > screen_width) {

            Random rand = new Random();

            brownie.x = rand.nextInt(screen_width + 1) - 24 * screen_width;
            brownie.y = random.nextInt(screen_height - brownie.height);

            brownie.hit_character = false;
            brownie.appeared = false;

            brownie.play_sound_allowed = true;
        }

        // Brownie appeared on screen
        if(brownie.x < screen_width && (brownie.x + brownie.width >= 0) && !brownie.appeared) {
            play_sound_brownie_appearing();
            brownie.appeared = true;
        }

        brownie.y += brownie.y_vel;

        if ((brownie.y > screen_height - brownie.get_character_image().getHeight()) || (brownie.y < 0)) {
            brownie.y_vel = brownie.y_vel * -1;
        }
        if(brownie.y > screen_height - brownie.get_character_image().getHeight()) {
            brownie.y = screen_height - brownie.get_character_image().getHeight();
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

            misty.x = 23 * screen_width;
            misty.pop_up_at_x = rand.nextInt((screen_width * 3) / 4 - screen_width / 4 + 1) + screen_width / 4;
            if(!misty.is_top) {
                misty.y = screen_height;
            }
            else {
                misty.y = -misty.get_misty().getHeight();
            }
            misty.y_vel = misty.y_vel * -1;

            misty.hit_character = false;
            misty.appeared = false;

            misty.play_sound_allowed = true;

            update_misty_pos();

        }

        // Misty appeared on screen
        if(misty.x < screen_width && (misty.x + misty.width >= 0) && !misty.appeared && misty.is_top == misty_comes_from_top) {
            play_sound_misty_appearing();
            misty.appeared = true;
        }

        // Update Misty position
        if(misty.x < screen_width && (misty.x + misty.width >= 0) && !misty.is_top) {
            misty.y -= misty.y_vel;
        }
        if(misty.x < screen_width && (misty.x + misty.width >= 0) && misty.is_top) {
            misty.y += misty.y_vel;
        }

        // Update Misty velocity
        if(misty.y < screen_height - misty.get_misty().getHeight() && !misty.is_top) {
            misty.y_vel = misty.y_vel * -1;
        }
        if(misty.y > 0 && misty.is_top) {
            misty.y_vel = misty.y_vel * -1;
        }

        // Check if Misty and Guapo intersect
        float misty_width = (float) misty.get_misty().getWidth();
        float misty_height = (float) misty.get_misty().getHeight();

        if(misty.appeared && misty.is_top == misty_comes_from_top) {
            Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + guapo_head.getWidth(), guapo_loc_y + guapo_head.getHeight());
            Rect rect_2 = new Rect(misty.pop_up_at_x, misty.y,
                    misty.pop_up_at_x + (int) ((misty_width * 5) / 5),
                    misty.y + (int) ((misty_height) * 5) / 5);

            if (Rect.intersects(rect_1, rect_2)) {
                if (misty.play_sound_allowed ) {
                    play_sound_bark_misty_hit();
                    misty.hit_character = true;
                    misty.play_sound_allowed = false;
                }
            }
        }
    }

    public void update_misty() {

        // Update Misty bottom
        update_misty_fcn(misty);

        // Update Misty top
        update_misty_fcn(misty_top);
    }

    public void update_bird(Bird bird) {
        bird.x -= bird.speed;

        if (bird.x + bird.width < 0) {

            int bound = 3 * background_speed;
            bird.speed = random.nextInt(bound);

            if (bird.speed < (bound/2)) {
                bird.speed = bound / 2;
            }

            bird.x = screen_width;
            bird.y = random.nextInt(screen_height - bird.height);

        }

        float bird_width = (float) bird.get_bird_image().getWidth();
        float bird_height = (float) bird.get_bird_image().getHeight();

        Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + guapo_head.getWidth(), guapo_loc_y + guapo_head.getHeight());
        Rect rect_2 = new Rect(bird.x + (int) ((bird_width * 35) / 100),
                bird.y + (int) ((bird_height * 35) / 100),
                bird.x + (int) ((bird_width * 65) / 100),
                bird.y + (int) ((bird_height * 65) / 100));

        if (Rect.intersects(rect_1, rect_2)) {
            hit_bird = true;
            if(bird.play_sound_allowed) {
                play_sound_bark_hit();
                bird.play_sound_allowed = false;
            }
        }
        else {
            bird.play_sound_allowed = true;
        }
    }

    public void update_birds(Bird[] birds) {

        if((score >= (difficulty_level * parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL)) &&
                (score <= ((difficulty_level + 1) * parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL)) &&
                (num_birds < parameters.MAX_NUM_BIRDS)) {
            num_birds = num_birds + 1;
            difficulty_level++;
        }

        for (int i = 0; i < num_birds; i++) {
            update_bird(birds[i]);
        }
    }

    public void update_all() {

        int[] coordinates_char_sprite;

        coordinates_char_sprite = update_sprite(loc_x, loc_y, action);

        int tutti_x = coordinates_char_sprite[0];
        int tutti_y = coordinates_char_sprite[1];

        guapo_loc_x = tutti_x;
        guapo_loc_y = tutti_y;

        update_beggin_strip();

        update_snacks(cheesy_bites);

        update_snacks(paprika);

        update_snacks(cucumbers);

        update_snacks(broccoli);

        // Update Frito
        update_frito();

        // Update Brownie
        update_brownie();

        // Update Misty
        update_misty();
    }

    public void save_lives(String level) {
        String game_state_id = parameters.GAME_STATE_STR + level;

        save_game_state(game_state_id, true);

        num_lives--;

        String num_lives_id = parameters.NUM_LIVES_STR + level;

        save_num_lives(num_lives_id);
    }

    public void save_all_game_state(Backgrounds backgrounds, Bird[] birds, String level) {
        save_game_state(parameters.GAME_STATE_STR + level, true);

        save_backgrounds(backgrounds, parameters.BACKGROUNDS_STR + level);

        save_birds(birds, parameters.BIRDS_STR + level);

        save_character(frito, parameters.FRITO_STR + level);

        save_character(misty, parameters.MISTY_STR + level);

        save_character(misty_top, parameters.MISTY_TOP_STR + level);

        save_character(brownie, parameters.BROWNIE_STR + level);

        save_snacks(broccoli, parameters.BROCCOLI_STR + level);

        save_snacks(cheesy_bites, parameters.CHEESY_BITES_STR + level);

        save_snacks(paprika, parameters.PAPRIKA_STR + level);

        save_snacks(cucumbers, parameters.CUCUMBERS_STR + level);

        save_loc(beggin_strip.x, beggin_strip.y, parameters.BEGGIN_STR + level);

        save_loc(guapo_loc_x, guapo_loc_y, parameters.GUAPO_LOC_STR + level);

        save_num_lives(parameters.NUM_LIVES_STR + level);

        save_score(parameters.SCORE_STR + level);

        save_difficulty_level(parameters.DIFFICULTY_LEVEL_STR + level);

        save_num_birds(parameters.NUM_BIRDS_STR + level);

        save_check_point(parameters.CHECKPOINT_STR + level);
    }

    private void save_background_fish() {
        save_fish(fishes1, parameters.FISH1);
        save_fish(fishes2, parameters.FISH2);
        save_fish(fishes3, parameters.FISH3);
        save_fish(fishes4, parameters.FISH4);
        save_fish(fishes5, parameters.FISH5);
        save_fish(fishes7, parameters.FISH7);
        save_pufferfish(pufferfishes, parameters.PUFFERFISH_STR);
    }

    public void save_all_game_state_water(Backgrounds backgrounds, Jellyfish[] jellyfish, String level) {
        save_game_state(parameters.GAME_STATE_STR + level, true);

        save_backgrounds(backgrounds, parameters.BACKGROUNDS_STR + level);

        save_num_jellyfish(parameters.NUM_JELLYFISH_STR);

        save_background_fish();

        save_jellyfish(jellyfish, parameters.JELLYFISH_STR);

        save_character(frito, parameters.FRITO_STR + level);

        save_character(misty, parameters.MISTY_STR + level);

        save_character(misty_top, parameters.MISTY_TOP_STR + level);

        save_character(brownie, parameters.BROWNIE_STR + level);

        save_snacks(broccoli, parameters.BROCCOLI_STR + level);

        save_snacks(cheesy_bites, parameters.CHEESY_BITES_STR + level);

        save_snacks(paprika, parameters.PAPRIKA_STR + level);

        save_snacks(cucumbers, parameters.CUCUMBERS_STR + level);

        save_loc(beggin_strip.x, beggin_strip.y, parameters.BEGGIN_STR + level);

        save_loc(guapo_loc_x, guapo_loc_y, parameters.GUAPO_LOC_STR + level);

        save_num_lives(parameters.NUM_LIVES_STR + level);

        save_score(parameters.SCORE_STR + level);

        save_difficulty_level(parameters.DIFFICULTY_LEVEL_STR + level);

        save_num_birds(parameters.NUM_BIRDS_STR + level);

        save_check_point(parameters.CHECKPOINT_STR + level);
    }

    public void save_snacks(Snack [] snacks, String store_id) {
        int num_snacks = snacks.length;

        for(int i = 0; i < num_snacks; i++) {
            SharedPreferences.Editor editor = prefs.edit();
            String stash_id_x = store_id + "x" + i;
            editor.putInt(stash_id_x, snacks[i].x);
            String stash_id_y = store_id + "y" + i;
            editor.putInt(stash_id_y, snacks[i].y);
            editor.apply();
        }
    }

    public void save_birds(Bird[] birds, String store_id) {
        int num_snacks = birds.length;

        for(int i = 0; i < num_snacks; i++) {
            SharedPreferences.Editor editor = prefs.edit();
            String stash_id_x = store_id + "x" + i;
            editor.putInt(stash_id_x, birds[i].x);
            String stash_id_vx = store_id + "vx" + i;
            editor.putInt(stash_id_vx, birds[i].speed);
            String stash_id_y = store_id + "y" + i;
            editor.putInt(stash_id_y, birds[i].y);
            editor.apply();
        }
    }

    public void save_jellyfish(Jellyfish[] jellyfish, String store_id) {
        int num_jelly = jellyfish.length;

        for(int i = 0; i < num_jelly; i++) {
            SharedPreferences.Editor editor = prefs.edit();
            String stash_id_x = store_id + "x" + i;
            editor.putInt(stash_id_x, jellyfish[i].x);
            String stash_id_vx = store_id + "vx" + i;
            editor.putInt(stash_id_vx, jellyfish[i].speed);
            String stash_id_y = store_id + "y" + i;
            editor.putInt(stash_id_y, jellyfish[i].y);
            editor.apply();
        }
    }

    public void save_pufferfish(Pufferfish[] pufferfish, String store_id) {

        for(int i = 0; i < parameters.NUM_PUFFER; i++) {
            SharedPreferences.Editor editor = prefs.edit();
            String stash_id_x = store_id + "x" + i;
            editor.putInt(stash_id_x, pufferfish[i].x);
            String stash_id_vx = store_id + "vx" + i;
            editor.putInt(stash_id_vx, pufferfish[i].speed);
            String stash_id_y = store_id + "y" + i;
            editor.putInt(stash_id_y, pufferfish[i].y);
            String stash_id_puffed = store_id + "puffed" + i;
            editor.putBoolean(stash_id_puffed, pufferfish[i].hit_pufferfish);
            editor.apply();
        }
    }

    public void save_fish(Fish[] fish, String store_id) {
        int num_fishes = fish.length;

        String prefix = "num_fish";

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(prefix + store_id, num_fishes);
        editor.apply();

        for(int i = 0; i < num_fishes; i++) {
            String stash_id_x = store_id + "x" + i;
            editor.putInt(stash_id_x, fish[i].x);
            String stash_id_vx = store_id + "vx" + i;
            editor.putInt(stash_id_vx, fish[i].speed);
            String stash_id_y = store_id + "y" + i;
            editor.putInt(stash_id_y, fish[i].y);
            editor.apply();
        }
    }

    public void save_backgrounds(Backgrounds backgrounds, String store_id) {
        int num_backgrounds = backgrounds.num_backgrounds;

        for(int i = 0; i < num_backgrounds; i++) {
            SharedPreferences.Editor editor = prefs.edit();
            String stash_id_x = store_id + "x" + i;
            editor.putInt(stash_id_x, backgrounds.backgrounds[i].x);
            String stash_id_y = store_id + "y" + i;
            editor.putInt(stash_id_y, backgrounds.backgrounds[i].y);
            editor.apply();
        }
    }

    public void save_character(CharacterImage character, String store_id) {
        SharedPreferences.Editor editor = prefs.edit();
        String stash_id_x = store_id + "x";
        editor.putInt(stash_id_x, character.x);
        String stash_id_y = store_id + "y";
        editor.putInt(stash_id_y, character.y);
        editor.apply();
    }

    public void save_loc(int x, int y, String store_id) {
        SharedPreferences.Editor editor = prefs.edit();
        String stash_id_x = store_id + "x";
        editor.putInt(stash_id_x, x);
        String stash_id_y = store_id + "y";
        editor.putInt(stash_id_y, y);
        editor.apply();
    }

    public void save_game_state(String store_id, boolean in_game) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(store_id, in_game);
        editor.apply();
    }

    public void save_num_lives(String store_id) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(store_id, num_lives);
        editor.apply();
    }

    public void save_score(String store_id) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(store_id, score);
        editor.apply();
    }

    public void save_difficulty_level(String store_id) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(store_id, difficulty_level);
        editor.apply();
    }

    public void save_num_birds(String store_id) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(store_id, num_birds);
        editor.apply();
    }

    public void save_num_jellyfish(String store_id) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(store_id, num_jelly_fish);
        editor.apply();
    }

    public void save_check_point(String store_id) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(store_id, checkpoint_num);
        editor.apply();
    }

    public void get_difficulty_level(String store_id) {
        difficulty_level = prefs.getInt(store_id, 0);
    }

    public void get_num_birds(String store_id) {
        num_birds = prefs.getInt(store_id, 2);
    }

    public void get_num_jellyfish(String store_id) {
        num_jelly_fish = prefs.getInt(store_id, 2);
    }

    public void get_check_point(String store_id) {
        checkpoint_num = prefs.getInt(store_id, 1) + 1;
    }

    public void get_snacks(Snack [] snacks, String store_id) {
        int num_snacks = snacks.length;

        for(int i = 0; i < num_snacks; i++) {
            String stash_id_x = store_id + "x" + i;
            snacks[i].x = prefs.getInt(stash_id_x, -screen_width);
            String stash_id_y = store_id + "y" + i;
            snacks[i].y = prefs.getInt(stash_id_y, -screen_height);
        }
    }

    public void get_eagles(Eagle[] birds, String store_id) {

        for(int i = 0; i < num_birds; i++) {
            Eagle bird = new Eagle(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            birds[i] = bird;
            String stash_id_x = store_id + "x" + i;
            birds[i].x = prefs.getInt(stash_id_x, -screen_width);
            String stash_id_vx = store_id + "vx" + i;
            birds[i].speed = prefs.getInt(stash_id_vx, 0);
            String stash_id_y = store_id + "y" + i;
            birds[i].y = prefs.getInt(stash_id_y, -screen_height);
        }
    }

    public void get_seagulls(Seagull[] birds, String store_id) {

        for(int i = 0; i < num_birds; i++) {
            Seagull bird = new Seagull(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            birds[i] = bird;
            String stash_id_x = store_id + "x" + i;
            birds[i].x = prefs.getInt(stash_id_x, -screen_width);
            String stash_id_vx = store_id + "vx" + i;
            birds[i].speed = prefs.getInt(stash_id_vx, 0);
            String stash_id_y = store_id + "y" + i;
            birds[i].y = prefs.getInt(stash_id_y, -screen_height);
        }
    }

    public void get_jellyfish(Jellyfish[] jellyfish, String store_id) {

        for(int i = 0; i < num_jelly_fish; i++) {
            Jellyfish jelly = new Jellyfish(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            jellyfish[i] = jelly;
            String stash_id_x = store_id + "x" + i;
            jellyfish[i].x = prefs.getInt(stash_id_x, -screen_width);
            String stash_id_vx = store_id + "vx" + i;
            jellyfish[i].speed = prefs.getInt(stash_id_vx, 0);
            String stash_id_y = store_id + "y" + i;
            jellyfish[i].y = prefs.getInt(stash_id_y, -screen_height);
        }
    }

    public void get_fish(Fish[] fishes, String store_id, int fish_id1, int fish_id2) {

        String prefix = "num_fish";

        int num_fish = prefs.getInt(prefix + store_id, 0);

        for(int i = 0; i < num_fish; i++) {
            Fish fish = new Fish(getResources(), (int) screen_factor_x, (int) screen_factor_y, fish_id1, fish_id2);
            fishes[i] = fish;
            String stash_id_x = store_id + "x" + i;
            fishes[i].x = prefs.getInt(stash_id_x, -screen_width);
            String stash_id_vx = store_id + "vx" + i;
            fishes[i].speed = prefs.getInt(stash_id_vx, 0);
            String stash_id_y = store_id + "y" + i;
            fishes[i].y = prefs.getInt(stash_id_y, -screen_height);
        }
    }

    public void get_pufferfish(Pufferfish[] fishes, String store_id) {

        for(int i = 0; i < parameters.NUM_PUFFER; i++) {
            Pufferfish fish = new Pufferfish(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            fishes[i] = fish;
            String stash_id_x = store_id + "x" + i;
            fishes[i].x = prefs.getInt(stash_id_x, -screen_width);
            String stash_id_vx = store_id + "vx" + i;
            fishes[i].speed = prefs.getInt(stash_id_vx, 0);
            String stash_id_y = store_id + "y" + i;
            fishes[i].y = prefs.getInt(stash_id_y, -screen_height);
            String stash_id_puffed = store_id + "puffed" + i;
            fishes[i].hit_pufferfish = prefs.getBoolean(stash_id_puffed, false);
        }
    }

    public void get_backgrounds(Backgrounds backgrounds, String store_id) {
        int num_backgrounds = backgrounds.num_backgrounds;

        for(int i = 0; i < num_backgrounds; i++) {
            String stash_id_x = store_id + "x" + i;
            backgrounds.backgrounds[i].x = prefs.getInt(stash_id_x, -screen_width);
            String stash_id_y = store_id + "y" + i;
            backgrounds.backgrounds[i].y = prefs.getInt(stash_id_y, -screen_height);
        }
    }

    public void get_character(CharacterImage character, String store_id) {
        String stash_id_x = store_id + "x";
        character.x = prefs.getInt(stash_id_x, -screen_width);
        String stash_id_y = store_id + "y";
        character.y = prefs.getInt(stash_id_y, -screen_height);
    }

    public Coordinates get_loc(String store_id) {
        Coordinates coordinates = new Coordinates();

        String stash_id_x = store_id + "x";
        coordinates.x = prefs.getInt(stash_id_x, 0);
        String stash_id_y = store_id + "y";
        coordinates.y = prefs.getInt(stash_id_y, 0);

        return coordinates;
    }

    public boolean get_game_state(String store_id) {
        return prefs.getBoolean(store_id, false);
    }

    public void get_num_lives(String store_id) {
        num_lives = prefs.getInt(store_id, 3);
    }

    public void get_score(String store_id) {
        score = prefs.getInt(store_id, 0);
    }

    public void draw_snacks(Canvas canvas, Snack [] snacks) {
        for (Snack snack : snacks)
            canvas.drawBitmap(snack.get_snack_image(), snack.x, snack.y, null);
    }

    public void draw_characters(Canvas canvas) {
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

    public void draw_birds(Canvas canvas, Bird[] birds) {
        for (int i = 0; i < num_birds; i++) {
            Bird bird = birds[i];
            canvas.drawBitmap(bird.get_bird_image(), bird.x, bird.y, null);
        }
    }

    public void draw_lives(Canvas canvas, int left_most_x, int y) {
        for (int i = 0; i < num_lives; i++) {
            canvas.drawBitmap(lives[i], left_most_x, y, null);

            left_most_x += lives[i].getWidth() + 5;
        }
    }

    public void draw_pause_button(Canvas canvas) {
        if(game_paused) {
            canvas.drawBitmap(play_button, pause_region_max_x - play_button.getWidth(), pause_region_min_y, null);
            paused_is_set = true;
        }
        else {
            canvas.drawBitmap(pause_button, pause_region_max_x - pause_button.getWidth(), pause_region_min_y, null);
        }
    }

    public void draw_guapo(Canvas canvas) {
        int yh = guapo_loc_y + (4 * guapo_head.getHeight()) / 5;
        int y2 = yh - cape_image.getHeight() / 2;
        int yh_body = guapo_loc_y + (5 * guapo_head.getHeight()) / 5;
        int y2_body = yh_body - guapo_body.getHeight() / 2;
        canvas.drawBitmap(cape_image, guapo_loc_x - (cape_image.getWidth() - (float) (11 * cape_image.getWidth()) / 20), y2, null);
        canvas.drawBitmap(guapo_body, guapo_loc_x - (guapo_body.getWidth() - (float) (guapo_body.getWidth() * 3) / 5), y2_body, null);
        if(!hit_bird) {
            canvas.drawBitmap(guapo_head, guapo_loc_x, guapo_loc_y, null);
        }
    }

    public void draw_sun_popup(Canvas canvas, String score_id) {
        if((prefs.getInt(score_id, 0) < parameters.LEVEL_UNLOCK_SCORE) && (score >= parameters.LEVEL_UNLOCK_SCORE) && (sun_popup.popup_counter < parameters.NUM_FRAMES_POPUP)) {
            canvas.drawBitmap(sun_popup.get_image(), (float) (screen_height * 2) / 6, (float) screen_height / 35, null);
            if(!sun_popup.popped_up) {
                play_sound_sun_popup();
                sun_popup.popped_up = true;
            }
            sun_popup.popup_counter++;
        }
    }

    public void draw_flag_popup(Canvas canvas, Backgrounds backgrounds, Bird[] birds, String level) {

        if(checkpoint_num == 0) {

            save_all_game_state(backgrounds, birds, level);
            checkpoint_num++;
        }

        if(score >= (checkpoint_num * parameters.CHECK_POINT_INTERVAL)) {

            if(!saved_state) {
                saved_state = true;
                save_all_game_state(backgrounds, birds, level);
            }
            if(flag_checkpoint.popup_counter < parameters.NUM_FRAMES_POPUP) {
                canvas.drawBitmap(flag_checkpoint.get_image(), (float) screen_width - (float) screen_width / 5, (float) screen_height / 35, null);
                if (!flag_checkpoint.popped_up) {
                    play_sound_sun_popup();
                    flag_checkpoint.popped_up = true;
                }
                flag_checkpoint.popup_counter++;
            }
            else {
                checkpoint_num++;
                flag_checkpoint.popup_counter = 0;
                flag_checkpoint.popped_up = false;
                saved_state = false;
            }
        }
    }

    public void draw_flag_popup_water(Canvas canvas, Backgrounds backgrounds, Jellyfish[] jellyfish, String level) {

        if(checkpoint_num == 0) {

            save_all_game_state_water(backgrounds, jellyfish, level);
            checkpoint_num++;
        }

        if(score >= (checkpoint_num * parameters.CHECK_POINT_INTERVAL)) {

            if(!saved_state) {
                saved_state = true;
                save_all_game_state_water(backgrounds, jellyfish, level);
            }
            if(flag_checkpoint.popup_counter < parameters.NUM_FRAMES_POPUP) {
                canvas.drawBitmap(flag_checkpoint.get_image(), (float) screen_width - (float) screen_width / 5, (float) screen_height / 35, null);
                if (!flag_checkpoint.popped_up) {
                    play_sound_sun_popup();
                    flag_checkpoint.popped_up = true;
                }
                flag_checkpoint.popup_counter++;
            }
            else {
                checkpoint_num++;
                flag_checkpoint.popup_counter = 0;
                flag_checkpoint.popped_up = false;
                saved_state = false;
            }
        }
    }

    public void draw_all(Canvas canvas) {

        // Draw snacks
        draw_snacks(canvas, cheesy_bites);

        draw_snacks(canvas, paprika);

        draw_snacks(canvas, cucumbers);

        draw_snacks(canvas, broccoli);

        canvas.drawBitmap(beggin_strip.get_snack_image(), beggin_strip.x, beggin_strip.y, null);

        // Draw characters
        draw_characters(canvas);

        // Draw score
        canvas.drawText(" " + score, 30, (float) screen_height / 6, paint);

        // Draw pause button
        draw_pause_button(canvas);
    }
}
