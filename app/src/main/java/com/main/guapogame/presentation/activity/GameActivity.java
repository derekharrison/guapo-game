package com.main.guapogame.presentation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.main.guapogame.presentation.view.View;

public class GameActivity extends AppCompatActivity {

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new View(this);
        setContentView(view);
        setUpScreen();
        view.startGame();
        getOnBackPressedDispatcher().addCallback(new BackPressedCallback(true));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpScreen();
    }


    @SuppressWarnings("deprecation")
    private void setUpScreen() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = getWindow().getInsetsController();
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

    private class BackPressedCallback extends OnBackPressedCallback {
        View view;

        public BackPressedCallback(boolean enabled) {
            super(enabled);
        }

        @Override
        public void handleOnBackPressed() {
            startActivity(new Intent(GameActivity.this, MainActivity.class));
            view.stopGame();
            finish();
        }
    }
}
