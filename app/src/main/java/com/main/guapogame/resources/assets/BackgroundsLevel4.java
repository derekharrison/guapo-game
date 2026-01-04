package com.main.guapogame.resources.assets;

import com.main.guapogame.R;

import java.util.ArrayList;

public class BackgroundsLevel4 extends Backgrounds {
    public BackgroundsLevel4() {
        createBackgrounds();
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
