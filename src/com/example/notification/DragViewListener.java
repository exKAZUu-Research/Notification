package com.example.notification;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class DragViewListener implements OnTouchListener {
	private ImageView dragView;
	private ImageView[][] cells;
	private int oldx;
	private int oldy;

	public DragViewListener(ImageView dragView, ImageView[][] cells) {
		this.dragView = dragView;
		this.cells = cells;
	}

	public boolean onTouch(View view, MotionEvent event) {
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();

		int left = dragView.getLeft() + (x - oldx);
		int top = dragView.getTop() + (y - oldy);
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			dragView.layout(left, top, left + dragView.getWidth(), top
					+ dragView.getHeight());
			break;
		case MotionEvent.ACTION_UP:
			int x_index = left / cells[0][0].getWidth();
			int y_index = top / cells[0][0].getHeight();
			if (0 <= x_index && x_index <= 2 && 0 <= y_index && y_index <= 9) {
				if (view.getId() == R.id.imageView1) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_right_hand_up);
				} else if (view.getId() == R.id.imageView2) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_right_hand_down);
				} else if (view.getId() == R.id.imageView3) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_left_hand_up);
				} else if (view.getId() == R.id.imageView4) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_left_hand_down);
				} else if (view.getId() == R.id.imageView5) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_loop);
				} else if (view.getId() == R.id.imageView6) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_kokomade);
				} else if (view.getId() == R.id.imageView7) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_yellow);
				} else if (view.getId() == R.id.imageView8) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_orange);
				} else if (view.getId() == R.id.imageView9) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_if);
				} else if (view.getId() == R.id.imageView10) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_else);
				} else if (view.getId() == R.id.imageView11) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_if_kokomade);
				} else if (view.getId() == R.id.imageGomi) {
					cells[x_index][y_index]
							.setImageResource(R.drawable.icon_if);
				}

			} else {
				// 初期状態に戻しておきたい
			}
		}

		oldx = x;
		oldy = y;
		return true;
	}
}