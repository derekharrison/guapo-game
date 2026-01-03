package com.main.guapogame.storage;

import android.content.Context;

public class Storage {

    private final Load load;
    private final Save save;

    public Storage(Context context) {
        load = new Load(context);
        save = new Save(context);
    }

    public Load loadGame() {
        return load;
    }

    public Save saveGame() {
        return save;
    }
}
