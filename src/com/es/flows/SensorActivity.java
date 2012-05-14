package com.es.flows;

import com.es.basic.R;
import com.es.basic.R.layout;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class SensorActivity extends Activity {
	/** Called when the activity is first created. */
	LocationManager mlocManager;
	LocationListener mlocListener;
	@Override
	public void onPause(){
		super.onPause();
		mlocManager.removeUpdates(mlocListener);
	}
	@Override
	public void onResume(){
		super.onResume();
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);

	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor);
		Toast.makeText(getApplicationContext(), "GPS ", 100).show();
		 mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Toast.makeText(this, "Please simulate gps via telnet", 200).show();
		 mlocListener = new LocationListener() {

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub

			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "GPS Enabled", 100)
						.show();

			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "GPS Disabled", 100)
						.show();
			}

			public void onLocationChanged(Location loc) {
				// TODO Auto-generated method stub
				loc.getLatitude();

				loc.getLongitude();

				String Text = "My current location is: " +

				"Latitud = " + loc.getLatitude() +

				"Longitud = " + loc.getLongitude();

				Toast.makeText(getApplicationContext(),

				Text,

				Toast.LENGTH_SHORT).show();
			}
		};

		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				mlocListener);

	}
}
