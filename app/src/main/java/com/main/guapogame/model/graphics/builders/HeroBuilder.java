package com.main.guapogame.model.graphics.builders;

import static com.main.guapogame.model.enums.HeroId.GUAPO;
import static com.main.guapogame.model.enums.HeroId.MICA;
import static com.main.guapogame.model.enums.HeroId.ROCCO;
import static com.main.guapogame.model.enums.HeroId.TUTTI;
import static com.main.guapogame.parameters.Keys.CHOOSE_CHARACTER;
import static com.main.guapogame.parameters.Keys.GAME;
import static com.main.guapogame.parameters.Keys.GAMESTATE;
import static com.main.guapogame.parameters.Keys.LEVEL;
import static com.main.guapogame.parameters.Keys.POSITION_X;
import static com.main.guapogame.parameters.Keys.POSITION_Y;
import static com.main.guapogame.parameters.Keys.getKey;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenHeight;
import static com.main.guapogame.model.state.ScreenDimensions.getScreenWidth;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;
import com.main.guapogame.model.enums.HeroId;
import com.main.guapogame.enums.Level;
import com.main.guapogame.model.graphics.gameobjects.Hero;
import com.main.guapogame.resources.assets.HeroAssets;
import com.main.guapogame.state.GameState;
import com.main.guapogame.resources.storage.Storage;

class HeroBuilder {
    private Context context;
    private Storage storage;

    HeroBuilder context(Context context) {
        this.context = context;
        return this;
    }

    HeroBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    Hero build() {
        return createHero();
    }

    private Hero createHero() {
        if(getHeroId().equals(TUTTI))
            return createTutti();

        if(getHeroId().equals(MICA))
            return createMica();

        if(getHeroId().equals(ROCCO))
            return createRocco();

        return createGuapo();
    }

    private Hero createGuapo() {
        HeroAssets assets = createGuapoAssets();
        return createHero(
                GUAPO,
                assets.getAssetId(),
                assets.getHitAssetId(),
                assets.getCape1AssetId(),
                assets.getCape2AssetId()
        );
    }

    private HeroAssets createGuapoAssets() {
        if(GameState.getLevel().equals(Level.OCEAN)) {
            return new HeroAssets.Builder()
                    .assetId(R.drawable.guapo_snorkel_bitmap_cropped)
                    .hitAssetId(R.drawable.guapo_snorkel_hit_bitmap_cropped)
                    .build();
        }

        return new HeroAssets.Builder()
                .assetId(R.drawable.guapo_main_image_bitmap_cropped)
                .hitAssetId(R.drawable.guapo_main_image_hit_bitmap_cropped)
                .cape1AssetId(R.drawable.cape1_bitmap_cropped1_green)
                .cape2AssetId( R.drawable.cape2_bitmap_cropped1_green)
                .build();
    }

    private Hero createTutti() {
        HeroAssets assets = createTuttiAssets();
        return createHero(
                TUTTI,
                assets.getAssetId(),
                assets.getHitAssetId(),
                assets.getCape1AssetId(),
                assets.getCape2AssetId()
        );
    }

    private Hero createMica() {
        HeroAssets assets = createMicaAssets();
        return createHero(
                MICA,
                assets.getAssetId(),
                assets.getHitAssetId(),
                assets.getCape1AssetId(),
                assets.getCape2AssetId()
        );
    }

    private Hero createRocco() {
        HeroAssets assets = createRoccoAssets();
        return createHero(
                ROCCO,
                assets.getAssetId(),
                assets.getHitAssetId(),
                assets.getCape1AssetId(),
                assets.getCape2AssetId()
        );
    }

