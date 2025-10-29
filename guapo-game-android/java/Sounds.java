package com.main.guapogame;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;

import java.util.Random;

public class Sounds {
    public int sound1, sound3;
    public int sound_brownie_appearing;
    public int sound_brownie_hit;
    public int sound_bubbles;
    public int sound_frito_appearing;
    public int sound_frito_hit;
    public int sound_hit_bird;
    public int sound_misty_appearing;
    public int sound_misty_hit;
    public int sound_sun_popup;
    public int sound_tutti_eating_knaagstok;
    public int sound_tutti_eating_pathe;
    public int sound_tutti_eating_tosti;
    public SoundPool soundPool;
    public int max_eat;
    public SharedPreferences prefs;

    public Sounds(Context activity) {
        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);
        initSounds(activity);
    }

    private void initSoundPool() {
        int num_sounds_eating = 3;
        int min_number_of_sound_streams = 7;
        int num_sounds_cat = 2;
        int num_sounds_brownie = 1;
        int num_sounds_sun_popup = 1;
        int num_sounds_bubbles = 1;
        int tot_num_sounds = min_number_of_sound_streams + 1 + num_sounds_eating + num_sounds_cat + num_sounds_brownie + num_sounds_sun_popup + num_sounds_bubbles;
        max_eat = num_sounds_eating - 1;

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(tot_num_sounds)
                .setAudioAttributes(audioAttributes)
                .build();
    }

    public void initSounds(Context game_activity) {
        initSoundPool();

        sound_hit_bird = soundPool.load(game_activity, R.raw.tutti_0, 1);
        sound_misty_hit = soundPool.load(game_activity, R.raw.tutti_1, 1);
        sound_frito_hit = soundPool.load(game_activity, R.raw.tutti_4, 1);
        sound_brownie_hit = soundPool.load(game_activity, R.raw.tutti_5, 1);
        sound_tutti_eating_knaagstok = soundPool.load(game_activity, R.raw.tutti_eating_knaagstok, 1);
        sound_tutti_eating_pathe = soundPool.load(game_activity, R.raw.tuttu_eating_pathe, 1);
        sound_frito_appearing = soundPool.load(game_activity, R.raw.cat_sound1, 1);
        sound_brownie_appearing = soundPool.load(game_activity, R.raw.tutti_6, 1);
        sound_misty_appearing = soundPool.load(game_activity, R.raw.misty_sounds, 1);
        sound_sun_popup = soundPool.load(game_activity, R.raw.sun_popup_sound, 1);
        sound1 = soundPool.load(game_activity, R.raw.tutti_1, 1);
        sound3 = soundPool.load(game_activity, R.raw.tutti_3, 1);
        sound_bubbles = soundPool.load(game_activity, R.raw.bubble_sounds, 1);
        sound_tutti_eating_tosti = soundPool.load(game_activity, R.raw.tutti_eating_tosti, 1);
    }


    public void playSoundEat() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        Random rand = new Random();
        int randomNum = rand.nextInt(max_eat);
        if(!is_mute) {
            switch (randomNum) {
                case 0:
                    soundPool.play(sound_tutti_eating_knaagstok, 1, 1, 0, 0, 1);
                    break;
                case 1:
                    soundPool.play(sound_tutti_eating_pathe, (float) 0.7, (float) 0.7, 0, 0, 1);
                    break;
            }
        }
    }

    public void playSoundBarkHit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_hit_bird, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundCatAppearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_frito_appearing, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBrownieAppearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_brownie_appearing, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundMistyAppearing() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_misty_appearing, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void playSoundSunPopup() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_sun_popup, ((float) 1)/4, ((float) 1)/4, 0, 0, 1);
        }
    }

    public void playSoundBarkFritoHit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_frito_hit, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBarkBrownieHit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_brownie_hit, 1, 1, 0, 0, 1);
        }
    }

    public void playSoundBarkMistyHit() {
        boolean is_mute = prefs.getBoolean("is_mute", false);
        if(!is_mute) {
            soundPool.play(sound_misty_hit, 1, 1, 0, 0, 1);
        }
    }

    void play_sound_bark_animal_hit(CharacterIds char_id) {
        if(char_id == CharacterIds.FRITO) {
            playSoundBarkFritoHit();
        }
        if(char_id == CharacterIds.BROWNIE) {
            playSoundBarkBrownieHit();
        }
        if(char_id == CharacterIds.MISTY || char_id == CharacterIds.MISTY_TOP) {
            playSoundBarkMistyHit();
        }
    }

}
