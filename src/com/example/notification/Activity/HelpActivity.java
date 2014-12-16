package com.example.notification.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notification.R;

public class HelpActivity extends Activity {

    private int pageNumber;
    private int[] Image = {R.drawable.help1, R.drawable.help2, R.drawable.help3, R.drawable.help4, R.drawable.help5,
            R.drawable.help6, R.drawable.help7, R.drawable.help88, R.drawable.help99, R.drawable.help10, R.drawable.help11};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        pageNumber = 1;
        ImageView helpImage = (ImageView) findViewById(R.id.helpImage);
        helpImage.setImageResource(R.drawable.help1);
        TextView page = (TextView) findViewById(R.id.page);
        page.setText("1 / 11");
    }

    public void onClickNext(View view) {
        if (pageNumber == 11) {
            pageNumber = 0;
        }
        pageNumber++;
        setHelpImage();
    }

    public void onClickBack(View view) {
        if (pageNumber == 1) {
            pageNumber = 12;
        }
        pageNumber--;
        setHelpImage();
    }

    public void onClickClose(View view) {
        finish();
    }

    public void setHelpImage() {
        ImageView helpImage = (ImageView) findViewById(R.id.helpImage);
        helpImage.setImageResource(Image[pageNumber - 1]);

        TextView page = (TextView) findViewById(R.id.page);
        page.setText(pageNumber + " / 11");
    }

    public void onClickStartButton(View v) {
        finish();
        Intent intent = new Intent(this, SelectActivity.class);
        startActivity(intent);
    }
}