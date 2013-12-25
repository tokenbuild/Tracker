package com.vitorteixeira.android.tracker.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.vitorteixeira.android.tracker.listener.CallReceiverListener;

public class CallReceiver extends BroadcastReceiver {
		
	private CallReceiverListener callReceiverListener;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub		
		this.callReceiverListener = new CallReceiverListener(context, intent);
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(this.callReceiverListener, PhoneStateListener.LISTEN_CALL_STATE);
        telephonyManager.listen(this.callReceiverListener, PhoneStateListener.LISTEN_NONE);     
        
	}
	

}
