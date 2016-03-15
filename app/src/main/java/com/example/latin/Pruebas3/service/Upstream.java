package com.example.latin.Pruebas3.service;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by diego on 1/2/2016.
 */
public class Upstream {

    public Upstream(){

    }

    public void doGcmSendUpstreamMessage(Activity activity, String senderId, String msgId,String key, String value){
        final GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(activity);
        final String senderID = senderId;
        final String msgID = msgId;
        //final String TTL = ttl;
        final String msgIdentificator = msgId;
        final Bundle data = new Bundle();
        data.putString(key,value);


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
