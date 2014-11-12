package com.example.notification;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Scanner;

//import android.widget.TextView;

public class MainActivity extends Activity {

    String[][] program = new String[3][9];
    String[] commands = new String[4];
    String[][] newcoms;
    ArrayList<String> Gcom = new ArrayList<String>();
    ArrayList<String> Ccom = new ArrayList<String>();
    ArrayList<String> Tcom = new ArrayList<String>();
    ArrayList<String> Fcom = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 右側のテキストたち

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 3; j++) {
                program[j][i] = "";
            }
        }

        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(program[0][0] + program[1][0] + program[2][0] + "\n"
                + program[0][1] + program[1][1] + program[2][1] + "\n"
                + program[0][2] + program[1][2] + program[2][2] + "\n"
                + program[0][3] + program[1][3] + program[2][3] + "\n"
                + program[0][4] + program[1][4] + program[2][4] + "\n"
                + program[0][5] + program[1][5] + program[2][5] + "\n"
                + program[0][6] + program[1][6] + program[2][6] + "\n"
                + program[0][7] + program[1][7] + program[2][7] + "\n"
                + program[0][8] + program[1][8] + program[2][8]);

        //記述可能部分
        ImageView[][] canwrite = new ImageView[3][9];
        int[][] resc = new int[3][9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                resc[i][j] = this.getResources().getIdentifier("canwrite" + i + "_" + j, "id", this.getPackageName());
                canwrite[i][j] = (ImageView) findViewById(resc[i][j]);
            }
        }

        // 背景たち
        int[][] cellsId = new int[3][9];
        ImageView[][] cells = new ImageView[3][9];
        DragViewListener[][] backgroundlistener = new DragViewListener[3][9];
        int[][] flag = new int[3][9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                flag[i][j] = 0;
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                cellsId[i][j] = this.getResources().getIdentifier("image" + i + "_" + j, "id", this.getPackageName());
                cells[i][j] = (ImageView) findViewById(cellsId[i][j]);
                backgroundlistener[i][j] = new DragViewListener(cells[i][j], cells, program, text, flag, cellsId, canwrite);
                cells[i][j].setOnTouchListener(backgroundlistener[i][j]);
            }
        }

        // ドラッグ対象Viewとイベント処理クラスを紐付ける
        // アイコンたち
        int[][] iconsId = new int[2][11];
        ImageView[][] dragView = new ImageView[2][11];
        DragViewListener[][] listener = new DragViewListener[2][11];
        for (int i = 0; i < 11; i++) {
            iconsId[0][i] = this.getResources().getIdentifier("imageView" + (i + 1), "id", this.getPackageName());
            dragView[0][i] = (ImageView) findViewById(iconsId[0][i]);
            listener[0][i] = new DragViewListener(dragView[0][i], cells, program, text, flag, cellsId, canwrite);
            dragView[0][i].setOnTouchListener(listener[0][i]);
        }
        for (int i = 0; i < 10; i++) {
            iconsId[1][i] = this.getResources().getIdentifier("imageView0" + i, "id", this.getPackageName());
            dragView[1][i] = (ImageView) findViewById(iconsId[1][i]);
            listener[1][i] = new DragViewListener(dragView[1][i], cells, program, text, flag, cellsId, canwrite);
            dragView[1][i].setOnTouchListener(listener[1][i]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    String html = "";

    //nextボタンの動作
    public void onClickNextButton(View v) {     //画面遷移
        Intent intent = new Intent(this, NextActivity.class);
        startActivity(intent);
    }

    public void onClickStartButton(View v) {
        MediaPlayer music;
        music = MediaPlayer.create(this, R.raw.led1);
        for (int i = 0; i < 9; i++) {
            if (program[0][i] == "黄色") {
                //音をならす
                music.setLooping(true); //ループ設定
                music.seekTo(0);    //再生位置を0ミリ秒に設定
                music.start();      //再生開始
            } else {
                if (music.isPlaying()) {
                    music.pause();  //音をとめる
                }
            }

            try { // 1秒待機
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (music.isPlaying()) {
            music.pause();
        }
    }

    public void onClickSaveButton(View v) {
        ArrayList<String> oldcommands = new ArrayList<String>();
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 3; i++) {
                if (program[i][j] != "") {
                    oldcommands.add(program[i][j]);
                }
            }
        }
        Parser parser = new Parser(oldcommands, commands, Gcom, Ccom, Tcom, Fcom);
        parser.main();
    }

    public void onClickGmailButton(View v) {
        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(commands[0]);
        LED(Gcom);
    }

    public void onClickCalendarButton(View v) {
        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(commands[1]);
        LED(Ccom);
    }

    public void onClickTwitterButton(View v) {
        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(commands[2]);
        LED(Tcom);
    }

    public void onClickFacebookButton(View v) {
        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(commands[3]);
        LED(Fcom);
    }

    public void LED(ArrayList<String> com) {
        MediaPlayer music;
        music = MediaPlayer.create(this, R.raw.led1);
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
    }
}