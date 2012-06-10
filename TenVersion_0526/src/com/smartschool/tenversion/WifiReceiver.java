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
import android.widget.Toast;

public class WifiReceiver extends BroadcastReceiver{
	//wifi Key
	public static final String TAG = "WifiReceiver";
	public static final String KEY_WIFI_MODE = "wifi_mode";
	public static final String KEY_WIFISSID = "wifi_ssid";
	public static final String KEY_WIFIBSSID = "wifi_bssid";
	//hw Key
	public static final String KEY_ALARM = "alarm_mode";
	public static final String KEY_VIBRATE = "vibrator_mode";
	
	//wifi
	private static WifiManager wifiManager =null;
	private static NotificationManager notiMgr = null;
	private List<ScanResult> mScanResult; // ScanResult List
	//private static Context mContext;
	
	//wifi state
	public static  boolean WifiState = false;
	
	//alarm
	private static MediaPlayer mplay = null;
	
	//vibrator
	private static int VIBRATE_TIME = 1000;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceive() context ::" + context);

		//mContext = context;

		String action = intent.getAction();

		// 네트웍에 변경이 일어났을때 발생하는 부분
		if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
			Log.d(TAG, "CONNECTIVITY_ACTION");

			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

			try {
				NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

				int networkType = networkInfo.getType();
				if (networkType == ConnectivityManager.TYPE_WIFI) {
					// wifi network
					Log.d(TAG, "wifi network");

					if (SettingListActivity.wifiListPref != null) {
						// SettingListActivity.wifiListPref.setEnabled(true);
						initWifiSetting(context);
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
						setNotification(context);

						WifiState = true;

					} else { 													// disconnected wifi mode
						Log.d(TAG, "noti.cancel");
						// cancel notification
						cancleNotification(context);

						// alarm
						ringAlam(context);
					}

				} else if (networkType == ConnectivityManager.TYPE_MOBILE) {	// disconnected wifi mode
					// mobile network
					Log.d(TAG, "mobile network");

					if (SettingListActivity.wifiListPref != null) {
						SettingListActivity.wifiListPref.setEnabled(false);
					}

					// cancel notification
					cancleNotification(context);
					// alarm
					ringAlam(context);
				}
			} catch (Exception e) {
				Log.e(TAG, "Exception e ::" + e);
				Log.e(TAG, "failed wifi ");
				// cancel notification
				cancleNotification(context);
			}
		} else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
			Log.d(TAG, "WIFI_STATE_CHANGED_ACTION");
		}
	}
	

	
	
	
/**Notification START**/	
	public static void setNotification(Context mContext){
		notiMgr = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
		Intent goIntent = new Intent(mContext,TenVersionActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, goIntent,PendingIntent.FLAG_CANCEL_CURRENT);

		Notification notification = new Notification(R.drawable.ic_launcher, "TenVersion",System.currentTimeMillis());
		notification.setLatestEventInfo(mContext, "TenVersion","TenVersion 에서 설정을 하실 수 있습니다.", pendingIntent);
		notification.flags = Notification.FLAG_NO_CLEAR;
		notiMgr.notify(0, notification);
	}
	
	public static void cancleNotification(Context context){
		notiMgr = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		notiMgr.cancel(0);
	}
/**Notification END**/
	
	
	
