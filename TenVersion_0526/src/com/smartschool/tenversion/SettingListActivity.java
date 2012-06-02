package com.smartschool.tenversion;



import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

public class SettingListActivity extends PreferenceActivity  implements OnPreferenceClickListener,OnPreferenceChangeListener{
	public static final String TAG = "SettingListActivity";
//	public static  String WIFI_MODE = null;
	public static final String KEY_WIFI_MODE = "wifi_mode";
	public static final String KEY_WIFISSID = "wifi_ssid";
	public static final String KEY_WIFIBSSID = "wifi_bssid";
	
	public static ListPreference wifiListPref = null;
	
//	private SharedPreferences sharedPrefs = null;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.prefs);

        wifiListPref = (ListPreference) findPreference(KEY_WIFI_MODE);  // (R.id.wifi_listpref);
        wifiListPref.setOnPreferenceClickListener(this);
        wifiListPref.setOnPreferenceChangeListener(this);
       
      
        

		String wifimode = WifiReceiver.getWifiSettingSSID(this);
		if (wifimode != null) {
			Log.v(TAG, "wifimode != null ::"+wifimode);
			wifiListPref.setSummary(wifimode);
			wifiListPref.setValue(wifimode);

		}

    }

	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG,"onResume()" );

		 
		WifiReceiver.initWifiSetting(this);
	}


	public boolean onPreferenceClick(Preference preference) {
		Log.v(TAG,"onPreferenceClick()" );
		
		WifiReceiver.initWifiSetting(this);
		return false;
	}
	
	public boolean onPreferenceChange(Preference preference, Object objValue){
		Log.v(TAG,"onPreferenceChange()  objValue::"+objValue.toString() );

		if(preference == wifiListPref){
			String wifiBSSID = objValue.toString() ;
			String wifiSSID = null;

			CharSequence[] entries = wifiListPref.getEntries();
			CharSequence[] entryValues = wifiListPref.getEntryValues();
			
			Log.v(TAG,"=======================================\n");
			for (int i = 0; i <entryValues.length; i++) {
				if(entryValues[i].equals(wifiBSSID)){
					Log.d(TAG,(i + 1) + "entries[i].equals(wifiBSSID)");
					Log.d(TAG,(i + 1) + ". entries : "+entries[i]);
					
					wifiSSID = entries[i].toString();
					//set sharedPrefs.
					setWifiSetting(wifiSSID,wifiBSSID);
					wifiListPref.setSummary(wifiSSID);
					wifiListPref.setValue(wifiBSSID);
				}
			}
			Log.v(TAG,"=======================================\n");
			
			
			
			
			String connectedWifi = WifiReceiver.getConnectedWifiBSSID(this);
			String wifimode = WifiReceiver.getWifiSettingBSSID(this);
			Log.e(TAG, "connectedWifi::"+connectedWifi+", wifimode"+wifimode);
			if(connectedWifi == null || wifimode == null){
				Log.e(TAG, "failed wifi info");
				WifiReceiver.cancleNotification(this);
				Toast.makeText(this, "failed wifi info", Toast.LENGTH_SHORT);
				
			}
				
			if (connectedWifi != null && connectedWifi.equals(wifimode)) {					//connected wifi mode
				Log.d(TAG, "noti.start");
				
				//set Notification
				WifiReceiver.setNotification(this);
				WifiReceiver.WifiState = true;
			}else{
				WifiReceiver.cancleNotification(this);
			}
			
		}
		return false;
	}
	
	
	

	
	//set sharedPreference
	public void setWifiSetting(String wifiSSID,String wifiBSSID){
		SharedPreferences sharedPrefs = this.getSharedPreferences(KEY_WIFI_MODE,Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
        
        editor.putString(KEY_WIFISSID, wifiSSID); //SSID
        editor.putString(KEY_WIFIBSSID, wifiBSSID); //BSSID
       
        editor.commit();
	}
}