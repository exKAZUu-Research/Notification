package com.example.notification.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.notification.Activity.HelpActivity;
import com.example.notification.Activity.MainActivity;
import com.example.notification.R;

public class TitleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
    }

    public void onClickStartButton(View v) {
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

    public void onClickHelpButton(View v) {
        Intent intent = new Intent(this, HelpActivity.class);
        startActivity(intent);
    }
}
