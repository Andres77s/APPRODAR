package com.andresvanegas.approdar.datos;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vanegas on 01/11/2016.
 */
public class BDHELPER extends SQLiteOpenHelper {

    ContentValues dataB;

    public BDHELPER(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private String DATA_BASE_NAME = "DATOSAPP";
    private int DATA_VERSION = 1;

    String sqlUsuarios = "CREATE TABLE usuarios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "documento INTEGER,"+
            "contacto INTEGER,"+
            "contrasena TEXT)";

    String sqlcronograma = "CREATE TABLE cronograma (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "fecha INTEGER," +
            "nombre TEXT,"+
            "latitud INTEGER,"+
            "longitud INTEGER,"+
            "descripcion TEXT)";

    String sqlconvenios = "CREATE TABLE convenios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre TEXT," +
            "latitud INTEGER,"+
            "longitud INTEGER,"+
            "servicio TEXT)";

    String sqlextras = "CREATE TABLE extras (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "encargado TEXT," +
            "conencar INTEGER,"+
            "latitud INTEGER,"+
            "longitud INTEGER,"+
            "descripcion TEXT)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlUsuarios);
        db.execSQL(sqlcronograma);
        db.execSQL(sqlconvenios);
        db.execSQL(sqlextras);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL(sqlUsuarios);
        db.execSQL("DROP TABLE IF EXISTS cronograma");
        db.execSQL(sqlcronograma);
        db.execSQL("DROP TABLE IF EXISTS convenios");
        db.execSQL(sqlconvenios);
        db.execSQL("DROP TABLE IF EXISTS extras");
        db.execSQL(sqlextras);
    }
}
