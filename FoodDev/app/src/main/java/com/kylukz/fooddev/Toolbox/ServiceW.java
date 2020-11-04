package com.kylukz.fooddev.Toolbox;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class ServiceW extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

        @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }
}
