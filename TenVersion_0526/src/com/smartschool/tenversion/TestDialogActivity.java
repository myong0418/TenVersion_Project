package com.smartschool.tenversion;



import android.app.Activity;
import android.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TestDialogActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testdialog);

		// testDialog1 btn
		Button testDialog1Btn = (Button) findViewById(R.id.test_dialog1_btn);
		testDialog1Btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				showAlertDialog();
		
			}
		});
	}

	private void showAlertDialog() {
		LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout spotlayout = (LinearLayout) vi.inflate(R.layout.mkdialog, null);

		final EditText newplace = (EditText) spotlayout.findViewById(R.id.spot);

		new AlertDialog.Builder(this)
		.setTitle("변경할 장소")
		.setView(spotlayout)
		.setNeutralButton("확인", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(TestDialogActivity.this,
								"새장소 : " + newplace.getText().toString(),
								Toast.LENGTH_LONG).show();
					}
				}).show();
	}

}
