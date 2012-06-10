package com.smartschool.tenversion;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import java.util.ArrayList;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class SafeListActivity extends Activity implements OnClickListener{
	private static final String TAG ="SafeListActivity";
	/**  UI  **/
	private static final int MODE = 1; //safe
	private static final String safeMode="1"; //safe
	private boolean DEL_MODE = false; 
	//setListView 
	private ListView safeListView = null;
	private CheckListAdapter checkListAdapter = null;
	private ArrayList<CheckListProfile> checkListItem = null;
	//set Button
	private Button checkListAddBtn = null; 
	private Button checkListDelBtn = null;
	private Button requastbuddylistBtn = null;
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
        setContentView(R.layout.safelist);
        
		/**  UI  **/
		//button
        requastbuddylistBtn = (Button)findViewById(R.id.testBtn);
        requastbuddylistBtn.setOnClickListener(this);
        checkListAddBtn =(Button)findViewById(R.id.addBtn);
        checkListAddBtn.setOnClickListener(this);
        checkListDelBtn = (Button)findViewById(R.id.deleteBtn);
        checkListDelBtn.setOnClickListener(this);
        
        //listView
        safeListView = (ListView)findViewById(R.id.listView);
        checkListItem = new ArrayList<CheckListProfile>();

		/**  DB  **/
        mDBHandler = DBHandler.open(this);
        updateListview();
    }
    
    public void updateListview(){
    	Log.v(TAG,"updateListview()");
    	checkListItem.clear();
    	mDBcursor = mDBHandler.dbSafeSelectAll();//mode = safe = 1
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
    	
		checkListAdapter = new CheckListAdapter(this,  R.layout.checklist_item_row, checkListItem, DEL_MODE); 
		safeListView.setAdapter(checkListAdapter);
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
	   	 mDBHandler.insert(safeMode, contents);  //1=safe, 2=live, 3=etc
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
    	Log.v(TAG,"[addCheckListDialog]");
    	modifyId = id;
    	CustomDialog checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
   	 	checkLlistDialog.modifyListDialog(MODE,contents);
   	 	checkLlistDialog.show();
    }
    public  void  modifyCheckList(String contents){
	   	 Log.v(TAG,"[addCheckList] contents :: "+ contents);
	   	 mDBHandler.update(modifyId, safeMode, contents);  //1=safe, 2=live, 3=etc
	   	 updateListview();
  	
    }

/**Custom dialog END**/    			
  
    public void onClick(View v) {
    	Intent intent = null;
		switch(v.getId()){
		case R.id.addBtn: 		//add list Item
			Log.v(TAG,"chekListAddBtn Click");
			addCheckListDialog();
			break;
		case R.id.deleteBtn:  	//Delete list Item
			Log.v(TAG,"buddylistDeleteBtn Click");
			if(DEL_MODE){
				DEL_MODE = false;
				updateListview();
			}else{
				DEL_MODE= true;
				updateListview();
			}
			
			break;
		case R.id.testBtn:  	//test
			intent = new Intent(this, LIveListDelActivity.class);
			startActivity(intent);
			break;
		}
	}
    
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDBHandler.close();
	}
}
