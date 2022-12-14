package com.main.guapogame;

import android.content.res.Resources;

public class BubbleProducers {
    Bubbles guapo_bubbles;
    Bubbles frito_bubbles;
    Bubbles misty_bubbles;
    Bubbles misty_top_bubbles;
    Bubbles brownie_bubbles;

    BubbleProducers(Resources res, int screenFactorX, int screenFactorY) {
        guapo_bubbles = new Bubbles(res, screenFactorX, screenFactorY);
        frito_bubbles = new Bubbles(res, screenFactorX, screenFactorY);
        misty_bubbles = new Bubbles(res, screenFactorX, screenFactorY);
        misty_top_bubbles = new Bubbles(res, screenFactorX, screenFactorY);
        brownie_bubbles = new Bubbles(res, screenFactorX, screenFactorY);
    }
}
