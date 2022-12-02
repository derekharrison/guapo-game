package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Misty extends Character {

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

}
