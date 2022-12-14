package com.main.guapogame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Random;

public class GameViewLevel4 extends MainView implements Runnable{
    private Thread thread;
    private Jellyfish[] jellyfish;
    private Backgrounds backgrounds_level4;
    private GameActivityLevel4 game_activity;
    private Bitmap scuba_tank;
    private Bitmap snorkel_mask;
    private boolean hit_jellyfish = false;
    private BubbleProducers bubble_producers;

    public GameViewLevel4(Context context) {
        super(context);
    }

    public GameViewLevel4(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameViewLevel4(GameActivityLevel4 activity) {
        super(activity);

        game_activity = activity;

        backgrounds_level4 = new Backgrounds(activity, screen_width, screen_height, parameters.BACKGROUND_IMAGES_LEVEL4);

        // Add fish and override character images
        init_all_images();

        init_reset(parameters.LEVEL4_STR);
    }

    private void init_reset(String level) {
        boolean in_game = get_game_state(parameters.GAME_STATE_STR + level);
        if(in_game) {
            get_num_lives(parameters.NUM_LIVES_STR + level);

            get_backgrounds(backgrounds_level4, parameters.BACKGROUNDS_STR + level);

            get_fish(fishes1, parameters.FISH1, R.drawable.fish5_bitmap_cropped, R.drawable.fish6_bitmap_cropped);

            get_fish(fishes2, parameters.FISH2, R.drawable.fish3_bitmap_cropped, R.drawable.fish4_bitmap_cropped);

            get_fish(fishes3, parameters.FISH3, R.drawable.fish8_bitmap_cropped, R.drawable.fish9_bitmap_cropped);

            get_fish(fishes4, parameters.FISH4, R.drawable.yellowfish_facingright_raw, R.drawable.yellowfish_facingright_raw2);

            get_fish(fishes5, parameters.FISH5, R.drawable.fish12_bitmap_cropped, R.drawable.fish12b_bitmap_cropped);

            get_fish(fishes7, parameters.FISH7, R.drawable.fish14b_bitmap_cropped_resized_purple, R.drawable.fish14bb_bitmap_cropped_resized_purple);

            get_pufferfish(pufferfishes, parameters.PUFFERFISH_STR);

            get_num_jellyfish(parameters.NUM_JELLYFISH_STR);

            get_jellyfish(jellyfish, parameters.JELLYFISH_STR);

            get_character(frito, parameters.FRITO_STR + level);

            get_character(misty, parameters.MISTY_STR + level);

            get_character(misty_top, parameters.MISTY_TOP_STR + level);

            get_character(brownie, parameters.BROWNIE_STR + level);

            get_snacks(broccoli, parameters.BROCCOLI_STR + level);

            get_snacks(cheesy_bites, parameters.CHEESY_BITES_STR + level);

            get_snacks(paprika, parameters.PAPRIKA_STR + level);

            get_snacks(cucumbers, parameters.CUCUMBERS_STR + level);

            Coordinates coordinates_beggin = get_loc(parameters.BEGGIN_STR + level);

            beggin_strip.x = coordinates_beggin.x;
            beggin_strip.y = coordinates_beggin.y;

            Coordinates coordinates_guapo = get_loc(parameters.GUAPO_LOC_STR + level);

            guapo_loc_x = coordinates_guapo.x;
            guapo_loc_y = coordinates_guapo.y;

            get_score(parameters.SCORE_STR + level);

            get_difficulty_level(parameters.DIFFICULTY_LEVEL_STR + level);

            get_check_point(parameters.CHECKPOINT_STR + level);
        }
    }

    public Fish[] init_fish(int num_fishes, int facx, int facy, int image_id1, int image_id2) {

        Fish[] fishes = new Fish[num_fishes];
        for (int i = 0;i < num_fishes; i++) {
            Fish fishes_inst = new Fish(getResources(), facx, facy, image_id1, image_id2);
            fishes[i] = fishes_inst;
        }

        return fishes;
    }

    public void init_puffer_fish() {
        pufferfishes = new Pufferfish[parameters.NUM_PUFFER];
        for (int i = 0; i < parameters.NUM_PUFFER; i++) {

            Pufferfish pufferfishes_inst = new Pufferfish(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            pufferfishes[i] = pufferfishes_inst;

        }
    }

    public void init_jelly_fish() {
        jellyfish = new Jellyfish[parameters.MAX_JELLY_FISH];
        for (int i = 0; i < parameters.MAX_JELLY_FISH; i++) {
            Jellyfish jellyfish_inst = new Jellyfish(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            jellyfish[i] = jellyfish_inst;
        }
    }

    public void init_images_characters() {
        bubble_producers = new BubbleProducers(getResources(), (int) screen_factor_x, (int) screen_factor_y);

        frito = init_character((int) screen_factor_x, (int) screen_factor_y, R.drawable.frito_snorkel_bitmap_cropped, R.drawable.frito_snorkel_hit_bitmap_rotated_cropped, CharacterIds.FRITO);

        brownie = init_character((int) screen_factor_x, (int) screen_factor_y, R.drawable.brownie_snorkel_bitmap_cropped, R.drawable.brownie_snorkel_bitmap_hit_cropped, CharacterIds.FRITO);

        init_misty_snorkel();

        guapo_image = new GuapoImage(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.guapo_snorkelhead_bitmap_cropped,
                R.drawable.cape1_guapo_bitmap_cropped,
                R.drawable.cape2_guapo_bitmap_cropped);

        guapo_head = guapo_image.get_image();
        cape_image = guapo_image.get_cape();

        guapo_head_hit = BitmapFactory.decodeResource(getResources(),R.drawable.guapo_snorkelhit_bitmap_cropped);
        guapo_head_hit = Bitmap.createScaledBitmap(guapo_head_hit, (int) screen_factor_x, (int) screen_factor_y, false);

        guapo_body = BitmapFactory.decodeResource(getResources(), R.drawable.guapo_body_bitmap_cropped);
        guapo_body = Bitmap.createScaledBitmap(guapo_body, (int) ((screen_factor_x *4)/3), (int) ((screen_factor_y *2)/3), false);

        scuba_tank = BitmapFactory.decodeResource(getResources(), R.drawable.scuba_tank_bitmap_cropped);
        scuba_tank = Bitmap.createScaledBitmap(scuba_tank, (int) ((screen_factor_x *4)/5), (int) ((screen_factor_y *2)/5), false);

        snorkel_mask = BitmapFactory.decodeResource(getResources(), R.drawable.snorkel_mask_bitmap_cropped);
        snorkel_mask = Bitmap.createScaledBitmap(snorkel_mask, (int) ((screen_factor_x *5)/5), (int) ((screen_factor_y *5)/5), false);

        pause_button = BitmapFactory.decodeResource(getResources(), R.drawable.pause_button_bitmap_cropped);
        pause_button = Bitmap.createScaledBitmap(pause_button, (int) (screen_factor_x *1)/4, (int) (screen_factor_y *1)/4, false);

        play_button = BitmapFactory.decodeResource(getResources(), R.drawable.play_button_bitmap_cropped);
        play_button = Bitmap.createScaledBitmap(play_button, (int) (screen_factor_x *1)/4, (int) (screen_factor_y *1)/4, false);

    }

    public void init_background_fish() {
        fishes1 = init_fish(parameters.NUM_FISH1, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish5_bitmap_cropped, R.drawable.fish6_bitmap_cropped);

        fishes2 = init_fish(parameters.NUM_FISH2, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish3_bitmap_cropped, R.drawable.fish4_bitmap_cropped);

        fishes3 = init_fish(parameters.NUM_FISH3, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish8_bitmap_cropped, R.drawable.fish9_bitmap_cropped);

        fishes4 = init_fish(parameters.NUM_FISH4, (int) screen_factor_x, (int) screen_factor_y, R.drawable.yellowfish_facingright_raw, R.drawable.yellowfish_facingright_raw2);

        fishes5 = init_fish(parameters.NUM_FISH5, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish12_bitmap_cropped, R.drawable.fish12b_bitmap_cropped);

        fishes7 = init_fish(parameters.NUM_FISH6, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish14b_bitmap_cropped_resized_purple, R.drawable.fish14bb_bitmap_cropped_resized_purple);

    }

    public void init_all_images() {
        init_background_fish();

        init_puffer_fish();

        init_jelly_fish();

        // Override images to snorkel images
        init_images_characters();
    }

    public void play_sound_pufferfish_hit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound3, 1, 1, 0, 0, 1);
        }
    }

