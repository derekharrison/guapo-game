package com.main.guapogame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

public class GameActivityLevel1 extends AppCompatActivity {

    private GameViewLevel1 gameViewLevel1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gameViewLevel1 = new GameViewLevel1(this);
        setContentView(gameViewLevel1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameViewLevel1.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameViewLevel1.resume();
    }
}
