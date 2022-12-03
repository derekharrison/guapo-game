package com.main.guapogame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Backgrounds {

    public BackgroundElem[] backgrounds;
    public int num_backgrounds;

    Backgrounds(Context activity, int screenX, int screenY, String name) {

        // Compute number of backgrounds
        int bg = 0;

        Resources resources = activity.getResources();

        while(0 != resources.getIdentifier(name + (bg + 1),
                "drawable",
                activity.getPackageName())) {
            bg++;
        }

        num_backgrounds = bg;

        backgrounds = new BackgroundElem[num_backgrounds];

        for(int i = 0; i < num_backgrounds; ++i) {
            backgrounds[i] = new BackgroundElem();
        }

        for(int i = 1; i <= num_backgrounds; ++i) {

            int resourceId = resources.getIdentifier(name + i, "drawable", activity.getPackageName());

            backgrounds[i - 1].background = BitmapFactory.decodeResource(resources, resourceId);
            backgrounds[i - 1].background = Bitmap.createScaledBitmap(backgrounds[i - 1].background, screenX, screenY, false);

        }

        for(int i = 1; i < num_backgrounds; ++i) {
            backgrounds[i].x = backgrounds[i - 1].x + backgrounds[i - 1].background.getWidth() - 10;
        }
    }
}
