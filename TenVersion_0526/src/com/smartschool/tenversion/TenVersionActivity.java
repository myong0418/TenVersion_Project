package com.smartschool.tenversion;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TenVersionActivity extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //list btn
        Button safeBtn = (Button)findViewById(R.id.safe_btn);
        safeBtn.setOnClickListener(this);
        Button liveBtn = (Button)findViewById(R.id.live_btn);
        liveBtn.setOnClickListener(this);
        Button etcBtn = (Button)findViewById(R.id.etc_btn);
        etcBtn.setOnClickListener(this);
        
        //setting btn
        Button settingBtn = (Button)findViewById(R.id.setting_btn);
        settingBtn.setOnClickListener(this);
        
        //test dialog btn
        Button testdailogBtn = (Button)findViewById(R.id.testdailog_btn);
        testdailogBtn.setOnClickListener(this);
        
       //test Flipper btn
        Button testFlipperBtn = (Button)findViewById(R.id.testFlipper_btn);
        testFlipperBtn.setOnClickListener(this);
        
    }

	public void onClick(View v) {
		Intent intent = null;
		switch(v.getId()) {
		case R.id.safe_btn:
			intent = new Intent(this,SafeListActivity.class);
			startActivity(intent);
			break;
			
		case R.id.live_btn:
			intent = new Intent(this,LiveListActivity2.class);
			startActivity(intent);
			break;
			
		case R.id.etc_btn:
			intent = new Intent(this,EtcListActivity.class);
			startActivity(intent);
			break;
			
		case R.id.setting_btn:
			intent = new Intent(this,SettingListActivity.class);
			startActivity(intent);
			break;
			
			
		case R.id.testdailog_btn:
			intent = new Intent(this,TestDialogActivity.class);
			startActivity(intent);
			break;

		case R.id.testFlipper_btn:
			intent = new Intent(this,FlipperView.class);
			startActivity(intent);
			break;
			
		}
		
	}
}