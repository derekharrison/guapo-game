package com.main.guapogame.resources;

public class HeroAssets {

    private final int assetId;
    private final int hitAssetId;
    private final int cape1AssetId;
    private final int cape2AssetId;

    protected HeroAssets(Builder builder) {
        this.assetId = builder.assetId;
        this.hitAssetId = builder.hitAssetId;
        this.cape1AssetId = builder.cape1AssetId;
        this.cape2AssetId = builder.cape2AssetId;
    }

    public int getAssetId() {
        return assetId;
    }
    public int getHitAssetId() {
        return hitAssetId;
    }

    public int getCape1AssetId() {
        return cape1AssetId;
    }

    public int getCape2AssetId() {
        return cape2AssetId;
    }

    public static class Builder {
        private int assetId;
        private int hitAssetId;
        private int cape1AssetId;
        private int cape2AssetId;

        public Builder assetId(int assetId) {
            this.assetId = assetId;
            return this;
        }

        public Builder hitAssetId(int hitAssetId) {
            this.hitAssetId = hitAssetId;
            return this;
        }

        public Builder cape1AssetId(int cape1AssetId) {
            this.cape1AssetId = cape1AssetId;
            return this;
        }

        public Builder cape2AssetId(int cape2AssetId) {
            this.cape2AssetId = cape2AssetId;
            return this;
        }

        public HeroAssets build() {
            return new HeroAssets(this);
        }
    }
}
