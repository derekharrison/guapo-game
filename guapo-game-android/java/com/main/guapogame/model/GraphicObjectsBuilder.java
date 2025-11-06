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

import java.util.ArrayList;
import java.util.List;

public class GraphicObjectsBuilder {
    private Hero hero;
    private List<Snack> snacks = new ArrayList<>();
    private List<Villain> villains = new ArrayList<>();
    private List<Background> backgrounds = new ArrayList<>();
    private List<Bitmap> lives = new ArrayList<>();
    private Bitmap playButton;
    private Bitmap pauseButton;
    private Popup checkpointPopup;
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
        createGameObjects();
        return new GraphicObjects.Builder()
                .hero(hero)
                .villains(villains)
                .backgrounds(backgrounds)
                .checkpointPopup(checkpointPopup)
                .snacks(snacks)
                .pauseButton(pauseButton)
                .playButton(playButton)
                .lives(lives)
                .build();
    }

    private void createGameObjects() {
        createHero();
        createSnacks();
        createVillains();
        createBackgrounds();
        createPopups();
        createPauseAndPlayButtons();
        createLives();
    }

    private void createHero() {
        hero = new HeroBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private void createSnacks() {
        snacks = new SnacksBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private void createVillains() {
        villains = new VillainsBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private void createBackgrounds() {
        backgrounds = new BackgroundsBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }

    private void createLives() {
        lives = new LivesBuilder()
                .context(context)
                .resources(resources)
                .storage(storage)
                .build();
    }
    private void createPauseAndPlayButtons() {
        playButton = new PlayButtonBuilder().resources(resources).build();
        pauseButton = new PauseButtonBuilder().resources(resources).build();
    }

    private void createPopups() {
        checkpointPopup = new CheckpointPopupBuilder()
                .resources(resources)
                .build();
    }
}
