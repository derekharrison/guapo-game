package com.main.guapogame.resources;

import com.main.guapogame.R;

import java.util.ArrayList;
import java.util.List;

public class WaraWaraAssets extends VillainAssets {
    
    public WaraWaraAssets() {
        createWaraWaras();
    }

    @Override
    public List<Integer> getAssetIds() {
        return super.getAssetIds();
    }

    private void createWaraWaras() {
        assetIds = new ArrayList<>();
        assetIds.add(R.drawable.warawara1_bitmap_custom_mod_cropped);
        assetIds.add(R.drawable.warawara2_bitmap_custom_mod_cropped);
        assetIds.add(R.drawable.warawara3_bitmap_custom_mod_cropped);
    }
}
