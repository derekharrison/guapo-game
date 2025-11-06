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

    List<Villain> villains = new ArrayList<>();
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
        createVillains();
        return villains;
    }

    private void createVillains() {
        int numVillains = getNumVillains();
        for(int villainId = 0; villainId < numVillains; villainId++) {
            if(isActiveSession())
                createVillain(String.valueOf(villainId));
            else
                addVillain(createVillain());
        }
    }

    private boolean isActiveSession() {
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private void addVillain(Villain villain) {
        this.villains.add(villain);
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

    private void createVillain(String villainId) {
        villains.add(
                new VillainBuilder()
                        .storage(storage)
                        .context(context)
                        .resources(resources)
                        .build(villainId)
        );
    }

    private String getLevelId() {
        return  context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }
}
