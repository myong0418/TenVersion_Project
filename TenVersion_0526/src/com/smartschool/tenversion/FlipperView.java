package com.smartschool.tenversion;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

public class FlipperView extends Activity{
	  private static final int MOUSE_DRAG_SENSITIVITY = 30;
	  private static final int MOUSE_MOVE_SENSITIVITY = 50;
	  
	  ViewFlipper flipper;
	  RelativeLayout rv;
	  float start;
	  float end;
	  
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.flipper);

	    flipper = (ViewFlipper) findViewById(R.id.view_flipper);
	    flipper.setOnTouchListener(new View.OnTouchListener() {
	      
	      public boolean onTouch(View v, MotionEvent event) {
	        if(v != flipper){ 
	          Log.d("IIIII","v != flipper");
	          return false;
	        }
	        if(event.getAction() == MotionEvent.ACTION_DOWN){
	          start = event.getX();
	          Log.d("IIIII","start = " + start);
	          
	        }else if(event.getAction() == MotionEvent.ACTION_UP){
	          end = event.getX();
	          Log.d("IIIII","end = " + end);
	          if(start-end < -MOUSE_DRAG_SENSITIVITY){
	            right();
	            flipper.showNext();
	          }else if(start-end > MOUSE_DRAG_SENSITIVITY){
	            left();
	            flipper.showPrevious();
	          }  
	        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
	          Log.d("IIIII","move = " + event.getX());
	          if(start-event.getX()< - MOUSE_MOVE_SENSITIVITY){
	            start = event.getX();
	            right();
	            flipper.showNext();
	          }else if(start-event.getX() > MOUSE_MOVE_SENSITIVITY){
	            start = event.getX();
	            left();
	            flipper.showPrevious();
	          }  
	        }
	        return true;
	      }
	    });
	  }
	  private void left(){
	    flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_left_out));
	  }
	  private void right(){
	    flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_in));
	    flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.push_right_out));
	  }
	}


