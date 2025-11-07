package com.main.guapogame.resources;

import com.main.guapogame.R;

import java.util.ArrayList;
import java.util.List;

public class JellyFishAssets extends VillainAssets {

    public JellyFishAssets() {
        createJellyFish();
    }

    @Override
    public List<Integer> getAssetIds() {
        return super.getAssetIds();
    }

    private void createJellyFish() {
        assetIds = new ArrayList<>();
        assetIds.add(R.drawable.jelly_fish_bitmap_cropped1);
        assetIds.add(R.drawable.jelly_fish_bitmap_cropped2);
        assetIds.add(R.drawable.jelly_fish_bitmap_cropped3);
    }
}
