package com.main.guapogame.activity;

import static com.main.guapogame.definitions.Keys.ARUBA;
import static com.main.guapogame.definitions.Keys.BEACH;
import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.OCEAN;
import static com.main.guapogame.definitions.Keys.HIGH_SCORE;
import static com.main.guapogame.definitions.Keys.TRIP;
import static com.main.guapogame.definitions.Keys.UTREG;
import static com.main.guapogame.definitions.Keys.getKey;

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

import com.main.guapogame.definitions.Parameters;
import com.main.guapogame.R;

import java.util.HashMap;
import java.util.Map;

public class LevelActivity extends AppCompatActivity {
    private boolean isMute;
    Map<Integer, String> levelIds = new HashMap<>();
    Map<String, String> previousLevel = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        setUpScreenApiVersionGreaterOrEqualTo30();
        setUpScreenApiVersionLessThan30();
        createLevelIdRelations();
        createPreviousLevelRelations();

        final SharedPreferences prefs = getSharedPreferences(GAME, MODE_PRIVATE);

        setHighScore(prefs, R.id.high_score_id_level1);
        setHighScore(prefs, R.id.high_score_id_level2);
        setHighScore(prefs, R.id.high_score_id_level3);
        setHighScore(prefs, R.id.high_score_id_level_4);
        setHighScore(prefs, R.id.high_score_id_level_5);

        setFirstLevelButton(prefs);
        setLevelButton(prefs, R.id.second_level2_id);
        setLevelButton(prefs, R.id.trip_id);
        setLevelButton(prefs, R.id.ocean_id);
        setLevelButton(prefs, R.id.utreg_id);

        setButton(R.id.choose_level_id3, ChooseCharacterActivity.class);
        setButton(R.id.main_menu_button3, MainActivity.class);

        setMuteButton(prefs);
        handleMuteAction(prefs);
    }

    private void createLevelIdRelations() {
        levelIds.put( R.id.high_score_id_level1, ARUBA);
        levelIds.put( R.id.high_score_id_level2, BEACH);
        levelIds.put( R.id.high_score_id_level3, TRIP);
        levelIds.put( R.id.high_score_id_level_4, OCEAN);
        levelIds.put( R.id.high_score_id_level_5, UTREG);

        levelIds.put(R.id.level1_id, ARUBA);
        levelIds.put(R.id.second_level2_id, BEACH);
        levelIds.put(R.id.trip_id, TRIP);
        levelIds.put(R.id.ocean_id, OCEAN);
        levelIds.put(R.id.utreg_id, UTREG);
    }

    private void createPreviousLevelRelations() {
        previousLevel.put(ARUBA, "");
        previousLevel.put(BEACH, ARUBA);
        previousLevel.put(TRIP, BEACH);
        previousLevel.put(OCEAN, TRIP);
        previousLevel.put(UTREG, OCEAN);
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

    private void setHighScore(SharedPreferences prefs, int assetId) {
        TextView textView = findViewById(assetId);
        String levelId = levelIds.get(assetId);
        String highScore = "" + getHighScore(prefs, levelId);
        textView.setText(highScore);
    }

    private int getHighScore(SharedPreferences prefs, String levelId) {
        return prefs.getInt(getKey(levelId, HIGH_SCORE), 0);
    }

    private void setLevelButton(SharedPreferences prefs, int buttonId) {
        String levelId = levelIds.get(buttonId);
        String previousLevelId = previousLevel.get(levelId);
        int score = prefs.getInt(getKey(previousLevelId, HIGH_SCORE), 0);
        if(score < Parameters.LEVEL_UNLOCK_SCORE) {
            TextView textView = findViewById(buttonId);
            textView.setTextColor(Color.parseColor("#00a8f3"));
        }
        else {
            findViewById(buttonId).setOnClickListener(_ -> {
                TextView textView = findViewById(buttonId);
                textView.setTextColor(Color.WHITE);
                setLevelId(prefs, levelId);
                setSessionIsActive(false);
                startActivity(new Intent(LevelActivity.this, GameActivity.class));
            });
        }
    }

    private void setFirstLevelButton(SharedPreferences prefs) {
        String levelId = levelIds.get(R.id.level1_id);
        findViewById(R.id.level1_id).setOnClickListener(_ -> {
            TextView textView = findViewById(R.id.level1_id);
            textView.setTextColor(Color.WHITE);
            setLevelId(prefs, levelId);
            setSessionIsActive(false);
            startActivity(new Intent(LevelActivity.this, GameActivity.class));
        });
    }

    private void setLevelId(SharedPreferences prefs, String levelId) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LEVEL, levelId);
        editor.apply();
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
        showMuteButton();
    }

    private void showMuteButton() {
        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);

        if (isMute)
            volumeCtrl.setImageResource(R.drawable.volume_off);
        if(!isMute)
            volumeCtrl.setImageResource(R.drawable.volume_on);
    }

    private void handleMuteAction(SharedPreferences prefs) {
        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);
        volumeCtrl.setOnClickListener(_ -> {
            mute(prefs);
            showMuteButton();
        });
    }

    private void mute(SharedPreferences prefs) {
        isMute = !isMute;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_mute", isMute);
        editor.apply();
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