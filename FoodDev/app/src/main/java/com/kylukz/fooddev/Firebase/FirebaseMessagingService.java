/*
 * Copyright 2016-2017 Tom Misawa, riversun.org@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the
 * Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 *  INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 * IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package com.kylukz.fooddev.Firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.aide.aiDelivery.R;
import com.google.firebase.messaging.RemoteMessage;
import com.kylukz.fooddev.SplashActivity;

/**
 * Starta serviço quando o firebase receber o token
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        try {

            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId).setStyle(new NotificationCompat.BigTextStyle()
                    .setBigContentTitle(remoteMessage.getNotification().getTitle())
                    .bigText(remoteMessage.getNotification().getBody()))
                    .setSmallIcon(R.drawable.ic_entregador)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setDefaults(Notification.DEFAULT_SOUND)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }



            manager.notify((int) Math.random(), builder.build());

            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Log.e("FIREBASE", "Message Notification Body: " + Objects.requireNonNull(remoteMessage.getNotification()).getBody());
                sendNotification(remoteMessage);
            }*/
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(RemoteMessage remoteMessage) {
//        try {
//            RemoteMessage.Notification notification = remoteMessage.getNotification();
//            Ferramentas ferramenta = new Ferramentas();
//            ferramenta.geraNotificacaoPushPadrao(MyFirebaseMessagingService.this, notification.getTitle(), notification.getBody(), (int) Math.random());
//        } catch (Exception e) {
//            System.err.println(e);
//        }
    }

    @Override
    public void onNewToken(String registrationToken) {
        Logg.d("Firebase #onNewToken registrationToken=" + registrationToken);
        startService(new Intent(this, FcmTokenRegistrationService.class));
    }




}
