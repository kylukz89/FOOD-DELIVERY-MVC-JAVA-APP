package com.kylukz.fooddev.Toolbox;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.content.ContextCompat;

import com.kylukz.fooddev.SplashActivity;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent a) {

        Intent startIntent = new Intent(SplashActivity.ctx, ServiceW.class);
        startIntent.addFlags(Integer.parseInt(IntentService.CONNECTIVITY_SERVICE));
System.out.println("aaaaaaaaaaaaaaaaaa =>>>>> ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            System.out.println("bbbbbbbbbb =>>>>> ");
            ContextCompat.startForegroundService(SplashActivity.ctx, startIntent);
        } else {
            SplashActivity.ctx.startService(startIntent);
        }

    }

}