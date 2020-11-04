package com.kylukz.fooddev.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class CustomService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android
        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 5000, restartServicePI);
    }

    @Override
    public void onCreate() {
        super.onCreate();


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);

        Timer timer = null;

        long TEMPO = (5000); // chama o método a cada 10 segundos (10000 = 10 seg)

        if (timer == null) {
            timer = new Timer();
            TimerTask tarefa = new TimerTask() {
                public void run() {

                    try {
                      //  AlertasDAO aledao = new AlertasDAO();
                      // Ferramentas.geraAlerta(getApplicationContext(), VariaveisGlobais.ALERTA_TITULO, aledao.retornaAlertasMassivos().getAlertaMassivo());
                        System.err.println(">>>============================================================================================>");
                    } catch (Exception e) {
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }
                }
            };
            timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
            Log.e("", "onStartCommand");
        }
    }
}