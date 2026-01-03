package com.main.guapogame.activity;

import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.getKey;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.main.guapogame.R;

public class ContinueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);
        setupScreen();

        setButtonContinue();
        setButtonRestart();
    }

    @SuppressWarnings("deprecation")
    private void setupScreen() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController;
            insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
                insetsController.hide(WindowInsets.Type.navigationBars());
            }
        }
        else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
    }

    private void setButtonContinue() {
        findViewById(R.id.game_continue_id).setOnClickListener(_ -> {
            TextView textView = findViewById(R.id.game_continue_id);
            textView.setTextColor(Color.WHITE);
            setSessionIsActive(true);
            startActivity(new Intent(ContinueActivity.this, GameActivity.class));
        });
    }

    private void setButtonRestart() {
        findViewById(R.id.game_restart_id).setOnClickListener(_ -> {
            TextView textView = findViewById(R.id.game_restart_id);
            textView.setTextColor(Color.WHITE);
            setSessionIsActive(false);
            startActivity(new Intent(ContinueActivity.this, LevelActivity.class));
        });
    }

    private void setSessionIsActive(boolean isActive) {
        SharedPreferences prefs = getSharedPreferences(GAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getKey(getLevelId(), GAMESTATE), isActive);
        editor.apply();
    }

    private String getLevelId() {
        return getSharedPreferences(GAME, MODE_PRIVATE).getString(LEVEL, "");
    }
}
