package com.gec.easysports.parser;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.gec.easysports.R;
import com.gec.easysports.constants.Constants;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by ittus on 12/2/15.
 */
public class ParseUtils {
    private static String TAG = ParseUtils.class.getSimpleName();

    public static void verifyParseConfiguration(Context context) {
        if (TextUtils.isEmpty(context.getResources().getString(R.string.parse_api_key)) || TextUtils.isEmpty(context.getResources().getString(R.string.parse_client_key))) {
            Toast.makeText(context, "Please configure your Parse Application ID and Client Key in AppConfig.java", Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
        }
    }

    public static void registerParse(Context context) {
        // initializing parse library
        Parse.initialize(context, context.getResources().getString(R.string.parse_api_key), context.getResources().getString(R.string.parse_client_key));
        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground(Constants.PARSE_CHANNEL, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.e(TAG, "Successfully subscribed to Parse!");
            }
        });
    }

    public static void subscribeWithEmail(String email) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();

        installation.put("email", email);

        installation.saveInBackground();
    }
}
