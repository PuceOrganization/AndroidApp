package com.example.latin.Pruebas3.Fragmentos;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.latin.Pruebas3.MainActivity;
import com.example.latin.Pruebas3.R;
import com.example.latin.Pruebas3.Utils.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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
 * Created by Latin on 24/02/2016.
 */
public class GmapFragment extends Fragment implements OnMapReadyCallback {


    private View myFragmentView;


    JSONObject jsonBody;
    private static final String JSON_URL = "http://192.168.1.195:8080/Mapping-web/rest/list/login";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // return inflater.inflate(R.layout.fragment_gmaps, container, false);
        myFragmentView = inflater.inflate(R.layout.fragment_gmaps, container, false);
        return myFragmentView;

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {


        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);

        // Asigno un nivel de zoom
        CameraUpdate ZoomCam = CameraUpdateFactory.zoomTo(14);
        googleMap.animateCamera(ZoomCam);

        // Establezco un listener para ver cuando cambio de posicion

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {

            public void onMyLocationChange(Location pos) {
                // TODO Auto-generated method stub

                // Extraigo la Lat y Lon del Listener
                double lat = pos.getLatitude();
                double lon = pos.getLongitude();

                // Muevo la camara a mi posicion
                CameraUpdate cam = CameraUpdateFactory.newLatLng(new LatLng(
                        lat, lon));

                googleMap.moveCamera(cam);

                // Notifico con un mensaje al usuario de su Lat y Lon
                Toast.makeText(GmapFragment.this.getActivity(),
                        "Lat: " + lat + "\nLon: " + lon, Toast.LENGTH_SHORT)
                        .show();
            }

        });
            final FloatingActionButton fab = (FloatingActionButton) myFragmentView.findViewById(R.id.flbmapa);
            fab.setOnClickListener(new View.OnClickListener()

                                   {
                                       @Override
                                       public void onClick(final View v) {

              /*  if (ActivityCompat.checkSelfPermission(myFragmentView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(myFragmentView.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);*/



                /*switch (v.getId()) {
                    case R.id.flbmapa:

                        class GetJSON extends AsyncTask<String, String, String> {
                            ProgressDialog loading;

                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                //loading = ProgressDialog.show(GmapFragment.this, "Please Wait...", null, true, true);
                            }

                            @Override
                            protected String doInBackground(String... params) {
                                String uri = params[0];
                                HttpURLConnection urlConnection = null;

                                String requestBody;

                                try {
                                    URL url = new URL(uri);
                                    jsonBody = new JSONObject();
                                    jsonBody.put("latitud", 0.0);
                                    jsonBody.put("longitud", 0.0);

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
                                    String resultado = (String) object.get("latitud");
                                    System.out.println("este es del dato: " + resultado);

                                    if (resultado.equals(new String("true"))) {

                                        googleMap.addMarker(new MarkerOptions().position(new LatLng(-0.206263, -78.498280)).title("Hola"));
                                        googleMap.addMarker(new MarkerOptions().position(new LatLng(-0.204589, -78.487766)).title("Hola"));
                                        googleMap.addMarker(new MarkerOptions().position(new LatLng(-0.183872, -78.494984)).title("Hola"));

                                        Snackbar.make(v, "Marcadores Actualizados", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();

                                        if (ActivityCompat.checkSelfPermission(myFragmentView.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                            // TODO: Consider calling
                                            //    ActivityCompat#requestPermissions
                                            // here to request the missing permissions, and then overriding
                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                            //                                          int[] grantResults)
                                            // to handle the case where the user grants the permission. See the documentation
                                            // for ActivityCompat#requestPermissions for more details.
                                            return;
                                        }
                                        googleMap.setMyLocationEnabled(true);

                                    }
                                    else
                                    {
                                        Snackbar.make(v, "Error al Actualizar datos", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                      /*    GetJSON gj = new GetJSON();
                        String logusuario = etLogUsuario.getText().toString();
                        String logpassword = etLogPassword.getText().toString();

                        gj.execute(JSON_URL, logusuario, logpassword);
                }*/

                                       }
                                   }

            );


        }


    }
