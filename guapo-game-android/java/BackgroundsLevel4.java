package com.main.guapogame;

import java.util.ArrayList;
import java.util.List;

public class BackgroundsLevel4 extends Backgrounds {
    public BackgroundsLevel4() {
        createBackgrounds();
    }

    @Override
    public List<Integer> getAssetIds() {
        return super.getAssetIds();
    }

    private void createBackgrounds() {
        backgroundAssetIds = new ArrayList<>();
        backgroundAssetIds.add(R.drawable.background_guapogame_underwaterlevel_1);
        backgroundAssetIds.add(R.drawable.background_guapogame_underwaterlevel_2);
        backgroundAssetIds.add(R.drawable.background_guapogame_underwaterlevel_3);
        backgroundAssetIds.add(R.drawable.background_guapogame_underwaterlevel_4);
        backgroundAssetIds.add(R.drawable.background_guapogame_underwaterlevel_5);
    }
}
