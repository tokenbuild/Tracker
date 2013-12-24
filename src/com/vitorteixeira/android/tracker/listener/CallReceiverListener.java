package com.vitorteixeira.android.tracker.listener;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.vitorteixeira.android.tracker.helper.CallDirection;
import com.vitorteixeira.android.tracker.service.CallRecorderService;

public class CallReceiverListener extends PhoneStateListener {

	private String phoneNumber;
	private Context context;
	private Intent intent;
	private final static String TAG = "CallReceiverListener";

	public CallReceiverListener(Context context, Intent intent) {
		this.intent = intent;
		this.context = context;
	}	
	
	private void startCallRecordService(int action, CallDirection direction) {
        Intent i = new Intent(this.context, CallRecorderService.class);	    	
        i.putExtra("action", action);
        i.putExtra("phoneNumber", this.phoneNumber);
        i.putExtra("direction", direction.toString());
        this.context.startService(i);			
	}	
	
	private void startCallRecordService(int action) {
        Intent i = new Intent(this.context, CallRecorderService.class);	    	
        i.putExtra("action", action);
        i.putExtra("phoneNumber", this.phoneNumber);
        this.context.startService(i);			
	}
	
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {	
    	this.setPhoneNumber(incomingNumber);
    	this.handleCall(state);    	
    	Log.i(TAG, this.phoneNumber + " " + state);
        super.onCallStateChanged(state, incomingNumber);
    }	
    
    private void setPhoneNumber(String incomingNumber) {
    	if (this.intent.getAction() == Intent.ACTION_NEW_OUTGOING_CALL) {
    		this.phoneNumber = this.intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
    	} else {
    		this.phoneNumber = incomingNumber;
    	}    	
    }
    
	private void handleCall(int state) {
    	if (this.intent.getAction() == Intent.ACTION_NEW_OUTGOING_CALL) {
    		this.outgoingFlow(state);
    	} else {
    		this.incomingFlow(state);
    	} 
	}  
	
	private void outgoingFlow(int state) {
        Log.i(TAG, "Ligação começou");     
        this.startCallRecordService(CallRecorderService.IDLE, CallDirection.OUTGOING);		
	}	
	
	private void incomingFlow(int state) {
        if (state == TelephonyManager.CALL_STATE_RINGING) {
            Log.i(TAG, "Ligação começou");     
            this.startCallRecordService(CallRecorderService.IDLE, CallDirection.INCOMING);
        } else {
        	if(state == TelephonyManager.CALL_STATE_IDLE) {
                Log.i(TAG, "Ligação terminou");	                
                this.startCallRecordService(CallRecorderService.STOP);
	        } else if(state == TelephonyManager.CALL_STATE_OFFHOOK) {
                Log.i(TAG, "Ligação atendida");
                this.startCallRecordService(CallRecorderService.START);
	        }
        }			
	}	
	
}
