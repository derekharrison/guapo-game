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
    private boolean isMute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_level);
        setUpScreenApiVersionGreaterOrEqualTo30();
        setUpScreenApiVersionLessThan30();

        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        setHighScore(prefs, "high_score", R.id.high_score_id_level1);
        setHighScore(prefs, "high_score_level2", R.id.high_score_id_level2);
        setHighScore(prefs, "high_score_level3", R.id.high_score_id_level3);
        setHighScore(prefs, "high_score_level4", R.id.high_score_id_level_4);
        setHighScore(prefs, "high_score_level5", R.id.high_score_id_level_5);

        setButton(R.id.level1_id, GameActivityLevel1.class);
        setLevelButton(prefs, "high_score", R.id.second_level2_id, GameActivityLevel2.class);
        setLevelButton(prefs, "high_score_level2", R.id.trip_id, GameActivityLevel3.class);
        setLevelButton(prefs, "high_score_level3", R.id.trip_id, GameActivityLevel4.class);
        setLevelButton(prefs, "high_score_level3", R.id.utreg_id, GameActivityLevel5.class);

        setButton(R.id.choose_level_id3, ChooseCharacterActivity.class);
        setButton(R.id.main_menu_button3, MainActivity.class);

        setMuteButton(prefs);
    }

    private void setUpScreenApiVersionGreaterOrEqualTo30() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController;
            insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
                insetsController.hide(WindowInsets.Type.navigationBars());
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void setUpScreenApiVersionLessThan30() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.R) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
    }

    private void setHighScore(SharedPreferences prefs, String key, int id) {
        TextView textView = findViewById(id);
        String highScore = "" + prefs.getInt(key, 0);
        textView.setText(highScore);
    }

    private <T extends AppCompatActivity> void setLevelButton(SharedPreferences prefs, String scoreCap, int buttonId, Class<T> clazz) {
        int score = prefs.getInt(scoreCap, 0);
        if(score < Parameters.LEVEL_UNLOCK_SCORE) {
            TextView textView = findViewById(buttonId);
            textView.setTextColor(Color.parseColor("#00a8f3"));
        }
        else {
            findViewById(buttonId).setOnClickListener(_ -> {
                TextView textView = findViewById(buttonId);
                textView.setTextColor(Color.WHITE);
                startActivity(new Intent(LevelActivity.this, clazz));
            });
        }
    }

    private <T extends AppCompatActivity> void setButton(int buttonId, Class<T> clazz) {
        findViewById(buttonId).setOnClickListener(_ -> {
            TextView textView = findViewById(buttonId);
            textView.setTextColor(Color.WHITE);
            startActivity(new Intent(LevelActivity.this, clazz));
        });
    }

    private void setMuteButton(SharedPreferences prefs) {
        isMute = prefs.getBoolean("is_mute", false);
        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if (isMute) {
            volumeCtrl.setImageResource(R.drawable.volume_off);
        }
        else {
            volumeCtrl.setImageResource(R.drawable.volume_on);
        }

        handleMute(prefs, volumeCtrl);
    }

    private void handleMute(SharedPreferences prefs, ImageView volumeCtrl) {
        volumeCtrl.setOnClickListener(_ -> {
            isMute = !isMute;

            if (isMute) {
                volumeCtrl.setImageResource(R.drawable.volume_off);
            }
            else {
                volumeCtrl.setImageResource(R.drawable.volume_on);
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("is_mute", isMute);
            editor.apply();
        });
    }
}