package com.main.guapogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {

    private boolean is_mute;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController;
            insetsController = getWindow().getInsetsController();

            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        TextView high_score_level1 = findViewById(R.id.high_score_id_level1);
        String high_score_l1 = "" + prefs.getInt("high_score", 0);
        high_score_level1.setText(high_score_l1);

        TextView high_score_level2 = findViewById(R.id.high_score_id_level2);
        String high_score_l2 = "" + prefs.getInt("high_score_level2", 0);
        high_score_level2.setText(high_score_l2);

        TextView high_score_level3 = findViewById(R.id.high_score_id_level3);
        String high_score_l3 = "" + prefs.getInt("high_score_level3", 0);
        high_score_level3.setText(high_score_l3);

        TextView high_score_level4 = findViewById(R.id.high_score_id_level_4);
        String high_score_l4 = "" + prefs.getInt("high_score_level4", 0);
        high_score_level4.setText(high_score_l4);

        TextView high_score_level5 = findViewById(R.id.high_score_id_level_5);
        String high_score_l5 = "" + prefs.getInt("high_score_level5", 0);
        high_score_level5.setText(high_score_l5);

        Parameters parameters = new Parameters();

        findViewById(R.id.level1_id).setOnClickListener(view -> {
            TextView play_button_coloring = findViewById(R.id.level1_id);
            play_button_coloring.setTextColor(Color.WHITE);
            startActivity(new Intent(LevelActivity.this, GameActivityLevel1.class));
        });

        if(prefs.getInt("high_score", 0) < parameters.LEVEL_UNLOCK_SCORE) {
            TextView play_button = findViewById(R.id.second_level2_id);
            play_button.setTextColor(Color.parseColor("#00a8f3"));
        }
        else if(prefs.getInt("high_score", 0) >= parameters.LEVEL_UNLOCK_SCORE){

            findViewById(R.id.second_level2_id).setOnClickListener(view -> {
                TextView play_button_coloring = findViewById(R.id.second_level2_id);
                play_button_coloring.setTextColor(Color.WHITE);
                startActivity(new Intent(LevelActivity.this, GameActivityLevel2.class));
            });

        }

        if(prefs.getInt("high_score_level2", 0) < parameters.LEVEL_UNLOCK_SCORE) {
            TextView play_button = findViewById(R.id.trip_id);
            play_button.setTextColor(Color.parseColor("#00a8f3"));
        }
        else if(prefs.getInt("high_score_level2", 0) >= parameters.LEVEL_UNLOCK_SCORE){

            findViewById(R.id.trip_id).setOnClickListener(view -> {
                TextView play_button_coloring = findViewById(R.id.trip_id);
                play_button_coloring.setTextColor(Color.WHITE);
                startActivity(new Intent(LevelActivity.this, GameActivityLevel3.class));
            });

        }

        if(prefs.getInt("high_score_level3", 0) < parameters.LEVEL_UNLOCK_SCORE) {
            TextView play_button = findViewById(R.id.ocean_id);
            play_button.setTextColor(Color.parseColor("#00a8f3"));
        }
        else if(prefs.getInt("high_score_level3", 0) >= parameters.LEVEL_UNLOCK_SCORE){

            findViewById(R.id.ocean_id).setOnClickListener(view -> {
                TextView play_button_coloring = findViewById(R.id.ocean_id);
                play_button_coloring.setTextColor(Color.WHITE);
                startActivity(new Intent(LevelActivity.this, GameActivityLevel4.class));
            });

        }

        if(prefs.getInt("high_score_level3", 0) < parameters.LEVEL_UNLOCK_SCORE) {
            TextView play_button = findViewById(R.id.utreg_id);
            play_button.setTextColor(Color.parseColor("#00a8f3"));
        }
        else if(prefs.getInt("high_score_level3", 0) >= parameters.LEVEL_UNLOCK_SCORE){

            findViewById(R.id.utreg_id).setOnClickListener(view -> {
                TextView play_button_coloring = findViewById(R.id.utreg_id);
                play_button_coloring.setTextColor(Color.WHITE);
                startActivity(new Intent(LevelActivity.this, GameActivityLevel5.class));
            });

        }

        is_mute = prefs.getBoolean("is_mute", false);
        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);
        if (is_mute)
            volumeCtrl.setImageResource(R.drawable.volume_off);
        else
            volumeCtrl.setImageResource(R.drawable.volume_on);

        volumeCtrl.setOnClickListener(view -> {

            is_mute = !is_mute;
            if (is_mute)
                volumeCtrl.setImageResource(R.drawable.volume_off);
            else
                volumeCtrl.setImageResource(R.drawable.volume_on);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("is_mute", is_mute);
            editor.apply();

        });

        findViewById(R.id.choose_level_id3).setOnClickListener(view -> {
            TextView play_button = findViewById(R.id.choose_level_id3);
            play_button.setTextColor(Color.WHITE);
            startActivity(new Intent(LevelActivity.this, ChooseCharacterActivity.class));
        });

        findViewById(R.id.main_menu_button3).setOnClickListener(view -> {
            TextView play_button = findViewById(R.id.main_menu_button3);
            play_button.setTextColor(Color.WHITE);
            startActivity(new Intent(LevelActivity.this, MainActivity.class));
        });
    }


}