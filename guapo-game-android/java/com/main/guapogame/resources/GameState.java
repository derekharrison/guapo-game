package com.main.guapogame.resources;

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

    public static State getGameState() {
        return state;
    }

    private final LoadGame loadGame;
    private final SaveGame saveGame;

    public GameState(Context context) {
        loadGame = new LoadGame(context);
        saveGame = new SaveGame(context);
    }

    public LoadGame loadGame() {
        return this.loadGame;
    }

    public SaveGame saveGame() {
        return saveGame;
    }
}
