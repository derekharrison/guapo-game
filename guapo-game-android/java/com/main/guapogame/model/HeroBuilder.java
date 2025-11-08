package com.main.guapogame.model;

import static com.main.guapogame.definitions.Keys.GAME;
import static com.main.guapogame.definitions.Keys.GAMESTATE;
import static com.main.guapogame.definitions.Keys.LEVEL;
import static com.main.guapogame.definitions.Keys.OCEAN;
import static com.main.guapogame.definitions.Keys.POSITION_X;
import static com.main.guapogame.definitions.Keys.POSITION_Y;
import static com.main.guapogame.definitions.Keys.getKey;
import static com.main.guapogame.types.Heros.TUTTI;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.main.guapogame.R;
import com.main.guapogame.graphics.Hero;
import com.main.guapogame.resources.HeroAssets;
import com.main.guapogame.storage.Storage;
import com.main.guapogame.types.Heros;
import com.main.guapogame.types.Level;

public class HeroBuilder {
    private Context context;
    private Storage storage;
    private Resources resources;

    public HeroBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public HeroBuilder storage(Storage storage) {
        this.storage = storage;
        return this;
    }

    public HeroBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public Hero build() {
        return createHero();
    }

    private Hero createHero() {
        if(getHeroId().equals(TUTTI)) {
            return createTutti();
        }
        else {
            return createGuapo();
        }
    }

    private Hero createGuapo() {
        HeroAssets assets = createGuapoAssets();
        return createHero(
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
                .cape1AssetId(R.drawable.cape1_bitmap_cropped1)
                .cape2AssetId( R.drawable.cape2_bitmap_cropped1)
                .build();
    }

    private Hero createTutti() {
        HeroAssets assets = createTuttiAssets();
        return createHero(
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

    private Hero createHero(int assetId, int hitAssetId, int capeAssetId1, int capeAssetId2) {
        int width = getHeroWidth();
        int height = getHeroHeight();
        return new Hero.Builder()
                .positionX(getHeroPositionX())
                .positionY(getHeroPositionY())
                .heroImage(getBitmapScaled(width, height, assetId))
                .heroHitImage(getBitmapScaled(width, height, hitAssetId))
                .capes(getBitmapScaled((2 * width) / 3, (2 * height) / 3, capeAssetId1))
                .capes(getBitmapScaled((2 * width) / 3, (2 * height) / 3, capeAssetId2))
                .hero(Heros.GUAPO)
                .frameCounter(getHeroFrameCounter())
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
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
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
        return  context
                .getSharedPreferences(GAME, Context.MODE_PRIVATE)
                .getString(LEVEL, "");
    }


    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    private int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
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

    private Heros getHeroId() {
        int heroId = getSharedPreferences().getInt("choose_character", 0);
        if(heroId == 1)
            return Heros.TUTTI;

        return Heros.GUAPO;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(GAME, Context.MODE_PRIVATE);
    }
}
