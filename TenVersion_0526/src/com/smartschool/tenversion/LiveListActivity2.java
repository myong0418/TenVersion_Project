package com.smartschool.tenversion;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LiveListActivity2 extends ListActivity  implements View.OnClickListener{
	EditText add_live;
	private LiveListAdapter adapter;
	private ArrayList<Lists> livelists;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livelist);
        
        add_live = (EditText) findViewById(R.id.add_live);
        
        DBHandler dbhandler = new DBHandler(this);
        livelists = (ArrayList<Lists>) dbhandler.selectAll();
        dbhandler.close();
        
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
			add_liveRow(dbhandler);
	        adapter = new LiveListAdapter(this);
			setListAdapter(adapter);

			break;
		case R.id.del_btn:
			intent = new Intent(this, LIveListDelActivity.class);
			startActivity(intent);
			break;
		}
	}

	public void add_liveRow(DBHandler dbhandler) {
		String liveName = add_live.getText().toString();
		long cnt = dbhandler.insert("1", liveName);
		
		if(cnt == -1 ){
			Toast.makeText(this, liveName + "가 추가되지 않았습니다. ",Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, liveName + "가 추가되었습니다. ",Toast.LENGTH_LONG).show();
		}
	}

	private class LiveListAdapter extends BaseAdapter {
		private Context context;

		public LiveListAdapter(Context context) {
			this.context = context;
		}

		public int getCount() {
			return livelists.size();
		}
		
		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.add_liverow, null);
				
				holder = new ViewHolder();
				holder.text = (TextView)convertView.findViewById(R.id.add_liveRow);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder)convertView.getTag();
			}
			
			Lists live_row = (Lists)livelists.get(position);
			holder.text.setText( live_row.getId() + " : " + live_row.getListdata() );
			
			return convertView;
		}
		private class ViewHolder {
			private TextView text;
		}
	}
}