package com.example.notification.function;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notification.R;

public class DragViewListener implements OnTouchListener {
    private ImageView dragView;
    private ImageView[][] cells;
    private String[][] program;
    private TextView text;
    private int[][] flag;
    private int[][] cellsId;
    private ImageView[][] canwrite;

    private int oldx;
    private int oldy;

    public DragViewListener(ImageView dragView, ImageView[][] cells,
                            String[][] program, TextView text, int[][] flag, int[][] cellsId, ImageView[][] canwrite) {
        this.dragView = dragView;
        this.cells = cells;
        this.program = program;
        this.text = text;
        this.flag = flag;
        this.cellsId = cellsId;
        this.canwrite = canwrite;
    }

    public boolean onTouch(View view, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        int left = dragView.getLeft() + (x - oldx);
        int top = dragView.getTop() + (y - oldy);

        //Nexus7 のサイズ800×1205
        int x_index = (800 - left) / cells[0][0].getWidth();
        int y_index = top / cells[0][0].getHeight();

        int test = 3 / 5;
        System.out.println(test);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                dragView.layout(left, top, left + dragView.getWidth(), top
                        + dragView.getHeight());
                break;
            case MotionEvent.ACTION_UP:
                if (2 <= x_index && x_index <= 4 && 0 <= y_index && y_index <= 11) {
                    x_index = x_index - 2;
                    if (x_index == 2) {
                        x_index = 0;
                    } else {
                        x_index = 1;
                    }
                    if (x_index == 1 && !program[0][y_index].equals("for") && !program[0][y_index].equals("if") && !program[0][y_index].equals("elseif")) {
                    } else {
                        if (view.getId() == R.id.imageView1) {
                            program[x_index][y_index] = "Gmail";
                        } else if (view.getId() == R.id.imageView2) {
                            program[x_index][y_index] = "Calendar";
                        } else if (view.getId() == R.id.imageView3) {
                            program[x_index][y_index] = "Twitter";
                        } else if (view.getId() == R.id.imageView4) {
                            program[x_index][y_index] = "Facebook";
                        } else if (view.getId() == R.id.imageView5) {
                            program[x_index][y_index] = "for";
                        } else if (view.getId() == R.id.imageView6) {
                            program[x_index][y_index] = "forend";
                        } else if (view.getId() == R.id.imageView7) {
                            program[x_index][y_index] = "ON";
                        } else if (view.getId() == R.id.imageView8) {
                            program[x_index][y_index] = "OFF";
                        } else if (view.getId() == R.id.imageView9) {
                            program[x_index][y_index] = "if";
                        } else if (view.getId() == R.id.imageView10) {
                            program[x_index][y_index] = "else";
                        } else if (view.getId() == R.id.imageView11) {
                            program[x_index][y_index] = "elseif";
                        } else if (view.getId() == R.id.imageView12) {
                            program[x_index][y_index] = "ifend";
                        } else if (view.getId() == R.id.imageView13) {
                            program[x_index][y_index] = "FadeIn";
                        } else if (view.getId() == R.id.imageView14) {
                            program[x_index][y_index] = "FadeOut";
                        } else if (view.getId() == R.id.imageView01) {
                            program[x_index][y_index] = "1";
                        } else if (view.getId() == R.id.imageView02) {
                            program[x_index][y_index] = "2";
                        } else if (view.getId() == R.id.imageView03) {
                            program[x_index][y_index] = "3";
                        } else if (view.getId() == R.id.imageView04) {
                            program[x_index][y_index] = "4";
                        } else if (view.getId() == R.id.imageView05) {
                            program[x_index][y_index] = "5";
                        } else if (view.getId() == R.id.imageView06) {
                            program[x_index][y_index] = "6";
                        } else if (view.getId() == R.id.imageView07) {
                            program[x_index][y_index] = "7";
                        } else if (view.getId() == R.id.imageView08) {
                            program[x_index][y_index] = "8";
                        } else if (view.getId() == R.id.imageView09) {
                            program[x_index][y_index] = "9";
                        } else {
                            for (int i = 0; i < 2; i++) {
                                for (int j = 0; j < 12; j++) {
                                    if (view.getId() == cellsId[i][j]) {
                                        if (program[i][j] != "") {
                                            if (x_index == i && y_index == j) {
                                            } else {
                                                program[x_index][y_index] = program[i][j];
                                                program[i][j] = "";
                                                break;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                } else if (x_index <= 2 && 10 <= y_index && y_index <= 11) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 12; j++) {
                            if (view.getId() == cellsId[i][j]) {
                                program[i][j] = "";
                            }
                        }
                    }
                } else {
                    //初期状態に戻しておきたい
                }

                for (int j = 0; j < 12; j++) {
                    for (int count = 0; count < 1; count++) {
                        for (int i = 0; i < 1; i++) {
                            if (program[i][j] == "") {
                                program[i][j] = program[i + 1][j];
                                program[i + 1][j] = "";
                            }
                        }
                    }
                }
                //アイコンに変更
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
                        } else if (program[i][j].equals("0")) {
                            cells[i][j].setImageResource(R.drawable.num0);
                        } else {
                            cells[i][j].setImageResource(R.drawable.haikei_kuro);
                        }
                    }
                }

                //次の入力場所の表示
                for (int j = 0; j < 12; j++) {
                    int flag = 0;
                    for (int i = 0; i < 2; i++) {
                        if (program[i][j].equals("")) {
                            if (flag == 0) {
                                canwrite[i][j].setImageResource(R.drawable.haikei);
                                flag = 1;
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
        }

        oldx = x;
        oldy = y;
        return true;
    }
}