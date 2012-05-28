package com.smartschool.tenversion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LiveListAddActivity extends Activity  implements View.OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livelist);
        
        Button button = (Button)findViewById(R.id.add_btn);
        button.setOnClickListener(this);
                
    }

	public void onClick(View v) {
		new AlertDialog.Builder(this)
		.setTitle("인사")
		.setMessage("안녕하세요!!")
		.setNeutralButton("확인",null)
		.show();
		
	}
}
