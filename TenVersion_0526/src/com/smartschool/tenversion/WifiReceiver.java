package com.smartschool.tenversion;

import java.util.List;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver{
	//wifi Key
	public static final String TAG = "WifiReceiver";
	public static final String KEY_WIFI_MODE = "wifi_mode";
	public static final String KEY_WIFISSID = "wifi_ssid";
	public static final String KEY_WIFIBSSID = "wifi_bssid";
	//hw Key
	public static final String KEY_SOUND = "sound_mode";
	public static final String KEY_VIBRATE = "vibrator_mode";
	
	//wifi
	private static WifiManager wifiManager =null;
	private static NotificationManager notiMgr = null;
	private List<ScanResult> mScanResult; // ScanResult List
	//private static Context mContext;
	//wifi state
	public static  boolean WifiState = false;
	//notification state
	public static final int WifiNotiState = 10;
	//alarm
	public static String ALARM_ACTION = "com.smartschool.tenversion.ALARM_ACTION";
	private static MediaPlayer mplay = null;
	
	//vibrator
	private static int VIBRATE_TIME = 1000;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v(TAG, "onReceive() context ::" + context);
		Log.v(TAG, "onReceive() intent ::" + intent);
		
		//mContext = context;
		String action = intent.getAction();
		
		if(action.equals(ALARM_ACTION)){
			Log.d(TAG, "ALARM_ACTION ");
			//alarm
			ringAlam(context);

		}
		// 네트웍에 변경이 일어났을때 발생하는 부분
		else if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			Log.d(TAG, "CONNECTIVITY_ACTION");

			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			try {
				NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

				int networkType = networkInfo.getType();
				if (networkType == ConnectivityManager.TYPE_WIFI) {
					// wifi network
					Log.d(TAG, "wifi network");

					//enable layout 
					if (SettingActivity.wifiLayout != null) {
						SettingActivity.wifiLayout.setEnabled(true);
						SettingActivity.wifiLayout_disable.setVisibility(View.GONE);
						for ( int i = 0; i < SettingActivity.wifiLayout.getChildCount();  i++ ){ 
						    View view = SettingActivity.wifiLayout.getChildAt(i); 
						    view.setEnabled(true);
						} 
					}

					String connectedWifi = getConnectedWifiBSSID(context);
					String wifimode = getWifiSettingBSSID(context);

					if (connectedWifi == null || wifimode == null) {
						Log.e(TAG, "failed wifi info");
						Toast.makeText(context, "failed wifi info",Toast.LENGTH_SHORT);
					}
					if (connectedWifi.equals(wifimode)) { 						// connected wifi mode
						Log.d(TAG, "noti.start");

						// set Notification
						setNotification(context,WifiNotiState);

						WifiState = true;

					} else { 													// disconnected wifi mode
						Log.d(TAG, "noti.cancel");
						// cancel notification
						cancleNotification(context,WifiNotiState);

						// alarm
						if (WifiState) {
							ringAlam(context);
						}WifiState = false;
					}

				} else if (networkType == ConnectivityManager.TYPE_MOBILE) {	// disconnected wifi mode
					// mobile network
					Log.d(TAG, "mobile network");

					//disable layout 
					if (SettingActivity.wifiLayout != null) {
						SettingActivity.wifiLayout.setEnabled(false);
						SettingActivity.wifiLayout_disable.setVisibility(View.VISIBLE);
						for ( int i = 0; i < SettingActivity.wifiLayout.getChildCount();  i++ ){ 
						    View view = SettingActivity.wifiLayout.getChildAt(i); 
						    view.setEnabled(false);
						} 
					}
					
					
					// cancel notification
					cancleNotification(context,WifiNotiState);
					// alarm
					if (WifiState) {
						ringAlam(context);
					}WifiState = false;
				}
			} catch (Exception e) {
				Log.e(TAG, "Exception e ::" + e);
				Log.e(TAG, "failed wifi ");
				// cancel notification
				cancleNotification(context,WifiNotiState);
			}
		} else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			Log.d(TAG, "WIFI_STATE_CHANGED_ACTION");
		
		} else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			Log.d(TAG, "WIFI_STATE_CHANGED_ACTION");
		}
	}
	

	
	
	
