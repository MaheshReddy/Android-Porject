package com.es.basic;

import com.es.flows.Flow1;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BasicActivity1 extends Activity {
	boolean first = true;
	AlertDialog.Builder alertbox;
	Button app1;
	EditText input;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooseexst);
		app1 = (Button) findViewById(R.id.button1);
		app1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						Flow1.class);
				myIntent.putExtra("apps", "goglCal&twitter");
				//myIntent.putExtra("apps", "goglCal&email");
				//myIntent.putExtra("apps", "amazone");
				startActivityForResult(myIntent, 0);
			}

		});
		
	}
	
		
}
	
