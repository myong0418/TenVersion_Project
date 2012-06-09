package com.smartschool.tenversion;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class FlipperView extends Activity  implements View.OnTouchListener {
	private static final String TAG = "TestFlipperviewActivity";
    ViewFlipper flipper;
     
     float xAtDown;
     float xAtUp;

     private int listNum = 20;      //TODO add list length
     
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flipper);

		flipper = (ViewFlipper) findViewById(R.id.view_flipper);
		flipper.setOnTouchListener(this);

		// 동적으로 화면하나 추가
		for(int i=0; i<listNum; i++){
			TextView tv = new TextView(this);
			tv.setText("View :: "+i+"\nDynamically added ");
			tv.setTextColor(Color.CYAN);
			tv.setTag(i);
			flipper.addView(tv);
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (v != flipper)
			return false;

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			xAtDown = event.getX(); // 처음 터치 위치 저장
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			xAtUp = event.getX(); // 터치 끝 위치 저장

			int childViewNum= flipper.getDisplayedChild();
			
			if (xAtUp < xAtDown) {
				
				
				if(childViewNum  >= listNum-1){	
					Log.v(TAG,"childViewNum  >= listNum-1::"+childViewNum);	
					showPreview();
				}else{
					Log.v(TAG,"showNext()  ::"+childViewNum );
					left();
					flipper.showNext();
				}
				
			} else if (xAtUp > xAtDown) {
				if(childViewNum  <= 0){
					Log.v(TAG,"childViewNum  <= 0::"+childViewNum);
				}else{
					Log.v(TAG,"showPrevious ::"+childViewNum);
					right();
					flipper.showPrevious();
				}
			}
		}
		return true;
	}

	  private void left(){
	    flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_out));
	  }
	  private void right(){
	    flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_out));
	  }
	  
	  //TODO showPreview
	  private void showPreview(){ 
		  
	  }
	}


