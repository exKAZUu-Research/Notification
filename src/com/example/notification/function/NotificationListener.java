package com.example.notification.function;

import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

public class NotificationListener extends NotificationListenerService {

    private Handler handler = new Handler();

    // [1]
    @Override
    public void onNotificationPosted(final StatusBarNotification sbn) {
        final String msg = "追加:pkg=" + sbn.getPackageName();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                if(sbn.getPackageName() == "jp.mynavi.notification.android.notificationsample"){
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
