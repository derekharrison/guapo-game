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
import com.main.guapogame.model.GameScore;
import com.main.guapogame.model.GameState;
import com.main.guapogame.types.Level;

import java.util.HashMap;
import java.util.Map;

public class LevelActivity extends AppCompatActivity {
    private boolean isMute;
    Map<Integer, Level> levels = new HashMap<>();
    Map<Level, String> levelKeys = new HashMap<>();
    Map<String, String> previousLevelIds = new HashMap<>();
    Map<Level, Level> previousLevels = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);
        setUpScreenApiVersionGreaterOrEqualTo30();
        setUpScreenApiVersionLessThan30();
        
        createLevelKeys();
        createLevelRelations();
        createPreviousLevelKeys();
        createPreviousLevelRelations();

        final SharedPreferences prefs = getSharedPreferences(GAME, MODE_PRIVATE);

        setHighScore(prefs, R.id.high_score_id_level1);
        setHighScore(prefs, R.id.high_score_id_level2);
        setHighScore(prefs, R.id.high_score_id_level3);
        setHighScore(prefs, R.id.high_score_id_level_4);
        setHighScore(prefs, R.id.high_score_id_level_5);

        setFirstLevelButton(prefs);
        setLevelButton(prefs, R.id.second_level2_id, Level.BEACH);
        setLevelButton(prefs, R.id.trip_id, Level.TRIP);
        setLevelButton(prefs, R.id.ocean_id, Level.OCEAN);
        setLevelButton(prefs, R.id.utreg_id, Level.UTREG);

        setButton(R.id.choose_level_id3, ChooseCharacterActivity.class);
        setButton(R.id.main_menu_button3, MainActivity.class);

        setMuteButton(prefs);
        handleMuteAction(prefs);
    }

    private void createLevelKeys() {
        levelKeys.put(Level.ARUBA, ARUBA);
        levelKeys.put(Level.BEACH, BEACH);
        levelKeys.put(Level.TRIP, TRIP);
        levelKeys.put(Level.OCEAN, OCEAN);
        levelKeys.put(Level.UTREG, UTREG);
    }

    private void createLevelRelations() {
        levels.put(R.id.high_score_id_level1, Level.ARUBA);
        levels.put(R.id.high_score_id_level2, Level.BEACH);
        levels.put(R.id.high_score_id_level3, Level.TRIP);
        levels.put(R.id.high_score_id_level_4, Level.OCEAN);
        levels.put(R.id.high_score_id_level_5, Level.UTREG);

        levels.put(R.id.level1_id, Level.ARUBA);
        levels.put(R.id.second_level2_id, Level.BEACH);
        levels.put(R.id.trip_id, Level.TRIP);
        levels.put(R.id.ocean_id, Level.OCEAN);
        levels.put(R.id.utreg_id, Level.UTREG);
    }

    private void createPreviousLevelKeys() {
        previousLevelIds.put(ARUBA, "");
        previousLevelIds.put(BEACH, ARUBA);
        previousLevelIds.put(TRIP, BEACH);
        previousLevelIds.put(OCEAN, TRIP);
        previousLevelIds.put(UTREG, OCEAN);
    }

    private void createPreviousLevelRelations() {
        previousLevels.put(Level.BEACH, Level.ARUBA);
        previousLevels.put(Level.TRIP, Level.BEACH);
        previousLevels.put(Level.OCEAN, Level.TRIP);
        previousLevels.put(Level.UTREG, Level.OCEAN);
    }

    private String getLevelKey(Level level) {
        return levelKeys.get(level);
    }

    private Level getPreviousLevel(Level level) {
        return previousLevels.get(level);
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
        String highScore = "" + getHighScore(prefs, getLevelId(assetId));
        textView.setText(highScore);
    }

    private String getLevelId(int assetId) {
        Level level = levels.get(assetId);
        return levelKeys.get(level);
    }

    private int getHighScore(SharedPreferences prefs, String levelId) {
        return prefs.getInt(getKey(levelId, HIGH_SCORE), 0);
    }

    private String getPreviousLevelKey(Level level) {
        Level previousLevel = getPreviousLevel(level);
        return getLevelKey(previousLevel);
    }

    private void setLevelButton(SharedPreferences prefs, int buttonId, Level level) {
        String levelKey = getLevelId(buttonId);
        String previousLevelKey = getPreviousLevelKey(level);
        int score = prefs.getInt(getKey(previousLevelKey, HIGH_SCORE), 0);
        if(score < Parameters.LEVEL_UNLOCK_SCORE) {
            TextView textView = findViewById(buttonId);
            textView.setTextColor(Color.parseColor("#00a8f3"));
        }
        else {
            findViewById(buttonId).setOnClickListener(_ -> {
                TextView textView = findViewById(buttonId);
                textView.setTextColor(Color.WHITE);
                setLevelId(prefs, levelKey);
                setLevel(level);
                setSessionIsActive();
                resetGameScore();
                startActivity(new Intent(LevelActivity.this, GameActivity.class));
            });
        }
    }

    private void setFirstLevelButton(SharedPreferences prefs) {
        String levelKey = getLevelId(R.id.level1_id);
        findViewById(R.id.level1_id).setOnClickListener(_ -> {
            TextView textView = findViewById(R.id.level1_id);
            textView.setTextColor(Color.WHITE);
            setLevelId(prefs, levelKey);
            setLevel(Level.ARUBA);
            setSessionIsActive();
            resetGameScore();
            startActivity(new Intent(LevelActivity.this, GameActivity.class));
        });
    }

    private void resetGameScore() {
        GameScore.score = 0;
    }

    private void setMute(boolean isMute) {
        GameState.setMute(isMute);
    }

    private void setLevelId(SharedPreferences prefs, String levelId) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LEVEL, levelId);
        editor.apply();
    }

    private void setLevel(Level level) {
        GameState.setLevel(level);
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
        setMute(isMute);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("is_mute", isMute);
        editor.apply();
    }

    private void setSessionIsActive() {
        SharedPreferences prefs = getSharedPreferences(GAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getKey(getLevelId(), GAMESTATE), false);
        editor.apply();
    }

    private String getLevelId() {
        return getSharedPreferences(GAME, MODE_PRIVATE).getString(LEVEL, "");
    }
}