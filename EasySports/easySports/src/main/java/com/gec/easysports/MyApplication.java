package com.gec.easysports;

import android.app.Application;

import com.gec.easysports.parser.ParseUtils;
import com.parse.ParseFacebookUtils;

/**
 * Created by ittus on 12/2/15.
 */
public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // register with parse
        ParseUtils.registerParse(this);
        ParseFacebookUtils.initialize(this);
    }


    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
}
