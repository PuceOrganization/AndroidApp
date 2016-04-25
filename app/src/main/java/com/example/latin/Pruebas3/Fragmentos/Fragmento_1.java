package com.example.latin.Pruebas3.Fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.latin.Pruebas3.R;

/**
 * Created by Latin on 23/02/2016.
 */
public class Fragmento_1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview =inflater.inflate(R.layout.fragmento_1,container,false);
        return rootview;

    }
}
