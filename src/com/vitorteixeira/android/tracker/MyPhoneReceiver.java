package com.vitorteixeira.android.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.vitorteixeira.android.tracker.service.CallRecorderService;
//import com.crashlytics.android.Crashlytics;

public class MyPhoneReceiver extends BroadcastReceiver {

    //Crashlytics.start(this);
	
	private static final String TAG = "MyPhoneReceiver";
	
	private Context context;
		
	PhoneStateListener phoneStateListener = new PhoneStateListener() {
		
		private String incomingNumber;
		
		private void startCallRecordService(int action) {
            Intent i = new Intent(MyPhoneReceiver.this.context, CallRecorderService.class);	    	
            i.putExtra("action", action);
            i.putExtra("phoneNumber", this.incomingNumber);
            MyPhoneReceiver.this.context.startService(i);			
		}
		
	    @Override
	    public void onCallStateChanged(int state, String incomingNumber) {	
	    	this.incomingNumber = incomingNumber;
	        if (state == TelephonyManager.CALL_STATE_RINGING) {
                Log.i(TAG, "Ligação começou");     
                this.startCallRecordService(CallRecorderService.IDLE);
	        } else {
	        	if(state == TelephonyManager.CALL_STATE_IDLE) {
	                Log.i(TAG, "Ligação terminou");	                
	                this.startCallRecordService(CallRecorderService.STOP);
		        } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
	                Log.i(TAG, "Ligação atendida");
	                this.startCallRecordService(CallRecorderService.START);
		        }
	        }
        	Log.i(TAG, incomingNumber + " " + state);
	        super.onCallStateChanged(state, incomingNumber);
	    }
	};
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(this.phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        telephonyManager.listen(this.phoneStateListener, PhoneStateListener.LISTEN_NONE);     
        
	}
	

}
