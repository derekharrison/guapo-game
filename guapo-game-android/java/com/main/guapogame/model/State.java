package com.main.guapogame.model;

public class State {
    private static com.main.guapogame.types.State state = com.main.guapogame.types.State.PLAY;

    public static void setGameStateToPlay() {
        state = com.main.guapogame.types.State.PLAY;
    }

    public static void setGameStateToPaused() {
        state = com.main.guapogame.types.State.PAUSED;
    }

    public static void setGameStateToGameOver() {
        state = com.main.guapogame.types.State.GAME_OVER;
    }

    public static com.main.guapogame.types.State getGameState() {
        return state;
    }
}