/**Notification START**/	
	public static void setNotification(Context mContext, int notiState){
		int notiImageIcon = 0;
		if(notiState == WifiNotiState){
			notiImageIcon = R.drawable.goout;
		}else {//if(notiState == alarmNotiState){
			notiImageIcon = R.drawable.ic_launcher;
		}
		notiMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
		Intent goIntent = new Intent(mContext,TenVersionActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, notiState, goIntent,PendingIntent.FLAG_CANCEL_CURRENT);

		Notification notification = new Notification(notiImageIcon, "TenVersion",System.currentTimeMillis());
		notification.setLatestEventInfo(mContext, "TenVersion","TenVersion 에서 설정을 하실 수 있습니다.", pendingIntent);
		notification.flags = Notification.FLAG_NO_CLEAR;
		notiMgr.notify(notiState, notification);
	}
	
	public static void cancleNotification(Context context, int notiState){
		notiMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		notiMgr.cancel(notiState);
	}
/**Notification END**/
	
	
	
/**SOUND START**/
	//ringing alarm
	public void ringAlam(Context context) {
		Log.v(TAG, "ringAlam()");			
			 Intent intent = new Intent();
			 intent.setClassName("com.smartschool.tenversion", "com.smartschool.tenversion.FlipperView");
			 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 context.startActivity( intent);
			
			 //start vibrator
			 startVibrate(context);							// ..............add setting vibrate
			 
			 //start alarm
			 startSound(context, R.raw.dingdong);     	// ..............add select sound
		
	}
	
	//set vibrate
	public static void startVibrate(Context mContext){
		  boolean vibrateState = WifiReceiver.getBooleanPrefence(mContext,KEY_VIBRATE);
		  if(vibrateState){
			 Vibrator vibrator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);
			 vibrator.vibrate(VIBRATE_TIME);  //2sec
		  }
	}
	//set Sound
	public void startSound(Context context, int id) {
		Log.v(TAG, "startSound()");
		boolean alramState = WifiReceiver.getBooleanPrefence(context, KEY_SOUND);
		if (alramState) {

			try {
				MediaPlayer mplay = MediaPlayer.create(context, id);
				if (mplay == null) {
					mplay = MediaPlayer.create(context, R.raw.dingdong);
				}
				mplay.seekTo(0);
				mplay.start();
		
			} catch (Exception e) {
				Log.v(TAG, "e::"+e);
			}
		}
		

	}

	public void stopSound(Context context, int id) {
		Log.v(TAG, "soundPlay()");
        try {
            if(mplay == null) {
            	mplay = MediaPlayer.create(context,  R.raw.dingdong);
            }
            mplay.stop();
            mplay = null;
        }catch( Exception e ) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
	}
/**SOUND END**/


	

	
	
	
	

