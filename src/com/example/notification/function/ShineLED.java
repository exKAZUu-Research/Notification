package com.example.notification.function;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.notification.R;

import java.util.ArrayList;

public class ShineLED {

    private ArrayList<String> com;
    private MediaPlayer on;
    private Context context;

    public ShineLED(ArrayList<String> com, Context context) {
        this.com = com;
        this.context = context;
    }

    public void main() {
        //音をならす
        on = MediaPlayer.create(context, R.raw.led110110part2);

        for (int i = 0; i < com.size(); i++) {
            if (com.get(i).equals("ON")) {
                on.seekTo(0);    //再生位置を0ミリ秒に設定
                on.start();      //再生開始
                try { // 0.5秒待機
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else if (com.get(i).equals("OFF")) {
                if (on.isPlaying()) {
                    on.pause();
                }
                try { // 0.5秒待機
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (on.isPlaying()) {
            on.pause();
        }
    }
}
