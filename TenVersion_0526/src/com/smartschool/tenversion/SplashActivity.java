package com.smartschool.tenversion;

import android.app.Activity;
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

	private void initialize() {
		Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				finish(); // ��Ƽ��Ƽ ����
			}
		};

		handler.sendEmptyMessageDelayed(0, 3000); // ms, 3���� �����Ŵ
	}
}
