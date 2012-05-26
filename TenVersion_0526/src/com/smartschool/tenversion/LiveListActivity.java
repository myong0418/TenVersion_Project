package com.smartschool.tenversion;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LiveListActivity extends Activity  implements OnClickListener{
	String[] lives = {"ª«ªﬂ π‰¡÷±‚","»≠∫– π∞¡÷±‚","ø≠¥ÎæÓ π‰¡÷±‚"};
	
	ArrayAdapter<String> adapter; 
	ArrayList<String> liveList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livelist);
          
        liveList = new ArrayList<String>();
        liveList.addAll(Arrays.asList(lives));
        
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, liveList);
        
        ListView list = (ListView) findViewById(R.id.live_listview);
        list.setAdapter(adapter);
        
        Button addButton = (Button) findViewById(R.id.add_btn);
        addButton.setOnClickListener(this);
        
        Button delButton = (Button) findViewById(R.id.del_btn);
        //delButton.setOnClickListener(this);
        
    }

	public void onClick(View v) {
		Intent intent = null;
		switch(v.getId()) {
		case R.id.add_btn:
			intent = new Intent(this, LiveListAddActivity.class);
			startActivity(intent);
			break;

		case R.id.del_btn:
			intent = new Intent(this, LIveListDelActivity.class);
			startActivity(intent);
			break;
			
		}
	}
}