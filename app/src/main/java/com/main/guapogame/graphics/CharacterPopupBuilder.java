package com.main.guapogame.graphics;

import android.content.Context;
import com.main.guapogame.storage.Storage;

import java.security.SecureRandom;

public class CharacterPopupBuilder {

    protected Context context;
    protected Storage storage;
    protected SecureRandom random = new SecureRandom();

    public CharacterPopupBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public CharacterPopupBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public CharacterPopup build() {
        return null;
    }
}
