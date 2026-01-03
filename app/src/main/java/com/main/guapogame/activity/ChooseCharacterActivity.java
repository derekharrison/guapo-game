package com.main.guapogame.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.main.guapogame.R;

public class ChooseCharacterActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose);

        setUpScreenApiVersionGreaterOrEqualTo30();
        setUpScreenApiVersionLessThan30();
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        setCharacterChoiceIsTuttiButton(prefs);
        setCharacterChoiceIsGuapoButton(prefs);

        setButton(R.id.choose_level_id, LevelActivity.class);
        setButton(R.id.main_menu_button2, MainActivity.class);
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

    private void setCharacterChoiceIsTuttiButton(SharedPreferences prefs) {
        TextView guapoButton = findViewById(R.id.guapo_char_id);
        TextView tuttiButton = findViewById(R.id.tutti_char_id);
        findViewById(R.id.tutti_char_id).setOnClickListener(_ -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("choose_character", 1);
            editor.apply();
            tuttiButton.setTextColor(Color.WHITE);
            guapoButton.setTextColor(Color.BLACK);
        });
    }

    private void setCharacterChoiceIsGuapoButton(SharedPreferences prefs) {
        TextView guapoButton = findViewById(R.id.guapo_char_id);
        TextView tuttiButton = findViewById(R.id.tutti_char_id);
        findViewById(R.id.guapo_char_id).setOnClickListener(_ -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("choose_character", 0);
            editor.apply();
            guapoButton.setTextColor(Color.WHITE);
            tuttiButton.setTextColor(Color.BLACK);
        });
    }

    private <T extends AppCompatActivity> void setButton(int buttonId, Class<T> clazz) {
        findViewById(buttonId).setOnClickListener(_ -> {
            TextView textView = findViewById(buttonId);
            textView.setTextColor(Color.WHITE);
            startActivity(new Intent(ChooseCharacterActivity.this, clazz));
        });
    }
}
