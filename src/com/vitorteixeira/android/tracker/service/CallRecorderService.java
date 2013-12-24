package com.vitorteixeira.android.tracker.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

import com.vitorteixeira.android.tracker.helper.CallDirection;
import com.vitorteixeira.android.tracker.helper.CallRecorder;

public class CallRecorderService extends IntentService {

	public static final int IDLE = 1;	
	public static final int START = 2;	
	public static final int STOP = 3;
	
	private static final String TAG = "CallRecorderService";		
	
	private CallRecorder callRecorder;
	
	public CallRecorderService() {
		super(TAG);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate() {
		super.onCreate();		
		Log.i(TAG, "onCreate");
		this.callRecorder = new CallRecorder();
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		int action = intent.getIntExtra("action", 0);
		
		if (action == CallRecorderService.START) {
			Log.i(TAG, "action START");
			this.callRecorder.start();
		} else if (action == CallRecorderService.STOP) {
			Log.i(TAG, "action STOP");	
			this.callRecorder.stop();
			this.stopSelf();
		} else if (action == CallRecorderService.IDLE) {
			Log.i(TAG, "action IDLE");
			this.callRecorder.setPhoneNumber(intent.getStringExtra("phoneNumber"));			
			this.callRecorder.setCallDirection(CallDirection.valueOf(intent.getStringExtra("direction")));			
			this.callRecorder.prepare();
		}
		
		Log.i(TAG, "Received an intent: " + intent);		
		
		return Service.START_STICKY;
	}

}
