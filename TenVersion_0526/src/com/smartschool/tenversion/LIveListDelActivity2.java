package com.smartschool.tenversion;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class LIveListDelActivity2 extends ListActivity implements
		View.OnClickListener, OnItemLongClickListener {
	private static final String TAG = "LIveListDelActivity";

	List<Lists> liveList = null;
	private CheckAdapter checkAdapter = null;
	Lists live = null;
	/** UI **/
	private static final int MODE = 2; // live
	private static final String Livemode = "2";
	// setListView
	private ListView buddyListView = null;
	// set Button
	private Button allcheckBtn = null;
	private Button delbtn = null;

	/** DB **/
	private static DBHandler mDBHandler = null;
	Cursor mDBcursor = null;
	ArrayList<Lists> delIds = new ArrayList<Lists>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livedellist);
		
		liveList = new ArrayList<Lists>();

		/** UI **/
		// button
		allcheckBtn = (Button) findViewById(R.id.allcheck);
		allcheckBtn.setOnClickListener(this);
		delbtn = (Button) findViewById(R.id.del_btn);
		delbtn.setOnClickListener(this);

		// listView
		buddyListView = (ListView) findViewById(R.id.listView);
		buddyListView = getListView();
//		buddyListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		buddyListView.setOnItemLongClickListener(this);

		/** DB **/
		mDBHandler = DBHandler.open(this);

		updateListview();
	}

	public void updateListview() {
		Log.v(TAG, "updateListview()");

		liveList.clear();
		mDBcursor = mDBHandler.selectAllList(Livemode);// mode = live = 2
		if (mDBcursor.moveToNext()) {
			do {
				// String id =
				// mDBcursor.getString(mDBcursor.getColumnIndex(ContactsContract.Contacts._ID));
				long id = mDBcursor.getLong(mDBcursor
						.getColumnIndex(DBHelper.KEY_ROWID));
				String mode = mDBcursor.getString(mDBcursor
						.getColumnIndex(DBHelper.KEY_MODE));
				String listData = mDBcursor.getString(mDBcursor
						.getColumnIndex(DBHelper.KEY_LIST_DATA));
				Log.v(TAG, "mode  :: " + mode + "   ,listData  :: " + listData);
//				checkListItem.add(new CheckListProfile(id, mode, listData));
				liveList.add(new Lists(id, mode, listData));
			} while (mDBcursor.moveToNext());
		}
		mDBcursor.close();
		
		checkAdapter = new CheckAdapter(this);
		buddyListView.setAdapter(checkAdapter);

	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		// selected.setText(cars[position]);
	}

	public void onClick(View v) {
		int cnt = 0;
		for (int i = 0; i < liveList.size(); i++) {
			
			if (liveList.get(i).isChecked() == false ) {
				cnt++;
			}

		}

		switch (v.getId()) {
		case R.id.allcheck:
			if (cnt > 0) {
				Log.v(TAG, "allcheck true cnt =========" + cnt);
				// 전체 선택
				for (int i = 0; i < liveList.size(); i++){
					liveList.get(i).setChecked(true);
				}
				
			} else {
				Log.v(TAG, "allcheck false cnt =========" + cnt);
				// 선택 해제
				for (int i = 0; i < liveList.size(); i++)
					liveList.get(i).setChecked(false);
			}
			checkAdapter.notifyDataSetChanged();
			break;

		case R.id.del_btn:
//			new AlertDialog.Builder(this).setTitle("삭제")
//					.setMessage("삭제하시겠습니까?").setNeutralButton("확인", null)
//					.setNegativeButton("취소", null).show();
			Log.v(TAG, "delete : " + cnt);
			
			break;

		}
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View v, int position,
			long id) {
		Log.v(TAG, "view " + v + " position :: " + position);
		return false;
	}
	/**
	 * ListView Adapter
	 * 
	 * @author Administrator
	 * 
	 */
	class CheckAdapter extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		CheckAdapter(Activity context) {
			super(context, R.layout.check_row, liveList);
			this.context = context;
		}

		/**
		 * 멤버 리스트의 갯수만큼 호출되는 함수
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.v(TAG, "getView()");
			// 멤버 정보
			final Lists lives = liveList.get(position);

			LayoutInflater inflater = context.getLayoutInflater();
			View row = inflater.inflate(R.layout.check_row, null);

			// / 이름
			TextView textView = (TextView) row.findViewById(R.id.name);
			textView.setText(lives.getMode());
			// 전화번호
			TextView textView2 = (TextView) row.findViewById(R.id.number);
			textView2.setText(lives.getListdata());
			// 체크박스 상태
			final int pos = position;
			final long id = lives.getId();
			CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			checkBox.setChecked(lives.isChecked());
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				// 클릭할때 마다 상태 저장
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					liveList.get(pos).setChecked(isChecked);
					if ( isChecked == false ){
						Log.v(TAG, "isChecked false " + id );
//						delIds.add(lives.getId());
					}else{
						Log.v(TAG, "isChecked true " + id );
					}
				}

			});

			return row;
		}
	}

	protected void onDestroy() {
		Log.v(TAG, "onDestroy()");
		super.onDestroy();
		mDBHandler.close();
	}


	
}