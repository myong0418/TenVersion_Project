package com.smartschool.tenversion;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class VersionInfo extends Activity{
	private static final String TAG = "VersionInfo";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.versioninfo);
	
		
		
		TextView versionTV = (TextView)findViewById(R.id.version_tv);
		TextView versionCodeTV = (TextView)findViewById(R.id.version_code_tv);
		
		PackageInfo pakageinfo = null;
		String versionName = "";
		int versionCode = 0;
		try {
			pakageinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionName = pakageinfo.versionName;
			versionCode = pakageinfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		versionTV.setText("  "+versionName);
		versionCodeTV.setText("  "+versionCode);
	}

}
