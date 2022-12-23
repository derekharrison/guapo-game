package com.main.guapogame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class GameViewLevel3 extends MainView implements Runnable {
    private Thread thread;

    private Eagle[] eagles;

    private Backgrounds backgrounds_level3;

    private GameActivityLevel3 game_activity;

    public GameViewLevel3(Context context) {
        super(context);
    }

    public GameViewLevel3(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameViewLevel3(GameActivityLevel3 activity) {
        super(activity);

        game_activity = activity;

        backgrounds_level3 = new Backgrounds(activity, screen_width, screen_height, parameters.BACKGROUND_IMAGES_LEVEL3);

        init_eagles();

        init_reset(parameters.LEVEL3_STR);
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

    private void init_reset(String level) {
        boolean in_game = get_game_state(parameters.GAME_STATE_STR + level);
        if(in_game) {
            get_num_lives(parameters.NUM_LIVES_STR + level);

            get_backgrounds(backgrounds_level3, parameters.BACKGROUNDS_STR + level);

            get_num_birds(parameters.NUM_BIRDS_STR + level);

            get_eagles(eagles, parameters.BIRDS_STR + level);

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

    public void init_eagles() {
        eagles = new Eagle[parameters.MAX_NUM_BIRDS];
        for (int i = 0; i < parameters.MAX_NUM_BIRDS; i++) {

            Eagle eagle = new Eagle(getResources(), (int) screen_factor_x, (int) screen_factor_y);
            eagles[i] = eagle;
        }
    }

    public void draw_backgrounds(Canvas canvas) {
        for(int i = 0; i < backgrounds_level3.num_backgrounds; ++i) {
            if(backgrounds_level3.backgrounds[i].x < screen_width) {
                canvas.drawBitmap(backgrounds_level3.backgrounds[i].background,
                        backgrounds_level3.backgrounds[i].x,
                        backgrounds_level3.backgrounds[i].y, null);
            }
        }
    }

    private void draw_back_restart_pressed(int left1, int top1, int left2, int top2) {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            // Draw backgrounds
            draw_backgrounds(canvas);

            // Draw lives
            draw_lives(canvas, screen_width / 2 - 20, 20);

            // Draw snacks, characters, score and pause button
            draw_all(canvas);

            // Draw birds
            draw_birds(canvas, eagles);

            // Draw sun popup
            draw_sun_popup(canvas, "high_score_level3");

            // Draw checkpoint flag
            if(num_lives > 0) {
                draw_flag_popup(canvas, backgrounds_level3, eagles, "level_3");
            }

            // Draw Guapo
            draw_guapo(canvas);

            // Draw hit Guapo
            canvas.drawBitmap(guapo_head_hit, guapo_loc_x, guapo_loc_y, null);

            canvas.drawBitmap(continue_button_not_pressed, left1, top1, null);
            canvas.drawBitmap(restart_game_pressed, left2, top2, null);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void draw_back_continue_pressed(int left1, int top1, int left2, int top2) {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            // Draw backgrounds
            draw_backgrounds(canvas);

            // Draw lives
            draw_lives(canvas, screen_width / 2 - 20, 20);

            // Draw snacks, characters, score and pause button
            draw_all(canvas);

            // Draw birds
            draw_birds(canvas, eagles);

            // Draw sun popup
            draw_sun_popup(canvas, "high_score_level3");

            // Draw checkpoint flag
            if(num_lives > 0) {
                draw_flag_popup(canvas, backgrounds_level3, eagles, "level_3");
            }

            // Draw Guapo
            draw_guapo(canvas);

            // Draw hit Guapo
            canvas.drawBitmap(guapo_head_hit, guapo_loc_x, guapo_loc_y, null);

            canvas.drawBitmap(continue_button_pressed, left1, top1, null);
            canvas.drawBitmap(restart_game_not_pressed, left2, top2, null);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            // Draw backgrounds
            draw_backgrounds(canvas);

            // Draw lives
            draw_lives(canvas, screen_width / 2 - 20, 20);

            // Draw snacks, characters, score and pause button
            draw_all(canvas);

            // Draw birds
            draw_birds(canvas, eagles);

            // Draw sun popup
            draw_sun_popup(canvas, "high_score_level3");

            // Draw checkpoint flag
            if(num_lives > 0) {
                draw_flag_popup(canvas, backgrounds_level3, eagles, "level_3");
            }

            // Draw Guapo
            draw_guapo(canvas);

            // Case Guapo hit a bird. Draw hit Guapo and stop game.
            if(hit_bird && num_lives > 0) {
                // Draw hit Guapo
                canvas.drawBitmap(guapo_head_hit, guapo_loc_x, guapo_loc_y, null);

                // Draw restart and continue buttons
                canvas.drawBitmap(continue_button_not_pressed, (float)screen_width / 2 - 10 - continue_button_not_pressed.getWidth(), (float) screen_height / 2 - 10, null);

                canvas.drawBitmap(restart_game_not_pressed, (float) screen_width / 2 + 10, (float) screen_height / 2 - 10, null);

                is_playing = false;
            }
            else if(hit_bird && num_lives == 0) {
                // Set playing bit to false
                is_playing = false;

                // Draw hit Guapo
                canvas.drawBitmap(guapo_head_hit, guapo_loc_x, guapo_loc_y, null);

                getHolder().unlockCanvasAndPost(canvas);

                // Save high score and stop game.
                save_high_score("high_score_level3");
                quit_game();
                return;
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    public void update_background() {
        for(int i = 0; i < backgrounds_level3.num_backgrounds; ++i) {
            backgrounds_level3.backgrounds[i].x -= background_speed;

            if(backgrounds_level3.backgrounds[i].x + backgrounds_level3.backgrounds[i].background.getWidth() < 0) {
                if(i == 0) {
                    backgrounds_level3.backgrounds[0].x = backgrounds_level3.backgrounds[backgrounds_level3.num_backgrounds - 1].x +
                            backgrounds_level3.backgrounds[backgrounds_level3.num_backgrounds - 1].background.getWidth() - 10;
                }
                if(i > 0) {
                    backgrounds_level3.backgrounds[i].x = backgrounds_level3.backgrounds[i - 1].x + backgrounds_level3.backgrounds[i - 1].background.getWidth() - 10;
                }
            }
        }
    }

    public void update() {

        update_background();

        update_birds(eagles);

        // Update Guapo, snacks and characters
        update_all();
    }

    private void restart_game() {

        try {
            Thread.sleep(1000);
            soundPool.release();
            soundPool = null;
            game_activity.startActivity(new Intent(game_activity, GameActivityLevel3.class));
            game_activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void quit_game() {

        save_game_state(parameters.GAME_STATE_STR + parameters.LEVEL3_STR, false);

        num_lives = parameters.NUM_LIVES;

        save_num_lives(parameters.NUM_LIVES_STR + parameters.LEVEL3_STR);

        try {
            Thread.sleep(3000);
            soundPool.release();
            soundPool = null;
            game_activity.startActivity(new Intent(game_activity, MainActivity.class));
            game_activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {

        is_playing = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {

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
                if(in_pause_area && !hit_bird) {
                    if(!game_paused) {
                        game_paused = true;
                        loc_x = guapo_loc_x + (float) guapo_head.getWidth() / 2;
                        loc_y = guapo_loc_y + (float) guapo_head.getHeight() / 2;
                    }
                    else if(game_paused) {
                        game_paused = false;
                    }
                }
                else if(!in_pause_area && !game_paused && !hit_bird){
                    loc_x = local_x;
                    loc_y = local_y;
                    x_velocity_o = 0;
                    y_velocity_o = 0;
                }
                else if(hit_bird && !continue_restart_pressed) {
                    int left_continue = screen_width / 2 - 10 - continue_button_not_pressed.getWidth();
                    int top_continue = screen_height / 2 - 10;
                    int right_continue = screen_width / 2 - 10;
                    int bottom_continue = screen_height / 2 - 10 + continue_button_not_pressed.getHeight();

                    int left_restart = screen_width / 2 + 10;
                    int top_restart = screen_height / 2 - 10;
                    int right_restart = screen_width / 2 + 10 + restart_game_not_pressed.getWidth();
                    int bottom_restart = screen_height / 2 - 10 + restart_game_not_pressed.getHeight();

                    if(local_x >= left_continue && local_x <= right_continue && local_y >= top_continue && local_y <= bottom_continue) {
                        continue_restart_pressed = true;
                        draw_back_continue_pressed(left_continue, top_continue, left_restart, top_restart);
                        save_lives(parameters.LEVEL3_STR);
                        restart_game();
                    }
                    else if(local_x >= left_restart && local_x <= right_restart && local_y >= top_restart && local_y <= bottom_restart) {
                        continue_restart_pressed = true;
                        draw_back_restart_pressed(left_continue, top_continue, left_restart, top_restart);
                        save_high_score("high_score_level3");
                        quit_game();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                local_x = (int) event.getX();
                local_y = (int) event.getY();
                in_pause_area = (local_x >= (float) (pause_region_min_x - pause_button.getWidth() / 2)) &&
                        (local_x <= (float) screen_width) &&
                        (local_y >= (float) 0) &&
                        (local_y <= (float) (pause_region_max_y + pause_button.getHeight() / 2));
                if(!game_paused && !in_pause_area) {
                    action = MotionEvent.ACTION_MOVE;
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
