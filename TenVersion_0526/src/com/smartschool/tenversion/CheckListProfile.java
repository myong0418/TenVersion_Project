package com.smartschool.tenversion;

public class CheckListProfile {
	long mId;
	String mMode;
	String mContents;

	public CheckListProfile(String mMode, String mContents) {
		this.mMode = mMode;
		this.mContents = mContents;
	}

	public CheckListProfile(long mId, String mode, String contents) {
		this.mId = mId;
		this.mMode = mode;
		this.mContents = contents;
	}

	public long getId() {
		return mId;
	}

	public String getMode() {
		return mMode;
	}

	public String getContents() {
		return mContents;
	}
}
