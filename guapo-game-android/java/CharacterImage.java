package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

// TODO : refactor

public class CharacterImage {
    public int speed = 20;
    public int y_vel = 20;
    public int x = 0, y, width, height;
    public Bitmap character, character_hit;
    public boolean play_sound_allowed = true;
    public boolean hit_character = false;
    public boolean appeared = false;
    public CharacterIds character_id;

    CharacterImage(Resources res, int screenFactorX, int screenFactorY, int id, int id_hit, CharacterIds char_id) {

        character = BitmapFactory.decodeResource(res, id);
        character_hit = BitmapFactory.decodeResource(res, id_hit);

        width = screenFactorX;
        height = screenFactorY;

        character = Bitmap.createScaledBitmap(character, width, height, false);
        character_hit = Bitmap.createScaledBitmap(character_hit, width, height, false);

        y = -height;

        this.character_id = char_id;

    }

    public Bitmap get_character_image () {
        return this.character;
    }

    public Bitmap get_image_hit () {
        return this.character_hit;
    }


    public void draw(Canvas canvas) {
        if(hit_character) {
            canvas.drawBitmap(get_image_hit(), x, y, null);
        }
        canvas.drawBitmap(get_character_image(), x, y, null);
    }

}
