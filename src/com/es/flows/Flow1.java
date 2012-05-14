package com.es.flows;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.es.basic.BasicActivity;
import com.es.basic.R;
import com.es.basic.R.id;
import com.es.basic.R.layout;
import com.es.services.Email;
import com.es.services.FacebookApp1Activity;
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

	private ArrayList<String> runApp(String app_name,ArrayList<String> args){
		if (app_name.equals("goglCal")){
			GoogleCal cal = new GoogleCal();
		     ArrayList<String> str = cal.onCreate();
		    return str;
		}
		else if (app_name.equals("twitter")){
			Intent step2Intent = new Intent(this.getBaseContext(),
					SocialLibActivity.class);
			step2Intent.putExtra("args", args);
			startActivityForResult(step2Intent, 1);
		}
		else if (app_name.equals("facebook")){

			Intent step3Intent = new Intent(this.getBaseContext(),
					FacebookApp1Activity.class);
			step3Intent.putExtra("args", args);
			startActivityForResult(step3Intent, 1);
		}
		else if (app_name.equals("email")){
			Intent step3Intent = new Intent(this.getBaseContext(),
					Email.class);
			step3Intent.putExtra("args", args);
			startActivityForResult(step3Intent, 1);
		}
		return null;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String apps = extras.getString("apps");
		String[] app_list = apps.split("&");
		ArrayList<String> args = null;
		for (int i=0;i<app_list.length;i++){
			System.out.println(app_list[i]);
			args = runApp(app_list[i],args);
		}
		//setContentView(R.layout.note);
		//SocialLibActivity twitter = new SocialLibActivity();
		//twitter.twitterPost();
		//Twitterservicetest.main(null);
		/*
		Intent step1Intent = new Intent(this.getBaseContext(),
				BasicActivity.class);
		startActivityForResult(step1Intent, 0);*/
		
		
		
		}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
	//	Toast.makeText(getApplicationContext(), "OnActRes", 800).show();
		if (data != null) {
			if (data.getStringExtra("FileName") != null) {
				System.out.println("got result "
						+ data.getStringExtra("FileName"));
				if (requestCode == 0) {
					if (resultCode == RESULT_OK) {
						
					}
				}
			}
		}
		System.out.println("Done");
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