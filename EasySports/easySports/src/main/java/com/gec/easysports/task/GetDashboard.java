package com.gec.easysports.task;

import android.os.AsyncTask;
import android.util.Log;

import com.gec.easysports.constants.Constants;
import com.gec.easysports.services.ServiceHandler;

/**
 * Created by ittus on 12/1/15.
 */
public class GetDashboard extends AsyncTask<Void, Void, Void> {
    private static String GET_DASHBOARD_TASK = 'GET_DASHBOARD_TASK';

    @Override
    protected Void doInBackground(Void... voids) {
        ServiceHandler sh = new ServiceHandler();
        String jsonStr = sh.makeServiceCall(Constants.URL_GET_DATA, ServiceHandler.GET);

        Log.d("GET_DASHBOARD_TASK", jsonStr);
        return null;
    }
}
