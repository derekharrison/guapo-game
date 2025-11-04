package com.main.guapogame;

import java.util.ArrayList;
import java.util.List;

public class BackgroundsLevel3 extends Backgrounds {
    public BackgroundsLevel3() {
        createBackgrounds();
    }

    @Override
    public List<Integer> getAssetIds() {
        return super.getAssetIds();
    }

    private void createBackgrounds() {
        backgroundAssetIds = new ArrayList<>();
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_01);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_02);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_03);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_04);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_05);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_06);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_07);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_08);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_09);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_10);
        backgroundAssetIds.add(R.drawable.background_guapo_game_level3_11);
    }
}
