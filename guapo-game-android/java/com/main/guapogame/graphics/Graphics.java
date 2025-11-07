package com.main.guapogame.graphics;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graphics {
    private final Hero hero;
    private final List<Snack> snacks;
    private final List<Villain> villains;
    private final List<Background> backgrounds;
    private Popup checkpointPopup;
    private final List<Bitmap> lives;
    private List<Bubble> bubbles = new ArrayList<>();
    private final Bitmap playButton;
    private final Bitmap pauseButton;


    protected Graphics(Builder builder) {
        this.hero = builder.hero;
        this.villains = builder.villains;
        this.backgrounds = builder.backgrounds;
        this.checkpointPopup = builder.checkpointPopup;
        this.snacks = builder.snacks;
        this.lives = builder.lives;
        this.playButton = builder.playButton;
        this.pauseButton = builder.pauseButton;
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

    public List<Bubble> getBubbles() {
        return bubbles;
    }

    public void addBubble(Bubble bubble) {
        this.bubbles.add(bubble);
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

    public List<Bitmap> getLives() {
        return lives;
    }

    public Bitmap getPlayButton() {
        return playButton;
    }

    public Bitmap getPauseButton() {
        return pauseButton;
    }

    public static class Builder {
        private Hero hero;
        private List<Snack> snacks = new ArrayList<>();
        private List<Villain> villains = new ArrayList<>();
        private List<Background> backgrounds = new ArrayList<>();
        private Popup checkpointPopup;
        private List<Bitmap> lives = new ArrayList<>();
        private Bitmap playButton;
        private Bitmap pauseButton;

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

        public Builder lives(List<Bitmap> lives) {
            this.lives = lives;
            return this;
        }

        public Builder playButton(Bitmap playButton) {
            this.playButton = playButton;
            return this;
        }

        public Builder pauseButton(Bitmap pauseButton) {
            this.pauseButton = pauseButton;
            return this;
        }

        public Graphics build() {
            return new Graphics(this);
        }
    }
}
