package com.smartschool.tenversion;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;


public class CustomDialog extends Dialog implements android.view.View.OnClickListener{
	private static final String TAG = "CustomDialog";

	private Context mContext = null; 
	private int MODE ;
	
	//add ListDialog
	private EditText addEditTxt = null;
	//modify ListDialog
	private EditText modifyEditTxt = null;
	
	
	public CustomDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}



	//addList dialog  safe,live,etc Activity
	public void addListDialog(int mode) {
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();    
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;
		getWindow().setAttributes(lpWindow);
		
		MODE = mode;
		setContentView(R.layout.dialog_addlist);
		addEditTxt = (EditText)findViewById(R.id.addlist_edittxt);
		
			
		Button okBtn = (Button)findViewById(R.id.addlist_okBtn);
		okBtn.setOnClickListener(this);
		Button cancelBtn = (Button)findViewById(R.id.addlist_cancelBtn);
		cancelBtn.setOnClickListener(this);
	}
	
	
	public void delListDialog(int mode, String contents){
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();    
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;
		getWindow().setAttributes(lpWindow);

		MODE = mode;
		setContentView(R.layout.dialog_dellist);
		
	    TextView contentsTxt = (TextView)findViewById(R.id.dellist_testview);
	    contentsTxt.setText(contents);
	    
		Button deleteBtn = (Button)findViewById(R.id.dellist_deleteBtn);
		deleteBtn.setOnClickListener(this);
		Button cancelBtn = (Button)findViewById(R.id.dellist_cancelBtn);
		cancelBtn.setOnClickListener(this);
		
	}
	
	public void modifyListDialog(int mode, String contents){
		WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();    
		lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
		lpWindow.dimAmount = 0.75f;
		getWindow().setAttributes(lpWindow);

		MODE = mode;
		setContentView(R.layout.dialog_modifylist);
		
		modifyEditTxt = (EditText)findViewById(R.id.modifylist_editview);
		modifyEditTxt.setText(contents);
				
//	    TextView contentsTxt = (TextView)findViewById(R.id.modifylist_textview);
//	    contentsTxt.setText(contents);
	    
		Button modifyBtn = (Button)findViewById(R.id.modifylist_modifyBtn);
		modifyBtn.setOnClickListener(this);
		Button cancelBtn = (Button)findViewById(R.id.modifylist_cancelBtn);
		cancelBtn.setOnClickListener(this);
		
	}

	@Override
	public void dismiss() {
		super.dismiss();
	}

	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addlist_okBtn:
			String addText =  addEditTxt.getText().toString();
			if(MODE ==1){ 			//safe
				((SafeListActivity)mContext).addCheckList(addText);
			}else if(MODE ==2){ 	//live
				((LiveListActivity)mContext).addCheckList(addText);
//			}else if(MODE ==3){	 //etc
//				((EtcListActivity)mContext).addCheckList(addText);
			}
			
			addEditTxt.setText("");
			this.dismiss();
			break;
		case R.id.addlist_cancelBtn:
			this.dismiss();
			break;
			
		case R.id.dellist_deleteBtn:
			if(MODE ==1){ 			//safe
				((SafeListActivity)mContext).delCheckList();
			}else if(MODE ==2){ 	//live
				((LiveListActivity)mContext).delCheckList();
//			}else if(MODE ==3){	 //etc
//				((EtcListActivity)mContext).delCheckList();
			}
			this.dismiss();
			break;
		case R.id.dellist_cancelBtn:
			this.dismiss();
			break;
			
		case R.id.modifylist_modifyBtn:
			String modifyText =  modifyEditTxt.getText().toString();
			if(MODE ==1){ 			//safe
				((SafeListActivity)mContext).modifyCheckList(modifyText);
//			}else if(MODE ==2){ 	//live
//				((LiveListActivity)mContext).modifyCheckList(modifyText);
//			}else if(MODE ==3){	 //etc
//				((EtcListActivity)mContext).addCheckList(addText);
			}
			this.dismiss();
			break;
		case R.id.modifylist_cancelBtn:
			this.dismiss();
			break;

		
		}
	}
}
