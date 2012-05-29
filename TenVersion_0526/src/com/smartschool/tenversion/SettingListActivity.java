package com.smartschool.tenversion;



import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.Button;

public class SettingListActivity extends PreferenceActivity  implements OnPreferenceClickListener,OnPreferenceChangeListener{
	public static final String TAG = "SettingListActivity";
	public static  String WIFI_MODE = null;
	public static final String KEY_WIFIMODE = "home_wifi";
	
	public static ListPreference wifiListPref = null;
	private static Context context;
	
	private SharedPreferences sharedPrefs = null;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.prefs);
        
        context= this;
        
        wifiListPref = (ListPreference) findPreference(KEY_WIFIMODE);  // (R.id.wifi_listpref);
        wifiListPref.setOnPreferenceClickListener(this);
        wifiListPref.setOnPreferenceChangeListener(this);
        wifiListPref.setValue(WIFI_MODE);
      
        
        
        sharedPrefs = this.getSharedPreferences(KEY_WIFIMODE, Context.MODE_PRIVATE);
        String wifimode = getWifiSetting();
        if(wifimode != null){
        	if(wifimode.equals(WIFI_MODE)){
        		Log.v(TAG,"wifimode = WIFI_MODE" );
        		wifiListPref.setSummary(WIFI_MODE);
        		
        	}
        }

    }

	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG,"onResume()" );

		 
		initWifiSetting();
	}


	public boolean onPreferenceClick(Preference preference) {
		Log.v(TAG,"onPreferenceClick()" );
		
		initWifiSetting();
		return false;
	}
	
	public boolean onPreferenceChange(Preference preference, Object objValue){
		String wifiMode = objValue.toString(); 
		if(preference == wifiListPref){

			//sharedPrefs.
			Editor editor = sharedPrefs.edit();
            
            editor.putString(KEY_WIFIMODE, wifiMode); //SSID
           
            editor.commit();
			
			WIFI_MODE = wifiMode;
			wifiListPref.setSummary(WIFI_MODE);
			  wifiListPref.setValue(WIFI_MODE);
		}
		return false;
	}
	
	
	
	public boolean initWifiSetting(){
		Log.v(TAG,"initWifiSetting()" );

        WifiManager	wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(wifiManager==null){
        	return false;
        }
        
        int scanCount = 0;
        
     // init WIFISCAN
        
        List<ScanResult> mScanResult; // ScanResult List
        mScanResult = wifiManager.getScanResults(); // ScanResult
        if(mScanResult==null){
        	Log.v(TAG,"mScanResult==null" );
        	wifiListPref.setEnabled(false);
        	return false;
        }else{
        	Log.v(TAG,"mScanResult!=null" );
        	wifiListPref.setEnabled(true);
        }
		CharSequence[] entries = new CharSequence[mScanResult.size()];
		//CharSequence[] entryValues = new CharSequence[mScanResult.size()];
        
        // Scan count
		Log.v(TAG,"Scan count is \t" + ++scanCount + " times \n");

		Log.v(TAG,"=======================================\n");
		for (int i = 0; i < mScanResult.size(); i++) {
			ScanResult result = mScanResult.get(i);
			Log.v(TAG,(i + 1) + ". SSID : " + result.SSID.toString()
					+ "\t\t RSSI : " + result.level + " dBm\n");
			
			entries[i] = result.SSID.toString();
			
			Log.d(TAG,(i + 1) + ". entries : "+entries[i]);
		//	entryValues[i] = result.SSID.toString();
			
		}
		Log.v(TAG,"=======================================\n");
		
        
        wifiListPref.setEntries(entries);
        wifiListPref.setEntryValues(entries);
        return true;
	}
	
	public String getWifiSetting(){
		return sharedPrefs.getString(KEY_WIFIMODE, null);
	}
	
//	 prefs = getContext().getSharedPreferences(getKey(), Context.MODE_PRIVATE);
//	 idEdit.setText(prefs.getString("id", ""));
	
}