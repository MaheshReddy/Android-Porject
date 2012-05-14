package com.es.basic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BasicActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.head);

		Button newApp = (Button) findViewById(R.id.button2);
		newApp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						NewAppActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});

		Button appStore = (Button) findViewById(R.id.button3);
		appStore.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent myIntent = new Intent(view.getContext(),
						AppStoreActivity.class);
				startActivityForResult(myIntent, 0);
			}
		});
	}

}
	
