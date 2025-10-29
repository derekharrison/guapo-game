package com.main.guapogame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

public class GameActivityLevel4 extends AppCompatActivity {

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameViewLevel4 gameView = new GameViewLevel4(this);
        setContentView(gameView);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController;
            insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
                insetsController.hide(WindowInsets.Type.navigationBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        gameView.pause();
        // TODO : implement pause handline
    }

    @Override
    protected void onResume() {
        super.onResume();
//        gameView.resume();
        // TODO : implement resume handling
    }
}