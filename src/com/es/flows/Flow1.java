package com.es.flows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.es.basic.R;
import com.es.basic.R.id;
import com.es.basic.R.layout;
import com.es.services.GoogleCal;
import com.es.services.SocialLibActivity;
import com.es.services.Twitterservicetest;
import com.google.gdata.data.DateTime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Flow1 extends Activity {
	/** Called when the activity is first created. */
	EditText name;
	EditText txt;
	Button save;
	EditText openname;
	Button open;
	AlertDialog.Builder alertbox;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);
		//SocialLibActivity twitter = new SocialLibActivity();
		//twitter.twitterPost();
		//Twitterservicetest.main(null);
		Intent step1Intent = new Intent(this.getBaseContext(),
				GoogleCal.class);
		startActivityForResult(step1Intent, 0);
		Intent step2Intent = new Intent(this.getBaseContext(),
				SocialLibActivity.class);
		step2Intent.putExtra("msg", "Mahesh:");
		startActivityForResult(step2Intent, 0);
		
		}
	
}

/*
 * 
 * 
 * boolean mExternalStorageAvailable = false; boolean mExternalStorageWriteable
 * = false;
 * 
 * // TODO Auto-generated method stub if
 * (Environment.MEDIA_MOUNTED.equals(state)) { // We can read and write the
 * media Toast.makeText(getApplicationContext(),
 * "SD card is available for read/write", 100); mExternalStorageAvailable =
 * mExternalStorageWriteable = true;
 * 
 * } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) { // We can
 * only read the media mExternalStorageAvailable = true;
 * mExternalStorageWriteable = false; Toast.makeText(getApplicationContext(),
 * "SD card is read only,please enable write", 100); } else { // Something else
 * is wrong. It may be one of many other // states, but all we need // to know
 * is we can neither read nor write Toast.makeText(getApplicationContext(),
 * "SD card is unavailable,cant save files", 100); mExternalStorageAvailable =
 * mExternalStorageWriteable = false; } Toast.makeText(getApplicationContext(),
 * mExternalStorageAvailable +" " + mExternalStorageWriteable, 100);
 * 
 * if (mExternalStorageAvailable && mExternalStorageWriteable) { File myDir =
 * new File("/sdcard/saved_txt"); myDir.mkdirs();
 * 
 * String fname = name.toString(); File file = new File(myDir, fname); if
 * (file.exists()) file.delete(); try { FileWriter out = new FileWriter(file);
 * BufferedWriter br = new BufferedWriter(out); br.write(txt.toString());
 * Toast.makeText(getApplicationContext(), "File saved at sdcard/saved_txt",
 * 100); out.flush(); out.close();
 * 
 * } catch (Exception e) { e.printStackTrace(); } }
 */