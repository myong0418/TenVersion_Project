package com.smartschool.tenversion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class LIveListDelActivity extends ListActivity  implements View.OnClickListener{
    private String[] cars = {"SM3", "SM5", "SM7", "SONATA", "AVANTE", "SOUL", "K5", "K7"};
    private TextView selected;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livedellist);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, cars));

        ListView listView = getListView();
        //listView.setItemsCanFocus(false);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        selected = (TextView) findViewById(R.id.selected);

        
        
        
        Button allcheck = (Button)findViewById(R.id.allcheck);
        allcheck.setOnClickListener(this);

        Button delbtn = (Button)findViewById(R.id.del_btn);
        delbtn.setOnClickListener(this);
                
    }
    public void onListItemClick(ListView parent, View v, int position, long id) {
        selected.setText(cars[position]);
    }
	public void onClick(View v) {

		switch(v.getId()) {
		case R.id.allcheck:
//			intent = new Intent(this, LiveListAddActivity.class);
//			startActivity(intent);
			new AlertDialog.Builder(this)
			.setTitle("전체선택")
			.setMessage("전체선택하시겠습니까?")
			.setNeutralButton("확인",null)
			.setNegativeButton("취소", null)
			.show();
			break;

		case R.id.del_btn:
			new AlertDialog.Builder(this)
			.setTitle("삭제")
			.setMessage("삭제하시겠습니까?")
			.setNeutralButton("확인",null)
			.setNegativeButton("취소", null)
			.show();
			break;
			
		}
	}
}