    public void play_sound_misty_appearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_misty_appearing, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void play_sound_bubbles() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_bubbles, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void play_sound_bark_misty_hit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound1, 1, 1, 0, 0, 1);
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

    @Override
    public void run() {

        long start_time;
        long time_millis;
        long wait_time;
        int target_FPS = 30;
        long target_time = 1000 / target_FPS;

        while (is_playing) {

            start_time = System.nanoTime();

            if(!game_paused) {
                update();
            }

            draw();

            time_millis = (System.nanoTime() - start_time) / 1000000;
            wait_time = target_time - time_millis;

            try{
                Thread.sleep(wait_time);
            }catch(Exception e){
                System.out.println("did not sleep");
            }
        }
    }

    public void update_background() {
        for(int i = 0; i < backgrounds_level4.num_backgrounds; ++i) {
            backgrounds_level4.backgrounds[i].x -= background_speed;

            if(backgrounds_level4.backgrounds[i].x + backgrounds_level4.backgrounds[i].background.getWidth() < 0) {
                if(i == 0) {
                    backgrounds_level4.backgrounds[0].x = backgrounds_level4.backgrounds[backgrounds_level4.num_backgrounds - 1].x +
                            backgrounds_level4.backgrounds[backgrounds_level4.num_backgrounds - 1].background.getWidth() - 10;
                }
                if(i > 0) {
                    backgrounds_level4.backgrounds[i].x = backgrounds_level4.backgrounds[i - 1].x + backgrounds_level4.backgrounds[i - 1].background.getWidth() - 10;
                }
            }
        }
    }

    public void update_jellyfish() {

        if((score >= (difficulty_level * parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL)) &&
                (score <= ((difficulty_level + 1) * parameters.SCORE_INTERVAL_DIFFICULTY_LEVEL)) &&
                (num_jelly_fish < parameters.MAX_JELLY_FISH)) {
            num_jelly_fish = num_jelly_fish + parameters.NUM_JELLY_FISH_INCREASE;
            difficulty_level++;
        }

        for (int i = 0; i < num_jelly_fish; i++) {

            Jellyfish jellyfish_inst = jellyfish[i];
            jellyfish_inst.x -= jellyfish_inst.speed;
            jellyfish[i].update_jellycounter();

            if (jellyfish_inst.x + jellyfish_inst.width < 0) {

                int bound = 3 * background_speed;
                jellyfish_inst.speed = random.nextInt(bound);

                if (jellyfish_inst.speed < (bound/2)) {
                    jellyfish_inst.speed = bound / 2;
                }

                jellyfish_inst.x = screen_width;
                jellyfish_inst.y = random.nextInt(screen_height - jellyfish_inst.height);

            }

            float jellyfish_inst_width = (float) jellyfish_inst.get_jellyfish().getWidth();
            float jellyfish_inst_height = (float) jellyfish_inst.get_jellyfish().getHeight();

            Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + guapo_head.getWidth(), guapo_loc_y + guapo_head.getHeight());
            Rect rect_2 = new Rect(jellyfish_inst.x + (int) ((jellyfish_inst_width*35)/100),
                    jellyfish_inst.y + (int) ((jellyfish_inst_height*35)/100),
                    jellyfish_inst.x + (int) ((jellyfish_inst_width*65)/100),
                    jellyfish_inst.y + (int) ((jellyfish_inst_height*65)/100));

            if (Rect.intersects(rect_1, rect_2)) {
                hit_jellyfish = true;
                if(jellyfish_inst.play_sound_allowed) {
                    play_sound_bark_hit();
                    jellyfish_inst.play_sound_allowed = false;
                }
            }
            else {
                jellyfish_inst.play_sound_allowed = true;
            }

        }
    }

    public void update_fish(Fish[] fishes, int num_fishes) {
        for (int i = 0;i < num_fishes;i++) {

            Fish fishes_inst = fishes[i];
            fishes_inst.x -= fishes_inst.speed;
            fishes_inst.update_fish_counter();

            if (fishes_inst.x + fishes_inst.width < 0) {

                int bound = 3 * background_speed;
                fishes_inst.speed = random.nextInt(bound);

                if (fishes_inst.speed < (bound/2)) {
                    fishes_inst.speed = bound / 2;
                }

                Random rand = new Random();

                fishes_inst.x = rand.nextInt(screen_width + 1) + screen_width;
                fishes_inst.y = random.nextInt(screen_height - fishes_inst.height);

            }
        }
    }

    public void update_fish_right(Fish[] fishes, int num_fishes) {
        for (int i = 0;i < num_fishes;i++) {

            Fish fishes_inst = fishes[i];
            fishes_inst.x += fishes_inst.speed;
            fishes_inst.update_fish_counter();

            if (fishes_inst.x > screen_width) {

                int bound = 3 * background_speed;
                fishes_inst.speed = random.nextInt(bound);

                if (fishes_inst.speed < (bound/2)) {
                    fishes_inst.speed = bound / 2;
                }

                Random rand = new Random();

                fishes_inst.x = rand.nextInt(screen_width + 1) - 2 * screen_width;
                fishes_inst.y = random.nextInt(screen_height - fishes_inst.height);
            }
        }
    }

    public void update_pufferfish(Pufferfish[] fishes, int num_fishes) {
        for (int i = 0;i < num_fishes;i++) {

            Pufferfish pufferfish_inst = fishes[i];
            pufferfish_inst.x -= pufferfish_inst.speed;
            pufferfish_inst.update_fish_counter();
            pufferfish_inst.update_fish_hit_counter();

            if (pufferfish_inst.x + pufferfish_inst.width < 0) {

                int bound = 3 * background_speed;
                pufferfish_inst.speed = random.nextInt(bound);

                if (pufferfish_inst.speed < (bound/2)) {
                    pufferfish_inst.speed = bound / 2;
                }

                pufferfish_inst.x = screen_width + 3 * screen_width;
                pufferfish_inst.y = random.nextInt(screen_height - pufferfish_inst.height);

                pufferfish_inst.hit_pufferfish = false;
                pufferfish_inst.play_sound_allowed = true;

            }

            float pufferfish_inst_width = (float) pufferfish_inst.get_fish_width();
            float pufferfish_inst_height = (float) pufferfish_inst.get_fish_height();

            Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + guapo_head.getWidth(), guapo_loc_y + guapo_head.getHeight());
            Rect rect_2 = new Rect(pufferfish_inst.x + (int) ((pufferfish_inst_width*35)/100),
                    pufferfish_inst.y + (int) ((pufferfish_inst_height*35)/100),
                    pufferfish_inst.x + (int) ((pufferfish_inst_width*65)/100),
                    pufferfish_inst.y + (int) ((pufferfish_inst_height*65)/100));

            if (Rect.intersects(rect_1, rect_2)) {
                pufferfish_inst.hit_pufferfish = true;
                if(pufferfish_inst.play_sound_allowed) {
                    play_sound_pufferfish_hit();
                    pufferfish_inst.play_sound_allowed = false;
                }
            }
        }
    }

    public void init_misty_snorkel() {

        misty = new Misty(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.misty_withsnorkel_bitmap_cropped,
                R.drawable.misty_withsnorkel_hit_bitmap_cropped,
                CharacterIds.MISTY);

        misty.x = 3 * screen_width;
        misty.pop_up_at_x = random.nextInt(((screen_width * 3) / 4 - screen_width / 4) + 1) + screen_width / 4;
        misty.y_vel = (2 * background_speed) / 4;
        misty.y = screen_height;
        misty.is_top = false;

        misty_top = new Misty(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.misty_withsnorkel_bitmap_cropped_rotated,
                R.drawable.misty_withsnorkel_hit_bitmap_cropped_rotated,
                CharacterIds.MISTY_TOP);

        misty_top.x = misty.x;
        misty_top.pop_up_at_x = misty.pop_up_at_x;
        misty_top.y_vel = (2 * background_speed) / 4;
        misty_top.y = -misty_top.get_misty().getHeight();
        misty_top.is_top = true;

        update_misty_pos();
    }

    public void update() {

        int[] coordinates_char_sprite;

        coordinates_char_sprite = update_sprite(loc_x, loc_y, action);

        int tutti_x = coordinates_char_sprite[0];
        int tutti_y = coordinates_char_sprite[1];

        guapo_loc_x = tutti_x;
        guapo_loc_y = tutti_y;

        update_background();

        update_character_bubbles(bubble_producers.brownie_bubbles, brownie);

        update_character_bubbles(bubble_producers.frito_bubbles, frito);

        update_guapo_bubbles();

        update_misty_bubbles(bubble_producers.misty_bubbles, misty);

        update_misty_top_bubbles(bubble_producers.misty_top_bubbles, misty_top);

        update_beggin_strip();

        update_jellyfish();

        update_fish(fishes1, parameters.NUM_FISH1);

        update_fish(fishes2, parameters.NUM_FISH2);

        update_fish(fishes3, parameters.NUM_FISH3);

        update_fish_right(fishes4, parameters.NUM_FISH4);

        update_fish_right(fishes5, parameters.NUM_FISH5);

        update_fish(fishes7, parameters.NUM_FISH6);

        update_pufferfish(pufferfishes, parameters.NUM_PUFFER);

        update_snacks(cheesy_bites);

        update_snacks(paprika);

        update_snacks(cucumbers);

        update_snacks(broccoli);

        // Update Frito
        update_frito();

        // Update Brownie
        update_brownie();

        //Update Misty
        update_misty();

    }

    public void draw_backgrounds(Canvas canvas) {
        for(int i = 0; i < backgrounds_level4.num_backgrounds; ++i) {
            if(backgrounds_level4.backgrounds[i].x < screen_width) {
                canvas.drawBitmap(backgrounds_level4.backgrounds[i].background,
                        backgrounds_level4.backgrounds[i].x,
                        backgrounds_level4.backgrounds[i].y, null);
            }
        }
    }

    private void draw_fish(Canvas canvas, Fish[] fishes, int num_fishes, int screen_width) {
        for (int i = 0;i < num_fishes;i++) {
            Fish fishes_inst = fishes[i];
            if(fishes_inst.x <= screen_width && fishes_inst.x > -fishes_inst.get_fish().getWidth())
                canvas.drawBitmap(fishes_inst.get_fish(), fishes_inst.x, fishes_inst.y, null);
        }
    }

    private void draw_pufferfish(Canvas canvas, Pufferfish[] pufferfishes, int num_puffer_fishes) {
        for (int i = 0;i < num_puffer_fishes;i++) {
            Pufferfish pufferfishes_inst = pufferfishes[i];
            if(pufferfishes_inst.hit_pufferfish) {
                canvas.drawBitmap(pufferfishes_inst.get_fish_hit(), pufferfishes_inst.x, pufferfishes_inst.y, null);
            }
            else {
                canvas.drawBitmap(pufferfishes_inst.get_fish(), pufferfishes_inst.x, pufferfishes_inst.y, null);
            }
        }
    }

    private void update_guapo_bubbles() {
        int yh_snorkel = guapo_loc_y + (guapo_head.getHeight()) / 4;
        int y2_snorkel = yh_snorkel - snorkel_mask.getHeight() / 5;

        bubble_producers.guapo_bubbles.update_bubble1();
        bubble_producers.guapo_bubbles.update_bubble2();
        bubble_producers.guapo_bubbles.update_bubble3();

        if(bubble_producers.guapo_bubbles.are_bubbles_out_of_bounds()) {
            bubble_producers.guapo_bubbles.x_b1 = guapo_loc_x + (3 * guapo_head.getWidth()) / 4;
            bubble_producers.guapo_bubbles.y_b1 = y2_snorkel;
            bubble_producers.guapo_bubbles.x_b2 = guapo_loc_x + (3 * guapo_head.getWidth()) / 4;
            bubble_producers.guapo_bubbles.y_b2 = y2_snorkel;
            bubble_producers.guapo_bubbles.x_b3 = guapo_loc_x + (3 * guapo_head.getWidth()) / 4;
            bubble_producers.guapo_bubbles.y_b3 = y2_snorkel;
            bubble_producers.guapo_bubbles.bubble1_is_just_in_bounds_again = true;
            bubble_producers.guapo_bubbles.bubble2_is_just_in_bounds_again = true;
            bubble_producers.guapo_bubbles.bubble3_is_just_in_bounds_again = true;
        }
        else {
            if(bubble_producers.guapo_bubbles.bubble1_is_just_in_bounds_again) {
                bubble_producers.guapo_bubbles.x_b1 = guapo_loc_x + (3 * guapo_head.getWidth()) / 4;
                bubble_producers.guapo_bubbles.y_b1 = y2_snorkel;
                bubble_producers.guapo_bubbles.bubble1_is_just_in_bounds_again = false;
                play_sound_bubbles();
            }

            bubble_producers.guapo_bubbles.x_b1 = bubble_producers.guapo_bubbles.x_b1 - background_speed;

            if(bubble_producers.guapo_bubbles.b2_can_be_produced) {
                if(bubble_producers.guapo_bubbles.bubble2_is_just_in_bounds_again) {
                    bubble_producers.guapo_bubbles.x_b2 = guapo_loc_x + (3* guapo_head.getWidth())/4;
                    bubble_producers.guapo_bubbles.y_b2 = y2_snorkel;
                    bubble_producers.guapo_bubbles.bubble2_is_just_in_bounds_again = false;
                }
            }
            bubble_producers.guapo_bubbles.x_b2 = bubble_producers.guapo_bubbles.x_b2 - background_speed;

            if(bubble_producers.guapo_bubbles.b3_can_be_produced) {
                if(bubble_producers.guapo_bubbles.bubble3_is_just_in_bounds_again) {
                    bubble_producers.guapo_bubbles.x_b3 = guapo_loc_x + (3 * guapo_head.getWidth())/4;
                    bubble_producers.guapo_bubbles.y_b3 = y2_snorkel;
                    bubble_producers.guapo_bubbles.bubble3_is_just_in_bounds_again = false;
                }
            }
            bubble_producers.guapo_bubbles.x_b3 = bubble_producers.guapo_bubbles.x_b3 - background_speed;
        }
    }

    private void draw_guapo_level4(Canvas canvas) {
        int yh_body = guapo_loc_y + (5 * guapo_head.getHeight()) / 5;
        int y2_body = yh_body - guapo_body.getHeight() / 2;
        int yh_scuba = guapo_loc_y + (4 * guapo_head.getHeight()) / 5;
        int y2_scuba = yh_scuba - scuba_tank.getHeight() / 2;

        canvas.drawBitmap(guapo_body, guapo_loc_x - (guapo_body.getWidth() - (float) (guapo_body.getWidth() * 50) / 100), y2_body, null);
        canvas.drawBitmap(scuba_tank, guapo_loc_x - (scuba_tank.getWidth() - (float) (8 * scuba_tank.getWidth()) / 20), y2_scuba, null);

        if(!hit_jellyfish) {
            canvas.drawBitmap(guapo_head, guapo_loc_x, guapo_loc_y, null);
        }

        Bitmap bubble1 = bubble_producers.guapo_bubbles.get_bubble1();
        Bitmap bubble2 = bubble_producers.guapo_bubbles.get_bubble2();
        Bitmap bubble3 = bubble_producers.guapo_bubbles.get_bubble3();

        canvas.drawBitmap(bubble1,
                bubble_producers.guapo_bubbles.x_b1,
                bubble_producers.guapo_bubbles.y_b1,
                null);

        if(bubble_producers.guapo_bubbles.b2_can_be_produced) {
            canvas.drawBitmap(bubble2,
                    bubble_producers.guapo_bubbles.x_b2,
                    bubble_producers.guapo_bubbles.y_b2,
                    null);
        }
        if(bubble_producers.guapo_bubbles.b3_can_be_produced) {
            canvas.drawBitmap(bubble3,
                    bubble_producers.guapo_bubbles.x_b3,
                    bubble_producers.guapo_bubbles.y_b3,
                    null);
        }
    }

    private void update_character_bubbles(Bubbles character_bubs, CharacterImage character) {
        if(character.appeared) {
            character_bubs.update_bubble1();
            character_bubs.update_bubble2();
            character_bubs.update_bubble3();
            if (character_bubs.are_bubbles_out_of_bounds()) {
                character_bubs.x_b1 = character.x + character.get_character_image().getWidth();
                character_bubs.y_b1 = character.y;
                character_bubs.x_b2 = character.x + character.get_character_image().getWidth();
                character_bubs.y_b2 = character.y;
                character_bubs.x_b3 = character.x + character.get_character_image().getWidth();
                character_bubs.y_b3 = character.y;
                character_bubs.bubble1_is_just_in_bounds_again = true;
                character_bubs.bubble2_is_just_in_bounds_again = true;
                character_bubs.bubble3_is_just_in_bounds_again = true;
            } else {
                if (character_bubs.bubble1_is_just_in_bounds_again) {
                    character_bubs.x_b1 = character.x + character.get_character_image().getWidth();
                    character_bubs.y_b1 = character.y;
                    character_bubs.bubble1_is_just_in_bounds_again = false;
                    play_sound_bubbles();
                }

                character_bubs.x_b1 = character_bubs.x_b1 - background_speed;

                if (character_bubs.b2_can_be_produced) {
                    if (character_bubs.bubble2_is_just_in_bounds_again) {
                        character_bubs.x_b2 = character.x + character.get_character_image().getWidth();
                        character_bubs.y_b2 = character.y;
                        character_bubs.bubble2_is_just_in_bounds_again = false;
                    }
                }
                character_bubs.x_b2 = character_bubs.x_b2 - background_speed;

                if (character_bubs.b3_can_be_produced) {
                    if (character_bubs.bubble3_is_just_in_bounds_again) {
                        character_bubs.x_b3 = character.x + character.get_character_image().getWidth();
                        character_bubs.y_b3 = character.y;
                        character_bubs.bubble3_is_just_in_bounds_again = false;
                    }
                }
                character_bubs.x_b3 = character_bubs.x_b3 - background_speed;
            }
        }
        else {
            character_bubs.y_b1 = -500;
            character_bubs.y_b2 = -500;
            character_bubs.y_b3 = -500;
        }
    }

    private void draw_character_bubbles(Canvas canvas, Bubbles character_bubs) {

        Bitmap bubble1 = character_bubs.get_bubble1();
        Bitmap bubble2 = character_bubs.get_bubble2();
        Bitmap bubble3 = character_bubs.get_bubble3();

        canvas.drawBitmap(bubble1,
                character_bubs.x_b1,
                character_bubs.y_b1,
                null);

        if (character_bubs.b2_can_be_produced) {

            canvas.drawBitmap(bubble2,
                    character_bubs.x_b2,
                    character_bubs.y_b2,
                    null);
        }

        if (character_bubs.b3_can_be_produced) {
            canvas.drawBitmap(bubble3,
                    character_bubs.x_b3,
                    character_bubs.y_b3,
                    null);
        }
    }

    private void update_misty_bubbles(Bubbles misty_bubbles, Misty misty) {
        if(misty.appeared) {

            misty_bubbles.update_bubble1();
            misty_bubbles.update_bubble2();
            misty_bubbles.update_bubble3();

            if (misty_bubbles.are_bubbles_out_of_bounds()) {
                misty_bubbles.x_b1 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b1 = misty.y;
                misty_bubbles.x_b2 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b2 = misty.y;
                misty_bubbles.x_b3 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b3 = misty.y;
                misty_bubbles.bubble1_is_just_in_bounds_again = true;
                misty_bubbles.bubble2_is_just_in_bounds_again = true;
                misty_bubbles.bubble3_is_just_in_bounds_again = true;
            } else {
                if (misty_bubbles.bubble1_is_just_in_bounds_again) {
                    misty_bubbles.x_b1 = misty.pop_up_at_x + misty.get_misty().getWidth();
                    misty_bubbles.y_b1 = misty.y;
                    misty_bubbles.bubble1_is_just_in_bounds_again = false;
                    play_sound_bubbles();
                }

                misty_bubbles.x_b1 = misty_bubbles.x_b1 - background_speed;

                if (misty_bubbles.b2_can_be_produced) {
                    if (misty_bubbles.bubble2_is_just_in_bounds_again) {
                        misty_bubbles.x_b2 = misty.pop_up_at_x + misty.get_misty().getWidth();
                        misty_bubbles.y_b2 = misty.y;
                        misty_bubbles.bubble2_is_just_in_bounds_again = false;
                    }
                }
                misty_bubbles.x_b2 = misty_bubbles.x_b2 - background_speed;

                if (misty_bubbles.b3_can_be_produced) {
                    if (misty_bubbles.bubble3_is_just_in_bounds_again) {
                        misty_bubbles.x_b3 = misty.pop_up_at_x + misty.get_misty().getWidth();
                        misty_bubbles.y_b3 = misty.y;
                        misty_bubbles.bubble3_is_just_in_bounds_again = false;
                    }
                }
                misty_bubbles.x_b3 = misty_bubbles.x_b3 - background_speed;
            }
        }
        else {
            misty_bubbles.y_b1 = -1500;
            misty_bubbles.y_b2 = -1500;
            misty_bubbles.y_b3 = -1500;

        }
    }

    private void update_misty_top_bubbles(Bubbles misty_bubbles, Misty misty) {
        if(misty.appeared) {

            misty_bubbles.update_bubble1();
            misty_bubbles.update_bubble2();
            misty_bubbles.update_bubble3();

            if (misty_bubbles.are_bubbles_out_of_bounds()) {
                misty_bubbles.x_b1 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b1 = misty.y + misty.get_misty().getHeight();
                misty_bubbles.x_b2 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b2 = misty.y + misty.get_misty().getHeight();
                misty_bubbles.x_b3 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b3 = misty.y + misty.get_misty().getHeight();
                misty_bubbles.bubble1_is_just_in_bounds_again = true;
                misty_bubbles.bubble2_is_just_in_bounds_again = true;
                misty_bubbles.bubble3_is_just_in_bounds_again = true;
            } else {
                if (misty_bubbles.bubble1_is_just_in_bounds_again) {
                    misty_bubbles.x_b1 = misty.pop_up_at_x + misty.get_misty().getWidth();
                    misty_bubbles.y_b1 = misty.y + misty.get_misty().getHeight();
                    misty_bubbles.bubble1_is_just_in_bounds_again = false;
                    play_sound_bubbles();
                }

                misty_bubbles.x_b1 = misty_bubbles.x_b1 - background_speed;

                if (misty_bubbles.b2_can_be_produced) {
                    if (misty_bubbles.bubble2_is_just_in_bounds_again) {
                        misty_bubbles.x_b2 = misty.pop_up_at_x + misty.get_misty().getWidth();
                        misty_bubbles.y_b2 = misty.y + misty.get_misty().getHeight();
                        misty_bubbles.bubble2_is_just_in_bounds_again = false;
                    }
                }
                misty_bubbles.x_b2 = misty_bubbles.x_b2 - background_speed;

                if (misty_bubbles.b3_can_be_produced) {
                    if (misty_bubbles.bubble3_is_just_in_bounds_again) {
                        misty_bubbles.x_b3 = misty.pop_up_at_x + misty.get_misty().getWidth();
                        misty_bubbles.y_b3 = misty.y + misty.get_misty().getHeight();
                        misty_bubbles.bubble3_is_just_in_bounds_again = false;
                    }
                }
                misty_bubbles.x_b3 = misty_bubbles.x_b3 - background_speed;
            }
        }
        else {
            misty_bubbles.y_b1 = -1500;
            misty_bubbles.y_b2 = -1500;
            misty_bubbles.y_b3 = -1500;

        }
    }

    private void draw_misty_bubbles(Canvas canvas, Bubbles misty_bubbles, Misty misty) {
        if(misty.appeared) {

            Bitmap bubble1 = misty_bubbles.get_bubble1();
            Bitmap bubble2 = misty_bubbles.get_bubble2();
            Bitmap bubble3 = misty_bubbles.get_bubble3();

            canvas.drawBitmap(bubble1,
                    misty_bubbles.x_b1,
                    misty_bubbles.y_b1,
                    null);

            if (misty_bubbles.b2_can_be_produced) {

                canvas.drawBitmap(bubble2,
                        misty_bubbles.x_b2,
                        misty_bubbles.y_b2,
                        null);
            }

            if (misty_bubbles.b3_can_be_produced) {
                canvas.drawBitmap(bubble3,
                        misty_bubbles.x_b3,
                        misty_bubbles.y_b3,
                        null);
            }
        }
    }
    
    private void draw_bubbles(Canvas canvas) {

        draw_character_bubbles(canvas, bubble_producers.frito_bubbles);

        draw_character_bubbles(canvas, bubble_producers.brownie_bubbles);

        if(!misty_comes_from_top) {
            draw_misty_bubbles(canvas, bubble_producers.misty_bubbles, misty);
        }

        if(misty_comes_from_top) {
            draw_misty_bubbles(canvas, bubble_producers.misty_top_bubbles, misty_top);
        }
    }

    private void draw_jellyfish(Canvas canvas, Jellyfish[] jellyfish, int num_jellyfish) {
        for (int i = 0;i < num_jellyfish;i++) {
            Jellyfish jellyfish_inst = jellyfish[i];
            canvas.drawBitmap(jellyfish_inst.get_jellyfish(), jellyfish_inst.x, jellyfish_inst.y, null);
        }
    }

    private void draw_back_restart_pressed(int left1, int top1, int left2, int top2) {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            draw_backgrounds(canvas);

            // Draw lives
            draw_lives(canvas, screen_width / 2 - 20, 20);

            draw_fish(canvas, fishes1, parameters.NUM_FISH1, screen_width);

            draw_fish(canvas, fishes2, parameters.NUM_FISH2, screen_width);

            draw_fish(canvas, fishes3, parameters.NUM_FISH3, screen_width);

            draw_fish(canvas, fishes4, parameters.NUM_FISH4, screen_width);

            draw_fish(canvas, fishes5, parameters.NUM_FISH5, screen_width);

            draw_fish(canvas, fishes7, parameters.NUM_FISH6, screen_width);

            draw_pufferfish(canvas, pufferfishes, parameters.NUM_PUFFER);

            // Draw snacks
            draw_snacks(canvas, cheesy_bites);

            draw_snacks(canvas, paprika);

            draw_snacks(canvas, cucumbers);

            draw_snacks(canvas, broccoli);

            canvas.drawBitmap(beggin_strip.get_snack_image(), beggin_strip.x, beggin_strip.y, null);

            draw_characters(canvas);

            draw_bubbles(canvas);

            draw_jellyfish(canvas, jellyfish, num_jelly_fish);

            if(num_lives > 0) {
                draw_flag_popup_water(canvas, backgrounds_level4, jellyfish, parameters.LEVEL4_STR);
            }

            // Draw score
            canvas.drawText(" " + score, 30, (float) screen_height / 6, paint);

            // Draw pause button
            draw_pause_button(canvas);

            draw_guapo_level4(canvas);

            // Draw hit Guapo
            canvas.drawBitmap(guapo_head_hit, guapo_loc_x, guapo_loc_y, null);

            // Draw restart and continue buttons
            canvas.drawBitmap(continue_button_not_pressed, left1, top1, null);
            canvas.drawBitmap(restart_game_pressed, left2, top2, null);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void draw_back_continue_pressed(int left1, int top1, int left2, int top2) {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            draw_backgrounds(canvas);

            // Draw lives
            draw_lives(canvas, screen_width / 2 - 20, 20);

            draw_fish(canvas, fishes1, parameters.NUM_FISH1, screen_width);

            draw_fish(canvas, fishes2, parameters.NUM_FISH2, screen_width);

            draw_fish(canvas, fishes3, parameters.NUM_FISH3, screen_width);

            draw_fish(canvas, fishes4, parameters.NUM_FISH4, screen_width);

            draw_fish(canvas, fishes5, parameters.NUM_FISH5, screen_width);

            draw_fish(canvas, fishes7, parameters.NUM_FISH6, screen_width);

            draw_pufferfish(canvas, pufferfishes, parameters.NUM_PUFFER);

            // Draw snacks
            draw_snacks(canvas, cheesy_bites);

            draw_snacks(canvas, paprika);

            draw_snacks(canvas, cucumbers);

            draw_snacks(canvas, broccoli);

            canvas.drawBitmap(beggin_strip.get_snack_image(), beggin_strip.x, beggin_strip.y, null);

            draw_characters(canvas);

            draw_bubbles(canvas);

            draw_jellyfish(canvas, jellyfish, num_jelly_fish);

            if(num_lives > 0) {
                draw_flag_popup_water(canvas, backgrounds_level4, jellyfish, parameters.LEVEL4_STR);
            }

            // Draw score
            canvas.drawText(" " + score, 30, (float) screen_height / 6, paint);

            // Draw pause button
            draw_pause_button(canvas);

            draw_guapo_level4(canvas);

            // Draw hit Guapo
            canvas.drawBitmap(guapo_head_hit, guapo_loc_x, guapo_loc_y, null);

            // Draw restart and continue buttons
            canvas.drawBitmap(continue_button_pressed, left1, top1, null);
            canvas.drawBitmap(restart_game_not_pressed, left2, top2, null);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            draw_backgrounds(canvas);

            // Draw lives
            draw_lives(canvas, screen_width / 2 - 20, 20);

            draw_fish(canvas, fishes1, parameters.NUM_FISH1, screen_width);

            draw_fish(canvas, fishes2, parameters.NUM_FISH2, screen_width);

            draw_fish(canvas, fishes3, parameters.NUM_FISH3, screen_width);

            draw_fish(canvas, fishes4, parameters.NUM_FISH4, screen_width);

            draw_fish(canvas, fishes5, parameters.NUM_FISH5, screen_width);

            draw_fish(canvas, fishes7, parameters.NUM_FISH6, screen_width);

            draw_pufferfish(canvas, pufferfishes, parameters.NUM_PUFFER);

            // Draw snacks
            draw_snacks(canvas, cheesy_bites);

            draw_snacks(canvas, paprika);

            draw_snacks(canvas, cucumbers);

            draw_snacks(canvas, broccoli);

            canvas.drawBitmap(beggin_strip.get_snack_image(), beggin_strip.x, beggin_strip.y, null);

            draw_characters(canvas);

            draw_bubbles(canvas);

            draw_jellyfish(canvas, jellyfish, num_jelly_fish);

            if(num_lives > 0) {
                draw_flag_popup_water(canvas, backgrounds_level4, jellyfish, parameters.LEVEL4_STR);
            }

            // Draw score
            canvas.drawText(" " + score, 30, (float) screen_height / 6, paint);

            // Draw pause button
            draw_pause_button(canvas);

            draw_guapo_level4(canvas);

            if(hit_jellyfish && num_lives > 0) {
                // Draw hit Guapo
                canvas.drawBitmap(guapo_head_hit, guapo_loc_x, guapo_loc_y, null);

                // Draw restart and continue buttons
                canvas.drawBitmap(continue_button_not_pressed, (float)screen_width / 2 - 10 - continue_button_not_pressed.getWidth(), (float) screen_height / 2 - 10, null);

                canvas.drawBitmap(restart_game_not_pressed, (float) screen_width / 2 + 10, (float) screen_height / 2 - 10, null);

                is_playing = false;
            }
            else if(hit_jellyfish && num_lives == 0) {
                is_playing = false;
                canvas.drawBitmap(guapo_head_hit, guapo_loc_x - (float) guapo_head_hit.getWidth() / 20, guapo_loc_y, null);
                getHolder().unlockCanvasAndPost(canvas);
                save_high_score();
                quit_game();
                return;
            }

            getHolder().unlockCanvasAndPost(canvas);

        }
    }

    private void restart_game() {

        try {
            Thread.sleep(2000);
            game_activity.startActivity(new Intent(game_activity, GameActivityLevel4.class));
            game_activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void quit_game() {

        save_game_state(parameters.GAME_STATE_STR + parameters.LEVEL4_STR, false);

        num_lives = parameters.NUM_LIVES;

        save_num_lives(parameters.NUM_LIVES_STR + parameters.LEVEL4_STR);

        try {
            Thread.sleep(2000);
            game_activity.startActivity(new Intent(game_activity, MainActivity.class));
            game_activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void save_high_score() {

        if (prefs.getInt("high_score_level4", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("high_score_level4", score);
            editor.apply();
        }
    }

    public void resume() {

        is_playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {

        try {
            is_playing = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int local_x = (int) event.getX();
                int local_y = (int) event.getY();
                action = MotionEvent.ACTION_DOWN;
                boolean in_pause_area = (local_x >= (float) (pause_region_min_x - pause_button.getWidth() / 2)) &&
                        (local_x <= (float) screen_width) &&
                        (local_y >= (float) 0) &&
                        (local_y <= (float) (pause_region_max_y + pause_button.getHeight() / 2));
                if(in_pause_area && !hit_jellyfish) {
                    if(!game_paused) {
                        game_paused = true;
                        loc_x = guapo_loc_x + (float) guapo_head.getWidth() / 2;
                        loc_y = guapo_loc_y + (float) guapo_head.getHeight() / 2;
                    }
                    else if(game_paused) {
                        game_paused = false;
                    }
                }
                else if(!in_pause_area && !game_paused && !hit_jellyfish){
                    loc_x = local_x;
                    loc_y = local_y;
                    x_velocity_o = 0;
                    y_velocity_o = 0;
                }
                else if(hit_jellyfish) {
                    int left_continue = screen_width / 2 - 10 - continue_button_not_pressed.getWidth();
                    int top_continue = screen_height / 2 - 10;
                    int right_continue = screen_width / 2 - 10;
                    int bottom_continue = screen_height / 2 - 10 + continue_button_not_pressed.getHeight();

                    int left_restart = screen_width / 2 + 10;
                    int top_restart = screen_height / 2 - 10;
                    int right_restart = screen_width / 2 + 10 + restart_game_not_pressed.getWidth();
                    int bottom_restart = screen_height / 2 - 10 + restart_game_not_pressed.getHeight();

                    if(local_x >= left_continue && local_x <= right_continue && local_y >= top_continue && local_y <= bottom_continue) {
                        draw_back_continue_pressed(left_continue, top_continue, left_restart, top_restart);
                        save_lives(parameters.LEVEL4_STR);
                        restart_game();
                    }
                    else if(local_x >= left_restart && local_x <= right_restart && local_y >= top_restart && local_y <= bottom_restart) {
                        draw_back_restart_pressed(left_continue, top_continue, left_restart, top_restart);
                        save_high_score();
                        quit_game();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                local_x = (int) event.getX();
                local_y = (int) event.getY();
                action = MotionEvent.ACTION_MOVE;
                if(!game_paused) {
                    loc_x = local_x;
                    loc_y = local_y;
                }
                break;
            case MotionEvent.ACTION_UP:
                action = MotionEvent.ACTION_UP;
                break;
        }

        return true;
    }
}
