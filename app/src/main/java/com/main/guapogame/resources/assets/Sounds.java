package com.main.guapogame.resources.assets;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import com.main.guapogame.R;
import com.main.guapogame.state.GameState;

import java.security.SecureRandom;

public class Sounds {
    private static SoundPool soundPool;
    private static int tuttiEatingKnaagstok;
    private static int tuttiEatingPathe;
    private static int tuttiEatingTosti;
    private static int bubbleSounds;
    private static int barkHit;
    private static int catAppearing;
    private static int brownieAppearing;
    private static int mistyAppearing;
    private static int sunPopUp;
    private static int fritoHit;
    private static int brownieHit;
    private static int mistyHit;
    private static final SecureRandom random = new SecureRandom();

    public static void playBubbles() {
        soundPool.play(bubbleSounds, 1, 1, 0, 0, 1);
    }

    public static void playSoundEat() {
        if(!GameState.getMute()) {
            int randomNum = random.nextInt(3);
            if(randomNum == 0)
                soundPool.play(tuttiEatingKnaagstok, 1, 1, 0, 0, 1);
            if(randomNum == 1)
                soundPool.play(tuttiEatingPathe, (float) 0.7, (float) 0.7, 0, 0, 1);
            if(randomNum == 2)
                soundPool.play(tuttiEatingTosti, (float) 0.7, (float) 0.7, 0, 0, 1);
        }
    }

    public static void playSoundBarkHit() {
        if(!GameState.getMute()) {
            soundPool.play(barkHit, 1, 1, 0, 0, 1);
        }
    }

    public static void playSoundCatAppearing() {
        if(!GameState.getMute()) {
            soundPool.play(catAppearing, 1, 1, 0, 0, 1);
        }
    }

    public static void playSoundBrownieAppearing() {
        if(!GameState.getMute()) {
            soundPool.play(brownieAppearing, 1, 1, 0, 0, 1);
        }
    }

    public static void playSoundMistyAppearing() {
        if(!GameState.getMute()) {
            soundPool.play(mistyAppearing, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public static void playSoundSunPopup() {
        if(!GameState.getMute()) {
            soundPool.play(sunPopUp, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public static void playSoundBarkFritoHit() {
        if(!GameState.getMute()) {
            soundPool.play(fritoHit, 1, 1, 0, 0, 1);
        }
    }

    public static void playSoundBarkBrownieHit() {
        if(!GameState.getMute()) {
            soundPool.play(brownieHit, 1, 1, 0, 0, 1);
        }
    }

    public static void playSoundBarkMistyHit() {
        if(!GameState.getMute()) {
            soundPool.play(mistyHit, 1, 1, 0, 0, 1);
        }
    }

    public static void playSoundCheckpoint() {
        if(!GameState.getMute()) {
            soundPool.play(sunPopUp, 1, 1, 0, 0, 1);
        }
    }

    public static void createSoundPool(Context context) {
        AudioAttributes audioAttributes = createAudioAttributes();
        soundPool = createSoundPool(audioAttributes);

        loadSounds(context);
    }

    private static void loadSounds(Context context) {
        tuttiEatingKnaagstok = soundPool.load(context, R.raw.tutti_eating_knaagstok, 0);
        tuttiEatingPathe = soundPool.load(context, R.raw.tuttu_eating_pathe, 0);
        tuttiEatingTosti = soundPool.load(context, R.raw.tutti_eating_tosti, 0);
        bubbleSounds = soundPool.load(context, R.raw.bubble_sounds, 0);
        barkHit = soundPool.load(context, R.raw.tutti_0, 0);
        catAppearing = soundPool.load(context, R.raw.cat_sound1, 0);
        brownieAppearing = soundPool.load(context, R.raw.tutti_6, 0);
        mistyAppearing = soundPool.load(context, R.raw.misty_sounds, 0);
        sunPopUp = soundPool.load(context, R.raw.sun_popup_sound, 0);
        fritoHit = soundPool.load(context, R.raw.tutti_4, 0);
        brownieHit = soundPool.load(context, R.raw.tutti_5, 0);
        mistyHit = soundPool.load(context, R.raw.tutti_1, 0);
    }

    public static void releaseSoundPool() {
        soundPool.release();
    }

    private static SoundPool createSoundPool(AudioAttributes audioAttributes) {
        return new SoundPool.Builder()
                .setMaxStreams(15)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    private static AudioAttributes createAudioAttributes() {
        return new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();
    }
    private Sounds() {}
}
