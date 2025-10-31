package com.main.guapogame;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.Random;

public class Sounds {
    private SoundPool soundPool;
    private final boolean isMute;
    private int soundHitVillain;
    private int tuttiEatingKnaagstok;
    private int tuttiEatingPathe;
    private int tuttiEatingTosti;
    private int bubbleSounds;
    private int barkHit;
    private int catAppearing;
    private int brownieAppearing;
    private int mistyAppearing;
    private int sunPopUp;
    private int fritoHit;
    private int brownieHit;
    private int mistyHit;

    public Sounds(Context activity) {
        SharedPreferences prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        isMute = prefs.getBoolean("is_mute", false);
        createSoundPool(activity);
    }

    private void createSoundPool(Context context) {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(30)
                .setAudioAttributes(audioAttributes)
                .build();

        loadSounds(context);
    }

    private void loadSounds(Context context) {
        soundHitVillain = soundPool.load(context, R.raw.tutti_0, 0);
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

    public void playBubbles() {
        soundPool.play(bubbleSounds, 1, 1, 0, 0, 1);
    }

    public void playSoundEat() {
        Random rand = new Random();
        int randomNum = rand.nextInt(3);
        if(!isMute) {
            switch (randomNum) {
                case 0:
                    soundPool.play(tuttiEatingKnaagstok, 1, 1, 0, 0, 1);
                    break;
                case 1:
                    soundPool.play(tuttiEatingPathe, (float) 0.7, (float) 0.7, 0, 0, 1);
                    break;
                case 2:
                    soundPool.play(tuttiEatingTosti, (float) 0.7, (float) 0.7, 0, 0, 1);
                    break;
            }
        }
    }

    public void playSoundBarkHit() {
        if(!isMute) {
            soundPool.play(barkHit, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundCatAppearing() {
        if(!isMute) {
            soundPool.play(catAppearing, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBrownieAppearing() {
        if(!isMute) {
            soundPool.play(brownieAppearing, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundMistyAppearing() {
        if(!isMute) {
            soundPool.play(mistyAppearing, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void playSoundSunPopup() {
        if(!isMute) {
            soundPool.play(sunPopUp, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void playSoundBarkFritoHit() {
        if(!isMute) {
            soundPool.play(fritoHit, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBarkBrownieHit() {
        if(!isMute) {
            soundPool.play(brownieHit, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBarkMistyHit() {
        if(!isMute) {
            soundPool.play(mistyHit, 1, 1, 0, 0, 1);
        }
    }
}
