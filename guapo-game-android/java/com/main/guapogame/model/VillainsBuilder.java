package com.main.guapogame.model;

import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.getKey;
import static com.main.guapogame.definitions.Parameters.START_NUM_OF_VILLAINS;

import android.content.Context;
import android.content.res.Resources;

import com.main.guapogame.graphics.Villain;
import com.main.guapogame.storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class VillainsBuilder {
    private Context context;
    private Storage storage;
    private Resources resources;

    public VillainsBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public VillainsBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public VillainsBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public List<Villain> build() {
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
                .resources(resources)
                .storage(storage)
                .build();
    }

    private Villain createVillain(String villainId) {
        return new VillainBuilder()
                .storage(storage)
                .context(context)
                .resources(resources)
                .build(villainId);
    }

    private String getLevelId() {
        return  context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }
}