    private HeroAssets createTuttiAssets() {
        if(GameState.getLevel().equals(Level.OCEAN)) {
            return new HeroAssets.Builder()
                    .assetId(R.drawable.tutti_snorkel1_cropped)
                    .hitAssetId(R.drawable.tutti_snorkel1_hit_cropped)
                    .build();
        }

        return new HeroAssets.Builder()
                .assetId(R.drawable.tutti_bitmap_no_cape_cropped)
                .hitAssetId(R.drawable.tutti_bitmap_hit_no_cape_cropped)
                .cape1AssetId(R.drawable.cape1_bitmap_cropped1)
                .cape2AssetId( R.drawable.cape2_bitmap_cropped1)
                .build();
    }

    private HeroAssets createMicaAssets() {
        if(GameState.getLevel().equals(Level.OCEAN)) {
            return new HeroAssets.Builder()
                    .assetId(R.drawable.mica_hit_scuba)
                    .hitAssetId(R.drawable.mica_scuba)
                    .build();
        }

        return new HeroAssets.Builder()
                .assetId(R.drawable.mica)
                .hitAssetId(R.drawable.mica_cropped_main2)
                .cape1AssetId(R.drawable.cape1_bitmap_cropped1_pink)
                .cape2AssetId( R.drawable.cape2_bitmap_cropped1_pink)
                .build();
    }

    private HeroAssets createRoccoAssets() {
        if(GameState.getLevel().equals(Level.OCEAN)) {
            return new HeroAssets.Builder()
                    .assetId(R.drawable.rocco)
                    .hitAssetId(R.drawable.rocco)
                    .build();
        }

        return new HeroAssets.Builder()
                .assetId(R.drawable.rocco)
                .hitAssetId(R.drawable.rocco_hit_cropped)
                .cape1AssetId(R.drawable.cape1_bitmap_cropped1_blue)
                .cape2AssetId( R.drawable.cape2_bitmap_cropped1_blue)
                .build();
    }

    private Hero createHero(HeroId heroId, int assetId, int hitAssetId, int capeAssetId1, int capeAssetId2) {
        int width = getHeroWidth();
        int height = getHeroHeight();
        int scaleX = (2 * width) / 3;
        int scaleY = (2 * height) / 3;
        return new Hero.Builder()
                .velX(0).velY(0)
                .positionX(getHeroPositionX())
                .positionY(getHeroPositionY())
                .heroImage(getBitmapScaled(width, height, assetId))
                .heroHitImage(getBitmapScaled(width, height, hitAssetId))
                .capes(getBitmapScaled(scaleX, scaleY, capeAssetId1))
                .capes(getBitmapScaled(scaleX, scaleY, capeAssetId2))
                .frameCounter(getHeroFrameCounter())
                .context(context)
                .heroId(heroId)
                .build();
    }

    private int getHeroFrameCounter() {
        return storage.loadGame().getHeroFrameCounter();
    }

    private float getHeroPositionX() {
        if(isActiveSession())
            return storage.loadGame().getHeroPosition(POSITION_X);

        return (float) (getScreenWidth() / 10.0);
    }

    private float getHeroPositionY() {
        if(isActiveSession())
            return storage.loadGame().getHeroPosition(POSITION_Y);

        return (float) (getScreenHeight() / 2.0);
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableIdentification);
        if(bitmap == null)
            return null;
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private boolean isActiveSession() {
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private String getLevelId() {
        return context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }

    private int getScreenFactorX() {
        return (int) (getScreenWidth() / 10.0);
    }

    private int getScreenFactorY() {
        return (int) (getScreenHeight() / 5.0);
    }

    private int getHeroWidth() {
        return  (int) (getScreenFactorX() * 3 / 2.0);
    }

    private int getHeroHeight() {
        return (int) (getScreenFactorY() * 3 / 2.0);
    }

    private HeroId getHeroId() {
        int heroId = getSharedPreferences().getInt(CHOOSE_CHARACTER, 0);

        if(heroId == 1)
            return TUTTI;

        if(heroId == 2)
            return MICA;

        if(heroId == 3)
            return ROCCO;

        return HeroId.GUAPO;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GAME, Context.MODE_PRIVATE);
    }
}
