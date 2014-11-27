package com.example.notification.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.notification.R;

public class HelpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onClickStartButton(View v) {
        finish();
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }

}

