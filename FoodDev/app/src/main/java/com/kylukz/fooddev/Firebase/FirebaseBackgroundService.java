package com.kylukz.fooddev.Firebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class FirebaseBackgroundService extends BroadcastReceiver {

    private static final String TAG = "FirebaseService";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "I'm in!!!");

        if (intent.getExtras() != null) {
            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
                Log.e("FirebaseDataReceiver", "Key: " + key + " Value: " + value);
                if (key.equalsIgnoreCase("gcm.notification.body") && value != null) {
                    Bundle bundle = new Bundle();
                    Intent backgroundIntent = new Intent(context, FirebaseMessagingService.class);
                    bundle.putString("push_message", value + "");
                    backgroundIntent.putExtras(bundle);
                    context.startService(backgroundIntent);
                }
            }
        }
    }
}