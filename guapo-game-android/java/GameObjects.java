package com.main.guapogame;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class GameObjects {
    private Hero hero;
    private List<Snack> snacks = new ArrayList<>();
    private List<Villain> villains = new ArrayList<>();
    private List<Background> backgrounds = new ArrayList<>();
    private Popup checkpointPopup;

    protected GameObjects(Builder builder) {
        this.hero = builder.hero;
        this.villains = builder.villains;
        this.backgrounds = builder.backgrounds;
        this.checkpointPopup = builder.checkpointPopup;
        this.snacks = builder.snacks;
    }

    public Hero getHero() {
        return hero;
    }

    public List<Villain> getVillains() {
        return villains;
    }

    public List<Background> getBackgrounds() {
        return backgrounds;
    }

    public Popup getCheckpointPopup() {
        return checkpointPopup;
    }

    public void setCheckpointPopup(Popup checkpointPopup) {
        this.checkpointPopup = checkpointPopup;
    }

    public List<Snack> getSnacks() {
        return snacks;
    }

    public static class Builder {
        private Hero hero;
        private List<Snack> snacks = new ArrayList<>();
        private List<Villain> villains = new ArrayList<>();
        private List<Background> backgrounds = new ArrayList<>();
        private Popup checkpointPopup;

        public Builder hero(Hero hero) {
            this.hero = hero;
            return this;
        }

        public Builder villains(List<Villain> villains) {
            this.villains = villains;
            return this;
        }

        public Builder backgrounds(List<Background> backgrounds) {
            this.backgrounds = backgrounds;
            return this;
        }

        public Builder checkpointPopup(Popup checkpointPopup) {
            this.checkpointPopup = checkpointPopup;
            return this;
        }

        public Builder snacks(List<Snack> snacks) {
            this.snacks = snacks;
            return this;
        }

        public GameObjects build() {
            return new GameObjects(this);
        }
    }
}
