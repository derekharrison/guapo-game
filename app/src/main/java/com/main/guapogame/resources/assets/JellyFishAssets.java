package com.main.guapogame.resources.assets;

import com.main.guapogame.R;

import java.util.ArrayList;

public class JellyFishAssets extends VillainAssets {

    public JellyFishAssets() {
        createJellyFish();
    }

    private void createJellyFish() {
        assetIds = new ArrayList<>();
        assetIds.add(R.drawable.jelly_fish_bitmap_cropped1);
        assetIds.add(R.drawable.jelly_fish_bitmap_cropped2);
        assetIds.add(R.drawable.jelly_fish_bitmap_cropped3);
    }
}
