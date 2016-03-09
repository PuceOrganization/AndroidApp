package com.example.latin.Pruebas3.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.latin.Pruebas3.Fragmentos.Main_Fragment;
import com.example.latin.Pruebas3.MainActivity;
import com.example.latin.Pruebas3.R;
import com.example.latin.Pruebas3.Utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Latin on 08/03/2016.
 */
public class Login extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private EditText etLogUsuario;
    private EditText etLogPassword;
    private Button btLogIngresar;
    private Button buttonRegistro;
    public TextView tvolvidar;

    JSONObject jsonBody;

    //Esto es una prueba

    private static final String JSON_URL = "http://192.168.1.195:8080/Mapping-web/rest/list/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        etLogUsuario = (EditText) findViewById(R.id.etLogUsuario);
        etLogPassword = (EditText) findViewById(R.id.etLogPassword);
        btLogIngresar = (Button) findViewById(R.id.buttonLogIngresar);
        btLogIngresar.setOnClickListener(this);
        buttonRegistro = (Button) findViewById(R.id.buttonLogRegistrarse);
        tvolvidar = (TextView) findViewById(R.id.tvOlvidar);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registrarse.class);
                startActivity(intent);
            }
        });


        tvolvidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonLogIngresar:

                class GetJSON extends AsyncTask<String, String, String> {
                    ProgressDialog loading;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        loading = ProgressDialog.show(Login.this, "Please Wait...", null, true, true);
                    }

                    @Override
                    protected String doInBackground(String... params) {
                        String uri = params[0];
                        HttpURLConnection urlConnection = null;
//                        JSONObject jsonBody;
                        String requestBody;

                        try {
                            URL url = new URL(uri);
                            jsonBody = new JSONObject();
                            jsonBody.put("nickname", params[1]);
                            jsonBody.put("password", params[2]);

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
                        JSONObject object;


                        try {
                            object = (JSONObject) new JSONTokener(s).nextValue();
                            String resultado = (String)object.get("login");
                            System.out.println("este es del dato: " + resultado);

                            if(resultado.equals(new String ("true"))) {

                                btLogIngresar.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            }
                            else
                            {
                                Context context = getApplicationContext();
                                Toast toast = Toast.makeText(context, "ingreso Fallido",Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                GetJSON gj = new GetJSON();
                String logusuario = etLogUsuario.getText().toString();
                String logpassword = etLogPassword.getText().toString();

                gj.execute(JSON_URL, logusuario, logpassword);

        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
