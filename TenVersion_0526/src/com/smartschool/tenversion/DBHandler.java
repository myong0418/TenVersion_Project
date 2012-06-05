package com.smartschool.tenversion;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
	
    public DBHandler(Context ctx) {
    	this.helper =  DBHelper.getInstance(ctx);  //db [true,false] check
    	this.db = helper.getWritableDatabase();
    }

    public DBHandler open(Context ctx) throws SQLException {
        DBHandler handler = new DBHandler(ctx);        
        return handler;    
	}

	public void close() {
		helper.close();
	}

	public long insert(String mode, String listData) {
		Log.v(TAG, "insert()  mode::" + mode + " , list_data ::" + listData);
		ContentValues values = new ContentValues();
		values.put(KEY_MODE, mode);
		values.put(KEY_LIST_DATA, listData);

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

	public boolean update(long rowID, String mode, String listData) {
		Log.v(TAG, "update()  rowID::" + rowID + ", mode::" + mode
				+ " ,listData::" + listData);
		ContentValues values = new ContentValues();
		values.put(KEY_MODE, mode);
		values.put(KEY_LIST_DATA, listData);

		return db.update(TABLE_NAME, values, KEY_ROWID + "=" + rowID, null) > 0;
	}

	public Cursor select(int id) throws SQLException {
		Log.v(TAG, "select()  id::" + id);
		Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
				KEY_MODE, KEY_LIST_DATA }, KEY_ROWID + "=" + id, null, null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}

		return cursor;
	}

	public ArrayList<Lists> selectAll() throws SQLException {
		Log.v(TAG, "selectAll() ");
		ArrayList<Lists> lists = new ArrayList<Lists>();
		Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
				KEY_MODE, KEY_LIST_DATA }, null, null, null, null, null, null);

		if (cursor != null) {
			cursor.moveToFirst();
		}
		while (cursor.moveToNext()) {
			lists.add(new Lists(
					cursor.getInt(cursor.getColumnIndex(KEY_ROWID)), cursor
							.getString(cursor.getColumnIndex(KEY_MODE)), cursor
							.getString(cursor.getColumnIndex(KEY_LIST_DATA))));
		}

		return lists;
	}

	public Cursor selectAll2() throws SQLException {
		Log.v(TAG, "selectAll() ");
		String[] from = new String[] { KEY_ROWID, KEY_MODE, KEY_LIST_DATA };

		Cursor cursor = db.query(true, TABLE_NAME, from, null, null, null,
				null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
		}
		return cursor;
	}

	public ArrayList<Lists> safeSelectAll() throws SQLException {
		Log.v(TAG, "liveSelectAll() ");
		ArrayList<Lists> lists = new ArrayList<Lists>();
		Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
				KEY_MODE, KEY_LIST_DATA }, KEY_MODE + "=?",
				new String[] { SAFEMODE }, null, null, null,
				null);

		if (cursor != null) {
			cursor.moveToFirst();
		}
		while (cursor.moveToNext()) {
			lists.add(new Lists(
					cursor.getInt(cursor.getColumnIndex(KEY_ROWID)), cursor
							.getString(cursor.getColumnIndex(KEY_MODE)), cursor
							.getString(cursor.getColumnIndex(KEY_LIST_DATA))));
		}

		return lists;
	}
	
	public ArrayList<Lists> liveSelectAll() throws SQLException {
		Log.v(TAG, "liveSelectAll() ");
		ArrayList<Lists> lists = new ArrayList<Lists>();
		Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
				KEY_MODE, KEY_LIST_DATA }, KEY_MODE + "=?",
				new String[] { LIVEMODE }, null, null, null,
				null);

		if (cursor != null) {
			cursor.moveToFirst();
		}
		while (cursor.moveToNext()) {
			lists.add(new Lists(
					cursor.getInt(cursor.getColumnIndex(KEY_ROWID)), cursor
							.getString(cursor.getColumnIndex(KEY_MODE)), cursor
							.getString(cursor.getColumnIndex(KEY_LIST_DATA))));
		}

		return lists;
	}
	
	public ArrayList<Lists> etcSelectAll() throws SQLException {
		Log.v(TAG, "liveSelectAll() ");
		ArrayList<Lists> lists = new ArrayList<Lists>();
		Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ROWID,
				KEY_MODE, KEY_LIST_DATA }, KEY_MODE + "=?",
				new String[] { SAFEMODE }, null, null, null,
				null);

		if (cursor != null) {
			cursor.moveToFirst();
		}
		while (cursor.moveToNext()) {
			lists.add(new Lists(
					cursor.getInt(cursor.getColumnIndex(KEY_ROWID)), cursor
							.getString(cursor.getColumnIndex(KEY_MODE)), cursor
							.getString(cursor.getColumnIndex(KEY_LIST_DATA))));
		}

		return lists;
	}

	// public Cursor selectAll() throws SQLException {
	// Log.v(TAG,"selectAll() ");
	// Cursor cursor = db.query(true,TABLE_NAME,
	// new String[] {KEY_ROWID, KEY_MODE, KEY_LIST_DATA},
	// null,
	// null,null, null, null, null);
	//
	// if (cursor != null) { cursor.moveToFirst(); }
	//
	// return cursor;
	// }

}
