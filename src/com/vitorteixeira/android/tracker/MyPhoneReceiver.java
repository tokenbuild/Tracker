package com.vitorteixeira.android.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyPhoneReceiver extends BroadcastReceiver {
	
	private static final String TAG = "MyPhoneReceiver";
	
	PhoneStateListener phoneStateListener = new PhoneStateListener() {
	    @Override
	    public void onCallStateChanged(int state, String incomingNumber) {
	        if (state == TelephonyManager.CALL_STATE_RINGING) {
                Log.i(TAG, "Ligação começou");
	        } else if(state == TelephonyManager.CALL_STATE_IDLE) {
                Log.i(TAG, "Ligação recusada");
	        } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                Log.i(TAG, "Ligação terminou");
	        }
        	Log.i(TAG, incomingNumber + " " + state);
	        super.onCallStateChanged(state, incomingNumber);
	    }
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(this.phoneStateListener,PhoneStateListener.LISTEN_CALL_STATE);
        telephonyManager.listen(this.phoneStateListener, PhoneStateListener.LISTEN_NONE);
	}

}
