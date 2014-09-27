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
			if (0 <= x_index && x_index <= 2 && 0 <= y_index && y_index <= 1) {
				cells[y_index][x_index]
						.setImageResource(R.drawable.icon_right_hand_up);
			}
		}

		oldx = x;
		oldy = y;

		return true;
	}

}
