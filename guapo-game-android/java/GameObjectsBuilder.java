package com.main.guapogame;

import static com.main.guapogame.Keys.ARUBA;
import static com.main.guapogame.Keys.BEACH;
import static com.main.guapogame.Keys.GAME;
import static com.main.guapogame.Keys.GAMESTATE;
import static com.main.guapogame.Keys.LEVEL;
import static com.main.guapogame.Keys.OCEAN;
import static com.main.guapogame.Keys.POSITION_X;
import static com.main.guapogame.Keys.POSITION_Y;
import static com.main.guapogame.Keys.TRIP;
import static com.main.guapogame.Keys.UTREG;
import static com.main.guapogame.Keys.VELOCITY_X;
import static com.main.guapogame.Keys.getKey;
import static com.main.guapogame.Parameters.POINTS_BEGGIN_STRIPS;
import static com.main.guapogame.Parameters.START_NUM_OF_VILLAINS;
import static com.main.guapogame.Parameters.getBackgroundSpeed;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameObjectsBuilder {
    private Hero hero;
    private final List<Snack> snacks = new ArrayList<>();
    private final List<Villain> villains = new ArrayList<>();
    private final List<Background> backgrounds = new ArrayList<>();
    private Popup checkpointPopup;
    private Context context;
    private GameState gameState;
    private Resources resources;

    public GameObjectsBuilder context(Context context) {
        this.context = context;
        return this;
    }

    public GameObjectsBuilder gameState(GameState gameState) {
        this.gameState = gameState;
        return this;
    }

    public GameObjectsBuilder resources(Resources resources) {
        this.resources = resources;
        return this;
    }

    public GameObjects build() {
        createGameObjects();
        return new GameObjects.Builder()
                .hero(hero)
                .villains(villains)
                .backgrounds(backgrounds)
                .checkpointPopup(checkpointPopup)
                .snacks(snacks)
                .build();
    }

    private void createGameObjects() {
        createHero();
        createSnacks();
        createVillains();
        createBackgrounds();
        createPopups();
    }

    private void createSnacks() {
        if(!isActiveSession())
            getSnacks();

        if(isActiveSession())
            getSnacksFromActiveSession();
    }

    private void getSnacks() {
        snacks.addAll(createSnacks(Parameters.NUM_CHEESY_BITES, Parameters.POINTS_CHEESY_BITES, R.drawable.cheesy_bite_resized));
        snacks.addAll(createSnacks(Parameters.NUM_PAPRIKA, Parameters.POINTS_PAPRIKA, R.drawable.paprika_bitmap_cropped));
        snacks.addAll(createSnacks(Parameters.NUM_CUCUMBERS, Parameters.POINTS_CUCUMBER, R.drawable.cucumber_bitmap_cropped));
        snacks.addAll(createSnacks(Parameters.NUM_BROCCOLI, Parameters.POINTS_BROCCOLI, R.drawable.broccoli_bitmap_cropped));
        snacks.addAll(createSnacks(1, POINTS_BEGGIN_STRIPS, R.drawable.beggin_strip_cropped));
    }

    private void getSnacksFromActiveSession() {
        int numSnacks = getNumSnacks();
        int width = (int) (getScreenFactorX() - getScreenFactorX() / 3.0);
        int height = (int) (getScreenFactorY() - getScreenFactorY() / 3.0);
        for(int snackId = 0; snackId < numSnacks; snackId++) {
            int assetId = gameState.loadGame().getSnackAssetId(String.valueOf(snackId));
            snacks.add(
                    new Snack.Builder()
                            .positionX((int) gameState.loadGame().getSnackPosition(POSITION_X, String.valueOf(snackId)))
                            .positionY((int) gameState.loadGame().getSnackPosition(POSITION_Y, String.valueOf(snackId)))
                            .velocityX(-getBackgroundSpeed())
                            .pointsForSnack(gameState.loadGame().getSnackPoints(String.valueOf(snackId)))
                            .snackImage(getBitmapScaled(width, height, assetId))
                            .assetId(assetId)
                            .build()
            );
        }
    }

    private int getNumSnacks() {
        if(isActiveSession()) {
            return gameState.loadGame().getNumSnacks();
        }

        return 1;
    }

    private List<Snack> createSnacks(int numSnacks, int pointsForSnack, int assetId) {
        List<Snack> snacks = new ArrayList<>();
        int width = (int) (getScreenFactorX() - getScreenFactorX() / 3.0);
        int height = (int) (getScreenFactorY() - getScreenFactorY() / 3.0);
        Bitmap snackImage = getBitmapScaled(width, height, assetId);
        for (int snack = 0; snack < numSnacks; snack++) {
            snacks.add(
                    new Snack.Builder()
                            .positionX((int) getSnackPositionX(snackImage))
                            .positionY((int) getSnackPositionY(snackImage))
                            .velocityX(-getBackgroundSpeed())
                            .pointsForSnack(pointsForSnack)
                            .snackImage(snackImage)
                            .assetId(assetId)
                            .build()
            );
        }

        return snacks;
    }

    private float getSnackPositionX(Bitmap snackImage) {
        Random random = new Random();
        return random.nextInt(2 * getScreenWidth() - snackImage.getWidth() / 2);
    }

    private float getSnackPositionY(Bitmap snackImage) {
        Random random = new Random();
        return random.nextInt(getScreenHeight() - snackImage.getHeight() / 2);
    }

    private void createPopups() {
        checkpointPopup = createCheckpointPopup();
    }

    private Popup createCheckpointPopup() {
        int width = (int) (getScreenWidth() / 10.0);
        int height = (int) (getScreenHeight() / 5.0);
        Bitmap image = getBitmapScaled(width, height, R.drawable.flag_aruba_bitmap_cropped);
        return new Popup.Builder()
                .duration(0)
                .positionX(getScreenWidth() - getScreenWidth() / 4)
                .positionY(image.getHeight() + getScreenHeight() / 10)
                .image(image)
                .build();
    }

    private void createBackgrounds() {
        List<Integer> assetIds = getBackgroundAssetIds();
        int backgroundId = 0;
        for(Integer assetId : assetIds) {
            this.backgrounds.add(createBackground(assetId, String.valueOf(backgroundId)));
            backgroundId++;
        }
    }

    private Background createBackground(int assetId, String backgroundId) {
        return new Background.Builder()
                .positionX(getStartPositionBackground(backgroundId))
                .velocityX(-getBackgroundSpeed())
                .background(getBitmapScaled(getScreenWidth(), getScreenHeight(), assetId))
                .build();
    }

    private float getStartPositionBackground(String backgroundId) {
        if(isActiveSession()) {
            return gameState.loadGame().getBackgroundPosition(POSITION_X, backgroundId);
        }

        return 0;
    }

    private List<Integer> getBackgroundAssetIds() {
        String levelId = getLevelId();
        if(levelId.equals(ARUBA)) {
            return new BackgroundsLevel1().getAssetIds();
        }
        if(levelId.equals(BEACH)) {
            return new BackgroundsLevel2().getAssetIds();
        }
        if(levelId.equals(TRIP)) {
            return new BackgroundsLevel3().getAssetIds();
        }
        if(levelId.equals(OCEAN)) {
            return new BackgroundsLevel4().getAssetIds();
        }
        if(levelId.equals(UTREG)) {
            return new BackgroundsLevel5().getAssetIds();
        }
        return new ArrayList<>();
    }
    private void createVillains() {
        int numVillains = getNumVillains();
        for(int villainId = 0; villainId < numVillains; villainId++) {
            if(isActiveSession())
                createVillain(String.valueOf(villainId));
            else
                addVillain(createVillain());
        }
    }

    private void addVillain(Villain villain) {
        this.villains.add(villain);
    }

    private int getNumVillains() {
        if(isActiveSession())
            return gameState.loadGame().getNumVillains();

        return START_NUM_OF_VILLAINS;
    }


    private Villain createVillain() {
        return new Villain.Builder()
                .positionX(-500)
                .images(createVillainImages())
                .build();
    }

    private List<Bitmap> createVillainImages() {
        if(getLevelId().equals(BEACH) || getLevelId().equals(OCEAN))
            return createSeagullImages();

        return createWaraWaraImages();
    }

    private List<Bitmap> createSeagullImages() {
        List<Bitmap> images = new ArrayList<>();

        int width =  (int) (((float) getScreenWidth()) / 10);
        int height = (int) (((float) getScreenHeight()) / 5);

        Bitmap bird_image1 = getBitmapScaled(width, height, R.drawable.seagull1_bitmap_cropped_new);
        Bitmap bird_image2 = getBitmapScaled(width, height, R.drawable.seagull2_bitmap_cropped_new);
        Bitmap bird_image3 = getBitmapScaled(width, height, R.drawable.seagull3_bitmap_cropped_new);

        images.add(bird_image1);
        images.add(bird_image2);
        images.add(bird_image3);

        return images;
    }

    private List<Bitmap> createWaraWaraImages() {
        List<Bitmap> images = new ArrayList<>();

        int width =  (int) (((float) getScreenWidth()) / 10);
        int height = (int) (((float) getScreenHeight()) / 5);

        Bitmap bird_image1 = getBitmapScaled(width, height, R.drawable.warawara1_bitmap_custom_mod_cropped);
        Bitmap bird_image2 = getBitmapScaled(width, height, R.drawable.warawara2_bitmap_custom_mod_cropped);
        Bitmap bird_image3 = getBitmapScaled(width, height, R.drawable.warawara3_bitmap_custom_mod_cropped);

        images.add(bird_image1);
        images.add(bird_image2);
        images.add(bird_image3);

        return images;
    }

    private void createVillain(String villainId) {
        villains.add(
                new Villain.Builder()
                        .positionX(getVillainPositionX(villainId))
                        .positionY(getVillainPositionY(villainId))
                        .velX(getVillainVelocityX(villainId))
                        .images(createVillainImages())
                        .frameCounter(getVillainFrameCounter(villainId))
                        .build()
        );
    }

    private int getVillainFrameCounter(String villainId) {
        return gameState.loadGame().getVillainFrameCounter(villainId);
    }

    private float getVillainPositionX(String villainId) {
        return gameState.loadGame().getVillainPosition(POSITION_X, villainId);
    }

    private float getVillainPositionY(String villainId) {
        return gameState.loadGame().getVillainPosition(POSITION_Y, villainId);
    }

    private float getVillainVelocityX(String villainId) {
        return gameState.loadGame().getVillainVelocity(VELOCITY_X, villainId);
    }

    private void createHero() {
        if(getHeroId().equals(Heros.TUTTI)) {
            hero = createTutti();
        }
        else {
            hero = createGuapo();
        }
    }

    private Hero createGuapo() {
        int width = (int) (getScreenFactorX() * 3 / 2.0);
        int height = (int) (getScreenFactorY() * 3 / 2.0);

        Bitmap heroImage = getBitmapScaled(width, height, R.drawable.guapo_main_image_bitmap_cropped);
        Bitmap heroImageHit = getBitmapScaled(width, height, R.drawable.guapo_main_image_hit_bitmap_cropped);
        Bitmap capeImage1 = getBitmapScaled(width / 2, (3 * height) / 4, R.drawable.cape1_bitmap_cropped1);
        Bitmap capeImage2 = getBitmapScaled(width / 2, (3 * height) / 4, R.drawable.cape2_bitmap_cropped1);

        return new Hero.Builder()
                .positionX(getHeroPositionX())
                .positionY(getHeroPositionY())
                .heroImage(heroImage)
                .heroHitImage(heroImageHit)
                .capes(capeImage1)
                .capes(capeImage2)
                .hero(Heros.GUAPO)
                .frameCounter(getHeroFrameCounter())
                .build();
    }

    private Hero createTutti() {
        int width = (int) (getScreenFactorX() * 3 / 2.0);
        int height = (int) (getScreenFactorY() * 3 / 2.0);

        Bitmap heroImage = getBitmapScaled(width, height, R.drawable.tutti_bitmap_no_cape_cropped);
        Bitmap heroImageHit = getBitmapScaled(width, height, R.drawable.tutti_bitmap_hit_no_cape_cropped);
        Bitmap capeImage1 = getBitmapScaled(width / 2, (3 * height) / 4, R.drawable.cape1_bitmap_cropped1);
        Bitmap capeImage2 = getBitmapScaled(width / 2, (3 * height) / 4, R.drawable.cape2_bitmap_cropped);

        return new Hero.Builder()
                .positionX(getHeroPositionX())
                .positionY(getHeroPositionY())
                .heroImage(heroImage)
                .heroHitImage(heroImageHit)
                .frameCounter(getHeroFrameCounter())
                .capes(capeImage1)
                .capes(capeImage2)
                .hero(Heros.TUTTI)
                .build();
    }

    private Bitmap getBitmapScaled(int scaleX, int scaleY, int drawableIdentification) {
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableIdentification);
        return Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);
    }

    private int getHeroFrameCounter() {
        return gameState.loadGame().getHeroFrameCounter();
    }

    private float getHeroPositionX() {
        if(isActiveSession()) {
            return gameState.loadGame().getHeroPosition(POSITION_X);
        }

        return (float) (getScreenWidth() / 10.0);
    }

    private float getHeroPositionY() {
        if(isActiveSession()) {
            return gameState.loadGame().getHeroPosition(POSITION_Y);
        }

        return (float) (getScreenHeight() / 2.0);
    }

    private boolean isActiveSession() {
        return context.getSharedPreferences(GAME, Context.MODE_PRIVATE).getBoolean(getKey(getLevelId(), GAMESTATE), false);
    }

    private String getLevelId() {
        return  context.getSharedPreferences(GAME, Context.MODE_PRIVATE).getString(LEVEL, "");
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

    private Heros getHeroId() {
        int heroId = context.getSharedPreferences(GAME, Context.MODE_PRIVATE).getInt("choose_character", 0);
        if(heroId == 1)
            return Heros.TUTTI;

        return Heros.GUAPO;
    }
}
