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
import android.provider.ContactsContract;
import android.provider.SyncStateContract.Helpers;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class SafeListActivity extends Activity implements OnClickListener, OnItemLongClickListener{
	private static final String TAG ="SafeListActivity";
	/**  UI  **/
	 private static final int MODE = 1; //safe
	private boolean DEL_MODE = false; 
	//setListView 
	private ListView buddyListView = null;
//	private LinearLayout buddylistlayout = null;
//	private BuddyList buddylistView = null;
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
//    CustomDialog freeDialog = null;
//    CustomDialog delListDialog = null;
    
    
//    /**  Service  **/
//    String phoneNum = "01029170572";
//    PhoneService phoneService = null;
//    NumberUtil numberUtil = null;
    
//    /*boolean freezomeLoc = false;*/
//    int cellId;
    
//    //Server
//    ServerConnection serverConnection = null;

    /**  DB  **/
//    ContactsDBHelper contactsDBHelper = null;
	//ArrayList<FreezoneContactsDBdata>  contactDBdataList = null;
	//FreezoneDB
	private static DBHandler mDBHandler = null;
	Cursor mDBcursor = null;
	//FreezoneBuddyListviewAdapter
	//FreezoneBuddyListviewAdapter mAdapter = null;
	
    
	
	
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
        buddyListView =(ListView)findViewById(R.id.listView);
        //buddyListView.setOnItemClickListener(this);
        buddyListView.setOnItemLongClickListener(this);
        checkListItem = new ArrayList<CheckListProfile>();

		/**  DB  **/
     //   contactsDBHelper = new ContactsDBHelper(this);
        mDBHandler = new DBHandler(this);
        mDBHandler.open(this);
        // requastBuddylist();
        updateListview();
       
		
    }
    
    public void updateListview(){
    	Log.v(TAG,"updateListview()");
    	checkListItem.clear();
    	mDBcursor = mDBHandler.selectAll2();//mode = safe = 1
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
    
    
//    public boolean checkFreezoneLocation(){
//    	cellId = phoneService.getCellid();
//		return phoneService.checkFreezomeLoc(cellId);
//    }
    
    
//    public void requestBuddylist(){
//    	if(checkFreezoneLocation()){
//			//Log.v(TAG,"[requestBuddylist] checkFreezoneLocation true ");
//			ArrayList<String> BuddyList = new ArrayList<String>();
//			BuddyList = serverConnection.getBuddyFreezoneMember(BuddyList, "null",cellId,phoneNum);
//			for(int i=0; i<BuddyList.size(); i++){
//				String freezonMemNumber = BuddyList.get(i).toString();
//				//Log.v(TAG, "[requastBuddylist]"+freezonMmemNumber);
//				CheckListProfile buddyList = contactsDBHelper.getCompareToContacts(freezonMemNumber);
//				if(buddyList!=null){
//				String buddyNumber = buddyList.getNumber();
//				String buddyName = buddyList.getName();
//				String buddyPhotoUri = buddyList.getPhotoUri();
//					//현재 freezone db에 있는지 비교 해야 됨.
//					mDBHandler.insertMemberRow(buddyName, buddyNumber, null,buddyPhotoUri);
//				}
//			}
//		} else Log.v(TAG,"[requestBuddylist] checkFreezoneLocation false");
//    }
//    
    
    public void addCheckListDialog(){ 
    	Log.v(TAG,"[addCheckListDialog]");
   	 	//delListDialog(number);
    	CustomDialog checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
   	 	checkLlistDialog.addListDialog(MODE);
   	 	checkLlistDialog.show();
    }
    public  void  addCheckList(String contents){
	   	 Log.v(TAG,"[addCheckList] contents :: "+ contents);
	   	 mDBHandler.insert("1", contents);  //1=safe, 2=live, 3=etc
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
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			// 받아온 이름과 전화번호를 InformationInput 액티비티에 표시
			if(requestCode==REQ_BUDDYLIST_ADD) {			
				Log.v(TAG,"[onActivityResult] BUDDYLIST_ADD "+data.getData());
				Log.v(TAG,"[onActivityResult] data :: "+data);
				/*
				FreezoneContactsDBdata MatchingContectdata = contactsDBHelper.getNumberUri(data.getData());
				String addBuddyName = MatchingContectdata.mName;
				String addBuddyNumber =MatchingContectdata.mHomeNumber;
				Log.v(TAG,"[onActivityResult] addBuddyName :: "+addBuddyName);
				Log.v(TAG,"[onActivityResult] addBuddyNumber :: "+addBuddyNumber);
				mFreezoneBuddylistDBHelper.insertMemberRow(name, number, null);*/

				//select in allcontacts
			//	Log.v(TAG,"[onActivityResult] data :: "+data.getStringExtra());
				//String addBuddyName = data.getStringExtra("addBuddyName");               
				//String addBuddyNumber = data.getStringExtra("addBuddyNumber");
				String mode = data.getStringExtra(DBHelper.KEY_MODE) ;
				String listData = data.getStringExtra(DBHelper.KEY_LIST_DATA) ;
				Log.v(TAG,"[onActivityResult] mode :: "+mode);
				Log.v(TAG,"[onActivityResult] listData :: "+listData);
				mDBHandler.insert(mode, listData);
				//contact에도 저장해야됨
				updateListview();
			}
			
		}
	}
	CustomDialog checkLlistDialog ;
	
    @Override
	protected Dialog onCreateDialog(int id) {
    	checkLlistDialog =  new CustomDialog(this, R.style.Dialog);
    	return checkLlistDialog;
//		switch (id) {
//		case ADD_DLG:
//			Log.v(TAG,"onCreateDialog :: ADD_DLG");
//			CustomDialog addcheckLlistDialog = new CustomDialog(this, R.style.Dialog);
//			addcheckLlistDialog.addListDialog(MODE);
//			return addcheckLlistDialog;
//		case DEL_DLG:
//			Log.v(TAG,"onCreateDialog :: DEL_DLG");
//			CustomDialog delCheckListDialog = new CustomDialog(this, R.style.Dialog);
//			delCheckListDialog.delListDialog(MODE,);
//			return delCheckListDialog;
//		case FREE_DLG:
//			freeDialog = new CustomDialog(this, R.style.Dialog);
//			freeDialog.create();
//			//freeDialog.setMessage("연락처 " + mSendContactsNum + "개, 사진 " + mSendPhotoNum + "개를" + "\n보내기 위해 상대방과 연결을 시도합니다.");
//			return freeDialog;
//		
//		case DEL_DLG:
//			delListDialog = new CustomDialog(this, R.style.Dialog);
//			delListDialog.delListCreate();
//			//freeDialog.setMessage("연락처 " + mSendContactsNum + "개, 사진 " + mSendPhotoNum + "개를" + "\n보내기 위해 상대방과 연결을 시도합니다.");
//			return delListDialog;
//		}
//		return null;
    }
			
  
    public void onClick(View v) {
		switch(v.getId()){
		case R.id.addBtn: 		//add list Item
			Log.v(TAG,"chekListAddBtn Click");
			addCheckListDialog();
			break;
		case R.id.deleteBtn:  	//Delete list Item
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
//			requestBuddylist();
//			updateListview();
			break;
		}
	}
    
	public boolean onItemLongClick(AdapterView<?> arg0, View v, int position,long id) {
		Log.v(TAG,"view "+ v+" position :: "+position);
//		CheckListProfile checkListData = checkListItem.get(position);
//		Log.v(TAG," mode "+ checkListData.getMode()+"  contents"+checkListData.getContents());
//		//custom dialog띄우기 
//		showDialog(FREE_DLG);

		return false;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mDBHandler.close();
	}


	
}
