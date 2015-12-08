package com.gec.easysports.preferences;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

/**
 * Created by ittus on 12/5/15.
 */
public class PlacePicker extends DialogPreference {
    public PlacePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public PlacePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlacePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PlacePicker(Context context) {
        super(context);
    }
}
