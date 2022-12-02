package com.main.guapogame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BackgroundsLevel1 extends Backgrounds {

    public int num_backgrounds = 10;

    BackgroundsLevel1(Context activity, int screenX, int screenY, Resources res) {

        backgrounds = new BackgroundElem[num_backgrounds];

        for(int i = 0; i < num_backgrounds; ++i) {
            backgrounds[i] = new BackgroundElem();
        }

        for(int i = 1; i <= num_backgrounds; ++i) {
            Resources resources = activity.getResources();
            int resourceId = resources.getIdentifier("background_guapo_game_nr" + i, "drawable", activity.getPackageName());

            backgrounds[i - 1].background = BitmapFactory.decodeResource(res, resourceId);
            backgrounds[i - 1].background = Bitmap.createScaledBitmap(backgrounds[i - 1].background, screenX, screenY, false);

        }

        for(int i = 1; i < num_backgrounds; ++i) {
            backgrounds[i].x = backgrounds[i - 1].x + backgrounds[i - 1].background.getWidth() - 10;
        }
    }
}