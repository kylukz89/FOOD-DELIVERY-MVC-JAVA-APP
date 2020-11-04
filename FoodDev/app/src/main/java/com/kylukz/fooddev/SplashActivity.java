package com.kylukz.fooddev;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.aide.aiDelivery.R;
import com.kylukz.fooddev.Animation.Animatoo;
import com.kylukz.fooddev.Toolbox.ServiceW;
import com.kylukz.fooddev.View.MainActivity;

public class SplashActivity extends AppCompatActivity {
    public static Context ctx;
//    public static boolean hasHardwareAcceleration(Activity activity) {
//        // Has HW acceleration been enabled manually in the current window?
//        Window window = activity.getWindow();
//        if (window != null) {
//            if ((window.getAttributes().flags
//                    & WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED) != 0) {
//                return true;
//            }
//        }
//
//        // Has HW acceleration been enabled in the manifest?
//        try {
//            ActivityInfo info = activity.getPackageManager().getActivityInfo(
//                    activity.getComponentName(), 0);
//            if ((info.flags & ActivityInfo.FLAG_HARDWARE_ACCELERATED) != 0) {
//                return true;
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.e("Chrome", "getActivityInfo(self) should not fail");
//        }
//
//        return false;
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // remove barra de título
       View view =  getWindow().getDecorView();
        setContentView(R.layout.activity_splash);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        startService(new Intent(this, ServiceW.class));
        Animatoo.animateSwipeRight(this);
        //////////////////////// 2º PLANO //////////////////////
        String manufacturer = "xiaomi";
        if (manufacturer.equalsIgnoreCase(android.os.Build.MANUFACTURER)) {
            //this will open auto start screen where user can enable permission for your app
            Intent intent1 = new Intent();
            intent1.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            //startActivity(intent1);
        }
        ////////////////////////////////////////////////////////




        //AutoStartPermissionHelper.getInstance().getAutoStartPermission(Splash.this);

        Thread geradorThread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        geradorThread.start();
    }
}
