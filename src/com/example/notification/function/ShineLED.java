package com.example.notification.function;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.notification.R;

import java.util.ArrayList;

public class ShineLED {

    private ArrayList<String> com;
    private MediaPlayer on;
    private MediaPlayer fadein;
    private MediaPlayer fadeout;
    private Context context;

    public ShineLED(ArrayList<String> com, Context context) {
        this.com = com;
        this.context = context;
    }

    public void main() {
        //音をならす
        on = MediaPlayer.create(context, R.raw.led110110part2);
        fadein = MediaPlayer.create(context, R.raw.fadein);
        fadeout = MediaPlayer.create(context, R.raw.fadeout);

        for (int i = 0; i < com.size(); i++) {
            if (com.get(i).equals("ON")) {
                on.seekTo(0);    //再生位置を0ミリ秒に設定
                on.start();      //再生開始
            } else if (com.get(i).equals("OFF")) {
                if (on.isPlaying()) {
                    on.pause();
                } else if (fadein.isPlaying()) {
                    fadein.pause();
                } else if (fadeout.isPlaying()) {
                    fadeout.pause();
                }
            } else if (com.get(i).equals("FadeIn")) {
                fadein.seekTo(0);
                fadein.start();
            } else if (com.get(i).equals("FadeOut")) {
                fadeout.seekTo(0);
                fadeout.start();
            }
            try { // 0.5秒待機
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (on.isPlaying()) {
            on.pause();
        }
    }

    /*public void main() {
        SoundPool sound;
        int soundId;

        sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundId = sound.load(context, R.raw.led110110part2, 0);
        sound.play(soundId, 1.0F, 1.0F,0,0,1.0F);
    }*/
}
