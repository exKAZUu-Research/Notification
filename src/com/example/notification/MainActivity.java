package com.example.notification;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ドラッグ対象Viewとイベント処理クラスを紐付ける
		ImageView dragView = (ImageView) findViewById(R.id.imageView1);
		ImageView cell = (ImageView) findViewById(R.id.image1_1);

		ImageView[][] cells = new ImageView[2][3];

		cells[0][0] = (ImageView) findViewById(R.id.image1_1);
		cells[0][1] = (ImageView) findViewById(R.id.image1_2);
		cells[0][2] = (ImageView) findViewById(R.id.image1_3);
		cells[1][0] = (ImageView) findViewById(R.id.image2_1);
		cells[1][1] = (ImageView) findViewById(R.id.image2_2);
		cells[1][2] = (ImageView) findViewById(R.id.image2_3);
		

		DragViewListener listener = new DragViewListener(dragView, cells);
		dragView.setOnTouchListener(listener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	String html = "";

	public void onButtonClick1(View v) {
		ImageGetterImpl imageGetter = new ImageGetterImpl(
				getApplicationContext());
		TextView tv = (TextView) findViewById(R.id.textview1);
		html += getResources().getString(R.string.imgstring1);
		Spanned words = Html.fromHtml(html, imageGetter, null);
		tv.setText(words);
	}

	public void onButtonClick2(View v) {
		ImageGetterImpl imageGetter = new ImageGetterImpl(
				getApplicationContext());
		TextView tv = (TextView) findViewById(R.id.textview1);
		html += getResources().getString(R.string.imgstring2);
		Spanned words = Html.fromHtml(html, imageGetter, null);
		tv.setText(words);
	}
}
