package com.gec.easysports;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.gec.easysports.view.FloatLabeledEditText;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class LogInPageActivity extends Activity implements OnClickListener {
	private TextView login, register, skip;
	CallbackManager callbackManager;
	private ProgressDialog pDialog;
	private FloatLabeledEditText fletUsername, fletPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		callbackManager = CallbackManager.Factory.create();
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
																// ActionBar
		setContentView();
	}

	@Override
	protected void onStart() {
		super.onStart();
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			Intent intentMain = new Intent(LogInPageActivity.this,
					MainActivity.class);
			startActivity(intentMain);
		}
	}

	private void setContentView()
	{
		EditText loginText;
		EditText passText;

		setContentView(R.layout.activity_login_page_travel);

		login = (TextView) findViewById(R.id.login);
		register = (TextView) findViewById(R.id.register);

		login.setOnClickListener(this);
		register.setOnClickListener(this);

		fletUsername = (FloatLabeledEditText) findViewById(R.id.fletUserName);
		fletPassword = (FloatLabeledEditText) findViewById(R.id.fletPassword);

		ImageButton lgFacebook = (ImageButton) findViewById(R.id.login_button_facebook);
		lgFacebook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				ParseFacebookUtils.logInWithReadPermissionsInBackground(LogInPageActivity.this, null, new LogInCallback() {
					@Override
					public void done(ParseUser user, ParseException err) {
						if (user == null) {
							Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
						} else if (user.isNew()) {
							Intent intentMain = new Intent(LogInPageActivity.this,
									MainActivity.class);
							startActivity(intentMain);
						} else {
							Intent intentMain = new Intent(LogInPageActivity.this,
									MainActivity.class);
							startActivity(intentMain);
						}
					}
				});
			}
		});

	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			login(fletUsername.getTextString(), fletPassword.getTextString());
			pDialog = new ProgressDialog(LogInPageActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();
			break;
		case R.id.register:
			Intent intentRegister = new Intent(LogInPageActivity.this,
					RegisterPageActivity.class);
			startActivity(intentRegister);
			break;
		default:
			break;
		}
	}

	public void login(String username, String password){
		ParseUser.logInInBackground(username, password, new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (pDialog.isShowing())
					pDialog.dismiss();
				if (user != null) {
					Intent intentMain = new Intent(LogInPageActivity.this,
							MainActivity.class);
					startActivity(intentMain);
				} else {
					Toast.makeText(LogInPageActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
	}
}
