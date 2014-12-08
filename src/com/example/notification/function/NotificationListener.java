package com.example.notification.function;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class NotificationListener extends NotificationListenerService {

    private Handler handler = new Handler();
    private String comName;

    // [1]
    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        final String msg = "追加:pkg=" + sbn.getPackageName();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                ArrayList<String> com = new ArrayList<String>();
                if (sbn.getPackageName().equals("jp.mynavi.notification.android.notificationsample")) {
                    System.out.println("音なるはず");
                    comName = "Gcom";
                } else {
                    comName = "Ccom";
                }

                //データベースから読み込む
                String str = "data/data/" + getPackageName() + "/Sample.db";
                SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(str, null);

                String qry1 = "CREATE TABLE " + comName + " (id INTEGER PRIMARY KEY, text STRING)";
                String qry3 = "SELECT * FROM " + comName;

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
                    com.add(text);
                }
                //db.close();

                if (comName.equals("Gcom") || comName.equals("Ccom") || comName.equals("Tcom") || comName.equals("Fcom")) {
                    ShineLED LED = new ShineLED(com, getApplicationContext());
                    LED.main();
                }
                System.out.println(msg);
            }
        });
    }

    // [2]
    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        final String msg = "削除:pkg=" + sbn.getPackageName();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
