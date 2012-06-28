package com.smartschool.tenversion;

import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WifiListAdapter extends ArrayAdapter<WifiListProfile>{// implements OnClickListener {
	private static final String TAG = "WifiListAdapter";
	private final LayoutInflater mInflater;
	private ArrayList<WifiListProfile> wifiInfoList;
	private Context mContext;
	private ViewHolder holder;

	public WifiListAdapter(Context context, int textViewResourceId, ArrayList<WifiListProfile> objects) {
		super(context, textViewResourceId,objects);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		wifiInfoList = objects;
	}
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.v(TAG, "getView");
		// final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.wifilist_item_row, null);
			holder = new ViewHolder();
			holder.mRLayout = (RelativeLayout) convertView.findViewById(R.id.wifilist_relativelayout);
			holder.mSSID_TV = (TextView) convertView.findViewById(R.id.wifilist_ssid_tv);
			holder.mBSSID_TV = (TextView) convertView.findViewById(R.id.wifilist_bssid_summary_tv);
			holder.mRSSI_IV = (ImageView) convertView.findViewById(R.id.wifilist_rssi_imageview);
			holder.mRadioBtn = (RadioButton) convertView.findViewById(R.id.wifilist_radio_Btn);
						
			convertView.setTag(holder);
			
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// set background
//		if (position % 2 == 0) {
//			holder.mRLayout.setBackgroundResource(R.drawable.panal_2);
//		} else {
//			holder.mRLayout.setBackgroundResource(R.drawable.panal_1);
//		}

		WifiListProfile item = wifiInfoList.get(position);
		holder.mSSID_TV.setText(item.getSSID().toString());
		holder.mBSSID_TV.setText(item.getBSSID().toString());

		
		String rssiStr = item.getRSSI();
		if(rssiStr == null || rssiStr.equals("")){
			Log.v(TAG, "rssiStr.equals()");
			holder.mRSSI_IV.setImageResource(R.drawable.wifi_not_enable); //base image
		}else{		
			int rssi =  Integer.parseInt(rssiStr);
			if(rssi > -70){ //Full Signal
				Log.v(TAG, "rssi > -70");
				holder.mRSSI_IV.setImageResource(R.drawable.wifi_full);
			}else if(rssi <= -70 && rssi > -75){ // Optimal signal
				Log.v(TAG, "rssi <= -70 && rssi > -75");
				holder.mRSSI_IV.setImageResource(R.drawable.wifi_optimal);
			}else if(rssi <= -75 && rssi > -85){ // Fair signal
				Log.v(TAG, "rssi <= -75 && rssi > -85");
				holder.mRSSI_IV.setImageResource(R.drawable.wifi_fair);
			}else if(rssi <= -85){ // poor signal
				Log.v(TAG, "rssi <= -85");
				holder.mRSSI_IV.setImageResource(R.drawable.wifi_poor);
			}
		}
		
		
		return convertView;
	}

	private class ViewHolder {
		private RelativeLayout mRLayout;
		private TextView mSSID_TV;
		private TextView mBSSID_TV;
		private ImageView mRSSI_IV;
		private RadioButton mRadioBtn;

	}
}
