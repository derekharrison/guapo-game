package com.main.guapogame.model.graphics.converters;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import com.main.guapogame.model.graphics.gameobjects.BoundaryPopup;
import com.main.guapogame.model.state.Random;

public class MistyConverter {

    public static void convert(BoundaryPopup popup) {
        update(popup);
    }

    protected static void update(BoundaryPopup popup) {
        popup.setPositionX(getPositionX());
        popup.setPositionY(getPositionY());
        popup.reset();
    }

    private static float getPositionX() {
        int pos = Random.getRandomNumber(getScreenWidth() / 2);
        return (float) getScreenWidth() / 4 + pos;
    }

    private static float getPositionY() {
        return -getScreenHeight();
    }

    private MistyConverter() {}
}
