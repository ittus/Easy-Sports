package com.gec.easysports;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.gec.easysports.view.FloatLabeledEditText;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterPageActivity extends Activity {
	private ProgressDialog pDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		final FloatLabeledEditText fletUserName = (FloatLabeledEditText) findViewById(R.id.fletUsername);
		final FloatLabeledEditText fletPassword = (FloatLabeledEditText) findViewById(R.id.fletPassword);
		final FloatLabeledEditText fletEmail = (FloatLabeledEditText) findViewById(R.id.fletEmail);

		final Button register = (Button) findViewById(R.id.btnRegister);

		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				pDialog = new ProgressDialog(RegisterPageActivity.this);
				pDialog.setMessage("Please wait...");
				pDialog.setCancelable(false);
				pDialog.show();
				register(fletUserName.getTextString(), fletPassword.getTextString(), fletEmail. getTextString());
			}
		});
	}

	private void register(String username,String password, final String email){
		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (pDialog.isShowing())
					pDialog.dismiss();
				if (e == null) {
					Toast.makeText(RegisterPageActivity.this, "Register successful with email " + email + "!", Toast.LENGTH_SHORT).show();
					finish();
				} else {
					Toast.makeText(RegisterPageActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

}
