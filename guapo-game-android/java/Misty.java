package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Misty extends CharacterImage {

    public int pop_up_at_x;
    public boolean is_top;

    Misty(Resources res, int screenFactorX, int screenFactorY, int id, int id_hit, CharacterIds char_id) {

        super(res, screenFactorX, screenFactorY, id, id_hit, char_id);

    }

    public Bitmap get_misty () {
        return this.character;
    }

    public Bitmap get_misty_hit () {
        return this.character_hit;
    }

    public void draw(Canvas canvas) {
        if(!hit_character && !is_top) {
            canvas.drawBitmap(get_misty(), pop_up_at_x, y, null);
        }
        else if(hit_character && !is_top) {
            canvas.drawBitmap(get_misty_hit(), pop_up_at_x, y, null);
        }

        if(!hit_character && is_top) {
            canvas.drawBitmap(get_misty(), pop_up_at_x, y, null);
        }
        else if(hit_character && is_top) {
            canvas.drawBitmap(get_misty_hit(), pop_up_at_x, y, null);
        }
    }

}
