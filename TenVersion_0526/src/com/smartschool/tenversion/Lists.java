package com.smartschool.tenversion;

public class Lists {
	private int id;
	private String mode;
	private String listdata;
	
	public Lists (int id, String mode, String listdata) {
		this.id = id; 
		this.mode = mode;
		this.listdata = listdata;
	}
	
	public int getId() {
		return id;
	}

	public String getMode() {
		return mode;
	}

	public String getListdata() {
		return listdata;
	}
	
	//-- USER TODO --//
	private boolean checked = false;
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
