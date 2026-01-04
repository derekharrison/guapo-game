package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.getKey;
import static com.main.guapogame.parameters.Parameters.START_NUM_OF_VILLAINS;

import android.content.Context;

import com.main.guapogame.model.graphics.gameobjects.Villain;
import com.main.guapogame.resources.storage.Storage;

import java.util.ArrayList;
import java.util.List;

class VillainsBuilder {
    private Context context;
    private Storage storage;

    VillainsBuilder context(Context context) {
        this.context = context;
        return this;
    }

    VillainsBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    List<Villain> build() {
        return createVillains();
    }

    private List<Villain> createVillains() {
        int numVillains = getNumVillains();
        List<Villain> villains1 = new ArrayList<>();
        for(int villainId = 0; villainId < numVillains; villainId++) {
            if(isActiveSession())
                villains1.add(createVillain(String.valueOf(villainId)));
            else
                villains1.add(createVillain());
        }

        return villains1;
    }

    private boolean isActiveSession() {
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private int getNumVillains() {
        if(isActiveSession())
            return storage.loadGame().getNumVillains();

        return START_NUM_OF_VILLAINS;
    }


    private Villain createVillain() {
        return new VillainBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private Villain createVillain(String villainId) {
        return new VillainBuilder()
                .storage(storage)
                .context(context)
                .build(villainId);
    }

    private String getLevelId() {
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }
}
