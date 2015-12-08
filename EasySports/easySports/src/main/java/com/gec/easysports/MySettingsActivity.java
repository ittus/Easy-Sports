package com.gec.easysports;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by ittus on 12/5/15.
 */
public class MySettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
