package com.kylukz.fooddev.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import com.kylukz.fooddev.Toolbox.MenusAbaLateralEsquerda;


/*
    Classe que verifica se o app foi fechado de qualquer maneira
 */
public class AppService extends Service {

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        super.onTaskRemoved(rootIntent);
        //here you will get call when app close.

        MenusAbaLateralEsquerda.botao3Pontinhos(AppService.this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}