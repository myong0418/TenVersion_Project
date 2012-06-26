package com.smartschool.tenversion;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class FlipperView extends Activity implements View.OnTouchListener {
	private static final String TAG = "TestFlipperviewActivity";
	ViewFlipper flipper;

	float xAtDown;
	float xAtUp;

	// set Button
	private Button leftBtn = null;
	private Button rightBtn = null;
	private Button skipBtn = null;

	private int listNum = 0; // TODO add list length

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flipper);

		// background blur
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

		flipper = (ViewFlipper) findViewById(R.id.view_flipper);
		flipper.setOnTouchListener(this);

		// add view
		addFlipperChildView();
		// }
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (v != flipper)
			return false;

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			xAtDown = event.getX(); // 처음 터치 위치 저장
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			xAtUp = event.getX(); // 터치 끝 위치 저장

			int childViewNum = flipper.getDisplayedChild() + 1;

			if (xAtUp < xAtDown) {

				if (childViewNum >= listNum) {
					Log.v(TAG, "childViewNum  >= listNum-1   listNum::"
							+ listNum + ", childViewNum ::" + childViewNum);
					showPreview();
				} else {
					Log.v(TAG, "showNext()  listNum::" + listNum
							+ ", childViewNum ::" + childViewNum);
					left();
					flipper.showNext();
				}

			} else if (xAtUp > xAtDown) {
				if (childViewNum <= 1) {
					Log.v(TAG, "childViewNum  <= 0::" + childViewNum);
				} else {
					Log.v(TAG, "showPrevious ::" + childViewNum);
					right();
					flipper.showPrevious();
				}
			}
		}
		return true;
	}

	private void left() {
		flipper.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_left_in));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_left_out));
	}

	private void right() {
		flipper.setInAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_right_in));
		flipper.setOutAnimation(AnimationUtils.loadAnimation(this,
				R.anim.push_right_out));
	}

	// // add FlipperChild View
	// public void addFlipperChildView() {
	// Log.v(TAG, "addFlipperChildView()");
	// DBHandler mDBHandler = DBHandler.open(this);
	// Cursor mDBcursor = mDBHandler.dbSelectAll();
	// // listNum = mDBcursor.getCount();
	// Log.v(TAG, "listNum  :: " + listNum);
	// int num = 0;
	// if (mDBcursor.moveToNext()) {
	// do {
	// num++;
	// // String id =
	// //
	// mDBcursor.getString(mDBcursor.getColumnIndex(ContactsContract.Contacts._ID));
	// long id =
	// mDBcursor.getLong(mDBcursor.getColumnIndex(DBHelper.KEY_ROWID));
	// String mode =
	// mDBcursor.getString(mDBcursor.getColumnIndex(DBHelper.KEY_MODE));
	// String listData =
	// mDBcursor.getString(mDBcursor.getColumnIndex(DBHelper.KEY_LIST_DATA));
	// Log.v(TAG, "mode  :: " + mode + "   ,listData  :: " + listData);
	//
	// TextView tv = new TextView(this);
	// tv.setText("View :: " + num + "\n  listData :: " + listData);
	// tv.setTextColor(Color.CYAN);
	// tv.setTag(num);
	// flipper.addView(tv);
	//
	// // num++;
	// } while (mDBcursor.moveToNext());
	// }
	// mDBcursor.close();
	// listNum = num;
	// }

	// add FlipperChild View
	public boolean addFlipperChildView() {
		Log.v(TAG, "addFlipperChildView()");

		DBHandler mDBHandler = DBHandler.open(this);
		ArrayList<CheckListProfile> checkList = mDBHandler.dbSelectAllList();
		listNum = checkList.size();
		Log.v(TAG, "listNum  :: " + listNum);

		if (listNum == 0) {
			return false;
		}
		flipper.removeAllViews(); // 추가한 부분
		
		for (int i = 0; i < listNum; i++) {
			TextView tv = new TextView(this);
			// tv.setText("View :: "+i+"   contents:"+checkList.get(i).getContents()
			// );
			tv.setText(i + " : " + checkList.get(i).getContents());
			tv.setTextSize(30);
			tv.setTextColor(Color.WHITE);
			tv.setShadowLayer(3, 3, 3, Color.BLACK);
			tv.setPadding(70, 300, 0, 0);
			tv.setTag(i);
			flipper.addView(tv);

		}
		mDBHandler.close();
		return true;
	}

	// showPreview
	private void showPreview() {
		Intent intent = new Intent(this, PreviewListActivity.class);
		startActivity(intent);
		finish();
	}

}
