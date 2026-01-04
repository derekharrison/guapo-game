package com.main.guapogame.model.graphics.builders;

import android.content.Context;
import android.graphics.Bitmap;

import com.main.guapogame.model.graphics.gameobjects.Background;
import com.main.guapogame.model.graphics.gameobjects.CharacterPopup;
import com.main.guapogame.model.graphics.gameobjects.Graphics;
import com.main.guapogame.model.graphics.gameobjects.Hero;
import com.main.guapogame.model.graphics.gameobjects.Misty;
import com.main.guapogame.model.graphics.gameobjects.Popup;
import com.main.guapogame.model.graphics.gameobjects.Snack;
import com.main.guapogame.model.graphics.gameobjects.Villain;
import com.main.guapogame.resources.storage.Storage;

import java.util.List;

public class GraphicsBuilder {
    private Context context;
    private Storage storage;

    public GraphicsBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public GraphicsBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public Graphics build() {
        return new Graphics.Builder()
                .hero(createHero())
                .misty(createMisty())
                .brownie(createBrownie())
                .frito(createFrito())
                .villains(createVillains())
                .backgrounds(createBackgrounds())
                .checkpointPopup(createCheckpointPopup())
                .sunPopup(createSunPopup())
                .snacks(createSnacks())
                .pauseButton(createPauseButton())
                .playButton(createPlayButton())
                .lives(createLives())
                .build();
    }

    private Hero createHero() {
        return new HeroBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private Misty createMisty() {
        return new MistyBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private CharacterPopup createBrownie() {
        return new BrownieBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private CharacterPopup createFrito() {
        return new FritoBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private List<Snack> createSnacks() {
        return new SnacksBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private List<Villain> createVillains() {
        return new VillainsBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private List<Background> createBackgrounds() {
        return new BackgroundsBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private List<Bitmap> createLives() {
        return new LivesBuilder()
                .context(context)
                .storage(storage)
                .build();
    }

    private Bitmap createPauseButton() {
        return new PauseButtonBuilder().resources(context.getResources()).build();
    }
    private Bitmap createPlayButton() {
        return new PlayButtonBuilder().resources(context.getResources()).build();
    }

    private Popup createCheckpointPopup() {
        return new CheckpointPopupBuilder()
                .resources(context.getResources())
                .duration(0)
                .build();
    }

    private Popup createSunPopup() {
        return new SunPopupBuilder()
                .resources(context.getResources())
                .duration(0)
                .build();
    }
}
