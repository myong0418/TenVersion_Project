package com.smartschool.tenversion;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LIveListDelActivity extends ListActivity implements
		View.OnClickListener {
	private String[] cars = { "SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL",
			"K5", "K7" };
	private TextView selected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livedellist);

		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, cars));

		ListView listView = getListView();
		// listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		selected = (TextView) findViewById(R.id.selected);

		Button allcheck = (Button) findViewById(R.id.allcheck);
		allcheck.setOnClickListener(this);

		Button delbtn = (Button) findViewById(R.id.del_btn);
		delbtn.setOnClickListener(this);

	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		selected.setText(cars[position]);
	}

	public void onClick(View v) {
		ListView list = getListView();
		int cnt = 0;
		Log.v("onClick start cnt =========", "" + cnt);
		for (int i = 0; i < list.getCount(); i++) {
			
			if (list.isItemChecked(i) == false) {
				Log.v("list.isItemChecked(" + i + ")", " ------- " + cnt );
				cnt++;
			}

		}

		switch (v.getId()) {
		case R.id.allcheck:
			if (cnt > 0) {
				Log.v("allcheck true cnt =========", "" + cnt);
				// 전체 선택
				for (int i = 0; i < list.getCount(); i++)
					list.setItemChecked(i, true);

			} else {
				Log.v("allcheck false cnt =========", "" + cnt);
				// 선택 해제
				
				for (int i = 0; i < list.getCount(); i++)
					list.setItemChecked(i, false);

			}
			break;

		case R.id.del_btn:
			new AlertDialog.Builder(this).setTitle("삭제")
					.setMessage("삭제하시겠습니까?").setNeutralButton("확인", null)
					.setNegativeButton("취소", null).show();
			break;

		}
	}
}