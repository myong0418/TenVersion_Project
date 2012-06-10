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
	private checkAdapter checkAdapter = null;
	/** UI **/
	private static final int MODE = 2; // live
	private static final String Livemode = "2";
	private boolean DEL_MODE = false;
	// setListView
	private ListView buddyListView = null;
	private CheckListAdapter checkListAdapter = null;
	private ArrayList<CheckListProfile> checkListItem = null;
	// set Button
	private Button allcheckBtn = null;
	private Button delbtn = null;

	// activity result
	private static final int REQ_BUDDYLIST_ADD = 1;
	private static final int REQ_PICK_PICTURE = 2;
	// dialog
	// private static final int FREE_DLG = 1;
	private static final int ADD_DLG = 1;
	private static final int DEL_DLG = 2;
	/** DB **/
	private static DBHandler mDBHandler = null;
	Cursor mDBcursor = null;
	String[] cars = null;
	ArrayList<Lists> car = new ArrayList<Lists>();
	private TextView selected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livedellist);
		mDBHandler = DBHandler.open(this);
//		int ins = 0;
//		mDBcursor = mDBHandler.selectAllList(Livemode);
//		
//		 car = mDBHandler.selectAll();// mode = live = 2
//		 cars = new String[car.size()];
//		// Iterator<Lists> it = car.iterator();
//		// while (it.hasNext()) {
//		// String listData = mDBcursor.getString(mDBcursor
//		// .getColumnIndex(DBHelper.KEY_LIST_DATA));
//		// Log.v(TAG, listData);
//		// cars[ins] = listData;
//		// ins++;
//		//
//		// }
//		// mDBcursor.close();
//
//		if (mDBcursor.moveToNext()) {
//			do {
//				// String id =
//				// mDBcursor.getString(mDBcursor.getColumnIndex(ContactsContract.Contacts._ID));
//				long id = mDBcursor.getLong(mDBcursor
//						.getColumnIndex(DBHelper.KEY_ROWID));
//				String mode = mDBcursor.getString(mDBcursor
//						.getColumnIndex(DBHelper.KEY_MODE));
//				String listData = mDBcursor.getString(mDBcursor
//						.getColumnIndex(DBHelper.KEY_LIST_DATA));
//				Log.v(TAG, "mode  :: " + mode + "   ,listData  :: " + listData);
//				// checkListItem.add(new CheckListProfile(id, mode, listData));
//				cars[ins] = listData;
//				ins++;
//
//			} while (mDBcursor.moveToNext());
//		}
//		mDBcursor.close();
//
//		setListAdapter(new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_multiple_choice, cars));

		selected = (TextView) findViewById(R.id.selected);

		/** UI **/
		// button
		allcheckBtn = (Button) findViewById(R.id.allcheck);
		allcheckBtn.setOnClickListener(this);
		delbtn = (Button) findViewById(R.id.del_btn);
		delbtn.setOnClickListener(this);

		// buddyListView.setOnItemClickListener(this);

		checkListItem = new ArrayList<CheckListProfile>();

		/** DB **/
		// contactsDBHelper = new ContactsDBHelper(this);
		// mDBHandler = new DBHandler(this);
		// mDBHandler.open(this);

		// requastBuddylist();


		// listView
		buddyListView = (ListView) findViewById(R.id.listView);
//		buddyListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
//		buddyListView.setOnItemLongClickListener(this);

		updateListview();
	}

	public void updateListview() {
		Log.v(TAG, "updateListview()");

		checkListItem.clear();
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
				checkListItem.add(new CheckListProfile(id, mode, listData));

			} while (mDBcursor.moveToNext());
		}
		mDBcursor.close();
		// buddylistView.setListData(checkListItem);
		checkListAdapter = new CheckListAdapter(this,
				R.layout.checklist_item_row,
				checkListItem, DEL_MODE);
		buddyListView.setAdapter(checkListAdapter);
		
//		checkAdapter = new checkAdapter(this);
//		setListAdapter(checkAdapter);

	}

	public void onListItemClick(ListView parent, View v, int position, long id) {
		// selected.setText(cars[position]);
	}

	public void onClick(View v) {
		ListView list = getListView();
		int cnt = 0;

		switch (v.getId()) {
		case R.id.allcheck:
			if (cnt > 0) {
				Log.v(TAG, "allcheck true cnt =========" + cnt);
				// 전체 선택
				for (int i = 0; i < list.getCount(); i++)
					list.setItemChecked(i, true);

			} else {
				Log.v(TAG, "allcheck false cnt =========" + cnt);
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

	public boolean onItemLongClick(AdapterView<?> arg0, View v, int position,
			long id) {
		Log.v(TAG, "view " + v + " position :: " + position);
		return false;
	}

	protected void onDestroy() {
		Log.v(TAG, "onDestroy()");
		super.onDestroy();
		mDBHandler.close();
	}

	/**
	 * ListView Adapter
	 * 
	 * @author Administrator
	 * 
	 */
	class checkAdapter extends ArrayAdapter {

		Activity context;

		@SuppressWarnings("unchecked")
		checkAdapter(Activity context) {
			super(context, R.layout.check_row, liveList);
			this.context = context;
		}

		/**
		 * 멤버 리스트의 갯수만큼 호출되는 함수
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.v(TAG, "getView()");
			// 멤버 정보
			Lists lives = liveList.get(position);

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
			CheckBox checkBox = (CheckBox) row.findViewById(R.id.checkBox);
			checkBox.setChecked(lives.isChecked());
			checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				// 클릭할때 마다 상태 저장
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					liveList.get(pos).setChecked(isChecked);
				}

			});

			return row;
		}
	}
	
}