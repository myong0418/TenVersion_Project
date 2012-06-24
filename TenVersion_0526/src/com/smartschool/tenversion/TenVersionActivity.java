package com.smartschool.tenversion;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class TenVersionActivity extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //list btn
        ImageButton safeBtn = (ImageButton)findViewById(R.id.safe_btn);
        safeBtn.setOnClickListener(this);
        ImageButton liveBtn = (ImageButton)findViewById(R.id.live_btn);
        liveBtn.setOnClickListener(this);
        ImageButton etcBtn = (ImageButton)findViewById(R.id.etc_btn);
        etcBtn.setOnClickListener(this);
        
        //setting btn
        ImageButton settingBtn = (ImageButton)findViewById(R.id.setting_btn);
        settingBtn.setOnClickListener(this);
                
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
			intent = new Intent(this,LiveListActivity.class);
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
			
			
		case R.id.testFlipper_btn:
			intent = new Intent(this,FlipperView.class);
			startActivity(intent);
			break;
			
		}
		
	}
}