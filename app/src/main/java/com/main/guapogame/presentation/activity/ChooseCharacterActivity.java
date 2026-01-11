package com.main.guapogame.presentation.activity;

import static com.main.guapogame.parameters.Keys.CHOOSE_CHARACTER;

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

        setUpScreen();
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);

        setCharacterChoiceIsTuttiButton(prefs);
        setCharacterChoiceIsGuapoButton(prefs);
        setCharacterChoiceIsMiccaButton(prefs);

        setButton(R.id.choose_level_id, LevelActivity.class);
        setButton(R.id.main_menu_button2, MainActivity.class);
    }

    @SuppressWarnings("deprecation")
    private void setUpScreen() {
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

    private void setCharacterChoiceIsTuttiButton(SharedPreferences prefs) {
        TextView guapoButton = findViewById(R.id.guapo_char_id);
        TextView tuttiButton = findViewById(R.id.tutti_char_id);
        TextView micaButton = findViewById(R.id.mica_char_id);
        findViewById(R.id.tutti_char_id).setOnClickListener(_ -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(CHOOSE_CHARACTER, 1);
            editor.apply();
            tuttiButton.setTextColor(Color.WHITE);
            guapoButton.setTextColor(Color.BLACK);
            micaButton.setTextColor(Color.BLACK);
        });
    }

    private void setCharacterChoiceIsGuapoButton(SharedPreferences prefs) {
        TextView guapoButton = findViewById(R.id.guapo_char_id);
        TextView tuttiButton = findViewById(R.id.tutti_char_id);
        TextView micaButton = findViewById(R.id.mica_char_id);
        findViewById(R.id.guapo_char_id).setOnClickListener(_ -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(CHOOSE_CHARACTER, 0);
            editor.apply();
            guapoButton.setTextColor(Color.WHITE);
            tuttiButton.setTextColor(Color.BLACK);
            micaButton.setTextColor(Color.BLACK);
        });
    }

    private void setCharacterChoiceIsMiccaButton(SharedPreferences prefs) {
        TextView guapoButton = findViewById(R.id.guapo_char_id);
        TextView tuttiButton = findViewById(R.id.tutti_char_id);
        TextView micaButton = findViewById(R.id.mica_char_id);
        findViewById(R.id.mica_char_id).setOnClickListener(_ -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(CHOOSE_CHARACTER, 2);
            editor.apply();
            micaButton.setTextColor(Color.WHITE);
            tuttiButton.setTextColor(Color.BLACK);
            guapoButton.setTextColor(Color.BLACK);
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
