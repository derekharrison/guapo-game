package com.main.guapogame.model.graphics.gameobjects;

import static com.main.guapogame.parameters.Parameters.FPS;

import android.graphics.Canvas;

import com.main.guapogame.enums.Level;
import com.main.guapogame.model.graphics.builders.BubbleBuilder;
import com.main.guapogame.resources.Sounds;
import com.main.guapogame.state.GameState;

import java.util.ArrayList;
import java.util.List;

public class Bubbles {
    private boolean playSound = true;
    private final GameObject gameObject;
    private final List<Popup> bubblez = new ArrayList<>();

    public Bubbles(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    public void updateBubbles(int frameCounter) {
        if(GameState.getLevel().equals(Level.OCEAN)) {
            if(frameCounter % FPS == 0) {
                Popup bubble = createBubble();
                addBubble(bubble);
                removeBubble();
                playBubbles();
            }

            for(Popup bubble : bubblez) {
                bubble.update();
            }
        }
    }

    public void drawBubbles(Canvas canvas) {
        if(GameState.getLevel().equals(Level.OCEAN)) {
            for(Popup bubble : bubblez)
                bubble.draw(canvas);
        }
    }

    private void playBubbles() {
        if(playSound) {
            Sounds.playBubbles();
            playSound = false;
        }
    }

    private Popup createBubble() {
        return new BubbleBuilder()
                .resources(gameObject.getContext().getResources())
                .positionX((int) gameObject.getPositionX())
                .positionY((int) gameObject.getPositionY())
                .build();
    }

    private void addBubble(Popup bubble) {
        bubblez.add(bubble);
    }

    private void removeBubble() {
        if(!bubblez.isEmpty() && bubblez.size() > 3)
            bubblez.remove(0);
    }
}
