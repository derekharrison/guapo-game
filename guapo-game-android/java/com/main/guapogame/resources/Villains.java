package com.main.guapogame.resources;

import java.util.ArrayList;
import java.util.List;

public class Villains {
    List<Integer> assetIds = new ArrayList<>();

    public List<Integer> getAssetIds() {
        return assetIds;
    }

    public void addAssetId(int assetId) {
        assetIds.add(assetId);
    }
}
