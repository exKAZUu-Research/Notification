package com.example.notification.function;

import android.media.MediaPlayer;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import com.example.notification.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
                }else{
                    comName = "Ccom";
                }

                //ファイルから読み込む
                try {
                    InputStream in = openFileInput(comName + ".text");
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String s;
                    while ((s = reader.readLine()) != "") {
                        com.add(s);
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    //どうやらここにはいっているらしい。
                    //ふぁいるがひらけていない？？
                }
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
