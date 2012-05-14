package com.es.basic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.es.flows.Flow1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AppStoreActivity extends Activity {
	private static final String TAG = "AppStoreActivity";
	int BTN_ID_START = 0x3f010000;

	/** Called when the activity is first created. */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chooseexst);

		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout1);
		LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		p.setMargins(3, 3, 3, 3);

		String mashup = null;
		try {
			FileReader fis = new FileReader(getApplicationContext()
					.getFilesDir() + "/" + "apps_created");
			BufferedReader br = new BufferedReader(fis);
			int i = 0;
			while ((mashup = br.readLine()) != null) {
				++i;
				Log.d(TAG, "read " + mashup);
				Button buttonView = new Button(this);
				buttonView.setText(mashup);
				buttonView.setTextAppearance(getApplicationContext(),
						R.style.ButtonText);
				buttonView.setShadowLayer(2, 1, 1, Color.BLACK);
				buttonView.setBackgroundResource(R.drawable.bluebt);
				buttonView.setId(BTN_ID_START + i);
				final String name = new String(mashup);
				buttonView.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						Intent myIntent = new Intent(view.getContext(),
								Flow1.class);
						myIntent.putExtra("apps", name);
						//myIntent.putExtra("apps", "goglCal&email");
						//myIntent.putExtra("apps", "amazone");
						startActivityForResult(myIntent, 0);
					}

				});
				layout.addView(buttonView, p);
			}
			br.close();
			fis.close();

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		/*
		LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout1);
		for (int i = 1; i <= 10; i++) {
			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p.setMargins(3, 3, 3, 3);
			Button buttonView = new Button(this);
			buttonView.setText("App " + i);
			buttonView.setTextAppearance(getApplicationContext(),
					R.style.ButtonText);
			buttonView.setShadowLayer(2, 1, 1, Color.BLACK);
			buttonView.setBackgroundResource(R.drawable.bluebt);
			buttonView.setId(BTN_ID_START + i);
			layout.addView(buttonView, p);
		}
		*/
	}

}
	
