package com.main.guapogame;

import android.content.Context;

public class GameState {
    private static State state = State.PLAY;

    public static void setGameStateToPlay() {
        state = State.PLAY;
    }

    public static void setGameStateToPaused() {
        state = State.PAUSED;
    }

    public static void setGameStateToGameOver() {
        state = State.GAME_OVER;
    }

    public static void setGameStateToContinue() {
        state = State.CONTINUE;
    }

    public static State getGameState() {
        return state;
    }

    private LoadGameState loadGameState;
    private SaveGameState saveGameState;

    public GameState(Context context) {
        loadGameState = new LoadGameState(context);
        saveGameState = new SaveGameState(context);
    }

    public LoadGameState getLoadGameState() {
        return this.loadGameState;
    }

    public SaveGameState saveGameState() {
        return saveGameState;
    }


}
