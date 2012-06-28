package com.smartschool.tenversion;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class SettingActivity extends Activity implements OnClickListener{
	private static final String TAG ="SettingActivity";
	/**  UI  **/
	//String
	public static String not_wifi_mode_str;
	//set Button
	public static RelativeLayout wifiLayout = null;
	public static RelativeLayout wifiLayout_disable = null;
	public static TextView wifiSummaryTV = null;
	
	public static TextView alarmSummaryTV = null;
	
	public static ToggleButton soundTB = null;
	public static ToggleButton vibrateTB = null;

	//set shared Preference
	public static final String KEY_WIFI_MODE = "wifi_mode";
	public static final String KEY_WIFISSID = "wifi_ssid";
	public static final String KEY_WIFIBSSID = "wifi_bssid";

	public static final String KEY_SOUND = "sound_mode";
	public static final String KEY_VIBRATOR = "vibrator_mode";
	
	//dialog
    private CustomDialog settingDialog = null ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
		/**  UI  **/
		//wifi layout
        wifiLayout = (RelativeLayout)findViewById(R.id.wifi_layout);
        wifiLayout_disable = (RelativeLayout)findViewById(R.id.wifi_layout_disable);
//        wifiLayout.setOnClickListener(this);
        
        TextView wifiTV = (TextView)findViewById(R.id.wifi_tv);
        TextView wifiSummaryTV = (TextView)findViewById(R.id.wifi_summary_tv);
        Button wifiBtn= (Button)findViewById(R.id.wifi_btn);
        wifiBtn.setOnClickListener(this);
               
        //alram layout
        RelativeLayout alramLayout =(RelativeLayout)findViewById(R.id.alram_layout);
        alramLayout.setOnClickListener(this);
        
        TextView alramTV = (TextView)findViewById(R.id.alram_tv);
        alarmSummaryTV= (TextView)findViewById(R.id.alram_summary_tv);
        Button alarmBtn= (Button)findViewById(R.id.alram_btn);
        alarmBtn.setOnClickListener(this);
        
        //sound layout
        RelativeLayout soundLayout =(RelativeLayout)findViewById(R.id.sound_layout);
        soundLayout.setOnClickListener(this);
        
        TextView soundTV =(TextView)findViewById(R.id.sound_tv);
        soundTB= (ToggleButton)findViewById(R.id.sound_togglebtn);
        soundTB.setOnClickListener(this);
        Boolean soundState = WifiReceiver.getBooleanPrefence(this, KEY_SOUND);
		soundTB.setChecked(soundState);
		
        //vibrate layout
        RelativeLayout vibrateLayout =(RelativeLayout)findViewById(R.id.vibrate_layout);
        vibrateLayout.setOnClickListener(this);
        
        TextView vibrateTV =(TextView)findViewById(R.id.vibrate_tv);
        vibrateTB= (ToggleButton)findViewById(R.id.vibrate_togglebtn);
        vibrateTB.setOnClickListener(this);
        Boolean vibrateState = WifiReceiver.getBooleanPrefence(this, KEY_VIBRATOR);
        vibrateTB.setChecked(vibrateState);
        
        //help layout
        RelativeLayout helpLayout =(RelativeLayout)findViewById(R.id.help_layout);
        helpLayout.setOnClickListener(this);
        
        TextView helpTV =(TextView)findViewById(R.id.help_tv);
        
        //version layout
        RelativeLayout versionLayout =(RelativeLayout)findViewById(R.id.version_layout);
        versionLayout.setOnClickListener(this);
        
        TextView versionTV =(TextView)findViewById(R.id.version_tv);
   
    

    }
    
	@Override
	protected void onResume() {
		super.onResume();
		Log.v(TAG, "onResume()");

		initWifiSetting();
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.wifi_layout:
			Log.v(TAG,"onclick() wifi_layout ");
			chooseWifiDialog();
			break;
		case R.id.wifi_btn:
			Log.v(TAG,"onclick() wifi_btn ");
			chooseWifiDialog();
			break;
			
		case R.id.alram_layout:
			Log.v(TAG,"onclick() alram_layout ");
			setAlramDialog();
			break;
		case R.id.alram_btn:
			Log.v(TAG,"onclick() alram_btn ");
			setAlramDialog();
			break;
		case R.id.sound_layout:
			Log.v(TAG,"onclick() sound_layout ");

			break;
		case R.id.vibrate_layout:
			Log.v(TAG,"onclick() vibrate_layout ");

			break;
		case R.id.help_layout:
			Log.v(TAG,"onclick() help_layout ");

			break;
		case R.id.version_layout:
			Log.v(TAG,"onclick() version_layout ");
			Intent intent = new Intent(this,VersionInfo.class);
			startActivity(intent);

			break;
		case R.id.sound_togglebtn:
			Log.v(TAG,"onclick() sound_togglebtn ");
			Boolean soundState = WifiReceiver.getBooleanPrefence(this, KEY_SOUND);
			if(soundState){
				soundTB.setChecked(false);
				WifiReceiver.setBooleanPrefence(this,KEY_SOUND, false);
			}else{
				soundTB.setChecked(true);
				WifiReceiver.setBooleanPrefence(this,KEY_SOUND, true);
			}
			
			break;
		case R.id.vibrate_togglebtn:
			Log.v(TAG,"onclick() vibrate_togglebtn ");
			Boolean vibrateState = WifiReceiver.getBooleanPrefence(this, KEY_VIBRATOR);
			if(vibrateState){
				vibrateTB.setChecked(false);
				WifiReceiver.setBooleanPrefence(this,KEY_VIBRATOR, false);
			}else{
				vibrateTB.setChecked(true);
				WifiReceiver.setBooleanPrefence(this,KEY_VIBRATOR, true);
			}
			break;
//		case R.id.addBtn:
//
//			break;
//		case R.id.addBtn:
//
//			break;
//		case R.id.addBtn:
//
//			break;
//		case R.id.addBtn:
//
//			break;
//		case R.id.addBtn:
//
//			break;
//		case R.id.addBtn:
//
//			break;
//		case R.id.addBtn:
//
//			break;
//		case R.id.addBtn:
//
//			break;
	
		}
		
	}
	
	
