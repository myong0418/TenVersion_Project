package com.smartschool.tenversion;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestDialogActivity extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testdialog);

        //testDialog1 btn
        Button testDialog1Btn = (Button)findViewById(R.id.test_dialog1_btn);
        testDialog1Btn.setOnClickListener(this); 

    }

	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.test_dialog1_btn:  //Dialog 

			
			break;
		}	
	}
}