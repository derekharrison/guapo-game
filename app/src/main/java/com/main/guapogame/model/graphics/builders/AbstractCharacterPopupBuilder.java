package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.model.graphics.gameobjects.CharacterPopup;
import com.main.guapogame.resources.storage.Storage;

import java.security.SecureRandom;

public abstract class AbstractCharacterPopupBuilder {

    protected Context context;
    protected Storage storage;
    protected SecureRandom random = new SecureRandom();

    public AbstractCharacterPopupBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public AbstractCharacterPopupBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public CharacterPopup build() {
        return null;
    }

    protected boolean isActiveSession() {
        return storage.loadGame().getSessionIsActive();
    }

    protected int getScreenFactorX() {
        return (int) (getScreenWidth() / 10.0);
    }

    protected int getScreenFactorY() {
        return (int) (getScreenHeight() / 5.0);
    }

    protected int getWidth() {
        return (int) (getScreenFactorX() * 3.0 / 2.0);
    }

    protected int getHeight() {
        return (int) (getScreenFactorY() * 3.0 / 2.0);
    }

    protected Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }
}
