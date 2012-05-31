package com.smartschool.tenversion;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class LiveListActivity2 extends Activity  implements OnClickListener{
	EditText add_live;
	
	String[] lives = {"뽀삐 밥주기","화분 물주기","열대어 밥주기"};
	
	ArrayAdapter<String> adapter; 
	ArrayList<String> liveList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livelist);
        
        add_live = (EditText) findViewById(R.id.add_live);
        
        liveList = new ArrayList<String>();
        liveList.addAll(Arrays.asList(lives));
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, liveList);
        
        ListView list = (ListView) findViewById(R.id.live_listview);
        list.setAdapter(adapter);
        
        Button addButton = (Button) findViewById(R.id.add_btn);
        addButton.setOnClickListener(this);
        
        Button delButton = (Button) findViewById(R.id.del_btn);
        delButton.setOnClickListener(this);
        
    }

	public void onClick(View v) {
		Intent intent = null;
		DBHandler dbhandler = DBHandler.open(this);
		switch(v.getId()) {
		case R.id.add_btn:
//			intent = new Intent(this, LiveListAddActivity.class);
//			startActivity(intent);
//			new AlertDialog.Builder(this)
//			.setTitle("추가")
//			.setMessage("추가하시겠습니까?")
//			.setNeutralButton("확인",null)
//			.setNegativeButton("취소", null)
//			.show();
			String liveName = add_live.getText().toString();
			
			long cnt = dbhandler.insert("1", liveName);
			
			if(cnt == -1 ){
				Toast.makeText(this, liveName + "가 추가되지 않았습니다. ",Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, liveName + "가 추가되었습니다. ",Toast.LENGTH_LONG).show();
			}
			
			break;

		case R.id.del_btn:
			intent = new Intent(this, LIveListDelActivity.class);
			startActivity(intent);
			break;
			
		}
	}
}