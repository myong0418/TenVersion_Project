package com.smartschool.tenversion;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;


public class PreviewListActivity extends Activity implements OnClickListener{
	private static final String TAG = "TestFlipperviewActivity";
	
	private TextView previewTV = null;
	private Cursor mDBcursor = null;

	Bundle flipperViewbundle ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = new Intent();
		
		setContentView(R.layout.preview);
		Button okBtn = (Button) findViewById(R.id.preview_ok_btn);
		okBtn.setOnClickListener(this);
		
		previewTV = (TextView)findViewById(R.id.preview_tv);

		
        updateListview();
	}
	
    public void updateListview(){
    	Log.v(TAG,"updateListview()");
    	String previewList = "";
    	DBHandler mDBHandler = DBHandler.open(this);
	    ArrayList<CheckListProfile> checkList = mDBHandler.dbSelectAllList();
	    
	    for(int i=0; i<checkList.size(); i++){
	    	previewList += i+". "+checkList.get(i).getContents()+"\n";
		}
	    
		mDBHandler.close();
    	setPreviewText(previewList);
    }
    
    
    public void setPreviewText(String previewList){ 
    	previewTV.setText(previewList);
    }

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.preview_ok_btn:
			finish();
			break;
		}
	}
    
    
}
