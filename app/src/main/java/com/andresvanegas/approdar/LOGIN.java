package com.andresvanegas.approdar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.andresvanegas.approdar.datos.BDHELPER;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LOGIN extends AppCompatActivity implements View.OnClickListener{
    Button registrar,ingresar;
    TextInputEditText usuario,contrasena;
    BDHELPER usuarios;
    SQLiteDatabase database;
    private Cursor fila;
    private LoginButton loginButton;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);


        usuarios = new BDHELPER(this, "DATOS", null, 1);
        database = usuarios.getWritableDatabase();

        registrar = (Button) findViewById(R.id.bregistrar);
        ingresar = (Button) findViewById(R.id.bingresar);
        usuario = (TextInputEditText) findViewById(R.id.campo_nombre);
        contrasena = (TextInputEditText) findViewById(R.id.campo_contraseña);

        registrar.setOnClickListener(this);
        ingresar.setOnClickListener(this);

        if(AccessToken.getCurrentAccessToken() == null){
            goMainActivity();
        }
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goMainActivity();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Login cancelado",Toast.LENGTH_SHORT);

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Login error",Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }
    private void goMainActivity(){
        Intent intentfb = new Intent(getApplicationContext(),MainActivity.class);
        intentfb.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentfb);
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.bregistrar:

                Intent intent1 = new Intent(LOGIN.this, REGISTRO.class);
                startActivity(intent1);

            break;
            case R.id.bingresar:
                String usuario1= usuario.getText().toString();
                String contra1= contrasena.getText().toString();

                fila = database.rawQuery("select nombre,contrasena from usuarios where nombre='"+usuario1+"' and contrasena='"+contra1+"'",null);

                if(usuario.getText().toString().isEmpty() || contrasena.getText().toString().isEmpty()) {
                    Toast.makeText(this ,"Faltan datos",Toast.LENGTH_SHORT).show();
                        }
                else if(fila.moveToFirst() == true){
                    String usu = fila.getString(0);
                    String pass = fila.getString(1);
                    if(usuario1.equals(usu) && contra1.equals(pass)){
                        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                        intent2.putExtra("usuario",usuario1);
                        startActivity(intent2);
                    }
                }
                else {
                    Toast.makeText(this ,"Datos incorrectos",Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
