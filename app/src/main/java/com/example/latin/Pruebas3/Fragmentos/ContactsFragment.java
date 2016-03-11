package com.example.latin.Pruebas3.Fragmentos;

/**
 * Created by Administrador on 18/02/2016.
 */
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.latin.Pruebas3.R;

/** Fragmento que servira como adaptador para los arreglos
 * que gestionaran las listas de contactos
  */



public class ContactsFragment extends Fragment {

    private ListView contactsList;
    private String[] strListContacts;

    public ContactsFragment(){

    }

    //545454
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.contacts_fragment, container,false);
        View rootView = inflater.inflate(R.layout.contacts_fragment, container,  false);

        contactsList = (ListView) rootView.findViewById(R.id.ListContacts);

        strListContacts = getResources().getStringArray(R.array.contact_list);

        ArrayAdapter<String> objAdapter = new ArrayAdapter <String>(this.getActivity(), android.R.layout.simple_expandable_list_item_1, strListContacts);
        contactsList.setAdapter(objAdapter);
/*
        contactsList.setOnClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(ContactsFragment.this, "Message", Toast.LENGTH_SHORT).show();
                System.out.println("Message");


            }
        });*/

        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            private int d = 5000;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AppCompatActivity app = new AppCompatActivity();

                //Toast.makeText(ContactsFragment.this,"Message" + strListContacts[position],Toast.LENGTH_SHORT).show();
                System.out.println("Click en" + strListContacts[position]);

                //startActivity(new Intent(ContactsFragment.this, ScrollingActivity.class));
                //app.startActivity(new Intent(ContactsFragment.this, ScrollingActivity.class));
                Intent i;
                i = new Intent(getActivity(),ScrollingActivity.class);
                startActivity(i);


            }
        });


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
