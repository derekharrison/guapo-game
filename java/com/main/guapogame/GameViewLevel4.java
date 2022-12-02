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
    private Fish[] fishes;
    private Fish[] fishes2;
    private Fish[] fishes3;
    private Fish[] fishes4;
    private Fish[] fishes5;
    private Fish[] fishes7;
    private Pufferfish[] pufferfishes;
    private BackgroundsLevel4 backgrounds_level4;
    private GameActivityLevel4 game_activity;
    private Bitmap scuba_tank;
    private Bitmap snorkel_mask;
    private boolean hit_jellyfish = false;
    private boolean[] hit_pufferfish;
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

        set_parameters();

        backgrounds_level4 = new BackgroundsLevel4(activity, screen_width, screen_height, getResources());

        init_all_images();
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
        pufferfishes = new Pufferfish[parameters.num_puffer_fishes];
        for (int i = 0;i < parameters.num_puffer_fishes;i++) {

            Pufferfish pufferfishes_inst = new Pufferfish(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            pufferfishes[i] = pufferfishes_inst;

        }

        hit_pufferfish = new boolean[parameters.num_puffer_fishes];
        for(int i = 0; i < parameters.num_puffer_fishes; ++i) {
            hit_pufferfish[i] = false;
        }
    }

    public void init_jelly_fish() {
        jellyfish = new Jellyfish[parameters.max_num_jellyfish];
        for (int i = 0;i < parameters.max_num_jellyfish; i++) {
            Jellyfish jellyfish_inst = new Jellyfish(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            jellyfish[i] = jellyfish_inst;
        }
    }

    public void init_images_snacks_and_characters() {
        bubble_producers = new BubbleProducers(getResources(), (int) screen_factor_x, (int) screen_factor_y);

        cheesy_bites = init_snacks(parameters.num_cheesy_bites, parameters.points_cheesy_bites, R.drawable.cheesy_bite_resized);

        paprika = init_snacks(parameters.num_paprika, parameters.points_paprika, R.drawable.paprika_bitmap_cropped);

        cucumbers = init_snacks(parameters.num_cucumbers, parameters.points_cucumber, R.drawable.cucumber_bitmap_cropped);

        frito = init_character((int) screen_factor_x, (int) screen_factor_y, R.drawable.frito_snorkel_bitmap_cropped, R.drawable.frito_snorkel_hit_bitmap_rotated_cropped, CharacterIds.FRITO);

        brownie = init_character((int) screen_factor_x, (int) screen_factor_y, R.drawable.brownie_snorkel_bitmap_cropped, R.drawable.brownie_snorkel_bitmap_hit_cropped, CharacterIds.FRITO);

        init_misty_snorkel();

        broccoli = init_snacks(parameters.num_broccoli, parameters.points_broccoli, R.drawable.broccoli_bitmap_cropped);

        beggin_strip = new Snack(getResources(), (int) screen_factor_x, (int) screen_factor_y, R.drawable.beggin_strip_cropped);

        tutti_image = new Image(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.guapo_snorkelhead_bitmap_cropped,
                R.drawable.cape1_guapo_bitmap_cropped,
                R.drawable.cape2_guapo_bitmap_cropped);

        image = tutti_image.get_tutti_image();
        cape_image = tutti_image.get_tutti_cape();

        image_hit = BitmapFactory.decodeResource(getResources(),R.drawable.guapo_snorkelhit_bitmap_cropped);
        image_hit = Bitmap.createScaledBitmap(image_hit, (int) screen_factor_x, (int) screen_factor_y, false);

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
        fishes = init_fish(parameters.num_fishes, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish5_bitmap_cropped, R.drawable.fish6_bitmap_cropped);

        fishes2 = init_fish(parameters.num_fishes2, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish3_bitmap_cropped, R.drawable.fish4_bitmap_cropped);

        fishes3 = init_fish(parameters.num_fishes3, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish8_bitmap_cropped, R.drawable.fish9_bitmap_cropped);

        fishes4 = init_fish(parameters.num_fishes4, (int) screen_factor_x, (int) screen_factor_y, R.drawable.yellowfish_facingright_raw, R.drawable.yellowfish_facingright_raw2);

        fishes5 = init_fish(parameters.num_fishes5, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish12_bitmap_cropped, R.drawable.fish12b_bitmap_cropped);

        fishes7 = init_fish(parameters.num_fishes7, (int) screen_factor_x, (int) screen_factor_y, R.drawable.fish14b_bitmap_cropped_resized_purple, R.drawable.fish14bb_bitmap_cropped_resized_purple);

    }

    public void init_all_images() {
        init_background_fish();

        init_puffer_fish();

        init_jelly_fish();

        init_images_snacks_and_characters();
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

        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        int targetFPS = 30;
        long targetTime = 1000 / targetFPS;

        while (is_playing) {

            startTime = System.nanoTime();

            update();
            draw();

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                Thread.sleep(waitTime);
            }catch(Exception e){
                System.out.println("did not sleep");
            }

            totalTime += System.nanoTime()-startTime;
            frameCount++;
            if(frameCount == targetFPS)
            {
                double averageFPS = 1000 / (double) ((totalTime / frameCount) / 1000000);
                frameCount =0;
                totalTime = 0;
                System.out.println(averageFPS);
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

        if((score >= (parameters.difficulty_level * parameters.score_interval_for_diff_level)) &&
                (score <= ((parameters.difficulty_level + 1) * parameters.score_interval_for_diff_level)) &&
                (parameters.num_jellyfish <= parameters.max_num_jellyfish)) {
            parameters.num_jellyfish = parameters.num_jellyfish + parameters.num_jellyfish_increase;
            parameters.difficulty_level++;
        }

        for (int i = 0; i < parameters.num_jellyfish; i++) {

            Jellyfish jellyfish_inst = jellyfish[i];
            jellyfish_inst.x -= jellyfish_inst.speed;

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

            Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + image.getWidth(), guapo_loc_y + image.getHeight());
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

            if (pufferfish_inst.x + pufferfish_inst.width < 0) {

                int bound = 3 * background_speed;
                pufferfish_inst.speed = random.nextInt(bound);

                if (pufferfish_inst.speed < (bound/2)) {
                    pufferfish_inst.speed = bound / 2;
                }

                pufferfish_inst.x = screen_width + 3 * screen_width;
                pufferfish_inst.y = random.nextInt(screen_height - pufferfish_inst.height);

                hit_pufferfish[i] = false;
                pufferfish_inst.play_sound_allowed = true;

            }

            float pufferfish_inst_width = (float) pufferfish_inst.get_fish_width();
            float pufferfish_inst_height = (float) pufferfish_inst.get_fish_height();

            Rect rect_1 = new Rect(guapo_loc_x, guapo_loc_y, guapo_loc_x + image.getWidth(), guapo_loc_y + image.getHeight());
            Rect rect_2 = new Rect(pufferfish_inst.x + (int) ((pufferfish_inst_width*35)/100),
                    pufferfish_inst.y + (int) ((pufferfish_inst_height*35)/100),
                    pufferfish_inst.x + (int) ((pufferfish_inst_width*65)/100),
                    pufferfish_inst.y + (int) ((pufferfish_inst_height*65)/100));

            if (Rect.intersects(rect_1, rect_2)) {
                hit_pufferfish[i] = true;
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

        misty.x = random.nextInt(screen_width - misty.get_character_image().getWidth() / 2) + 23 * screen_width;
        misty.pop_up_at_x = random.nextInt(((screen_width * 3) / 4 - screen_width / 4) + 1) + screen_width / 4;
        misty.y_vel = (2 * background_speed) / 4;
        misty.y = screen_height;

        misty_top = new Misty(getResources(),
                (int) screen_factor_x,
                (int) screen_factor_y,
                R.drawable.misty_withsnorkel_bitmap_cropped_rotated,
                R.drawable.misty_withsnorkel_hit_bitmap_cropped_rotated,
                CharacterIds.MISTY_TOP);

        misty_top.x = misty.x;
        misty_top.pop_up_at_x = random.nextInt(((screen_width * 3) / 4 - screen_width / 4) + 1) + screen_width / 4;
        misty_top.y_vel = (2 * background_speed) / 4;
        misty_top.y = -misty_top.get_misty().getHeight();

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

        update_beggin_strip();

        update_jellyfish();

        update_fish(fishes, parameters.num_fishes);

        update_fish(fishes2, parameters.num_fishes2);

        update_fish(fishes3, parameters.num_fishes3);

        update_fish_right(fishes4, parameters.num_fishes4);

        update_fish_right(fishes5, parameters.num_fishes5);

        update_fish(fishes7, parameters.num_fishes7);

        update_pufferfish(pufferfishes, parameters.num_puffer_fishes);

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

    private void draw_fish(Canvas canvas, Fish[] fishes, int num_fishes) {
        for (int i = 0;i < num_fishes;i++) {
            Fish fishes_inst = fishes[i];
            canvas.drawBitmap(fishes_inst.get_fish(), fishes_inst.x, fishes_inst.y, null);
        }
    }

    private void draw_pufferfish(Canvas canvas, Pufferfish[] pufferfishes, int num_puffer_fishes) {
        for (int i = 0;i < num_puffer_fishes;i++) {
            Pufferfish pufferfishes_inst = pufferfishes[i];
            if(hit_pufferfish[i]) {
                canvas.drawBitmap(pufferfishes_inst.get_fish_hit(), pufferfishes_inst.x, pufferfishes_inst.y, null);
            }
            else {
                canvas.drawBitmap(pufferfishes_inst.get_fish(), pufferfishes_inst.x, pufferfishes_inst.y, null);
            }
        }
    }

    private void draw_guapo_level4(Canvas canvas) {
        int yh_body = guapo_loc_y + (5 * image.getHeight()) / 5;
        int y2_body = yh_body - guapo_body.getHeight() / 2;
        int yh_scuba = guapo_loc_y + (4 * image.getHeight()) / 5;
        int y2_scuba = yh_scuba - scuba_tank.getHeight() / 2;
        int yh_snorkel = guapo_loc_y + (image.getHeight()) / 4;
        int y2_snorkel = yh_snorkel - snorkel_mask.getHeight() / 5;

        canvas.drawBitmap(guapo_body, guapo_loc_x - (guapo_body.getWidth() - (float) (guapo_body.getWidth() * 50) / 100), y2_body, null);
        canvas.drawBitmap(scuba_tank, guapo_loc_x - (scuba_tank.getWidth() - (float) (8 * scuba_tank.getWidth()) / 20), y2_scuba, null);

        if(!hit_jellyfish) {
            canvas.drawBitmap(image, guapo_loc_x, guapo_loc_y, null);
        }

        if(bubble_producers.guapo_bubbles.are_bubbles_out_of_bounds()) {
            bubble_producers.guapo_bubbles.x_b1 = guapo_loc_x + (3 * image.getWidth()) / 4;
            bubble_producers.guapo_bubbles.y_b1 = y2_snorkel;
            bubble_producers.guapo_bubbles.x_b2 = guapo_loc_x + (3 * image.getWidth()) / 4;
            bubble_producers.guapo_bubbles.y_b2 = y2_snorkel;
            bubble_producers.guapo_bubbles.x_b3 = guapo_loc_x + (3 * image.getWidth()) / 4;
            bubble_producers.guapo_bubbles.y_b3 = y2_snorkel;
            bubble_producers.guapo_bubbles.bubble1_is_just_in_bounds_again = true;
            bubble_producers.guapo_bubbles.bubble2_is_just_in_bounds_again = true;
            bubble_producers.guapo_bubbles.bubble3_is_just_in_bounds_again = true;
        }
        else {
            Bitmap bubble1 = bubble_producers.guapo_bubbles.get_bubble1();
            Bitmap bubble2 = bubble_producers.guapo_bubbles.get_bubble2();
            Bitmap bubble3 = bubble_producers.guapo_bubbles.get_bubble3();

            if(bubble_producers.guapo_bubbles.bubble1_is_just_in_bounds_again) {
                bubble_producers.guapo_bubbles.x_b1 = guapo_loc_x + (3 * image.getWidth()) / 4;
                bubble_producers.guapo_bubbles.y_b1 = y2_snorkel;
                bubble_producers.guapo_bubbles.bubble1_is_just_in_bounds_again = false;
                play_sound_bubbles();
            }

            canvas.drawBitmap(bubble1,
                    bubble_producers.guapo_bubbles.x_b1,
                    bubble_producers.guapo_bubbles.y_b1,
                    null);
            bubble_producers.guapo_bubbles.x_b1 = bubble_producers.guapo_bubbles.x_b1 - background_speed;

            if(bubble_producers.guapo_bubbles.b2_can_be_produced) {
                if(bubble_producers.guapo_bubbles.bubble2_is_just_in_bounds_again) {
                    bubble_producers.guapo_bubbles.x_b2 = guapo_loc_x + (3*image.getWidth())/4;
                    bubble_producers.guapo_bubbles.y_b2 = y2_snorkel;
                    bubble_producers.guapo_bubbles.bubble2_is_just_in_bounds_again = false;
                }

                canvas.drawBitmap(bubble2,
                        bubble_producers.guapo_bubbles.x_b2,
                        bubble_producers.guapo_bubbles.y_b2,
                        null);
            }
            bubble_producers.guapo_bubbles.x_b2 = bubble_producers.guapo_bubbles.x_b2 - background_speed;

            if(bubble_producers.guapo_bubbles.b3_can_be_produced) {
                if(bubble_producers.guapo_bubbles.bubble3_is_just_in_bounds_again) {
                    bubble_producers.guapo_bubbles.x_b3 = guapo_loc_x + (3*image.getWidth())/4;
                    bubble_producers.guapo_bubbles.y_b3 = y2_snorkel;
                    bubble_producers.guapo_bubbles.bubble3_is_just_in_bounds_again = false;
                }

                canvas.drawBitmap(bubble3,
                        bubble_producers.guapo_bubbles.x_b3,
                        bubble_producers.guapo_bubbles.y_b3,
                        null);
            }
            bubble_producers.guapo_bubbles.x_b3 = bubble_producers.guapo_bubbles.x_b3 - background_speed;
        }
    }

    private void draw_character_bubbles(Canvas canvas, Bubbles character_bubs, Character character) {
        if(character.appeared) {
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
                Bitmap bubble1 = character_bubs.get_bubble1();
                Bitmap bubble2 = character_bubs.get_bubble2();
                Bitmap bubble3 = character_bubs.get_bubble3();

                if (character_bubs.bubble1_is_just_in_bounds_again) {
                    character_bubs.x_b1 = character.x + character.get_character_image().getWidth();
                    character_bubs.y_b1 = character.y;
                    character_bubs.bubble1_is_just_in_bounds_again = false;
                    play_sound_bubbles();
                }

                canvas.drawBitmap(bubble1,
                        character_bubs.x_b1,
                        character_bubs.y_b1,
                        null);
                character_bubs.x_b1 = character_bubs.x_b1 - background_speed;

                if (character_bubs.b2_can_be_produced) {
                    if (character_bubs.bubble2_is_just_in_bounds_again) {
                        character_bubs.x_b2 = character.x + character.get_character_image().getWidth();
                        character_bubs.y_b2 = character.y;
                        character_bubs.bubble2_is_just_in_bounds_again = false;
                    }

                    canvas.drawBitmap(bubble2,
                            character_bubs.x_b2,
                            character_bubs.y_b2,
                            null);
                }
                character_bubs.x_b2 = character_bubs.x_b2 - background_speed;

                if (character_bubs.b3_can_be_produced) {
                    if (character_bubs.bubble3_is_just_in_bounds_again) {
                        character_bubs.x_b3 = character.x + character.get_character_image().getWidth();
                        character_bubs.y_b3 = character.y;
                        character_bubs.bubble3_is_just_in_bounds_again = false;
                    }

                    canvas.drawBitmap(bubble3,
                            character_bubs.x_b3,
                            character_bubs.y_b3,
                            null);
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
    
    private void draw_misty_bubbles(Canvas canvas, Bubbles misty_bubbles, Misty misty) {
        if(misty.appeared) {

            if (misty_bubbles.are_bubbles_out_of_bounds()) {
                misty_bubbles.x_b1 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b1 = misty.y + misty_top.get_misty().getHeight();
                misty_bubbles.x_b2 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b2 = misty.y + misty_top.get_misty().getHeight();
                misty_bubbles.x_b3 = misty.pop_up_at_x + misty.get_misty().getWidth();
                misty_bubbles.y_b3 = misty.y + misty_top.get_misty().getHeight();
                misty_bubbles.bubble1_is_just_in_bounds_again = true;
                misty_bubbles.bubble2_is_just_in_bounds_again = true;
                misty_bubbles.bubble3_is_just_in_bounds_again = true;
            } else {
                Bitmap bubble1 = misty_bubbles.get_bubble1();
                Bitmap bubble2 = misty_bubbles.get_bubble2();
                Bitmap bubble3 = misty_bubbles.get_bubble3();

                if (misty_bubbles.bubble1_is_just_in_bounds_again) {
                    misty_bubbles.x_b1 = misty.pop_up_at_x + misty.get_misty().getWidth();
                    misty_bubbles.y_b1 = misty.y;
                    misty_bubbles.bubble1_is_just_in_bounds_again = false;
                    play_sound_bubbles();
                }

                canvas.drawBitmap(bubble1,
                        misty_bubbles.x_b1,
                        misty_bubbles.y_b1,
                        null);
                misty_bubbles.x_b1 = misty_bubbles.x_b1 - background_speed;

                if (misty_bubbles.b2_can_be_produced) {
                    if (misty_bubbles.bubble2_is_just_in_bounds_again) {
                        misty_bubbles.x_b2 = misty.pop_up_at_x + misty.get_misty().getWidth();
                        misty_bubbles.y_b2 = misty.y;
                        misty_bubbles.bubble2_is_just_in_bounds_again = false;
                    }

                    canvas.drawBitmap(bubble2,
                            misty_bubbles.x_b2,
                            misty_bubbles.y_b2,
                            null);
                }
                misty_bubbles.x_b2 = misty_bubbles.x_b2 - background_speed;

                if (misty_bubbles.b3_can_be_produced) {
                    if (misty_bubbles.bubble3_is_just_in_bounds_again) {
                        misty_bubbles.x_b3 = misty.pop_up_at_x + misty.get_misty().getWidth();
                        misty_bubbles.y_b3 = misty.y;
                        misty_bubbles.bubble3_is_just_in_bounds_again = false;
                    }

                    canvas.drawBitmap(bubble3,
                            misty_bubbles.x_b3,
                            misty_bubbles.y_b3,
                            null);
                }
                misty_bubbles.x_b3 = misty_bubbles.x_b3 - background_speed;
            }

        }
        else if(!misty.appeared && !misty_comes_from_top){
            misty_bubbles.y_b1 = -500;
            misty_bubbles.y_b2 = -500;
            misty_bubbles.y_b3 = -500;
        }    
    }
    
    private void draw_bubbles(Canvas canvas) {

        draw_character_bubbles(canvas, bubble_producers.frito_bubbles, frito);

        draw_character_bubbles(canvas, bubble_producers.brownie_bubbles, brownie);

        if(!misty_comes_from_top) {
            draw_misty_bubbles(canvas, bubble_producers.misty_bubbles, misty);
        }

        if(misty_comes_from_top) {
            draw_misty_bubbles(canvas, bubble_producers.misty_bubbles, misty_top);
        }
    }

    private void draw_jellyfish(Canvas canvas, Jellyfish[] jellyfish, int num_jellyfish) {
        for (int i = 0;i < num_jellyfish;i++) {
            Jellyfish jellyfish_inst = jellyfish[i];
            canvas.drawBitmap(jellyfish_inst.get_jellyfish(), jellyfish_inst.x, jellyfish_inst.y, null);
        }
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            draw_backgrounds(canvas);

            draw_fish(canvas, fishes, parameters.num_fishes);

            draw_fish(canvas, fishes2, parameters.num_fishes2);

            draw_fish(canvas, fishes3, parameters.num_fishes3);

            draw_fish(canvas, fishes4, parameters.num_fishes4);

            draw_fish(canvas, fishes5, parameters.num_fishes5);

            draw_fish(canvas, fishes7, parameters.num_fishes7);

            draw_pufferfish(canvas, pufferfishes, parameters.num_puffer_fishes);

            // Draw snacks
            draw_snacks(canvas, cheesy_bites);

            draw_snacks(canvas, paprika);

            draw_snacks(canvas, cucumbers);

            draw_snacks(canvas, broccoli);

            canvas.drawBitmap(beggin_strip.get_snack_image(), beggin_strip.x, beggin_strip.y, null);

            draw_characters(canvas);

            draw_bubbles(canvas);

            draw_jellyfish(canvas, jellyfish, parameters.num_jellyfish);

            // Draw score
            canvas.drawText(" " + score, 30, (float) screen_height / 6, paint);

            // Draw pause button
            draw_pause_button(canvas);

            draw_guapo_level4(canvas);

            if(hit_jellyfish) {
                is_playing = false;
                canvas.drawBitmap(image_hit, guapo_loc_x - (float) image_hit.getWidth() / 20, guapo_loc_y, null);
                getHolder().unlockCanvasAndPost(canvas);
                save_high_score();
                quit_game();
                return;
            }

            getHolder().unlockCanvasAndPost(canvas);

        }

        if(paused_is_set) {
            pause();
        }
    }

    private void quit_game() {

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
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int local_x = (int) event.getX();
                int local_y = (int) event.getY();
                action = MotionEvent.ACTION_DOWN;
                boolean in_pause_area = (local_x >= (float) (pause_region_min_x - pause_button.getWidth() / 2)) &&
                        (local_x <= (float) screen_width) &&
                        (local_y >= (float) 0) &&
                        (local_y <= (float) (pause_region_max_y + pause_button.getHeight() / 2));
                if(in_pause_area) {
                    if(!game_paused) {
                        game_paused = true;
                        loc_x = guapo_loc_x + (float) image.getWidth() / 2;
                        loc_y = guapo_loc_y + (float) image.getHeight() / 2;
                    }
                    else if(game_paused) {
                        game_paused = false;
                        paused_is_set = false;
                        resume();
                    }
                }
                else if(!in_pause_area && !game_paused){
                    loc_x = local_x;
                    loc_y = local_y;
                    x_velocity_o = 0;
                    y_velocity_o = 0;
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
