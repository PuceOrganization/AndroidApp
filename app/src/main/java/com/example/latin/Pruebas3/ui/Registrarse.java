package com.example.latin.Pruebas3.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.latin.Pruebas3.R;
import com.example.latin.Pruebas3.Utils.Utils;

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
public class Registrarse extends AppCompatActivity implements  View.OnClickListener {

    private EditText etNickName;
    private EditText etRegnombre;
    private EditText etRegApellido;
    private EditText etRegMail;
    private EditText etRegPassword;
    private EditText etRegPasswordConf;
    private EditText etRegCodReg;


    private Button butRegCuenta;
    private static final String JSON_URL = "http://192.168.1.195:8080/Mapping-web/rest/list/register";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrarse_activity);


        //Obtenemos una referencia a los controles de la interfaz
        etNickName = ((EditText)findViewById(R.id.etNickName));
        etRegnombre = (EditText)findViewById(R.id.etRegnombre);
        etRegApellido = (EditText)findViewById(R.id.etRegApellido);
        etRegMail = (EditText)findViewById(R.id.etRegMail);
        etRegPassword = (EditText)findViewById(R.id.etRegPassword);
        etRegPasswordConf = (EditText) findViewById(R.id.etRegPasswordConf);
        etRegCodReg = (EditText)findViewById(R.id.etRegCodReg);

        butRegCuenta = (Button)findViewById(R.id.butRegCuenta);
        butRegCuenta.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.butRegCuenta:

                class GetJSON extends AsyncTask<String, Void, String> {
                    ProgressDialog loading;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(Registrarse.this, "Please Wait...",null,true,true);
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

                            jsonBody.put("nickname", params[1]);
                            jsonBody.put("name", params[2]);
                            jsonBody.put("lastname", params[3]);
                            jsonBody.put("email", params[4]);
                            jsonBody.put("password", params[5]);
                            jsonBody.put("registerCode", params[6]);
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

                    }
                }
                GetJSON gj = new GetJSON();
                String nickname = etNickName.getText().toString();
                String name = etRegnombre.getText().toString();
                String lastname = etRegApellido.getText().toString();
                String email = etRegMail.getText().toString();
                String password = etRegPassword.getText().toString();
                String regcode = etRegCodReg.getText().toString();

                gj.execute(JSON_URL, nickname,name,lastname,email,password,regcode);
                break;


        }

    }
}