//Wifi START
	public void initWifiSetting() {
		Log.v(TAG, "initWifiSetting()");

		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		List<ScanResult> mScanResult = wifiManager.getScanResults(); // ScanResult
		if (wifiManager == null || mScanResult == null) {
			Toast.makeText(this, "선택 할  Wi-Fi가 없습니다.", Toast.LENGTH_LONG).show();
			wifiLayout.setEnabled(false);
			wifiLayout_disable.setVisibility(View.VISIBLE);
			for (int i = 0; i < wifiLayout.getChildCount(); i++) {
				View view = wifiLayout.getChildAt(i);
				view.setEnabled(false);  //child view
			}
		} else {
			wifiLayout.setEnabled(true);
			wifiLayout_disable.setVisibility(View.GONE);
			for (int i = 0; i < wifiLayout.getChildCount(); i++) {
				View view = wifiLayout.getChildAt(i);
				view.setEnabled(true); //child view
			}
		}

        String wifiSSID = WifiReceiver.getWifiSettingSSID(this);
        if(wifiSSID == null){
        	  wifiSummaryTV.setText(getResources().getText(R.string.not_wifi_ssid));
        }else{
        	  wifiSummaryTV.setText(wifiSSID);
        }
       
//        String wifiBSSID = WifiReceiver.getWifiSettingBSSID(this);
//        wifiSummaryTV.setText(wifiBSSID);	
	}
	
	public static ArrayList<WifiListProfile> getWifiScanList(Context context) {
		Log.v(TAG, "getWifiScanList()");

		WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// init WIFISCAN
		List<ScanResult> mScanResult = wifiManager.getScanResults(); // ScanResult
			
		ArrayList<WifiListProfile> wifiListItem = new ArrayList<WifiListProfile>(); 
		wifiListItem.add(new WifiListProfile(context.getResources().getText(R.string.not_setting).toString(), "",""));
		
		if (wifiManager != null && mScanResult != null) {
			Log.v(TAG, "=======================================\n");
			for (int i = 0; i < mScanResult.size(); i++) {
				ScanResult result = mScanResult.get(i);
//				Log.v(TAG, (i + 1) + ". SSID : " + result.SSID.toString()+ "\t\t RSSI : " + result.level + " dBm\n");			
//				Log.v(TAG, "frequency : "+result.frequency);
//				Log.v(TAG, "capabilities : "+result.capabilities);
//				Log.v(TAG, "describeContents : "+result.describeContents());
				String ssid  = result.SSID.toString();
				String bssid = result.BSSID.toString();
				String rssi = String.valueOf(result.level); 
				
				wifiListItem.add(new WifiListProfile(ssid, bssid, rssi));
			}
			Log.v(TAG, "=======================================\n");
		}
		return wifiListItem;
	}
	
	/**Custom dialog START**/    
    public void chooseWifiDialog(){ 
    	Log.v(TAG,"[chooseWifiDialog]");
    	CustomDialog customDialog =  new CustomDialog(this, R.style.Dialog);   
    	customDialog.chooseWifiDialog(getWifiScanList(this));
    	customDialog.show();
    }
    public  void  chooseWifi(WifiListProfile item){
	   	 Log.v(TAG,"[chooseWifi] ");
		String ssid = item.getSSID();
		String bssid = item.getBSSID();
//		String rssi = item.getRSSI();
		
		wifiSummaryTV.setText(ssid);
		setWifiSetting(ssid,bssid);
		
		WifiReceiver.compairWifiConnectionMode(this);
    }
    public void setAlramDialog(){ 
    	Log.v(TAG,"[setAlramDialog]");
    	CustomDialog customDialog =  new CustomDialog(this, R.style.Dialog);   
    	customDialog.alarmDialog();
    	customDialog.show();
    }
    public  void  setAlram(String time){
	   	 Log.v(TAG,"[setAlram] ");
	   	 alarmSummaryTV.setText("");
    }
    

	// set wifi sharedPreference
	public void setWifiSetting(String wifiSSID, String wifiBSSID) {
		SharedPreferences sharedPrefs = this.getSharedPreferences(KEY_WIFI_MODE, Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();

		editor.putString(KEY_WIFISSID, wifiSSID); // SSID
		editor.putString(KEY_WIFIBSSID, wifiBSSID); // BSSID

		editor.commit();
	}
	
//Wifi END
	
	
//Alram START	
	
	
//Alram END
	

//	// set checkBoxPreference Summary
//	public void setCheckBoxSummary(Preference pref, String hwMode,
//			String checkState) {
//		if (checkState.equals("true")) {
//			checkStateStr = " on";
//		} else {
//			checkStateStr = " off";
//		}
//		pref.setSummary(hwMode + checkStateStr);
//	}
	
}
