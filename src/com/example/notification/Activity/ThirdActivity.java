package com.example.notification.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.notification.function.DragViewListener;
import com.example.notification.R;

public class ThirdActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        String[][] program = new String[3][15];
        TextView text = (TextView) findViewById(R.id.programs);

        //記述可能部分
        ImageView[][] canwrite = new ImageView[3][15];
        int[][] resc = new int[3][15];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 15; j++) {
                resc[i][j] = this.getResources().getIdentifier("canwrite" + i + "_" + j, "id", this.getPackageName());
                canwrite[i][j] = (ImageView) findViewById(resc[i][j]);
            }
        }
        // 背景たち
        int[][] cellsId = new int[3][15];
        ImageView[][] cells = new ImageView[3][15];
        DragViewListener[][] backgroundlistener = new DragViewListener[3][15];
        int[][] flag = new int[3][15];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 15; j++) {
                flag[i][j] = 0;
                cellsId[i][j] = this.getResources().getIdentifier("image" + i + "_" + j, "id", this.getPackageName());
                cells[i][j] = (ImageView) findViewById(cellsId[i][j]);
                backgroundlistener[i][j] = new DragViewListener(cells[i][j], cells, program, text, flag, cellsId, canwrite);
                cells[i][j].setOnTouchListener(backgroundlistener[i][j]);
            }
        }

        //アイコンたち
        int[] iconsId = new int[22];
        ImageView[] dragView = new ImageView[22];
        DragViewListener[] listener = new DragViewListener[22];
        for (int i = 0; i < 12; i++) {
            iconsId[i] = this.getResources().getIdentifier("imageView" + (i + 1), "id", this.getPackageName());
            dragView[i] = (ImageView) findViewById(iconsId[i]);
            listener[i] = new DragViewListener(dragView[i], cells, program, text, flag, cellsId, canwrite);
            dragView[i].setOnTouchListener(listener[i]);
        }
        for (int i = 12; i < 22; i++) {
            iconsId[i] = this.getResources().getIdentifier("imageView0" + (i - 12), "id", this.getPackageName());
            dragView[i] = (ImageView) findViewById(iconsId[i]);
            listener[i] = new DragViewListener(dragView[i], cells, program, text, flag, cellsId, canwrite);
            dragView[i].setOnTouchListener(listener[i]);
        }

        /*
        android.view.View scrollView = super.findViewById(R.id.ScrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                if(){
                    return true;        //スクロールさせたくないとき
                }else{
                    return false;       //スクロールさせたいとき
                }
            }
        });
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next, menu);
        return true;
    }

}
