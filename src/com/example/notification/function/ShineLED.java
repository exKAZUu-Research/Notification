package com.example.notification.function;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.notification.R;

import java.util.ArrayList;

public class ShineLED {

    private ArrayList<String> com;
    private MediaPlayer music;
    private Context context;

    public ShineLED(ArrayList<String> com, Context context) {
        this.com = com;
        this.context = context;
    }

    public void main() {
        music = MediaPlayer.create(context, R.raw.led1);
        //音をならす
        music.setLooping(true); //ループ設定
        music.seekTo(0);    //再生位置を0ミリ秒に設定

        for (int i = 0; i < com.size(); i++) {
            if (com.get(i).equals("ON")) {
                if (!music.isPlaying()) {
                    music.start();      //再生開始
                }
                try { // 1秒待機
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (com.get(i).equals("OFF")) {
                if (music.isPlaying()) {
                    music.pause();
                }
                try { // 1秒待機
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (music.isPlaying()) {
            music.pause();
        }
    }
}
