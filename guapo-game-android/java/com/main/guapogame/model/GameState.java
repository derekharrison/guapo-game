package com.main.guapogame.model;

import com.main.guapogame.types.State;

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
}
