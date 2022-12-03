package com.main.guapogame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class GameViewLevel2 extends MainView implements Runnable {
    private Thread thread;

    private Backgrounds backgrounds_level2;

    private GameActivityLevel2 game_activity;

    public GameViewLevel2(Context context) {
        super(context);
    }

    public GameViewLevel2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameViewLevel2(GameActivityLevel2 activity) {
        super(activity);

        game_activity = activity;

        backgrounds_level2 = new Backgrounds(activity, screen_width, screen_height, parameters.BACKGROUND_IMAGES_LEVEL2);
    }

    @Override
    public void run() {

        long start_time;
        long time_millis;
        long wait_time;
        long total_time = 0;
        int frame_count = 0;
        int target_FPS = 30;
        long target_time = 1000 / target_FPS;

        while (is_playing) {

            start_time = System.nanoTime();

            update();
            draw();

            time_millis = (System.nanoTime() - start_time) / 1000000;
            wait_time = target_time - time_millis;

            try{
                Thread.sleep(wait_time);
            }catch(Exception e){
                System.out.println("did not sleep");
            }

            total_time += System.nanoTime()-start_time;
            frame_count++;
            if(frame_count == target_FPS)
            {
                double averageFPS = 1000 / (double) ((total_time / frame_count) / 1000000);
                frame_count =0;
                total_time = 0;
                System.out.println(averageFPS);
            }
        }
    }

    public void draw_backgrounds(Canvas canvas) {
        for(int i = 0; i < backgrounds_level2.num_backgrounds; ++i) {
            if(backgrounds_level2.backgrounds[i].x < screen_width) {
                canvas.drawBitmap(backgrounds_level2.backgrounds[i].background,
                        backgrounds_level2.backgrounds[i].x,
                        backgrounds_level2.backgrounds[i].y, null);
            }
        }
    }

    private void draw() {

        if (getHolder().getSurface().isValid()) {

            Canvas canvas = getHolder().lockCanvas();

            // Draw backgrounds
            draw_backgrounds(canvas);

            // Draw snacks, characters, score and pause button
            draw_all(canvas);

            // Draw seagulls
            draw_seagulls(canvas);

            // Draw sun popup
            draw_sun_popup(canvas, "high_score_level2");

            // Draw Guapo
            draw_guapo(canvas);

            // Case Guapo hit a bird. Draw hit Guapo and stop game.
            if(hit_bird) {
                // Set playing bit to false
                is_playing = false;

                // Draw hit Guapo
                canvas.drawBitmap(guapo_head_hit, guapo_loc_x, guapo_loc_y, null);


                getHolder().unlockCanvasAndPost(canvas);

                // Save high score and stop game.
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

    public void update_background() {
        for(int i = 0; i < backgrounds_level2.num_backgrounds; ++i) {
            backgrounds_level2.backgrounds[i].x -= background_speed;

            if(backgrounds_level2.backgrounds[i].x + backgrounds_level2.backgrounds[i].background.getWidth() < 0) {
                if(i == 0) {
                    backgrounds_level2.backgrounds[0].x = backgrounds_level2.backgrounds[backgrounds_level2.num_backgrounds - 1].x +
                            backgrounds_level2.backgrounds[backgrounds_level2.num_backgrounds - 1].background.getWidth() - 10;
                }
                if(i > 0) {
                    backgrounds_level2.backgrounds[i].x = backgrounds_level2.backgrounds[i - 1].x + backgrounds_level2.backgrounds[i - 1].background.getWidth() - 10;
                }
            }
        }
    }

    public void update() {

        update_background();

        update_seagulls();

        update_all();
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

        if (prefs.getInt("high_score_level2", 0) < score) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("high_score_level2", score);
            editor.apply();
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
                        loc_x = guapo_loc_x + (float) guapo_head.getWidth() / 2;
                        loc_y = guapo_loc_y + (float) guapo_head.getHeight() / 2;
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
