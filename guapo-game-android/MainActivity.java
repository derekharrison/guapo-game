package com.main.guapogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        findViewById(R.id.play).setOnClickListener(view -> {
            TextView play_button = findViewById(R.id.play);
            play_button.setTextColor(Color.WHITE);
            startActivity(new Intent(MainActivity.this, ChooseCharacterActivity.class));
        });

        findViewById(R.id.choose_level_id_main).setOnClickListener(view -> {
            TextView play_button = findViewById(R.id.choose_level_id_main);
            play_button.setTextColor(Color.WHITE);
            startActivity(new Intent(MainActivity.this, LevelActivity.class));
        });
    }
}
