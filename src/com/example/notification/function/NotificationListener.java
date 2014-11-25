package com.example.notification.function;

import android.app.Activity;
import android.os.Handler;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notification.R;

public class NotificationListener extends NotificationListenerService {

    private Handler handler =  new Handler();

    private Activity activity;
    public NotificationListener(Activity activity){
        this.activity = activity;
    }

    TextView textview = (TextView)activity.findViewById(R.id.text_message);

    // [1]
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        final String msg = "追加:pkg=" + sbn.getPackageName();
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                textview.setText(msg);
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
                textview.setText(msg);
            }
        });
    }
}
