package com.es.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.es.basic.BasicActivity;
import com.expertiseandroid.lib.sociallib.connectors.SocialNetworkHelper;
import com.expertiseandroid.lib.sociallib.connectors.TwitterConnector;
import com.expertiseandroid.lib.sociallib.exceptions.NotAuthentifiedException;

public class SocialLibActivity extends Activity {
	private static final String CONS_KEY = "9AM37qFIN46S6s4RBnSQw";
	private static final String CONS_SEC = "rwWcmNd0vjxvjeVqt8qyGWkq6zOufjt3Jznb3oDfY";
	private static final String CALLBACK = "proapp://sociallib.com";

	private static TwitterConnector mConn;

	private static final String LOG_TAG = "SocialLibActivity";

	private static DateFormat logDateFormat = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss", Locale.getDefault());

	private static ArrayList<String> msg;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.head);

	}

	@Override
	public void onResume() {
		super.onResume();

		Uri uri = this.getIntent().getData();
		if (uri != null && uri.toString().startsWith(CALLBACK)) {
			returningFromWebPage();
		} else {
			firstTimeHere();
		}
	}

	private void firstTimeHere() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			Log.d(LOG_TAG, "making msg array from extras");
			msg = extras.getStringArrayList("args");
			if (msg == null) {
				Log.e(LOG_TAG, "Null msg array passed from previous step?");
			}
		}
		else{
			Log.d(LOG_TAG, "creating default msg array");
			msg = new ArrayList<String>();
			msg.add("Default:");
		}

		mConn = SocialNetworkHelper.createTwitterConnector(CONS_KEY, CONS_SEC,
				CALLBACK);
		/*
		mConn = SocialNetworkHelper.createTwitterConnector(CONS_KEY, CONS_SEC,
				null);
		 */
		try {
			mConn.requestAuthorization(this);
		} catch (OAuthMessageSignerException e) {
			Log.e(LOG_TAG, "", e);
		} catch (OAuthNotAuthorizedException e) {
			Log.e(LOG_TAG, "", e);
		} catch (OAuthExpectationFailedException e) {
			Log.e(LOG_TAG, "", e);
		} catch (OAuthCommunicationException e) {
			Log.e(LOG_TAG, "", e);
		}
		// After this line of code, the user will be redirected to a web page to
		// enter his credentials
	}

	private void returningFromWebPage() {
		// List<TwitterUser> ulist;
		// List<TwitterStatus> list;

		// We are returning to the activity, the user has enterd his credentials
		// already, so we can authorize the application
		try {
			mConn.authorize(this);

			/*
			ulist = mConn.getFollowers();
			Log.i(LOG_TAG, "===== Followers =====");
			for (TwitterUser us : ulist) {
				Log.i(LOG_TAG, us.getUsername() + ", " + us.name);
			}
			 */
			if (msg == null) {
				Log.e(LOG_TAG, "NULL msg array?");
			} else {
				Iterator<String> itr = msg.iterator();
				while (itr.hasNext()) {
					mConn.tweet(itr.next() + " @ "
							+ logDateFormat.format(new Date()));
				}
			}

			/*
			Thread.sleep(20 * 1000);

			list = mConn.getWallPosts(); // We create a list containing the
											// user's wall posts
			Log.i(LOG_TAG, "===== Wall Posts =====");
			for (TwitterStatus st : list) {
				Log.i(LOG_TAG, st.getContents());
			}
			 */

		} catch (NotAuthentifiedException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		} catch (OAuthMessageSignerException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		} catch (OAuthNotAuthorizedException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		} catch (OAuthExpectationFailedException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		} catch (OAuthCommunicationException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		} catch (SAXException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		} catch (ParserConfigurationException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		} catch (IOException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		} /*catch (InterruptedException e) {
			Log.e(LOG_TAG, "ERROR! ", e);
		}*/

		//Intent intent = new Intent();
		System.out.println("Flow1.returningFromWebPage()"+"Returning Twitter");
		Intent step3Intent = new Intent(this,
				BasicActivity.class);
		startActivityForResult(step3Intent,0);
	}

}
