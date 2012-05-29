package com.smartschool.tenversion;

import java.util.List;


import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.preference.ListPreference;
import android.util.Log;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver{
	public static final String TAG = "WifiReceiver";
	private WifiManager wifiManager =null;
	private NotificationManager notiMgr = null;
	private List<ScanResult> mScanResult; // ScanResult List
	private static Context mContext;
	
	public  boolean WifiState = false;
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive() context ::" + context);

		mContext = context;

		String action = intent.getAction();

		// 네트웍에 변경이 일어났을때 발생하는 부분
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			Log.d(TAG, "CONNECTIVITY_ACTION");
			
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

			int networkType = networkInfo.getType();
			if (networkType == ConnectivityManager.TYPE_WIFI) { 		
				// wifi network
				Log.d(TAG, "wifi network");

				wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				String connectedWifi = wifiManager.getConnectionInfo().getSSID();
				String wifimode = getWifiSetting();

				if (connectedWifi.equals(wifimode)) {					//connected wifi mode
					Log.d(TAG, "noti.start");
					
					//set Notification
					setNotification();

					WifiState = true;
				} else {												//disconnected wifi mode
					Log.d(TAG, "noti.cancel");
					//cancel notification
					cancleNotification();
					
					// allam
					ringAlam();
				}
			} else if (networkType == ConnectivityManager.TYPE_MOBILE) {//disconnected wifi mode
				// mobile network
				Log.d(TAG, "mobile network");
				//cancel notification
				cancleNotification();

				// allam
				ringAlam();
			}

		} else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			Log.d(TAG, "WIFI_STATE_CHANGED_ACTION");
//			wifiManager = (WifiManager) context
//					.getSystemService(Context.WIFI_SERVICE);
//			// wifiManager.getScanResults();
//			getWIFIScanResult();
		}
	}
	private int scanCount = 0;
	
	public void getWIFIScanResult() {

		mScanResult = wifiManager.getScanResults(); // ScanResult
		
		CharSequence[] entries = new CharSequence[mScanResult.size()];
		
		// Scan count
		Log.v(TAG,"Scan count is \t" + ++scanCount + " times \n");

		Log.v(TAG,"=======================================\n");
		for (int i = 0; i < mScanResult.size(); i++) {
			ScanResult result = mScanResult.get(i);
			Log.v(TAG,(i + 1) + ". SSID : " + result.SSID.toString()
					+ "\t\t RSSI : " + result.level + " dBm\n");
		
			entries[i] = result.SSID.toString();
			
			Log.d(TAG,(i + 1) + ". entries : "+entries[i]);
		}
		Log.v(TAG,"=======================================\n");
		
//		int scanCount2 = 0;
//		
//		List<WifiConfiguration> mConfigure = wifiManager.getConfiguredNetworks(); // ScanResult
//		// Scan count
//		Log.v(TAG,"Scan count is \t" + ++scanCount2 + " times \n");
//
//		Log.v(TAG,"=======================================\n");
//		for (int i = 0; i < mConfigure.size(); i++) {
//			WifiConfiguration result2 = mConfigure.get(i);
//			Log.v(TAG,(i + 1) + ". SSID : " + result2.SSID.toString()
//					+ "\t\t BSSID : " + result2.BSSID + " dBm\n");
//		}
//		Log.v(TAG,"=======================================\n");
	}


	
	public void setNotification(){
		notiMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
		Intent goIntent = new Intent(mContext,
				TenVersionActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(
				mContext, 0, goIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Notification notification = new Notification(
				R.drawable.ic_launcher, "TenVersion",
				System.currentTimeMillis());
		notification.setLatestEventInfo(mContext, "TenVersion",
				"TenVersion 에서 설정을 하실 수 있습니다.", pendingIntent);
		notification.flags = Notification.FLAG_NO_CLEAR;
		notiMgr.notify(0, notification);
	}
	
	public void cancleNotification(){
		notiMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
		notiMgr.cancel(0);
	}

	
	public void ringAlam(){
		Log.v(TAG, "ringAlam()");
		if(WifiState){
			//alam
			
//start flipperView
//			Intent intent = new Intent(mContext,FlipperView.class);
//			mContext.startActivity(intent);
		}
		WifiState = false;
	}
	
	
	public String getWifiSetting(){
		Log.v(TAG, "getWifiSetting()");
		SharedPreferences sharedPrefs = mContext.getSharedPreferences("home_wifi", Context.MODE_PRIVATE);
		return sharedPrefs.getString("home_wifi", null);
	}
}
