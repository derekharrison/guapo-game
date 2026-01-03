package com.main.guapogame.state;

import com.main.guapogame.enums.Level;
import com.main.guapogame.enums.State;

public class GameState {
    private static State state = State.PLAY;
    private static Level level;
    private static boolean isMute;

    public static void setMute(boolean isMute1) {
        isMute = isMute1;
    }

    public static boolean getMute() {
        return isMute;
    }

    public static void setLevel(Level level1) {
        level = level1;
    }

    public static Level getLevel() {
        return level;
    }
    
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

    private GameState() {}
}
