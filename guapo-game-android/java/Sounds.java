package com.main.guapogame;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.Random;

public class Sounds {
    private SoundPool soundPool;
    private SharedPreferences prefs;
    private boolean isMute;

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
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
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
        soundHitVillain = soundPool.load(context, R.raw.tutti_0, 1);
        tuttiEatingKnaagstok = soundPool.load(context, R.raw.tutti_eating_knaagstok, 1);
        tuttiEatingPathe = soundPool.load(context, R.raw.tuttu_eating_pathe, 1);
        tuttiEatingTosti = soundPool.load(context, R.raw.tutti_eating_tosti, 1);
        bubbleSounds = soundPool.load(context, R.raw.bubble_sounds, 1);
        barkHit = soundPool.load(context, R.raw.tutti_0, 1);
        catAppearing = soundPool.load(context, R.raw.cat_sound1, 1);
        brownieAppearing = soundPool.load(context, R.raw.tutti_6, 1);
        mistyAppearing = soundPool.load(context, R.raw.misty_sounds, 1);
        sunPopUp = soundPool.load(context, R.raw.sun_popup_sound, 1);
        fritoHit = soundPool.load(context, R.raw.tutti_4, 1);
        brownieHit = soundPool.load(context, R.raw.tutti_5, 1);
        mistyHit = soundPool.load(context, R.raw.tutti_1, 1);
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
