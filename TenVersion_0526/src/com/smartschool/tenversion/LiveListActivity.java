package com.smartschool.tenversion;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class LiveListActivity extends Activity implements OnClickListener {
	private static final String TAG = "LiveListActivity";
	/**  UI  **/
	//String
	 String allDelSelectTxt ;
	 String allDelDeselectTxt;
	private static final int MODE = 2; //safe
	private static final String liveMode="2"; //safe
	private boolean DEL_MODE = false; 
	private boolean ALL_DEL_MODE = false; 
	//setListView 
	private ListView liveListView = null;
	private CheckListAdapter checkListAdapter = null;
	private ArrayList<CheckListProfile> checkListItem = null;
	//set Button
	private Button checkListAddBtn = null; 
	private Button checkListDelBtn = null;
	private Button checkListAllDelBtn = null;
    //dialog
    private CustomDialog checkLlistDialog = null ;
    long delId;
    long modifyId;
    
    /**  DB  **/
	private static DBHandler mDBHandler = null;
	Cursor mDBcursor = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.livelist);

		/**  UI  **/
        //String
        allDelSelectTxt = getResources().getString(R.string.all_del_select_txt);
        allDelDeselectTxt = getResources().getString(R.string.all_del_deselect_txt);
		//button
        checkListAddBtn =(Button)findViewById(R.id.addBtn);
        checkListAddBtn.setOnClickListener(this);
        checkListDelBtn = (Button)findViewById(R.id.deleteBtn);
        checkListDelBtn.setOnClickListener(this);
        checkListAllDelBtn = (Button)findViewById(R.id.allCheckBtn);
        checkListAllDelBtn.setOnClickListener(this);
       if(DEL_MODE){ 
    	//   checkListAllDelBtn.setVisibility(View.VISIBLE);
    	   checkListAllDelBtn.setEnabled(true);
       }else{
    	   checkListAllDelBtn.setEnabled(false);
       }
        //listView
        liveListView = (ListView)findViewById(R.id.listView);
        checkListItem = new ArrayList<CheckListProfile>();

		/**  DB  **/
        mDBHandler = DBHandler.open(this);
        updateListview();
	}

	public void updateListview() {
    	Log.v(TAG,"updateListview()");
    	checkListItem.clear();
    	mDBcursor = mDBHandler.selectAllList(liveMode);//mode = safe = 1
    	if(mDBcursor.moveToFirst()){
			do {
				long id = mDBcursor.getLong(mDBcursor.getColumnIndex(DBHelper.KEY_ROWID));
				String mode = mDBcursor.getString(mDBcursor.getColumnIndex(DBHelper.KEY_MODE));
				String listData = mDBcursor.getString(mDBcursor.getColumnIndex(DBHelper.KEY_LIST_DATA));  			    		
				Log.v(TAG,"mode  :: "+mode+ "   ,listData  :: "+listData );
				checkListItem.add(new CheckListProfile(id, mode, listData));
				
			} while(mDBcursor.moveToNext());
		}
    	mDBcursor.close();
    	
		checkListAdapter = new CheckListAdapter(this,  R.layout.checklist_item_row, checkListItem, DEL_MODE,ALL_DEL_MODE); 
		liveListView.setAdapter(checkListAdapter);
	}

	/**Custom dialog START**/    
    @Override
	protected Dialog onCreateDialog(int id) {
    	checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
    	return checkLlistDialog;
    }
    
    
    public void addCheckListDialog(){ 
    	Log.v(TAG,"[addCheckListDialog]");
    	CustomDialog checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
   	 	checkLlistDialog.addListDialog(MODE);
   	 	checkLlistDialog.show();
    }
    public  void  addCheckList(String contents){
	   	 Log.v(TAG,"[addCheckList] contents :: "+ contents);
	   	 mDBHandler.insert(this, liveMode, contents);  //1=safe, 2=live, 3=etc
	   	 updateListview();
   	
   }

    public void delCheckListDialog(long id,String contents){
    	 Log.v(TAG,"[delListDialog] id :: "+ id);
    	 delId = id;
    	 CustomDialog checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
    	 checkLlistDialog.delListDialog(MODE, contents);
    	 checkLlistDialog.show();
    }
    public void delCheckList(){
	   	 Log.v(TAG,"[delCheckList] delId :: "+ delId);
	   	 mDBHandler.delete(delId);
	   	 updateListview();
    }
    
    public void modifyCheckListDialog(long id,String contents){ 
    	Log.v(TAG,"[modifyCheckListDialog]");
    	modifyId = id;
    	CustomDialog checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
   	 	checkLlistDialog.modifyListDialog(MODE,contents);
   	 	checkLlistDialog.show();
    }
    public  void  modifyCheckList(String contents){
	   	 Log.v(TAG,"[modifyCheckList] contents :: "+ contents);
	   	 mDBHandler.update(this, modifyId, liveMode, contents);  //1=safe, 2=live, 3=etc
	   	 updateListview();
  	
    }

/**Custom dialog END**/    			

	public void onClick(View v) {
    	//Intent intent = null;
		switch(v.getId()){
		case R.id.addBtn: 		//add list Item
			Log.v(TAG,"chekListAddBtn Click");
			addCheckListDialog();
			break;
		case R.id.deleteBtn:  	//Delete list Item
			Log.v(TAG,"deleteBtn Click");
			if(DEL_MODE){
				DEL_MODE = false;
				ALL_DEL_MODE = false;
			    checkListAllDelBtn.setEnabled(false);
			    
			    ArrayList<CheckListProfile> delList = checkListAdapter.getAllDelList();
			    if(delList != null && delList.size() != 0){
			    	for(int i=0; i<delList.size(); i++){
			    		 Log.v(TAG,"[delList] delId :: "+ delList.get(i).getId()+",   contents"+ delList.get(i).getContents());
			    		 mDBHandler.delete(delList.get(i).getId());
			    	}
			    }
				updateListview();
				
			}else{
				DEL_MODE= true;
				checkListAllDelBtn.setEnabled(true);
				updateListview();
			}
			
			break;
		case R.id.allCheckBtn:  	//test
			Log.v(TAG,"allCheckBtn Click");
			if(ALL_DEL_MODE){
				ALL_DEL_MODE = false;
				checkListAllDelBtn.setText(allDelSelectTxt);
				
				updateListview();
			}else{
				ALL_DEL_MODE= true;
				checkListAllDelBtn.setText(allDelDeselectTxt);
				updateListview();
			}
			break;
		}
	}

	protected void onDestroy() {
		Log.v(TAG, "onDestroy()");
		super.onDestroy();
		mDBHandler.close();
	}

}