package com.gec.easysports.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gec.easysports.DetailActivity;
import com.gec.easysports.model.DashboardModel;
import com.gec.easysports.util.NotificationUtils;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ittus on 12/2/15.
 */
public class CustomReceiver extends ParsePushBroadcastReceiver {
    private final String TAG = CustomReceiver.class.getSimpleName();

    private NotificationUtils notificationUtils;

    private Intent parseIntent;

    public CustomReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.e(TAG, "Push received: " + json);

            parseIntent = intent;

            parsePushJson(context, json);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(Context context, JSONObject json) {
        try {
            boolean isBackground = json.getBoolean("is_background");
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");

            JSONObject rawData = json.getJSONObject("data_raw");
            String time = rawData.getString(DashboardModel.TAG_TIME);
            String date = rawData.getString(DashboardModel.TAG_DATE);
            String description = rawData.getString(DashboardModel.TAG_DESCRIPTION);
            String imageUrl = rawData.getString(DashboardModel.TAG_IMAGE_URL);
            String name = rawData.getString(DashboardModel.TAG_NAME);
            String category = rawData.getString(DashboardModel.TAG_CATEGORY);
            String userEmail = rawData.getString(DashboardModel.TAG_USER_EMAIL);
            String address = rawData.getString(DashboardModel.TAG_ADDRESS);
            String numberPlayer = rawData.getString(DashboardModel.TAG_NUM_OF_PLAYER);
            String location = rawData.getString(DashboardModel.TAG_LOCATION);

            Log.e(TAG, json.toString());
            if (!isBackground) {
                Intent resultIntent = new Intent(context, DetailActivity.class);
                resultIntent.putExtra(DashboardModel.TAG_TIME, time);
                resultIntent.putExtra(DashboardModel.TAG_DATE, date);
                resultIntent.putExtra(DashboardModel.TAG_DESCRIPTION, description);
                resultIntent.putExtra(DashboardModel.TAG_IMAGE_URL, imageUrl);
                resultIntent.putExtra(DashboardModel.TAG_NAME, name);
                resultIntent.putExtra(DashboardModel.TAG_CATEGORY,category);
                resultIntent.putExtra(DashboardModel.TAG_USER_EMAIL, userEmail);
                resultIntent.putExtra(DashboardModel.TAG_LOCATION, location);
                resultIntent.putExtra(DashboardModel.TAG_ADDRESS, address);
                resultIntent.putExtra(DashboardModel.TAG_NUM_OF_PLAYER, numberPlayer);

                showNotificationMessage(context, resultIntent, message, title);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }


    /**
     * Shows the notification message in the notification bar
     * If the app is in background, launches the app
     *
     * @param context
     * @param title
     * @param message
     * @param intent
     */
    private void showNotificationMessage(Context context, Intent intent, String message, String title
                                         ) {

        notificationUtils = new NotificationUtils(context);

        intent.putExtras(parseIntent.getExtras());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationUtils.showNotificationMessage(intent,message,title);
    }
}
