package com.smartschool.tenversion;

import java.util.Calendar;
import java.util.GregorianCalendar;
import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;

public class AlramActivity extends Activity implements OnDateChangedListener,
		OnTimeChangedListener {
	/*
	 * �˶����� �ɹ� ����
	 */
	// �˶� �޴���
	private AlarmManager mManager;
	// ���� �Ͻ�
	private GregorianCalendar mCalendar;
	// ���� ���� Ŭ����
	private DatePicker mDate;
	// ���� ���� Ŭ����
	private TimePicker mTime;

	/*
	 * ���� ���� �ɹ� ����
	 */
	private NotificationManager mNotification;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���� �Ŵ����� ���
		mNotification = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// �˶� �Ŵ����� ���
		mManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		// ���� �ð��� ���
		mCalendar = new GregorianCalendar();
		Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
		// �� ��ư, ���¹�ư�� �����ʸ� ���
		setContentView(R.layout.alram);
		Button b = (Button) findViewById(R.id.set);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setAlarm();
			}
		});
		b = (Button) findViewById(R.id.reset);
		b.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				resetAlarm();
			}
		});
		// �Ͻ� ���� Ŭ������ ���� �ð��� ����
		mDate = (DatePicker) findViewById(R.id.date_picker);
		mDate.init(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
				mCalendar.get(Calendar.DAY_OF_MONTH), this);
		mTime = (TimePicker) findViewById(R.id.time_picker);
		mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
		mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
		mTime.setOnTimeChangedListener(this);
	}

	// �˶��� ����
	private void setAlarm() {
		mManager.set(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(),
				pendingIntent());
		Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
	}

	// �˶��� ����
	private void resetAlarm() {
		mManager.cancel(pendingIntent());
	}

	// �˶��� ���� �ð��� �߻��ϴ� ����Ʈ �ۼ�
	private PendingIntent pendingIntent() {
		Intent i = new Intent(getApplicationContext(), AlramActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
		return pi;
	}

	// ���� ���� Ŭ������ ���º�ȭ ������
	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		mCalendar.set(year, monthOfYear, dayOfMonth, mTime.getCurrentHour(),
				mTime.getCurrentMinute());
		Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
	}

	// �ð� ���� Ŭ������ ���º�ȭ ������
	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		mCalendar.set(mDate.getYear(), mDate.getMonth(), mDate.getDayOfMonth(),
				hourOfDay, minute);
		Log.i("HelloAlarmActivity", mCalendar.getTime().toString());
	}
}