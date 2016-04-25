package com.example.latin.Pruebas3.service;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by diego on 1/2/2016.
 */
public class Upstream {

    public Upstream(){

    }

    public void doGcmSendUpstreamMessage(Activity activity, String senderId, String msgId,String type, String message){
        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(activity);
        final String senderID = senderId;
        final String msgID = msgId;
        //final String TTL = ttl;
        final String msgIdentificator = msgId;
        final Bundle data = new Bundle();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        data.putString("type",type);
        data.putString("sentDate", format.format(Calendar.getInstance().getTime()));
        data.putString("messageText",message);


                new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    // gcm.send(senderID + "@gcm.googleapis.com", msgID, data);
                    gcm.send(senderID + "@gcm.googleapis.com", msgID,Long.parseLong("1000"), data);


                    return null;
                }  catch (IOException ex) {
                    System.out.println("Error sending upstream message:" + ex.getMessage());
                    return "Error sending upstream message:" + ex.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {

                if (result != null) {
                    //Poner el activity en ves del null
                    System.out.println("send message failed:" +result);
                    Toast.makeText(null,"send message failed: " + result,
                            Toast.LENGTH_LONG).show();
                }
            }
                }.execute(null,null,null);
    }

}