/**SOUND START**/
	//ringing alarm
	public void ringAlam(Context context) {
		Log.v(TAG, "ringAlam()");
		if (WifiState) {
			// set alarm
//			int SECS = 1000;
//			int MINS = 60 * SECS;
//		//	Calendar cal = Calendar.getInstance();
//			Intent goIntent = new Intent(mContext, alarmDialogActivity.class);
//			PendingIntent pi = PendingIntent.getActivity(mContext, 0, goIntent,PendingIntent.FLAG_CANCEL_CURRENT);
//			AlarmManager alarms = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//			alarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),10 * MINS, pi);

			
			 // start flipperView
//			 Intent intent = new Intent(mContext,FlipperView.class);
//			 mContext.startActivity(intent);
//			 
//			 Intent i = new Intent("com.smartschool.tenversion", "com.smartschool.tenversion.FlipperView");
//			 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			 context.startActivity(i);
//			 
			
			 Intent intent = new Intent();
			 intent.setClassName("com.smartschool.tenversion", "com.smartschool.tenversion.FlipperView");
			 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			 context.startActivity( intent);
			
			 //start vibrator
			 startVibrate(context);							// ..............add setting vibrate
			 
			 //start alarm
			 startSound(context, R.raw.dingdong);     	// ..............add select sound
			

		}
		WifiState = false;
	}
	
	//set vibrate
	public static void startVibrate(Context mContext){
		  boolean vibrateState = WifiReceiver.getCheckBoxPrefence(mContext,KEY_VIBRATE);
		  if(vibrateState){
			 Vibrator vibrator = (Vibrator)mContext.getSystemService(Context.VIBRATOR_SERVICE);
			 vibrator.vibrate(VIBRATE_TIME);  //2sec
		  }
	}
	//set Sound
	public void startSound(Context context, int id) {
		Log.v(TAG, "startSound()");
		boolean alramState = WifiReceiver.getCheckBoxPrefence(context, KEY_ALARM);
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
	public static boolean initWifiSetting(Context context) {
		Log.v(TAG, "initWifiSetting()");

		if (SettingListActivity.wifiListPref == null) {
			SettingListActivity.wifiListPref.setEnabled(false);
			return false;
		}

		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager == null) {
			SettingListActivity.wifiListPref.setEnabled(false);
			return false;
		}

		int scanCount = 0;

		// init WIFISCAN
		List<ScanResult> mScanResult; // ScanResult List
		mScanResult = wifiManager.getScanResults(); // ScanResult
		if (mScanResult == null) {
			Log.v(TAG, "mScanResult==null");
			SettingListActivity.wifiListPref.setEnabled(false);
			return false;
			
		} else {
			Log.v(TAG, "mScanResult!=null");
			SettingListActivity.wifiListPref.setEnabled(true);
		}
		CharSequence[] entries = new CharSequence[mScanResult.size()];
		CharSequence[] entryValues = new CharSequence[mScanResult.size()];

		// Scan count
		Log.v(TAG, "Scan count is \t" + ++scanCount + " times \n");

		Log.v(TAG, "=======================================\n");
		for (int i = 0; i < mScanResult.size(); i++) {
			ScanResult result = mScanResult.get(i);
			Log.v(TAG, (i + 1) + ". SSID : " + result.SSID.toString()
					+ "\t\t RSSI : " + result.level + " dBm\n");

			entries[i] = result.SSID.toString();
			entryValues[i] = result.BSSID.toString();

			Log.d(TAG, (i + 1) + ". entries : " + entries[i]);
			Log.d(TAG, (i + 1) + ". entryValues : " + entryValues[i]);
		}
		Log.v(TAG, "=======================================\n");

		SettingListActivity.wifiListPref.setEntries(entries);
		SettingListActivity.wifiListPref.setEntryValues(entryValues);
		return true;
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
		String wifiBSSID = sharedPrefs.getString(KEY_WIFIBSSID, null);
		return wifiBSSID;
	}
/**WIFI END**/
	
	
/**checkbox sharedPreference START**/
	public static void setCheckBoxPrefence(Context mContext, String key, boolean check){
		SharedPreferences sharedPrefs = mContext.getSharedPreferences(key,Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
        
        editor.putBoolean(key, check); 
        editor.commit();
	}
	
	public static boolean getCheckBoxPrefence(Context mContext,String key){
		SharedPreferences sharedPrefs = mContext.getSharedPreferences(key,Context.MODE_PRIVATE);
		boolean checkBoxState = sharedPrefs.getBoolean(key, false);
		return checkBoxState;
	}
/**set checkbox sharedPreference END**/
}
