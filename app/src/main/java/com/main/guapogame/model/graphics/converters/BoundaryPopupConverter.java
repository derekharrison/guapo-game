package com.main.guapogame.model.graphics.converters;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import com.main.guapogame.model.graphics.gameobjects.BoundaryPopup;
import com.main.guapogame.model.state.Random;

public class BoundaryPopupConverter {

    public static void convert(BoundaryPopup popup) {
        update(popup);
    }

    protected static void update(BoundaryPopup popup) {
        popup.reset();
        popup.setPositionX(getPositionX());
        popup.setPositionY(getPositionY(popup));
    }

    private static float getPositionX() {
        int pos = Random.getRandomNumber(getScreenWidth() / 2);
        return (float) getScreenWidth() / 4 + pos;
    }

    private static int getPositionY(BoundaryPopup popup) {
        if(popup.isTop())
            return calcStartPositionTopY(popup);

        return calcStartPositionBottomY();
    }

    private static int calcStartPositionTopY(BoundaryPopup popup) {
        return -popup.getImage().getHeight();
    }

    private static int calcStartPositionBottomY() {
        return getScreenHeight();
    }

    private BoundaryPopupConverter() {}
}
