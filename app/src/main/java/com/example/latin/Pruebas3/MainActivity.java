package com.example.latin.Pruebas3;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.latin.Pruebas3.Fragmentos.Fragmento_2;
import com.example.latin.Pruebas3.Fragmentos.Fragmento_1;
import com.example.latin.Pruebas3.Fragmentos.GmapFragment;
import com.example.latin.Pruebas3.Fragmentos.Main_Fragment;
//import com.example.latin.Pruebas3.SQLite.DatabaseHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    //Creando instancia para la BDD
    //DatabaseHelper myDb;
    public Fragmento_2 fr = new Fragmento_2();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fr = new Fragmento_2();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, new Main_Fragment()).commit();



//        Intent gcmIntent = getIntent();
//        String message = gcmIntent.getStringExtra("mensaje");
//        String user = gcmIntent.getStringExtra("user");
//        String response = gcmIntent.getStringExtra("response");
//        System.out.println("mensaje prueba" + message);
//
//        Bundle bundle = new Bundle();
//        bundle.putString("mensaje",message);
//        bundle.putString("user",user);
//        bundle.putString("reponse",response);
//
//        fr.setArguments(bundle);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fm = getFragmentManager();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            fm.beginTransaction().replace(R.id.content_frame, new Fragmento_1()).commit();
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            fm.beginTransaction().replace(R.id.content_frame, new GmapFragment()).commit();

        } else if (id == R.id.nav_slideshow) {

            fm.beginTransaction().replace(R.id.content_frame, new Main_Fragment()).commit();

        } else if (id == R.id.nav_manage) {
            fm.beginTransaction().replace(R.id.content_frame, fr).commit();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    protected void onPause() {
//        // Unregister since the activity is paused.
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(
//                mMessageReceiver);
//        super.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        // Register to receive messages.
//        // We are registering an observer (mMessageReceiver) to receive Intents
//        // with actions named "custom-event-name".
//        LocalBroadcastManager.getInstance(this).registerReceiver(
//                mMessageReceiver, new IntentFilter("custom-event-name"));
//        super.onResume();
//    }

//    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // TODO Auto-generated method stub
//            // Get extra data included in the Intent
//            System.out.println("entro al broadcast");
//            String message = intent.getStringExtra("message");
//            System.out.println("message: "+message);
//        }
//    };


}
