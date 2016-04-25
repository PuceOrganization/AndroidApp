/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.example.latin.Pruebas3.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.EditText;

import com.example.latin.Pruebas3.Fragmentos.Fragmento_2;
import com.example.latin.Pruebas3.MainActivity;
import com.example.latin.Pruebas3.R;
import com.example.latin.Pruebas3.Utils.GcmAction;
import com.google.android.gms.gcm.GcmListenerService;

import java.util.ArrayList;


/**
 * Service used for receiving GCM messages. When a message is received this service will log it.
 */
public class GcmService extends GcmListenerService {

    private LoggingService.Logger logger;



    public GcmService() {
        logger = new LoggingService.Logger(this);
    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        Boolean flag = new Boolean(data.getString("Response"));
        System.out.println("flag" + flag);

        if(flag) {
            sendNotification(data.getString("Message"),data.getString("User"),data.getString("Response"));
            System.out.println("data: "+data.toString());
        }else{
            System.out.println("Se recibio el mensaje");
        }

    }

    @Override
    public void onDeletedMessages() {
        sendNotification("Deleted messages on server",null,null);
    }

    @Override
    public void onMessageSent(String msgId) {
       // sendNotification("Upstream message sent. Id=" + msgId);
    }

    @Override
    public void onSendError(String msgId, String error) {
        sendNotification("Upstream message send error. Id=" + msgId + ", error" + error,null,null);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg,String to,String response) {
        System.out.println("entro al service");
        Intent intent = new Intent("custom-event-name");
        intent.putExtra("mensaje", msg);
        intent.putExtra("user", to);
        intent.putExtra("response", response);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);



//        Intent intent = new Intent(this,MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//
//        intent.putExtra("mensaje", msg);
//        intent.putExtra("user", to);
//        intent.putExtra("response", response);

       // getBaseContext().sendBroadcast(intent);
//        startActivity(intent);

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);


//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(
//                RingtoneManager.TYPE_NOTIFICATION);
//        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.ic_stat_ic_notification)
//                .setContentTitle("Mensaje")
//                .setContentText(msg)
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }


}


