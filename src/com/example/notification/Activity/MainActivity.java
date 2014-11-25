package com.example.notification.Activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notification.function.DragViewListener;
import com.example.notification.function.Parser;
import com.example.notification.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


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

        //ファイルから読み込む
        try {
            Intent intent = getIntent();
            String num = intent.getStringExtra("num");
            System.out.println(num);
            if (num.equals("new")) {
                Intent intent2 = getIntent();
                String number = intent2.getStringExtra("number");
                number = String.valueOf(Integer.parseInt(number) + 1);
                String fileName = "number.text";
                InputStream in = openFileInput("number.text");
                String inputText = number;
                String message = "";
                try {
                    FileOutputStream outStream = openFileOutput(fileName, MODE_PRIVATE);
                    OutputStreamWriter writer = new OutputStreamWriter(outStream);
                    writer.write(inputText);
                    writer.flush();
                    writer.close();
                    message = "File saved.";
                } catch (FileNotFoundException e) {
                    message = e.getMessage();
                    e.printStackTrace();
                } catch (IOException e) {
                    message = e.getMessage();
                    e.printStackTrace();
                }
            } else {
                InputStream in = openFileInput(num + ".text");
                //InputStream in = openFileInput(num + ".text");
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String s;

                for (int j = 0; j < 9; j++) {
                    for (int i = 0; i < 3; i++) {
                        s = reader.readLine();
                        if (s.equals("")) {
                            program[i][j] = "";
                        } else {
                            program[i][j] = s;
                        }
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
                cellsId[i][j] = this.getResources().getIdentifier("image" + i + "_" + j, "id", this.getPackageName());
                cells[i][j] = (ImageView) findViewById(cellsId[i][j]);
                backgroundlistener[i][j] = new DragViewListener(cells[i][j], cells, program, text, flag, cellsId, canwrite);
                cells[i][j].setOnTouchListener(backgroundlistener[i][j]);
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                if (program[i][j] == "Gmail") {
                    cells[i][j].setImageResource(R.drawable.icon_gmail);
                } else if (program[i][j] == "Calendar") {
                    cells[i][j].setImageResource(R.drawable.icon_calender);
                } else if (program[i][j] == "Twitter") {
                    cells[i][j].setImageResource(R.drawable.icon_twitter);
                } else if (program[i][j] == "Facebook") {
                    cells[i][j].setImageResource(R.drawable.icon_fb);
                } else if (program[i][j] == "for") {
                    cells[i][j].setImageResource(R.drawable.icon_loop);
                } else if (program[i][j] == "forend") {
                    cells[i][j].setImageResource(R.drawable.icon_kokomade);
                } else if (program[i][j] == "ON") {
                    cells[i][j].setImageResource(R.drawable.icon_on);
                } else if (program[i][j] == "OFF") {
                    cells[i][j].setImageResource(R.drawable.icon_off);
                } else if (program[i][j] == "if") {
                    cells[i][j].setImageResource(R.drawable.icon_if);
                } else if (program[i][j] == "else") {
                    cells[i][j].setImageResource(R.drawable.icon_else);
                } else if (program[i][j] == "elseif") {
                    cells[i][j].setImageResource(R.drawable.icon_elseif);
                } else if (program[i][j] == "ifend") {
                    cells[i][j].setImageResource(R.drawable.icon_if_kokomade);
                } else if (program[i][j] == "1") {
                    cells[i][j].setImageResource(R.drawable.num1);
                } else if (program[i][j] == "2") {
                    cells[i][j].setImageResource(R.drawable.num2);
                } else if (program[i][j] == "3") {
                    cells[i][j].setImageResource(R.drawable.num3);
                } else if (program[i][j] == "4") {
                    cells[i][j].setImageResource(R.drawable.num4);
                } else if (program[i][j] == "5") {
                    cells[i][j].setImageResource(R.drawable.num5);
                } else if (program[i][j] == "6") {
                    cells[i][j].setImageResource(R.drawable.num6);
                } else if (program[i][j] == "7") {
                    cells[i][j].setImageResource(R.drawable.num7);
                } else if (program[i][j] == "8") {
                    cells[i][j].setImageResource(R.drawable.num8);
                } else if (program[i][j] == "9") {
                    cells[i][j].setImageResource(R.drawable.num9);
                } else if (program[i][j] == "0") {
                    cells[i][j].setImageResource(R.drawable.num0);
                } else {
                    cells[i][j].setImageResource(R.drawable.haikei_kuro);
                }
            }
        }
        // ドラッグ対象Viewとイベント処理クラスを紐付ける
        // アイコンたち
        int[][] iconsId = new int[2][12];
        ImageView[][] dragView = new ImageView[2][12];
        DragViewListener[][] listener = new DragViewListener[2][12];
        for (int i = 0; i < 12; i++) {
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
        Toast.makeText(this, "保存しました。", Toast.LENGTH_SHORT).show();

        //ファイルにプログラムを保存
        String message = "";
        Intent intent = getIntent();
        String num = intent.getStringExtra("num");
        String fileName = num + ".text";
        String inputText = "";
        for (int j = 0; j < 9; j++) {
            for (int i = 0; i < 3; i++) {
                inputText += program[i][j];
                inputText += "\n";
            }
        }
        try {
            FileOutputStream outStream = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outStream);
            writer.write(inputText);
            writer.flush();
            writer.close();

            message = "File saved.";
        } catch (FileNotFoundException e) {
            message = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            message = e.getMessage();
            e.printStackTrace();
        }
    }

    private static boolean checkBeforeWritefile(File file) {
        if (file.exists()) {
            if (file.isFile() && file.canWrite()) {
                return true;
            }
        }
        return false;
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
        if (music.isPlaying()) {
            music.pause();
        }
    }
}