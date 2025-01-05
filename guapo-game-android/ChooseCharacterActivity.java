package com.main.guapogame;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ChooseCharacterActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choose);

        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        findViewById(R.id.guapo_char_id).setOnClickListener(view -> {
            TextView play_button1 = findViewById(R.id.guapo_char_id);
            TextView play_button2 = findViewById(R.id.tutti_char_id);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("choose_character", 0);
            editor.apply();
            play_button1.setTextColor(Color.WHITE);
            play_button2.setTextColor(Color.BLACK);
       });

        findViewById(R.id.tutti_char_id).setOnClickListener(view -> {
            TextView play_button1 = findViewById(R.id.tutti_char_id);
            TextView play_button2 = findViewById(R.id.guapo_char_id);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("choose_character", 1);
            editor.apply();
            play_button1.setTextColor(Color.WHITE);
            play_button2.setTextColor(Color.BLACK);
        });

        findViewById(R.id.choose_level_id).setOnClickListener(view -> {
            TextView play_button = findViewById(R.id.choose_level_id);
            play_button.setTextColor(Color.WHITE);
            startActivity(new Intent(ChooseCharacterActivity.this, LevelActivity.class));
        });

        findViewById(R.id.main_menu_button2).setOnClickListener(view -> {
            TextView play_button = findViewById(R.id.main_menu_button2);
            play_button.setTextColor(Color.WHITE);
            startActivity(new Intent(ChooseCharacterActivity.this, MainActivity.class));
        });
    }
}
