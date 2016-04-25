package com.example.latin.Pruebas3.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.latin.Pruebas3.R;

import java.util.Timer;
import java.util.TimerTask;

public class Class_Splash extends AppCompatActivity {

    private long splashRetraso=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class__splash);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TimerTask task= new TimerTask() {
            @Override
            public void run() {

                Intent NuevoLayout;
                NuevoLayout = new Intent(Class_Splash.this,Login.class);
                startActivity(NuevoLayout);

                Class_Splash.this.finish();

            }
        };

        Timer timer = new Timer();
        timer.schedule(task,splashRetraso);


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
}
