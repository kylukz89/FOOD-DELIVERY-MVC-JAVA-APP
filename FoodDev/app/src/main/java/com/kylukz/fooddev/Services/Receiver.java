package com.kylukz.fooddev.Services;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.content.ContextCompat;

import com.kylukz.fooddev.SplashActivity;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent startIntent = new Intent(SplashActivity.ctx, CustomService.class);
        startIntent.addFlags(Integer.parseInt(IntentService.CONNECTIVITY_SERVICE));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(SplashActivity.ctx, startIntent);
        } else {
            SplashActivity.ctx.startService(startIntent);
        }

    }

}