/**WIFI START**/
//	public static boolean initWifiSetting(Context context) {
//		Log.v(TAG, "initWifiSetting()");
//
//		if (SettingListActivity.wifiListPref == null) {
//			SettingListActivity.wifiListPref.setEnabled(false);
//			return false;
//		}
//
//		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//		if (wifiManager == null) {
//			SettingListActivity.wifiListPref.setEnabled(false);
//			return false;
//		}
//
//		int scanCount = 0;
//
//		// init WIFISCAN
//		List<ScanResult> mScanResult; // ScanResult List
//		mScanResult = wifiManager.getScanResults(); // ScanResult
//		if (mScanResult == null) {
//			Log.v(TAG, "mScanResult==null");
//			SettingListActivity.wifiListPref.setEnabled(false);
//			return false;
//			
//		} else {
//			Log.v(TAG, "mScanResult!=null");
//			SettingListActivity.wifiListPref.setEnabled(true);
//		}
//		CharSequence[] entries = new CharSequence[mScanResult.size()+1];
//		CharSequence[] entryValues = new CharSequence[mScanResult.size()+1];
//
//		// Scan count
//		Log.v(TAG, "Scan count is \t" + ++scanCount + " times \n");
//
//		entries[0] = "설정안함";
//		entryValues[0] = "";
//		
//		Log.v(TAG, "=======================================\n");
//		for (int i = 0; i < mScanResult.size(); i++) {
//			ScanResult result = mScanResult.get(i);
//			Log.v(TAG, (i + 1) + ". SSID : " + result.SSID.toString()
//					+ "\t\t RSSI : " + result.level + " dBm\n");
//
//			entries[i+1] = result.SSID.toString();
//			entryValues[i+1] = result.BSSID.toString();
//
//			Log.d(TAG, (i + 1) + ". entries : " + entries[i+1]);
//			Log.d(TAG, (i + 1) + ". entryValues : " + entryValues[i+1]);
//		}
//		Log.v(TAG, "=======================================\n");
//
//		SettingListActivity.wifiListPref.setEntries(entries);
//		SettingListActivity.wifiListPref.setEntryValues(entryValues);
//		return true;
//	}
	


	public static void compairWifiConnectionMode(Context context){
		String connectedWifi = getConnectedWifiBSSID(context);
		String wifimode = getWifiSettingBSSID(context);
		Log.e(TAG, "connectedWifi::" + connectedWifi + ", wifimode"+ wifimode);
		if (connectedWifi == null || wifimode == null) {
			Log.e(TAG, "failed wifi info");
			cancleNotification(context,WifiNotiState);
			Toast.makeText(context, "failed wifi info", Toast.LENGTH_SHORT);
		}

		if (connectedWifi != null && connectedWifi.equals(wifimode)) { // same  connected wifi mode
			Log.d(TAG, "noti.start");
			// set Notification
			setNotification(context,WifiNotiState);
			WifiState = true;
		} else {
			cancleNotification(context,WifiNotiState);
		}
	}
	
	
	public static String getConnectedWifiSSID(Context mContext) {
		Log.v(TAG, "getConnectedWifiSSID()");
		wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		String connectedWifiSSID = wifiManager.getConnectionInfo().getSSID();

		return connectedWifiSSID;
	}

	public static String getConnectedWifiBSSID(Context mContext) {
		Log.v(TAG, "getConnectedWifiBSSID()");
		wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		String connectedWifiBSSID = wifiManager.getConnectionInfo().getBSSID();

		return connectedWifiBSSID;
	}

	public static String getWifiSettingSSID(Context mContext) {
		SharedPreferences sharedPrefs = mContext.getSharedPreferences(KEY_WIFI_MODE, Context.MODE_PRIVATE);
		String wifiSSID = sharedPrefs.getString(KEY_WIFISSID, null);
		return wifiSSID;
	}

	public static String getWifiSettingBSSID(Context mContext) {
		SharedPreferences sharedPrefs = mContext.getSharedPreferences(KEY_WIFI_MODE, Context.MODE_PRIVATE);
		String wifiBSSID = sharedPrefs.getString(KEY_WIFIBSSID, "");
		return wifiBSSID;
	}
/**WIFI END**/
	
	
/**checkbox sharedPreference START**/
	public static void setBooleanPrefence(Context mContext, String key, boolean check){
		SharedPreferences sharedPrefs = mContext.getSharedPreferences(key,Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
        
        editor.putBoolean(key, check); 
        editor.commit();
	}
	
	public static boolean getBooleanPrefence(Context mContext,String key){
		SharedPreferences sharedPrefs = mContext.getSharedPreferences(key,Context.MODE_PRIVATE);
		boolean checkBoxState = sharedPrefs.getBoolean(key, false);
		return checkBoxState;
	}
/**set checkbox sharedPreference END**/
}
