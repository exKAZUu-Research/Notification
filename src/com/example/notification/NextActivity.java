package com.example.notification;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class NextActivity extends Activity {
    MediaPlayer music;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_next);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.next, menu);
		return true;
	}

    public void MusicStart(View v){
        //音をならす
        music = MediaPlayer.create(this, R.raw.led1);
        music.setLooping(true); //ループ設定
        music.seekTo(0);    //再生位置を0ミリ秒に設定
        music.start();      //再生開始
    }

    public void MusicStop(View v){
        if(music.isPlaying()){
            music.pause();
        }
    }
  	public void onClickFinishButton(View v) {
        if(music.isPlaying()){
            music.pause();
        }
		finish();
	}
}
