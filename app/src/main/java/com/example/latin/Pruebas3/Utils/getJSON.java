package com.example.latin.Pruebas3.Utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Latin on 08/03/2016.
 */
public class getJSON extends AppCompatActivity {

    private String NickName;
    private String Nombre;
    private String Apellido;
    private String Mail;
    private String Password;
    private String CodReg;

    private static final String JSON_URL = "http://192.168.1.195:8080/Mapping-web/rest/list/test";
    private void getJSON(String url , String request) {

        //Recuperamos la información pasada en el intent
        Bundle bundle = this.getIntent().getExtras();

        // Seteamos La informacion pasada en variables
        NickName = bundle.getString("NickName");
        Nombre = bundle.getString("RegNombre");
        Apellido = bundle.getString("RegApellido");
        Mail = bundle.getString("RegMail");
        Password = bundle.getString("RegPassword");
        CodReg = bundle.getString("RegCodReg");

        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getJSON.this, "Please Wait...",null,true,true);
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                HttpURLConnection urlConnection = null;

                JSONObject jsonBody;
                String requestBody;

                try {
                    URL url = new URL(uri);
                    jsonBody = new JSONObject();
                    jsonBody.put("Nick", NickName);
                    jsonBody.put("Nombre", Nombre);
                    jsonBody.put("Apellido", Apellido);
                    jsonBody.put("Mail", Mail);
                    jsonBody.put("Password", Password);
                    jsonBody.put("CodigoRegistro", CodReg);
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


                    /*primera forma para mandar unicamente un string*/
//                    try {
//                        URL url = new URL(uri);
//                        String requestBody = Utils.buildPostParameters("'Esto es una prueba mamaverga'"); // must have '' for FromBody parameter
//
//                        urlConnection = (HttpURLConnection) Utils.makeRequest("POST", url.toString(), null, "application/json", requestBody);
//                        InputStream inputStream;
//                        // get stream
//                        if (urlConnection.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST) {
//                            inputStream = urlConnection.getInputStream();
//                        } else {
//                            inputStream = urlConnection.getErrorStream();
//                        }
//                        // parse stream
//                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                        String temp, response = "";
//                        while ((temp = bufferedReader.readLine()) != null) {
//                            response += temp;
//                        }
//                        return response;
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        return e.toString();
//                    } finally {
//                        if (urlConnection != null) {
//                            urlConnection.disconnect();
//                        }
//                    }



            }

            @Override
            protected void onPostExecute(String s) {
                Log.d("asd", s);
                super.onPostExecute(s);
                loading.dismiss();

            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(JSON_URL);

    }
}
