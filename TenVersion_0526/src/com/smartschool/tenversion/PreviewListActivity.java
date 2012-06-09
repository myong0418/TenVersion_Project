package com.smartschool.tenversion;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class PreviewListActivity extends Activity{
	private static final String TAG = "TestFlipperviewActivity";
	
	TextView previewTV = null;
	private static DBHandler mDBHandler = null;
	Cursor mDBcursor = null;
	private ArrayList<CheckListProfile> checkListItem = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.preview);
		
		previewTV = (TextView)findViewById(R.id.preview_tv);
		checkListItem = new ArrayList<CheckListProfile>();
		mDBHandler = DBHandler.open(this);
		
        updateListview();
	}
	
    public void updateListview(){
    	Log.v(TAG,"updateListview()");
    	String previewList = "";
    	checkListItem.clear();
    	mDBcursor = mDBHandler.selectAll2();//mode = safe = 1
    	int num = 0;
    	if(mDBcursor.moveToNext()){
			do {
				num++;
				//String id = mDBcursor.getString(mDBcursor.getColumnIndex(ContactsContract.Contacts._ID));
				long id = mDBcursor.getLong(mDBcursor.getColumnIndex(DBHelper.KEY_ROWID));
				String mode = mDBcursor.getString(mDBcursor.getColumnIndex(DBHelper.KEY_MODE));
				String listData = mDBcursor.getString(mDBcursor.getColumnIndex(DBHelper.KEY_LIST_DATA));  			    		
				Log.v(TAG,"mode  :: "+mode+ "   ,listData  :: "+listData );
				checkListItem.add(new CheckListProfile(id, mode, listData));
				previewList = num+". "+listData+"\n";
			} while(mDBcursor.moveToNext());
		}
    	mDBcursor.close();
    	
    	setPreviewText(previewList);
    }
    
    
    public void setPreviewText(String previewList){ 
    	previewTV.setText(previewList);
    }
    
    
}
