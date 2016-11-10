package com.andresvanegas.approdar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.andresvanegas.approdar.datos.BDHELPER;

public class REGISTRO extends AppCompatActivity implements View.OnClickListener{

    EditText eusuario, edocumento, econtacto, econtraseña, ercontraseña;
    Button baceptar, bcancelar;
    String usuario, contraseña, rcontraseña;
    String  contacto, documento;
    BDHELPER usuarios;
    SQLiteDatabase database;
    ContentValues dataBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        usuarios = new BDHELPER(this, "DATOS", null, 1);
        database = usuarios.getWritableDatabase();

        eusuario = (EditText) findViewById(R.id.eusuarior);
        edocumento = (EditText) findViewById(R.id.edocumentor);
        econtacto = (EditText) findViewById(R.id.ecele);
        econtraseña = (EditText) findViewById(R.id.econtar);
        ercontraseña = (EditText) findViewById(R.id.erecontrar);
        baceptar = (Button) findViewById(R.id.baceptar);
        bcancelar = (Button) findViewById(R.id.bcancelar);

        baceptar.setOnClickListener(this);
        bcancelar.setOnClickListener(this);
    }

       @Override
    public void onClick(View view) {
           int id = view.getId();

           usuario = eusuario.getText().toString();
           documento = edocumento.getText().toString();
           contacto = econtacto.getText().toString();
           contraseña = econtraseña.getText().toString();
           rcontraseña = ercontraseña.getText().toString();

        switch (id){
            case R.id.baceptar:

                dataBD = new ContentValues();
                dataBD.put("nombre",usuario);
                dataBD.put("documento",documento);
                dataBD.put("contacto",contacto);
                dataBD.put("contrasena",contraseña);
                //database.insert("usuarios",null,dataBD);
                database.execSQL("INSERT INTO usuarios VALUES(null, '"+usuario+"','"+documento+"'," +
                        "'"+contacto+"','"+contraseña+"')");
                database.close();
                Intent intent1 = new Intent(REGISTRO.this, LOGIN.class);
                startActivity(intent1);
                break;
            case R.id.bcancelar:
                Intent intent = new Intent(REGISTRO.this, LOGIN.class);
                startActivity(intent);
                break;
        }
    }
}
