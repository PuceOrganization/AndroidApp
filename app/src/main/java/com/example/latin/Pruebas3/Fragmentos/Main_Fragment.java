package com.example.latin.Pruebas3.Fragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.latin.Pruebas3.R;

/**
 * Created by Latin on 22/02/2016.
 */
public class Main_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview =inflater.inflate(R.layout.fragment_main,container,false);
        return rootview;

    }
}
