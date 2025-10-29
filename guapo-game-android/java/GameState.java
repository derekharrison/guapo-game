package com.main.guapogame;

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
}
