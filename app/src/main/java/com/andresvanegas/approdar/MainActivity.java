package com.andresvanegas.approdar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.andresvanegas.approdar.datos.BDHELPER;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String[] opciones = new String[] { "Cronograma", "Perfil","Convenios","cerrar sesion"};
    ListView list;
    Button ir_crono;
    BDHELPER usuarios;
    SQLiteDatabase database;
    ContentValues dataBD;
    String[] nombres = {"caballo","perro","carro","moto","loro"};
    String[] fechas = {"12","23","34","35","56"};
    String[] lats = {"1","2","3","4","5"};
    String[] lots = {"5","4","3","2","1"};
    String[] descri = {"burros_porahi","poraca_no", "aqui_tampoco","aqui_si", "chao"};
    String datousu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ir_crono = (Button)findViewById(R.id.ir_crono);

        Intent intent4 = getIntent();
        Bundle extras = intent4.getExtras();
        if(extras != null){
            datousu = (String)extras.get("usuario");
        }

        //---------------Navigation drawer------------------------------------------------
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        list = (ListView)findViewById(R.id.left_drawer);
        list.setAdapter(new ArrayAdapter<String>(getSupportActionBar().getThemedContext(),
                android.R.layout.simple_list_item_1, opciones));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent carta = new Intent(MainActivity.this, CRONOGRAMA.class);
                        carta.putExtra("usuario",datousu);
                        startActivity(carta);
                        finish();
                        break;
                    case 1:
                        Intent intent = new Intent(MainActivity.this, PERFIL.class);
                        intent.putExtra("usuario",datousu);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        Intent intent1 = new Intent(MainActivity.this, CONVENIOS.class);
                        intent1.putExtra("usuario",datousu);
                        startActivity(intent1);
                        finish();
                        break;
                    case 3:
                        Intent cerrar = new Intent(MainActivity.this, LOGIN.class);
                        startActivity(cerrar);
                        finish();
                        break;
                }
                list.setItemChecked(1, true);
                drawerLayout.closeDrawer(list);
            }
        });
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abierto, R.string.cerrado);
        drawerLayout.setDrawerListener(drawerToggle);

        //------------------------------------------------------------

        usuarios = new BDHELPER(this, "DATOS", null, 1);
        database = usuarios.getWritableDatabase();

            dataBD = new ContentValues();
            database.beginTransaction();
        try {
            for (int i = 0;i < nombres.length;i++) {

                dataBD.put("nombre", nombres[i]);
                dataBD.put("fecha", fechas[i]);
                dataBD.put("latitud", lats[i]);
                dataBD.put("longitud", lots[i]);
                dataBD.put("descripcion", descri[i]);

                database.execSQL("INSERT INTO cronograma VALUES(null, '" + nombres[i] + "','" + fechas[i] + "'," +
                        "'" + lats[i] + "','" + lots[i] + "','" + descri[i] + "')");

            }
            database.setTransactionSuccessful();
        }finally {
            database.endTransaction();
        }
            }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(Gravity.LEFT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    }
