package com.vitorteixeira.android.tracker.helper;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import android.media.MediaRecorder;
import android.os.Environment;
import android.text.format.DateFormat;

/**
 * Created by vitorteixeira on 12/19/13.
 */
public class CallRecorder {

    private MediaRecorder recorder;

	private String phoneNumber;
    
    private static final int PREPARED = 1;
    private static final int STARTED = 2; 
    private static final int INICIALIZED = 0;
    
    private int status;
    
    public CallRecorder() {
    	this.phoneNumber = new String();
    	this.status = CallRecorder.INICIALIZED;
    }
    
    public void setPhoneNumber(String phoneNumber) {
    	this.phoneNumber = phoneNumber;
    }
    
    public void prepare(){
    	
    	this.recorder = new MediaRecorder();
    	
        try {
            this.recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_CALL);
            this.recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            this.recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);        
            this.recorder.setOutputFile(this.getPath() + this.getNewFileName());        	
            this.recorder.prepare();
            this.status = CallRecorder.PREPARED;
        } catch (Exception e) {
            e.printStackTrace();
        }    	
    }

    private String getNewFileName() {
    	Date date = new Date(java.lang.System.currentTimeMillis());    	
    	return this.phoneNumber + "_" + DateFormat.format("yyyy_MM_d_hh_mm_ss", date).toString() +".3gp";
	}

    public void start() {
    	if (this.status > CallRecorder.INICIALIZED) {
            try {            	
                this.recorder.start();
                this.status = CallRecorder.STARTED;
            } catch (Exception e) {
                e.printStackTrace();
            }    		
    	}
    }

    public void stop(){
    	if (this.status > CallRecorder.INICIALIZED) {
            try {
                this.recorder.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }    		
    	}    	
    }

    private String getPath() throws IOException{
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Path to file could not be created.");
        }
        return path;
    }
}
