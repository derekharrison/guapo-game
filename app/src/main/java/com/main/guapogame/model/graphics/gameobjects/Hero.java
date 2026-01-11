package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.parameters.Parameters.FPS;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.main.guapogame.enums.State;
import com.main.guapogame.model.state.Position;
import com.main.guapogame.model.state.Trajectory;
import com.main.guapogame.resources.assets.Sounds;
import com.main.guapogame.state.GameState;

import java.util.ArrayList;
import java.util.List;

public class Hero extends Character {
    private final Bitmap heroImage;
    private final Bitmap heroHitImage;
    private final List<Bitmap> capes;
    private boolean playSound = true;
    private boolean allowUpdate = true;
    private Bubbles bubbles = new Bubbles(this);

    protected Hero(Builder builder) {
        super(builder);
        this.heroImage = builder.heroImage;
        this.heroHitImage = builder.heroHitImage;
        this.capes = builder.capes;
    }

    public static class Builder extends Character.Builder {
        private Bitmap heroImage;
        private Bitmap heroHitImage;
        private final List<Bitmap> capes = new ArrayList<>();

        @Override
        public Builder positionX(float positionX) {
            super.positionX(positionX);
            return this;
        }

        @Override
        public Builder positionY(float positionY) {
            super.positionY(positionY);
            return this;
        }

        public Builder heroImage(Bitmap heroImage) {
            this.heroImage = heroImage;
            return this;
        }

        public Builder heroHitImage(Bitmap heroHit) {
            this.heroHitImage = heroHit;
            return this;
        }

        public Builder capes(Bitmap cape) {
            capes.add(cape);
            return this;
        }

        @Override
        public Builder frameCounter(int frameCounter) {
            super.frameCounter(frameCounter);
            return this;
        }

        @Override
        public Builder context(Context context) {
            super.context(context);
            return this;
        }

        @Override
        public Hero build() {
            return new Hero(this);
        }
    }

    @Override
    public void update() {
        super.update();
        if(allowUpdate) {
            setPositionX(getPositionX() + getVelocityX());
            setPositionY(getPositionY() + getVelocityY());
        }
        if(!allowUpdate) {
            setPositionX(getPositionX());
            setPositionY(getPositionY());
        }

        updateTrajectory();
        updateBubbles();

        reflect();
    }

    @Override
    public void draw(Canvas canvas) {
        drawHero(canvas);
        drawBubbles(canvas);
    }

    public float getWidth() {
        return getImage().getWidth();
    }

    public float getHeight() {
        return getImage().getHeight();
    }

    @Override
    public Bitmap getImage() {
        if(GameState.getGameState().equals(State.GAME_OVER))
            return heroHitImage;

        return heroImage;
    }

    public void playSoundInteractingWithVillain() {
        if(playSound) {
            Sounds.playSoundBarkHit();
            playSound = false;
        }
    }

    public void setAllowUpdate(boolean allowUpdate) {
        this.allowUpdate = allowUpdate;
    }

    public boolean getAllowUpdate() {
        return allowUpdate;
    }

    private void updateBubbles() {
        if(getBubbleFrameCounter() >= 6 * FPS) {
            bubbles = new Bubbles(this);
            resetBubbleFrameCounter();
        }

        bubbles.updateBubbles(getBubbleFrameCounter());
    }

    private void drawHero(Canvas canvas) {
        if(hasCapeImage()) {
            canvas.drawBitmap(
                    getCapeImage(),
                    getPositionX(),
                    getPositionY() + getHeight() / 5,
                    null
            );
        }
        canvas.drawBitmap(getImage(), getPositionX(), getPositionY(), null);
    }

    private void drawBubbles(Canvas canvas) {
        bubbles.drawBubbles(canvas);
    }

    private boolean hasCapeImage() {
        return getCapeImage() != null;
    }

    private Bitmap getCapeImage() {
        if (getFrameCounter() < 2)
            return capes.get(1);

        else if(getFrameCounter() >= 2 && getFrameCounter() < 4)
            return capes.get(0);

        resetFrameCounter();
        
        return capes.get(0);
    }

    private void reflect() {
        int height = getImage().getHeight() / 2;
        int width = getImage().getWidth() / 2;

        if(positionY < -height) {
            setVelY(-getVelocityY());
            positionY = -height;
        }

        if(positionY > getScreenHeight() - height) {
            setVelY(-getVelocityY());
            positionY = (float) getScreenHeight() - height;
        }

        if(positionX < -width) {
            setVelX(-getVelocityX());
            positionX = -width;
        }

        if(positionX > getScreenWidth() - width) {
            setVelX(-getVelocityX());
            positionX = (float) getScreenWidth() - width;
        }
    }

    private void updateTrajectory() {
        Position position = new Position(getPositionX(), getPositionY());
        Trajectory.addPosition(position);
    }
}
