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

public class LiveListActivity extends Activity  implements OnClickListener, OnItemLongClickListener{
	private static final String TAG ="LiveListActivity";
	/**  UI  **/
	 private static final int MODE = 2; //live
	private boolean DEL_MODE = false; 
	//setListView 
	private ListView buddyListView = null;
	private CheckListAdapter checkListAdapter = null;
	private ArrayList<CheckListProfile> checkListItem = null;
	//set Button
	private Button checkListAddBtn = null; 
	private Button checkListDelBtn = null;
	private Button requastbuddylistBtn = null;

    //activity result
    private static final int REQ_BUDDYLIST_ADD = 1;
    private static final int REQ_PICK_PICTURE = 2;
    //dialog
//    private static final int FREE_DLG = 1;
    private static final int ADD_DLG =1;
    private static final int DEL_DLG = 2;
    /**  DB  **/
	private static DBHandler mDBHandler = null;
	Cursor mDBcursor = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livelist);
                  
//        ListView list = (ListView) findViewById(R.id.live_listview);
//        list.setAdapter(adapter);
		/**  UI  **/
		//button
        requastbuddylistBtn = (Button)findViewById(R.id.testBtn);
        requastbuddylistBtn.setOnClickListener(this);
        checkListAddBtn =(Button)findViewById(R.id.add_btn);
        checkListAddBtn.setOnClickListener(this);
        checkListDelBtn = (Button)findViewById(R.id.del_btn);
        checkListDelBtn.setOnClickListener(this);
        
        //listView
        buddyListView =(ListView)findViewById(R.id.listView);
        //buddyListView.setOnItemClickListener(this);
        buddyListView.setOnItemLongClickListener(this);
        checkListItem = new ArrayList<CheckListProfile>();

		/**  DB  **/
     //   contactsDBHelper = new ContactsDBHelper(this);
//        mDBHandler = new DBHandler(this);
//        mDBHandler.open(this);
        mDBHandler = DBHandler.open(this);
        
        
        // requastBuddylist();
        updateListview();
        
    }
    public void updateListview(){
    	Log.v(TAG,"updateListview()");
    	checkListItem.clear();
    	mDBcursor = mDBHandler.selectAll3();//mode = live = 2
    	if(mDBcursor.moveToNext()){
			do {
				//String id = mDBcursor.getString(mDBcursor.getColumnIndex(ContactsContract.Contacts._ID));
				long id = mDBcursor.getLong(mDBcursor.getColumnIndex(DBHelper.KEY_ROWID));
				String mode = mDBcursor.getString(mDBcursor.getColumnIndex(DBHelper.KEY_MODE));
				String listData = mDBcursor.getString(mDBcursor.getColumnIndex(DBHelper.KEY_LIST_DATA));  			    		
				Log.v(TAG,"mode  :: "+mode+ "   ,listData  :: "+listData );
				checkListItem.add(new CheckListProfile(id, mode, listData));
				
			} while(mDBcursor.moveToNext());
		}
    	mDBcursor.close();
    	
    	
    	//buddylistView.setListData(checkListItem);
		checkListAdapter = new CheckListAdapter(this,  R.layout.checklist_item_row, checkListItem, DEL_MODE); 
		buddyListView.setAdapter(checkListAdapter);
    }
    public void addCheckListDialog(){ 
    	Log.v(TAG,"[addCheckListDialog]");
   	 	//delListDialog(number);
    	CustomDialog checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
   	 	checkLlistDialog.addListDialog(MODE);
   	 	checkLlistDialog.show();
    }
    public  void  addCheckList(String contents){
	   	 Log.v(TAG,"[addCheckList] contents :: "+ contents);
	   	 mDBHandler.insert("2", contents);  //1=safe, 2=live, 3=etc
	   	 updateListview();
   	
   }
    long delId;
    public void delCheckListDialog(long id,String contents){
    	 Log.v(TAG,"[delListDialog] id :: "+ id);
    	 delId = id;
    	 CustomDialog checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
    	 checkLlistDialog.delListDialog(MODE, contents);
    	 checkLlistDialog.show();//showDialog(DEL_DLG);
    	
    }
    public void delCheckList(){
	   	 Log.v(TAG,"[delCheckList] delId :: "+ delId);
	   	 //delListDialog.dismiss();
	   	 mDBHandler.delete(delId);
	   	 updateListview();
    	
    }
    
    CustomDialog checkLlistDialog ;
	protected Dialog onCreateDialog(int id) {
    	checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
    	return checkLlistDialog;
    }


	public void onClick(View v) {
		Intent intent = null;
		switch(v.getId()) {
		case R.id.add_btn:
			Log.v(TAG,"chekListAddBtn Click");
			addCheckListDialog();
			break;

		case R.id.del_btn:
			Log.v(TAG,"buddylistDeleteBtn Click");
			/*mDBHandler.deleteMemberAllRows();
			updateListview();*/
			if(DEL_MODE){
				DEL_MODE = false;
				updateListview();
			}else{
				DEL_MODE= true;
				updateListview();
			}
			break;
		case R.id.testBtn:  	//reques tbuddylist
			intent = new Intent(this, LIveListDelActivity.class);
			startActivity(intent);
			break;
		}
	}

	public boolean onItemLongClick(AdapterView<?> arg0, View v, int position,long id) {
		Log.v(TAG,"view "+ v+" position :: "+position);
		return false;
	}
}