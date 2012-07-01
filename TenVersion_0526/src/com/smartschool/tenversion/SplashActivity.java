package com.smartschool.tenversion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {
	private Handler mHandler;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		initialize();
	}

	private void initialize() {
		mHandler = new Handler();

		mainThread mMainrThread = new mainThread();
		mMainrThread.start();
	}
	
	
	
	 class mainThread extends Thread {
		 
	        @Override
	        public void run() {
	            super.run();
	         
	            try {
	            		Thread.sleep(3000);
	            		Intent intent = new Intent(SplashActivity.this, TenVersionActivity.class);
	        			startActivity(intent);
	            	 finish();

	            } catch (Exception e) {
	            	
	            }
	            
	        }
	 }

}
