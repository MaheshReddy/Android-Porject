package com.es.basic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class NewAppActivity extends Activity {
	private static final String TAG = "NewAppActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);

		final Set<String> apps = new HashSet<String>();
		String app = null;
		try {
			FileInputStream fis = openFileInput("apps_created");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			while ((app = br.readLine()) != null) {
				apps.add(app);
			}
			br.close();
			fis.close();

		} catch (FileNotFoundException e) {
			Log.d(TAG, "File apps_created NOT found!");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		final Spinner app1 = (Spinner) findViewById(R.id.spinner1);
		final Spinner app2 = (Spinner) findViewById(R.id.spinner2);
		final Spinner app3 = (Spinner) findViewById(R.id.spinner3);
		Button create = (Button) findViewById(R.id.button1);
		create.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				String mashup = (String) app1.getSelectedItem();
				mashup += "-" + (String) app2.getSelectedItem();
				mashup += "-" + (String) app3.getSelectedItem();
				apps.add(mashup);

				FileOutputStream fos = null;
				try {
					fos = openFileOutput("apps_created", Context.MODE_PRIVATE);
					for (String app : apps) {
						fos.write((app + '\n').getBytes());
					}
					Log.d(TAG, "wrote " + mashup);
					fos.close();

				} catch (FileNotFoundException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

}
	
