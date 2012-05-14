package com.es.flows;

import java.util.ArrayList;

import com.es.basic.BasicActivity;
import com.es.basic.BasicActivity1;
import com.es.services.AmazonWSApp1Activity;
import com.es.services.Email;
import com.es.services.FacebookApp1Activity;
import com.es.services.GoogleCal;
import com.es.services.SocialLibActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
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
	private static String[] app_list;
	private static ArrayList<String> args;
	private static int app_count;
	
	private ArrayList<String> runApp(String app_name,ArrayList<String> args,int retVal){
		System.out.println("Flow1.runApp():"+retVal+":"+app_name);
		if (app_name.equals("gcal")){
			GoogleCal cal = new GoogleCal();
		     ArrayList<String> str = cal.onCreate();
		    return str;
		}
		else if (app_name.equals("twitter")){
			Intent step2Intent = new Intent(this,
					SocialLibActivity.class);
			step2Intent.putExtra("args", args);
			startActivityForResult(step2Intent, retVal);
			System.out.println("Flow1.runApp() return " + app_name);
			return args;
		}
		else if (app_name.equals("facebook")){

			Intent step3Intent = new Intent(this,
					FacebookApp1Activity.class);
			step3Intent.putExtra("args", args);
			startActivityForResult(step3Intent, retVal);
		}
		else if (app_name.equals("gmail")){
			Intent step3Intent = new Intent(this,
					Email.class);
			step3Intent.putExtra("args", args);
			startActivityForResult(step3Intent, retVal);
			System.out.println("Flow1.runApp() return " + app_name);
			return args;
		}
		else if(app_name.equals("aws")){
			AmazonWSApp1Activity amz = new AmazonWSApp1Activity();
			return amz.onCreate();
			
		}
		else if (app_name.equals("home")){
			Intent step3Intent = new Intent(this,
					BasicActivity.class);
			step3Intent.putExtra("args", args);
			startActivityForResult(step3Intent, retVal);
		}
		return null;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String apps = extras.getString("apps");
		apps = apps + "-home";
		app_list = apps.split("-");
		args = null;
		app_count=0;
		System.out.println("Flow1.onCreate()"+app_count);
		args = runApp(app_list[app_count++],null,1);
		onActivityResult(1,1,null);
		//Intent intent = new Intent();
		//setResult(RESULT_OK, intent);
		//finish();
		//setContentView(R.layout.note);
		//SocialLibActivity twitter = new SocialLibActivity();
		//twitter.twitterPost();
		//Twitterservicetest.main(null);
		/*
		Intent step1Intent = new Intent(this.getBaseContext(),
				BasicActivity.class);
		startActivityForResult(step1Intent, 0);*/
		
		
		
		}

	protected void onActivityResult(int requestCode, int resultCode,
            Intent data) {
		System.out.println("Flow1.onActivityResult():"+app_list[app_count++]+requestCode);
        if (requestCode == 1) {
                Toast.makeText(getApplicationContext(),"back from 1st activity", 200);
                //System.out.println("BasicActivity.onActivityResult() 1");
                	//System.out.println(app_list[app_count]);
                	//if (app_count < app_list.length)
                	args = runApp(app_list[1],args,2);
                
    			
        		
            }
        if (requestCode == 2) {
            Toast.makeText(getApplicationContext(),"back from 1st activity", 200);
            //System.out.println("BasicActivity.onActivityResult() 1");
            	//System.out.println(app_list[app_count]);
            	//if (app_count < app_list.length)
            	args = runApp(app_list[2],args,3);
    		
        }
        if (requestCode == 3) {
            Toast.makeText(getApplicationContext(),"back from 1st activity", 200);
            //System.out.println("BasicActivity.onActivityResult() 1");
            	//System.out.println(app_list[app_count]);
            	//if (app_count < app_list.length)
            setResult(RESULT_OK);
            finish();
            //args = runApp(app_list[3],args,4);
    		
        }
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