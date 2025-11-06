package com.main.guapogame.resources;

import android.graphics.Bitmap;

import com.main.guapogame.R;

import java.util.ArrayList;
import java.util.List;

public class Seagulls extends Villains {

    public Seagulls() {
        createSeagulls();
    }

    @Override
    public List<Integer> getAssetIds() {
        return super.getAssetIds();
    }

    private void createSeagulls() {
        assetIds = new ArrayList<>();
        assetIds.add(R.drawable.seagull1_bitmap_cropped_new);
        assetIds.add(R.drawable.seagull2_bitmap_cropped_new);
        assetIds.add(R.drawable.seagull3_bitmap_cropped_new);
    }
}
