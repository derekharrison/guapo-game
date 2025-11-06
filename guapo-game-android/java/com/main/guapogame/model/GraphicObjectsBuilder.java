package com.main.guapogame.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;

import com.main.guapogame.graphics.Background;
import com.main.guapogame.graphics.GraphicObjects;
import com.main.guapogame.graphics.Hero;
import com.main.guapogame.graphics.Popup;
import com.main.guapogame.graphics.Snack;
import com.main.guapogame.graphics.Villain;
import com.main.guapogame.storage.Storage;

import java.util.List;

public class GraphicObjectsBuilder {
    private Context context;
    private Storage storage;
    private Resources resources;

    public GraphicObjectsBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public GraphicObjectsBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public GraphicObjectsBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public GraphicObjects build() {
        return new GraphicObjects.Builder()
                .hero(createHero())
                .villains(createVillains())
                .backgrounds(createBackgrounds())
                .checkpointPopup(createCheckpointPopup())
                .snacks(createSnacks())
                .pauseButton(createPauseButton())
                .playButton(createPlayButton())
                .lives(createLives())
                .build();
    }

    private Hero createHero() {
        return new HeroBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private List<Snack> createSnacks() {
        return new SnacksBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private List<Villain> createVillains() {
        return new VillainsBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private List<Background> createBackgrounds() {
        return new BackgroundsBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private List<Bitmap> createLives() {
        return new LivesBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private Bitmap createPauseButton() {
        return new PauseButtonBuilder().resources(resources).build();
    }
    private Bitmap createPlayButton() {
        return new PlayButtonBuilder().resources(resources).build();
    }

    private Popup createCheckpointPopup() {
        return new CheckpointPopupBuilder()
                .resources(resources)
                .build();
    }
}
