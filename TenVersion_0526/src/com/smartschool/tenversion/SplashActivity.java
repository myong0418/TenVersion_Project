package com.smartschool.tenversion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		initialize();
	}

//	private void initialize() {
//		Handler handler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				finish(); // 액티비티 종료
//			}
//		};
//
//		handler.sendEmptyMessageDelayed(0, 3000); // ms, 3초후 종료시킴
//	}
	Handler mHandler;
	private void initialize() {
	mHandler = new Handler() ;
	
	
	mainThread mMainrThread = new mainThread();     
	mMainrThread.start();

	
	
	}
	
	
	
	 class mainThread extends Thread {
		 
	        private int i = 0;
	        private boolean isPlay = false;


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
	            
	            
//	            while (isPlay) {
//	                try { 
//	                	
//	                	Thread.sleep(1000);
//	                } catch (InterruptedException e) {
//	                    e.printStackTrace();
//	                }
//	                mHandler.post(new Runnable() {
//	                    @Override
//	                    public void run() {
//	                      //  mTvNumber.setText(""+i++);
//	                    }
//	                });
//	            }
	        }
	 }

}
