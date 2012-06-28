package com.smartschool.tenversion;

public class WifiListProfile {
	String mSSID;
	String mBSSID;
	String mRSSI;

	public WifiListProfile(String mSSID, String mBSSID) {
		this.mSSID = mSSID;
		this.mBSSID = mBSSID;
	}

	public WifiListProfile(String ssid, String bssid,String rssi) {
		this.mSSID = ssid;
		this.mBSSID = bssid;
		this.mRSSI = rssi;
	}

	public String getSSID() {
		return mSSID;
	}

	public String getBSSID () {
		return mBSSID;
	}

	public String getRSSI() {
		return mRSSI;
	}
}
