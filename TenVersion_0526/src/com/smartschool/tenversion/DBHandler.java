package com.smartschool.tenversion;

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

	public static final String TABLE_NAME = "checklist_table";
	public static final String KEY_ROWID = "_id";
	public static final String KEY_MODE = "mode";			//1=safe, 2=live, 3=etc
	public static final String KEY_LIST_DATA = "list_data";
    
    public DBHandler(Context ctx) {
        this.helper = new DBHelper(ctx);
        this.db = helper.getWritableDatabase();
    }

    public static DBHandler open(Context ctx) throws SQLException {
        DBHandler handler = new DBHandler(ctx);        

        return handler;    
    }
    
    public void close() {
        helper.close();
    }

    public long insert(String mode, String listData) {
    	Log.v(TAG,"insert()  mode::"+mode+" , list_data ::"+listData);
        ContentValues values = new ContentValues();
        values.put(KEY_MODE,mode);       
        values.put(KEY_LIST_DATA, listData);        

        return db.insert(TABLE_NAME, null, values);
    } 
    
    public boolean delete(long rowID){    
    	Log.v(TAG,"delete()  rowID::"+rowID);
        return db.delete(TABLE_NAME, KEY_ROWID + "=" + rowID, null) > 0;
    } 

    public boolean update(long rowID, String mode, String listData){
    	Log.v(TAG,"update()  rowID::"+rowID+", mode::"+mode+" ,listData::"+listData);
    	ContentValues values = new ContentValues();
    	values.put(KEY_MODE, mode);
    	values.put(KEY_LIST_DATA, listData);
    	
    	return db.update(TABLE_NAME, values, KEY_ROWID + "=" + rowID, null) > 0;
    } 

    public Cursor select(int id) throws SQLException { 
    	Log.v(TAG,"select()  id::"+id);
        Cursor cursor = db.query(true, TABLE_NAME, 
                                new String[] {KEY_ROWID, KEY_MODE, KEY_LIST_DATA},
                                KEY_ROWID + "=" + id, 
                                null, null, null, null, null);        
        if (cursor != null) { cursor.moveToFirst(); }        

        return cursor;
    }
    
    public Cursor selectAll() throws SQLException {
    	Log.v(TAG,"selectAll() ");
        Cursor cursor = db.query(true,TABLE_NAME, 
                                new String[] {KEY_ROWID, KEY_MODE, KEY_LIST_DATA},
                                null, 
                                null,null, null, null, null);
        
        if (cursor != null) { cursor.moveToFirst(); }
        
        return cursor;
    }
    
    
}
