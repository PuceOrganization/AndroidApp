package com.example.latin.Pruebas3.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Latin on 11/03/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    //Variables para  conectarse a la base de datos
    public static final String DATABASE_NAME = "Agenda.db";
    public static final String TABLE_NAME = "contactos_table";
    public static final String Colum_1 = "Id";
    public static final String Colum_2 = "Nombre";
    public static final String Colum_3 = "Apellido";
    public static final String Colum_4 = "Numero";

//  CONSTRUCTOR BDD CON TODOS LOS PARAMETROS
//    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    public DatabaseHelper(Context context) {
        super(context,DATABASE_NAME, null , 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table"+TABLE_NAME+"(ID INTERGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, SURNAME TEXT,MAKRS INTERGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("Drop Table if Exists"+TABLE_NAME);
        onCreate(db);
    }
}
