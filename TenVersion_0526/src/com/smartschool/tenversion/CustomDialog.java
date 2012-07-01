package com.smartschool.tenversion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class CustomDialog extends Dialog implements View.OnClickListener,
		OnItemClickListener {
	private static final String TAG = "CustomDialog";

	private Context mContext = null;
	private int MODE;

	// add ListDialog
	private EditText addEditTxt = null;
	// modify ListDialog
	private EditText modifyEditTxt = null;
	// alarm Dialog
	private TimePicker mTime = null;
	//notification state
	public static final int alarmNotiState = 11;
	
	public CustomDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	/** LIST Dialog START **/
	// addList dialog safe,live,etc Activity
	public void addListDialog(int mode) {
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;
		getWindow().setAttributes(lpWindow);

		MODE = mode;
		setContentView(R.layout.dialog_addlist);
		addEditTxt = (EditText) findViewById(R.id.addlist_edittxt);

		Button okBtn = (Button) findViewById(R.id.addlist_okBtn);
		okBtn.setOnClickListener(this);
		Button cancelBtn = (Button) findViewById(R.id.addlist_cancelBtn);
		cancelBtn.setOnClickListener(this);

	}

	public void delListDialog(int mode, String contents) {
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;
		getWindow().setAttributes(lpWindow);

		MODE = mode;
		setContentView(R.layout.dialog_dellist);

		TextView contentsTxt = (TextView) findViewById(R.id.dellist_testview);
		contentsTxt.setText(contents);

		Button deleteBtn = (Button) findViewById(R.id.dellist_deleteBtn);
		deleteBtn.setOnClickListener(this);
		Button cancelBtn = (Button) findViewById(R.id.dellist_cancelBtn);
		cancelBtn.setOnClickListener(this);

	}

	public void modifyListDialog(int mode, String contents) {
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;	
		getWindow().setAttributes(lpWindow);

		MODE = mode;
		setContentView(R.layout.dialog_modifylist);

		modifyEditTxt = (EditText) findViewById(R.id.modifylist_editview);
		modifyEditTxt.setText(contents);

		// TextView contentsTxt =
		// (TextView)findViewById(R.id.modifylist_textview);
		// contentsTxt.setText(contents);

		Button modifyBtn = (Button) findViewById(R.id.modifylist_modifyBtn);
		modifyBtn.setOnClickListener(this);
		Button cancelBtn = (Button) findViewById(R.id.modifylist_cancelBtn);
		cancelBtn.setOnClickListener(this);

	}

	/** LIST Dialog END **/

	/** FUN Dialog START **/
	public void funDialog() {
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.dialog_funlist);

		String[] funlistArray = mContext.getResources().getStringArray(
				R.array.fun_list);
		ArrayList<String> funList = new ArrayList<String>(funlistArray.length);

		for (int i = 0; i < funlistArray.length; i++) {
			Log.v(TAG, "funList " + i + " :: " + funlistArray[i].toString());
			funList.add(funlistArray[i].toString());
		}
		Collections.shuffle(funList);

		TextView funTxt = (TextView) findViewById(R.id.fundialog_textview);
		funTxt.setText(funList.get(1).toString());

		Button okBtn = (Button) findViewById(R.id.fundialog_okBtn);
		okBtn.setOnClickListener(this);
	}

	/** FUN Dialog END **/

	/** Setting Dialog START **/
	// choose Wifi Dialog
	public void chooseWifiDialog(ArrayList<WifiListProfile> wifiListItem) {
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.dialog_choose_wifi);

		Button cancelBtn = (Button) findViewById(R.id.choose_wifi_cancelBtn);
		cancelBtn.setOnClickListener(this);

		ListView chooseWifiListview = (ListView) findViewById(R.id.choose_wifi_listview);

		// ArrayList<WifiListProfile> wifiListItem = new
		// ArrayList<WifiListProfile>();
		WifiListAdapter wifiListAdapter = new WifiListAdapter(mContext,R.layout.wifilist_item_row, wifiListItem);
		Log.v(TAG, "wifiListAdapter ::" + wifiListAdapter);
		chooseWifiListview.setAdapter(wifiListAdapter);
		chooseWifiListview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		chooseWifiListview.setOnItemClickListener(this);

		String wifimode = WifiReceiver.getWifiSettingBSSID(mContext);
		chooseWifiListview.setItemChecked(0, true);
		if (wifimode != null) {
			for (int i = 0; i < wifiListItem.size(); i++) {
				String searchWifiMode = wifiListItem.get(i).mBSSID;
				if (wifimode.equals(searchWifiMode)) {
					chooseWifiListview.setItemChecked(i, true);
				}
			}
		}
	}

	/** Setting Dialog END **/

	/** Alarm Dialog START **/
	public void alarmDialog() {
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;
		getWindow().setAttributes(lpWindow);

		setContentView(R.layout.dialog_alarm);
		Calendar mCalendar = Calendar.getInstance();
	
		// 일시 설정 클래스로 현재 시각을 설정
//		DatePicker mDate = (DatePicker) findViewById(R.id.date_picker);
//		mDate.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),mCalendar.get(Calendar.DAY_OF_MONTH), this);
		mTime = (TimePicker) findViewById(R.id.time_picker);
		mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
		mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
//		mTime.setOnTimeChangedListener(this);
		Log.v(TAG, mCalendar.getTime().toString());			
				
		// 셋 버튼, 리셋버튼의 리스너를 등록
		Button alarmSetBtn = (Button) findViewById(R.id.alarm_set_btn);
		alarmSetBtn.setOnClickListener(this);
		
		Button alarmResetBtn = (Button) findViewById(R.id.alarm_reset_btn);
		alarmResetBtn.setOnClickListener(this);
	
	}
	static int mAlarmCode = 2030;
	public static void registerAlarm(Context context, int hour,int minute) {
		Log.e(TAG, "registerAlarm hour :: "+hour+"   minute::"+minute);
		PendingIntent padingIntent_Alarm = PendingIntent.getBroadcast(context, mAlarmCode,new Intent(WifiReceiver.ALARM_ACTION), 0);
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTimeInMillis(System.currentTimeMillis());
		mCalendar.set(Calendar.HOUR_OF_DAY, hour);
		mCalendar.set(Calendar.MINUTE, minute);
		mCalendar.set(Calendar.SECOND,0);
		mCalendar.set(Calendar.MILLISECOND,0);
		Log.e(TAG, "mCalendar getTime :: "+mCalendar.getTime().toString());
		manager.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, padingIntent_Alarm);
	}
	public static void stopAlarm(Context context) {
		AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		manager.cancel(PendingIntent.getBroadcast(context, mAlarmCode,new Intent(WifiReceiver.ALARM_ACTION), 0));
	}

	/** Alarm Dialog END **/

	@Override
	public void dismiss() {
		super.dismiss();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		/** Add Dialog **/
		case R.id.addlist_okBtn:
			String addText = addEditTxt.getText().toString();
			if (MODE == 1) { // safe
				((SafeListActivity) mContext).addCheckList(addText);
			} else if (MODE == 2) { // live
				((LiveListActivity) mContext).addCheckList(addText);
			} else if (MODE == 3) { // etc
				((EtcListActivity) mContext).addCheckList(addText);
			}

			addEditTxt.setText("");
			this.dismiss();
			break;
		case R.id.addlist_cancelBtn:
			this.dismiss();
			break;
		/** Delete Dialog **/
		case R.id.dellist_deleteBtn:
			if (MODE == 1) { // safe
				((SafeListActivity) mContext).delCheckList();
			} else if (MODE == 2) { // live
				((LiveListActivity) mContext).delCheckList();
			} else if (MODE == 3) { // etc
				((EtcListActivity) mContext).delCheckList();
			}
			this.dismiss();
			break;
		case R.id.dellist_cancelBtn:
			this.dismiss();
			break;
		/** Modify Dialog **/
		case R.id.modifylist_modifyBtn:
			String modifyText = modifyEditTxt.getText().toString();
			if (MODE == 1) { // safe
				((SafeListActivity) mContext).modifyCheckList(modifyText);
			} else if (MODE == 2) { // live
				((LiveListActivity) mContext).modifyCheckList(modifyText);
			} else if (MODE == 3) { // etc
				((EtcListActivity) mContext).modifyCheckList(modifyText);
			}
			this.dismiss();
			break;
		case R.id.modifylist_cancelBtn:
			this.dismiss();
			break;

		/** Fun Dialog **/
		case R.id.fundialog_okBtn:
			this.dismiss();
			((PreviewListActivity) mContext).finish();
			break;

		/** Setting Dialog START **/
		/** Choose Wifi Dialog **/
		case R.id.choose_wifi_cancelBtn:
			this.dismiss();
			break;
		/** Set Alarm Dialog **/	
		case R.id.alarm_set_btn:
			int hour = mTime.getCurrentHour();
			int minute = mTime.getCurrentMinute();
			registerAlarm(mContext,hour, minute);
			WifiReceiver.setNotification(mContext,alarmNotiState);
			this.dismiss();
			break;
		case R.id.alarm_reset_btn:
			stopAlarm(mContext);
			WifiReceiver.cancleNotification(mContext,alarmNotiState);
			this.dismiss();
			break;
			
		/** Setting Dialog END **/
		}
	}

	public void onItemClick(AdapterView<?> listView, View view, int position,long id) {

		WifiListProfile item = (WifiListProfile) listView.getItemAtPosition(position);

		String ssid = item.getSSID();
		String bssid = item.getBSSID();
		String rssi = item.getRSSI();
		Log.v(TAG, "ssid" + ssid);
		Log.v(TAG, "bssid" + bssid);
		Log.v(TAG, "rssi" + rssi);

		((SettingActivity) mContext).chooseWifi(item);
		this.dismiss();
	}
}
