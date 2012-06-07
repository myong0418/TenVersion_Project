package com.smartschool.tenversion;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CheckListAdapter extends ArrayAdapter<CheckListProfile> implements
		OnClickListener {
	private static final String TAG = "CheckListAdapter";
	private final LayoutInflater mInflater;
	// private static int MODE; //safe,live,etc
	private static final int DEL_DLG = 1;
	private boolean DEL_MODE = false;
	private static final int KEY_ID = 55;
	private static final int KEY_MODE = 66;

	ArrayList<CheckListProfile> checkListProfileList;

	private Context mContext;
	private SafeListActivity safeListActivity;
	private LiveListActivity liveListActivity;

	private boolean[] isChecked;
	private String mselectAll;
	private String munselectAll;
	private String sortOrder;

	private final int HIDE_CODE = 92728202;
	private boolean[] checkFlag;
	private boolean[] loadingFlag;

	// List<ContacstData> contacts;

	public CheckListAdapter(Context context, int textViewResourceId,
			ArrayList<CheckListProfile> objects, boolean del_mode) {
		super(context, textViewResourceId, objects);
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		checkListProfileList = objects;

		DEL_MODE = del_mode;
//		safeListActivity = (SafeListActivity) context;
//		liveListActivity = (LiveListActivity) context;

	}

	ViewHolder holder;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.v(TAG, "getView");
		// final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.checklist_item_row, null);
			holder = new ViewHolder();
			holder.mRLayout = (RelativeLayout) convertView
					.findViewById(R.id.buddy_rayout);
			// holder.mImage
			// =(ImageView)convertView.findViewById(R.id.buddy_image);
			// holder.mMode = (TextView) convertView.findViewById(R.id.mode);
			holder.mContents = (TextView) convertView
					.findViewById(R.id.contents);

			holder.mDelBtn = (ImageButton) convertView
					.findViewById(R.id.mindelBtn);
			if (DEL_MODE) {
				holder.mDelBtn.setVisibility(View.VISIBLE);
			} else {
				holder.mDelBtn.setVisibility(View.GONE);
			}

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// set background
		if (position % 2 == 0) {
			holder.mRLayout.setBackgroundResource(R.drawable.panal_2);
		} else {
			holder.mRLayout.setBackgroundResource(R.drawable.panal_1);
		}

		CheckListProfile item = checkListProfileList.get(position);
		holder.mContents.setText(item.getContents());

		holder.mDelBtn.setTag(R.id.tag_id, item.getId());
		holder.mDelBtn.setTag(R.id.tag_mode, item.getMode());
		holder.mDelBtn.setTag(R.id.tag_contents, item.getContents());

		holder.mDelBtn.setOnClickListener(this);
		return convertView;
	}

	private class ViewHolder {
		private RelativeLayout mRLayout;
		private TextView mContents;
		private ImageButton mDelBtn;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mindelBtn:
			Log.v(TAG, "mDelBtn [onClick]");
			long id = Long.valueOf(v.getTag(R.id.tag_id).toString());
			Log.v(TAG, "id " + id);

			int mode = Integer.parseInt(v.getTag(R.id.tag_mode).toString());
			Log.v(TAG, "mode " + mode);

			String contents = v.getTag(R.id.tag_contents).toString();
			Log.v(TAG, "contents " + contents);

			if (mode == 1) { // safe
				((SafeListActivity) mContext).delCheckListDialog(id, contents);

			} else if (mode == 2) { // live
				((LiveListActivity) mContext).delCheckListDialog(id, contents);
				// }else if(MODE ==3){ //etc
				// ((EtcListActivity)mContext).addCheckList(addText);
			}
			break;
		}

	}

}
