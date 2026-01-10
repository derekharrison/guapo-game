package com.main.guapogame.model.graphics.converters;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import com.main.guapogame.model.graphics.gameobjects.CharacterPopup;
import com.main.guapogame.model.state.Random;

public class RoccoConverter {

    public static void convert(CharacterPopup popup) {
        update(popup);
    }

    protected static void update(CharacterPopup popup) {
        popup.setPositionX(getPositionX(popup));
        popup.setPositionY(getPositionY());
        popup.reset();
    }

    private static float getPositionX(CharacterPopup popup) {
        int screenWidth = getScreenWidth();
        return (float) -screenWidth - popup.getImage().getWidth()
                + Random.getRandomNumber(screenWidth);
    }

    private static float getPositionY() {
        return (float) getScreenHeight() / 2;
    }

    private RoccoConverter() {}
}
