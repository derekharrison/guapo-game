package com.main.guapogame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HeroImage {

    public int x, y, width, height, image_counter = 0;
    public int x_o, y_o;
    public int x_vel, y_vel;
    public int num_frames_display = 2;
    public Bitmap hero_image, hero_image_hit, cape1, cape2;
    public boolean play_sound_allowed = true;
    public boolean hero_is_hit = false;

    public HeroImage(Resources res, int screenFactorX, int screenFactorY, int hero_image, int hero_image_hit, int cape_image1, int cape_image2) {

        this.hero_image = BitmapFactory.decodeResource(res, hero_image);
        this.hero_image_hit = BitmapFactory.decodeResource(res, hero_image_hit);
        this.cape1 = BitmapFactory.decodeResource(res, cape_image1);
        this.cape2 = BitmapFactory.decodeResource(res, cape_image2);

        width = screenFactorX;
        height = screenFactorY;

        this.hero_image = Bitmap.createScaledBitmap(this.hero_image, width, height, false);
        this.hero_image_hit = Bitmap.createScaledBitmap(this.hero_image_hit, width, height, false);
        this.cape1 = Bitmap.createScaledBitmap(this.cape1, (2 * width) / 3, (3 * height) / 4, false);
        this.cape2 = Bitmap.createScaledBitmap(this.cape2, (2 * width) / 3, (3 * height) / 4, false);

        x = 100;
        y = 100;

        x_vel = 0;
        y_vel = 0;
    }

    public HeroImage(Resources res, int screenFactorX, int screenFactorY, int hero_image, int hero_image_hit) {

        this.hero_image = BitmapFactory.decodeResource(res, hero_image);
        this.hero_image_hit = BitmapFactory.decodeResource(res, hero_image_hit);

        width = screenFactorX;
        height = screenFactorY;

        this.hero_image = Bitmap.createScaledBitmap(this.hero_image, width, height, false);
        this.hero_image_hit = Bitmap.createScaledBitmap(this.hero_image_hit, width, height, false);

        x = 100;
        y = 100;

        x_vel = 0;
        y_vel = 0;
    }

    public void update() {
        image_counter++;
    }

    public Bitmap get_hero_image() {

        if(!hero_is_hit) return hero_image;

        return hero_image_hit;
    }

    public Bitmap get_cape_image() {

        if (image_counter >= 0 && image_counter <= num_frames_display) {
            return cape1;
        }

        if (image_counter > num_frames_display && image_counter <= 2 * num_frames_display) {
            return cape2;
        }

        image_counter = 0;

        return cape1;
    }
}
