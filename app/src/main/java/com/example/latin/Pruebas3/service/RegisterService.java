package com.example.latin.Pruebas3.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.latin.Pruebas3.Utils.Utils;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by diego on 28/1/2016.
 */
public class RegisterService extends AsyncTask<String, Void, String> {

    private Context context;

    private InformComplete myCallback;

    ProgressDialog loading;

    public RegisterService(Context context, InformComplete callback){
        this.context = context;
        this.myCallback = callback;
    }

    public interface InformComplete
    {
        public void PostData(String a);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(context, "Please Wait...",null,true,true);
    }
    @Override
    protected String doInBackground(String... params) {
        String uri = params[0];
        HttpURLConnection urlConnection = null;
        JSONObject jsonBody;
        String requestBody;

        try {
            URL url = new URL(uri);
            Bundle extras = new Bundle();
            String token = InstanceID.getInstance(context.getApplicationContext())
                    .getToken("59156844246", "GCM", extras);
            Log.d("token: ", token);
            jsonBody = new JSONObject();
            jsonBody.put("nickname", params[1]);
            jsonBody.put("name", params[2]);
            jsonBody.put("lastname", params[3]);
            jsonBody.put("email", params[4]);
            jsonBody.put("password", params[5]);
            jsonBody.put("registerCode", params[6]);
            jsonBody.put("token", token);
            requestBody = Utils.buildPostParameters(jsonBody);
            urlConnection = (HttpURLConnection) Utils.makeRequest("POST", url.toString(), null, "application/json", requestBody);
            InputStream inputStream;
            // get stream
            if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
            // parse stream
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String temp, response = "";
            while ((temp = bufferedReader.readLine()) != null) {
                response += temp;
            }
            return response;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

    }
    @Override
    protected void onPostExecute(String s) {
        Log.d("asd", s);
        super.onPostExecute(s);

        loading.dismiss();

        try {
            JSONObject jsonResponse = new JSONObject();
            myCallback.PostData((String)jsonResponse.get("register"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
