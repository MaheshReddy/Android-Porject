package com.es.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.es.basic.R;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class FacebookApp1Activity extends Activity {
	private static String TAG = "FacebookApp1Activity";
	private static String FB_APP_ID = "101864806618686";

	private final Facebook facebook = new Facebook(FB_APP_ID);
	private AsyncFacebookRunner mAsyncRunner = null;
	private SharedPreferences mPrefs;

	/** Called when the activity is first created. */
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/*
		 * Get existing access_token if any
		 */
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);
		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}
		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		/*
		 * Only call authorize if the access_token has expired.
		 */
		if (!facebook.isSessionValid()) {
			final String TAG1 = TAG + ".DialogListener";
			facebook.authorize(this, new String[] { "email",
					"publish_checkins", "publish_stream" },
					new DialogListener() {
				public void onComplete(Bundle values) {
					String str = "{ ";
					for (String key : values.keySet()) {
						str += "\"" + key + "\":\"" + values.get(key) + "\", ";
					}
					str += " }";
					Log.d(TAG1, "Completed! " + str);

					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token", facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();
				}

				public void onFacebookError(FacebookError error) {
					Log.e(TAG1, "Facebook Error! ", error);
				}

				public void onError(DialogError e) {
					Log.e(TAG1, "Dialog Error! ", e);
				}

				public void onCancel() {
					Log.d(TAG1, "User Canceled");
				}
			});
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		facebook.extendAccessTokenIfNeeded(this, null);

		String token = facebook.getAccessToken();
		if (token != null) {
			Log.d(TAG, "FB authorization completed, access token = " + token);
		} else {
			Log.e(TAG, "NULL access token?");
			return;
		}

		if (mAsyncRunner == null)
			mAsyncRunner = new AsyncFacebookRunner(facebook);

		Bundle params = new Bundle();
		params.putString(Facebook.TOKEN, token);
		params.putString("message", createRandomMessage());
		mAsyncRunner.request("/me/feed", params, "POST", new SampleReqListener(
				FacebookOper.FB_POST), null);
	}

	private static String createRandomMessage() {
		return "some high entropy text: " + System.currentTimeMillis();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		facebook.authorizeCallback(requestCode, resultCode, data);
	}

	class SampleReqListener implements RequestListener {
		private final FacebookOper mOper;
		private final String mTag;

		SampleReqListener(FacebookOper oper) {
			super();

			this.mOper = oper;
			if (FacebookOper.FB_POST.equals(this.mOper)) {
				this.mTag = TAG + ".My_Post_Listener";
			} else if (FacebookOper.FB_REVOKE_PERMS.equals(oper)) {
				this.mTag = TAG + ".My_RevokePerms_Listener";
			} else if (FacebookOper.FB_LOGOUT.equals(oper)) {
				this.mTag = TAG + ".My_Logout_Listener";
			} else {
				this.mTag = TAG + ".SampleReqListener";
			}
		}

		public void onComplete(String response, Object state) {
			Log.d(mTag, "Completed! got response: " + response);
			if (FacebookOper.FB_POST.equals(mOper)) {
				/*
				 * this will revoke 'publish_stream' permission Note: If you
				 * don't specify a permission then this will de-authorize the
				 * application completely.
				 */
				Bundle params = new Bundle();
				params.putString("permission", "publish_stream");
				mAsyncRunner.request("/me/permissions", params, "DELETE",
						new SampleReqListener(FacebookOper.FB_REVOKE_PERMS),
						null);

			} else if (FacebookOper.FB_REVOKE_PERMS.equals(mOper)) {
				mAsyncRunner.logout(getApplicationContext(),
						new SampleReqListener(FacebookOper.FB_LOGOUT));

			} else if (FacebookOper.FB_LOGOUT.equals(mOper)) {
				SharedPreferences.Editor editor = mPrefs.edit();
				editor.clear();
				editor.commit();

				finish();

			}
		}

		public void onIOException(IOException e, Object state) {
			Log.e(mTag, "IO Exception! ", e);
		}

		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			Log.e(mTag, "File Not Found! ", e);
		}

		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			Log.e(mTag, "Invalid URL! ", e);
		}

		public void onFacebookError(FacebookError e, Object state) {
			Log.e(mTag, "Facebook error! ", e);
		}

	}
}