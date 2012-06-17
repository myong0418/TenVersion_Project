package com.smartschool.tenversion;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DBHandler {
	public static final String TAG = "DBHandler";

	private DBHelper helper;
	private SQLiteDatabase db;

	public static final String TABLE_NAME = DBHelper.TABLE_NAME;		//"checklist_table";
	public static final String KEY_ROWID = DBHelper.KEY_ROWID;			//"_id";
	public static final String KEY_MODE = DBHelper.KEY_MODE;			// "mode";			//1=safe, 2=live, 3=etc
	public static final String KEY_LIST_DATA = DBHelper.KEY_LIST_DATA;//"list_data";
	
	public static final String SAFEMODE = "1";	
	public static final String LIVEMODE = "2";	
	public static final String ETCMODE = "3";	
	
    private DBHandler(Context ctx) {
    	this.helper =  new DBHelper(ctx);
    	this.db = helper.getWritableDatabase();
    }

    public static DBHandler open(Context ctx) throws SQLException {
        return new DBHandler(ctx);        
	}

	public void close() {
		helper.close();
	}

	public long insert(Context ctx,  String mode, String listData) {
		Log.v(TAG, "insert()  mode::" + mode + " , list_data ::" + listData);
		ContentValues values = new ContentValues();
		if(listData.equals(null) || listData.equals("")){
			Toast.makeText(ctx, "빈 값은 입력 할 수 없습니다", Toast.LENGTH_LONG).show();
		}
		else{
			values.put(KEY_MODE, mode);
			values.put(KEY_LIST_DATA, listData);
		}

		return db.insert(TABLE_NAME, null, values);
	}
	
	//all delete
	public boolean deleteMemberAllRows() {
		return db.delete(TABLE_NAME, null, null) == 1;
	}
	
	public boolean delete(long rowID) {
		Log.v(TAG, "delete()  rowID::" + rowID);
		return db.delete(TABLE_NAME, KEY_ROWID + "=" + rowID, null) > 0;
	}

	public boolean update(Context ctx, long rowID, String mode, String listData) {
		Log.v(TAG, "update()  rowID::" + rowID + ", mode::" + mode
				+ " ,listData::" + listData);
		ContentValues values = new ContentValues();
		if(listData.equals(null) || listData.equals("")){
			Toast.makeText(ctx, "빈 값은 입력 할 수 없습니다", Toast.LENGTH_LONG).show();
			return false;
		}
		else{
			values.put(KEY_MODE, mode);
			values.put(KEY_LIST_DATA, listData);
			return db.update(TABLE_NAME, values, KEY_ROWID + "=" + rowID, null) > 0;
		}
		
	}

	public Cursor selectAllList(String mode) throws SQLException {
		Log.v(TAG, "selectAllList() ");

		Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
				KEY_MODE, KEY_LIST_DATA }, KEY_MODE + "=?",
				new String[] { mode }, null, null, null,
				null);
		
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}
	
	//120610_MyoHyun_SelectAll method START
		public Cursor dbSelectAll() throws SQLException {
			Log.v(TAG, "dbSelectAll() ");
			String[] from = new String[] { KEY_ROWID, KEY_MODE, KEY_LIST_DATA };

			Cursor cursor = db.query(true, TABLE_NAME, from, null, null, null,null, null, null);
		
			if (cursor != null) {
				cursor.moveToFirst();
			}
			return cursor;
		}
		
		public Cursor dbSafeSelectAll() throws SQLException {
			Log.v(TAG, "dbSafeSelectAll() ");
			String[] from = new String[] { KEY_ROWID, KEY_MODE, KEY_LIST_DATA };

			Cursor cursor = db.query(true, TABLE_NAME,from, KEY_MODE + "=?",
					new String[] { SAFEMODE }, null, null, null,
					null);
			if (cursor != null) {
				cursor.moveToFirst();
			}
			return cursor;
		}
		
		
		public ArrayList<CheckListProfile> dbSelectAllList() throws SQLException {
			Log.v(TAG, "dbSelectAllList() ");
			ArrayList<CheckListProfile> checkList = new ArrayList<CheckListProfile>();
			//safe
			Cursor cursor = db.query(true, TABLE_NAME, 
					new String[] { KEY_ROWID,KEY_MODE, KEY_LIST_DATA }, 
					KEY_MODE + "=?",new String[] { SAFEMODE }, null, null, null,null);
			if (cursor.moveToFirst()) {
				do {
					checkList.add(new CheckListProfile(
							cursor.getInt(cursor.getColumnIndex(KEY_ROWID)), 
							cursor.getString(cursor.getColumnIndex(KEY_MODE)), 
							cursor.getString(cursor.getColumnIndex(KEY_LIST_DATA))));
					Log.v(TAG,"Safe KEY_LIST_DATA) :: "+ cursor.getString(cursor.getColumnIndex(KEY_LIST_DATA)));

				} while (cursor.moveToNext());
			}
			
			//live
			cursor = db.query(true, TABLE_NAME, 
					new String[] { KEY_ROWID,KEY_MODE, KEY_LIST_DATA }, 
					KEY_MODE + "=?",new String[] { LIVEMODE }, null, null, null,null);
			if (cursor.moveToFirst()) {
				do {
					checkList.add(new CheckListProfile(
							cursor.getInt(cursor.getColumnIndex(KEY_ROWID)), 
							cursor.getString(cursor.getColumnIndex(KEY_MODE)), 
							cursor.getString(cursor.getColumnIndex(KEY_LIST_DATA))));
					Log.v(TAG,"Live KEY_LIST_DATA) :: "+ cursor.getString(cursor.getColumnIndex(KEY_LIST_DATA)));

				} while (cursor.moveToNext());
			}
			
			//etc
			cursor = db.query(true, TABLE_NAME, 
					new String[] { KEY_ROWID,KEY_MODE, KEY_LIST_DATA }, 
					KEY_MODE + "=?",new String[] { ETCMODE }, null, null, null,null);
			if(cursor.moveToFirst()){
				do {
					checkList.add(new CheckListProfile(
							cursor.getInt(cursor.getColumnIndex(KEY_ROWID)), 
							cursor.getString(cursor.getColumnIndex(KEY_MODE)), 
							cursor.getString(cursor.getColumnIndex(KEY_LIST_DATA))));
					Log.v(TAG, "Etc KEY_LIST_DATA) :: "+cursor.getString(cursor.getColumnIndex(KEY_LIST_DATA)));
					
				} while(cursor.moveToNext());
			}
			return checkList;
		}

	//120610_MyoHyun_SelectAll method END
}
