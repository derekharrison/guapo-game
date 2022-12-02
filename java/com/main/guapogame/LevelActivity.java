package com.main.guapogame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelActivity extends AppCompatActivity {

    private boolean is_mute;
    public int high_score_for_level_unlock = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_level);

        TextView high_score_level1 = findViewById(R.id.high_score_id_level1);
        final SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        high_score_level1.setText("" + prefs.getInt("high_score", 0));

        TextView high_score_level2 = findViewById(R.id.high_score_id_level2);
        high_score_level2.setText("" + prefs.getInt("high_score_level2", 0));

        TextView high_score_level3 = findViewById(R.id.high_score_id_level3);
        high_score_level3.setText("" + prefs.getInt("high_score_level3", 0));

        TextView high_score_level4 = findViewById(R.id.high_score_id_level_4);
        high_score_level4.setText("" + prefs.getInt("high_score_level4", 0));

        TextView high_score_level5 = findViewById(R.id.high_score_id_level_5);
        high_score_level5.setText("" + prefs.getInt("high_score_level5", 0));

        findViewById(R.id.level1_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView play_button_coloring = (TextView) findViewById(R.id.level1_id);
                play_button_coloring.setTextColor(Color.WHITE);
                startActivity(new Intent(LevelActivity.this, GameActivityLevel1.class));
            }
        });

        if(prefs.getInt("high_score", 0) < high_score_for_level_unlock) {
            TextView play_button = findViewById(R.id.second_level2_id);
            play_button.setTextColor(Color.parseColor("#00a8f3"));
        }
        else if(prefs.getInt("high_score", 0) >= high_score_for_level_unlock){

            findViewById(R.id.second_level2_id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView play_button_coloring = (TextView) findViewById(R.id.second_level2_id);
                    play_button_coloring.setTextColor(Color.WHITE);
                    startActivity(new Intent(LevelActivity.this, GameActivityLevel2.class));
                }
            });

        }

        if(prefs.getInt("high_score_level2", 0) < high_score_for_level_unlock) {
            TextView play_button = findViewById(R.id.trip_id);
            play_button.setTextColor(Color.parseColor("#00a8f3"));
        }
        else if(prefs.getInt("high_score_level2", 0) >= high_score_for_level_unlock){

            findViewById(R.id.trip_id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView play_button_coloring = (TextView) findViewById(R.id.trip_id);
                    play_button_coloring.setTextColor(Color.WHITE);
                    startActivity(new Intent(LevelActivity.this, GameActivityLevel3.class));
                }
            });

        }

        if(prefs.getInt("high_score_level3", 0) < high_score_for_level_unlock) {
            TextView play_button = findViewById(R.id.ocean_id);
            play_button.setTextColor(Color.parseColor("#00a8f3"));
        }
        else if(prefs.getInt("high_score_level3", 0) >= high_score_for_level_unlock){

            findViewById(R.id.ocean_id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView play_button_coloring = (TextView) findViewById(R.id.ocean_id);
                    play_button_coloring.setTextColor(Color.WHITE);
                    startActivity(new Intent(LevelActivity.this, GameActivityLevel4.class));
                }
            });

        }

        if(prefs.getInt("high_score_level3", 0) < high_score_for_level_unlock) {
            TextView play_button = findViewById(R.id.utreg_id);
            play_button.setTextColor(Color.parseColor("#00a8f3"));
        }
        else if(prefs.getInt("high_score_level3", 0) >= high_score_for_level_unlock){

            findViewById(R.id.utreg_id).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView play_button_coloring = (TextView) findViewById(R.id.utreg_id);
                    play_button_coloring.setTextColor(Color.WHITE);
                    startActivity(new Intent(LevelActivity.this, GameActivityLevel5.class));
                }
            });

        }

        is_mute = prefs.getBoolean("is_mute", false);
        final ImageView volumeCtrl = findViewById(R.id.volumeCtrl);
        if (is_mute)
            volumeCtrl.setImageResource(R.drawable.volume_off);
        else
            volumeCtrl.setImageResource(R.drawable.volume_on);

        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                is_mute = !is_mute;
                if (is_mute)
                    volumeCtrl.setImageResource(R.drawable.volume_off);
                else
                    volumeCtrl.setImageResource(R.drawable.volume_on);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("is_mute", is_mute);
                editor.apply();

            }
        });

    }
}