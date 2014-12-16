package com.example.notification.Activity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notification.function.DragViewListener;
import com.example.notification.function.Parser;
import com.example.notification.R;
import com.example.notification.function.ShineLED;

import java.util.ArrayList;

public class MainActivity extends Activity {

    String[][] program = new String[2][12];
    String[] commands = new String[4];
    ArrayList<String> Gcom = new ArrayList<String>();
    ArrayList<String> Ccom = new ArrayList<String>();
    ArrayList<String> Tcom = new ArrayList<String>();
    ArrayList<String> Fcom = new ArrayList<String>();
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 右側のテキストたち
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 2; j++) {
                program[j][i] = "";
            }
        }

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        System.out.println(name);

        //データベースからプログラムを読み込む
        String str = "data/data/" + getPackageName() + "/Sample.db";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);

        String qry1 = "CREATE TABLE " + name + " (id INTEGER PRIMARY KEY, text STRING)";
        String qry3 = "SELECT * FROM " + name;

        //テーブルの作成
        try {
            db.execSQL(qry1);
        } catch (SQLException e) {
            Log.e("ERROR", e.toString());
        }

        //データの検索
        Cursor cr = db.rawQuery(qry3, null);
        //startManagingCursor(cr);

        int x = 0;
        int y = 0;
        while (cr.moveToNext()) {
            int t = cr.getColumnIndex("text");
            String text = cr.getString(t);
            if (text.equals("")) {
                program[x][y] = "";
            } else {
                program[x][y] = text;
            }
            x++;
            y++;
            if (x == 2) {
                x = 0;
            } else {
                y--;
            }
        }

        //db.close();

        //textviewに表示
        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(program[0][0] + program[1][0] + "\n"
                + program[0][1] + program[1][1] + "\n"
                + program[0][2] + program[1][2] + "\n"
                + program[0][3] + program[1][3] + "\n"
                + program[0][4] + program[1][4] + "\n"
                + program[0][5] + program[1][5] + "\n"
                + program[0][6] + program[1][6] + "\n"
                + program[0][7] + program[1][7] + "\n"
                + program[0][8] + program[1][8] + "\n"
                + program[0][9] + program[1][9] + "\n"
                + program[0][10] + program[1][10] + "\n"
                + program[0][11] + program[1][11]);

        //記述可能部分
        ImageView[][] canwrite = new ImageView[2][12];
        int[][] resc = new int[2][12];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                resc[i][j] = this.getResources().getIdentifier("canwrite" + i + "_" + j, "id", this.getPackageName());
                canwrite[i][j] = (ImageView) findViewById(resc[i][j]);
            }
        }

        // 背景たち
        int[][] cellsId = new int[2][12];
        ImageView[][] cells = new ImageView[2][12];
        DragViewListener[][] backgroundlistener = new DragViewListener[2][12];
        int[][] flag = new int[2][12];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                flag[i][j] = 0;
                cellsId[i][j] = this.getResources().getIdentifier("image" + i + "_" + j, "id", this.getPackageName());
                cells[i][j] = (ImageView) findViewById(cellsId[i][j]);
                backgroundlistener[i][j] = new DragViewListener(cells[i][j], cells, program, text, flag, cellsId, canwrite);
                cells[i][j].setOnTouchListener(backgroundlistener[i][j]);
            }
        }

        //保存されていたものをアイコンに戻す用
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 12; j++) {
                if (program[i][j].equals("Gmail")) {
                    cells[i][j].setImageResource(R.drawable.icon_gmail);
                } else if (program[i][j].equals("Calendar")) {
                    cells[i][j].setImageResource(R.drawable.icon_calender);
                } else if (program[i][j].equals("Twitter")) {
                    cells[i][j].setImageResource(R.drawable.icon_twitter);
                } else if (program[i][j].equals("Facebook")) {
                    cells[i][j].setImageResource(R.drawable.icon_fb);
                } else if (program[i][j].equals("for")) {
                    cells[i][j].setImageResource(R.drawable.icon_loop);
                } else if (program[i][j].equals("forend")) {
                    cells[i][j].setImageResource(R.drawable.icon_kokomade);
                } else if (program[i][j].equals("ON")) {
                    cells[i][j].setImageResource(R.drawable.icon_on);
                } else if (program[i][j].equals("OFF")) {
                    cells[i][j].setImageResource(R.drawable.icon_off);
                } else if (program[i][j].equals("FadeIn")) {
                    cells[i][j].setImageResource(R.drawable.icon_fadein);
                } else if (program[i][j].equals("FadeOut")) {
                    cells[i][j].setImageResource(R.drawable.icon_fadeout);
                } else if (program[i][j].equals("if")) {
                    cells[i][j].setImageResource(R.drawable.icon_if);
                } else if (program[i][j].equals("else")) {
                    cells[i][j].setImageResource(R.drawable.icon_else);
                } else if (program[i][j].equals("elseif")) {
                    cells[i][j].setImageResource(R.drawable.icon_elseif);
                } else if (program[i][j].equals("ifend")) {
                    cells[i][j].setImageResource(R.drawable.icon_if_kokomade);
                } else if (program[i][j].equals("1")) {
                    cells[i][j].setImageResource(R.drawable.num1);
                } else if (program[i][j].equals("2")) {
                    cells[i][j].setImageResource(R.drawable.num2);
                } else if (program[i][j].equals("3")) {
                    cells[i][j].setImageResource(R.drawable.num3);
                } else if (program[i][j].equals("4")) {
                    cells[i][j].setImageResource(R.drawable.num4);
                } else if (program[i][j].equals("5")) {
                    cells[i][j].setImageResource(R.drawable.num5);
                } else if (program[i][j].equals("6")) {
                    cells[i][j].setImageResource(R.drawable.num6);
                } else if (program[i][j].equals("7")) {
                    cells[i][j].setImageResource(R.drawable.num7);
                } else if (program[i][j].equals("8")) {
                    cells[i][j].setImageResource(R.drawable.num8);
                } else if (program[i][j].equals("9")) {
                    cells[i][j].setImageResource(R.drawable.num9);
                } else {
                    cells[i][j].setImageResource(R.drawable.haikei_kuro);
                }
            }
        }

        //次の入力場所の表示
        for (int j = 0; j < 12; j++) {
            int f = 0;
            for (int i = 0; i < 2; i++) {
                if (program[i][j] == "") {
                    if (f == 0) {
                        canwrite[i][j].setImageResource(R.drawable.haikei);
                        f = 1;
                    } else {
                        canwrite[i][j].setImageResource(R.drawable.haikei_kuro);
                    }
                } else {
                    canwrite[i][j].setImageResource(R.drawable.haikei_kuro);
                }
            }
        }

        //2列目以降入力可能にするかどうかの変更
        for (int j = 0; j < 12; j++) {
            if (!program[0][j].equals("") && !program[0][j].equals("if") && !program[0][j].equals("elseif") && !program[0][j].equals("for")) {
                canwrite[1][j].setImageResource(R.drawable.haikei_kuro);
            }
        }


        // ドラッグ対象Viewとイベント処理クラスを紐付ける
        // アイコンたち
        int[][] iconsId = new int[2][14];
        ImageView[][] dragView = new ImageView[2][14];
        DragViewListener[][] listener = new DragViewListener[2][14];
        for (int i = 0; i < 14; i++) {
            iconsId[0][i] = this.getResources().getIdentifier("imageView" + (i + 1), "id", this.getPackageName());
            dragView[0][i] = (ImageView) findViewById(iconsId[0][i]);
            listener[0][i] = new DragViewListener(dragView[0][i], cells, program, text, flag, cellsId, canwrite);
            dragView[0][i].setOnTouchListener(listener[0][i]);
        }
        for (int i = 1; i < 10; i++) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.helpInMenu:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //保存ボタン
    public void onClickSaveButton(View v) {
        ArrayList<String> oldcommands = new ArrayList<String>();
        for (int j = 0; j < 12; j++) {
            for (int i = 0; i < 2; i++) {
                if (program[i][j] != "") {
                    oldcommands.add(program[i][j]);
                }
            }
        }
        Parser parser = new Parser(oldcommands, commands, Gcom, Ccom, Tcom, Fcom);
        parser.main();
        if (!parser.isErr()) {

            //データベースにプログラムを保存
            String str = "data/data/" + getPackageName() + "/Sample.db";
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);
            String delete = "DELETE FROM " + name;
            db.execSQL(delete);

            for (int j = 0; j < 12; j++) {
                for (int i = 0; i < 2; i++) {
                    String qry2 = "INSERT INTO " + name + "(text) VALUES('" + program[i][j] + "')";
                    db.execSQL(qry2);
                }
            }
            //db.close();

            Toast.makeText(this, "保存しました。", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "文法がどこか違うよ", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickReturnButton(View v) {
        finish();
    }

    public void onClickGmailButton(View v) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_gmail, null);

        Toast gtoast = new Toast(getApplicationContext());
        gtoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        gtoast.setView(layout);
        gtoast.show();

        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(commands[0]);
        ShineLED LED = new ShineLED(Gcom, this);
        LED.main();
    }

    public void onClickCalendarButton(View v) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_calendar, null);

        Toast gtoast = new Toast(getApplicationContext());
        gtoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        gtoast.setView(layout);
        gtoast.show();

        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(commands[1]);
        ShineLED LED = new ShineLED(Ccom, this);
        LED.main();
    }

    public void onClickTwitterButton(View v) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_twitter, null);

        Toast gtoast = new Toast(getApplicationContext());
        gtoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        gtoast.setView(layout);
        gtoast.show();

        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(commands[2]);
        ShineLED LED = new ShineLED(Tcom, this);
        LED.main();
    }

    public void onClickFacebookButton(View v) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_facebook, null);

        Toast gtoast = new Toast(getApplicationContext());
        gtoast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        gtoast.setView(layout);
        gtoast.show();

        TextView text = (TextView) findViewById(R.id.programs);
        text.setText(commands[3]);
        ShineLED LED = new ShineLED(Fcom, this);
        LED.main();
    }

}