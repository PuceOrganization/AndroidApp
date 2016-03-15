package com.example.latin.Pruebas3.ui;


import android.content.Context;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.latin.Pruebas3.R;
import com.example.latin.Pruebas3.service.RegisterService;

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
                String nickname = etNickName.getText().toString();
                String name = etRegnombre.getText().toString();
                String lastname = etRegApellido.getText().toString();
                String email = etRegMail.getText().toString();
                String password = etRegPassword.getText().toString();
                String regcode = etRegCodReg.getText().toString();
                RegisterService registerService = new RegisterService(this,letMeKnow);
                registerService.execute(JSON_URL, nickname,name,lastname,email,password,regcode);
                break;


        }
    }

    private RegisterService.InformComplete letMeKnow=new RegisterService.InformComplete()
    {
        public void PostData(String a)
        {
            if(a.equals(new String("true"))){

            }
            else{
                Context context = getApplicationContext();
                Toast toast = Toast.makeText(context, "ingreso Fallido",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    };

}


