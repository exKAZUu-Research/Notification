package com.example.notification.function;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ScrollViewTest extends Activity{
    ScrollView scrollView;
    LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        scrollView = new ScrollView(this);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        for(int i=0; i< 12; i++){
            Button button = new Button(this);
            button.setText("Button" + (i+1));
            linearLayout.addView(button);
        }

        scrollView.addView(linearLayout);

        setContentView(scrollView);
    }
}
