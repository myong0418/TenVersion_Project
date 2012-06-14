package com.smartschool.tenversion;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
private static final String TAG = "DBHelper";
private static Context mContext ;

private static final String DATABASE_NAME = "tenversion.db";
private static final int DATABASE_VERSION = 1;
public static final String TABLE_NAME = "checklist_table";


public static final String KEY_ROWID = "_id";
public static final String KEY_MODE = "mode";
public static final String KEY_LIST_DATA = "list_data";
	
private static DBHelper mInstance = null;

	public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

	private static final String DATABASE_CREATE =
			"CREATE TABLE "+TABLE_NAME+" ("+
			KEY_ROWID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
			KEY_MODE+" TEXT NOT NULL, "+
			KEY_LIST_DATA+" TEXT NOT NULL);";
	
    @Override
    public void onCreate(SQLiteDatabase db) { 
        db.execSQL(DATABASE_CREATE);
        initInsert(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {        
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    
    public void initInsert(SQLiteDatabase db) {
		Log.v(TAG, "DB insert() defult data");
		String[] setSafeArray= mContext.getResources().getStringArray(R.array.set_safe_dbdata);
		String[] setLiveArray= mContext.getResources().getStringArray(R.array.set_live_dbdata);
		String[] setEtcArray= mContext.getResources().getStringArray(R.array.set_etc_dbdata);
		for(int i = 0;i<setSafeArray.length; i++ ){
			ContentValues values = new ContentValues();
			values.put(KEY_MODE, "1");
			values.put(KEY_LIST_DATA, setSafeArray[i].toString());
			db.insert(TABLE_NAME, null, values);
		}
		for(int i = 0;i<setLiveArray.length; i++ ){
			ContentValues values = new ContentValues();
			values.put(KEY_MODE, "2");
			values.put(KEY_LIST_DATA, setLiveArray[i].toString());
			db.insert(TABLE_NAME, null, values);
		}
		for(int i = 0;i<setEtcArray.length; i++ ){
			ContentValues values = new ContentValues();
			values.put(KEY_MODE, "3");
			values.put(KEY_LIST_DATA, setEtcArray[i].toString());
			db.insert(TABLE_NAME, null, values);
		}
	}
}
