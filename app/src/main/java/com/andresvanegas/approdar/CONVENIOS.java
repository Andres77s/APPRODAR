package com.andresvanegas.approdar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.andresvanegas.approdar.datos.BDHELPER;

public class CONVENIOS extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String[] opciones = new String[] { "Main", "Perfil","Cronograma","cerrar sesion"};
    ListView list;
    String datousu;
    private int position;
    private String selection;
    protected ArrayAdapter<CharSequence> adapter;
    BDHELPER usuarios;
    SQLiteDatabase database;
    ContentValues dataBD;
    String[] nombres = {"Convenio 1","Convenio 2","Convenio 3","Convenio 4","Convenio 5"};
    String[] lats = {"1","2","3","4","5"};
    String[] lots = {"5","4","3","2","1"};
    String[] servi = {"taller","lavado", "almacen","taller", "taller-shop"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenios);

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
                        Intent carta = new Intent(CONVENIOS.this, MainActivity.class);
                        carta.putExtra("usuario",datousu);
                        startActivity(carta);
                        finish();
                        break;
                    case 1:
                        Intent intent = new Intent(CONVENIOS.this, PERFIL.class);
                        intent.putExtra("usuario",datousu);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        Intent intent1 = new Intent(CONVENIOS.this, CRONOGRAMA.class);
                        intent1.putExtra("usuario",datousu);
                        startActivity(intent1);
                        finish();
                        break;
                    case 3:
                        Intent cerrar = new Intent(CONVENIOS.this, LOGIN.class);
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
        //----------------------spinner---------------------------------------
        Spinner spinner = (Spinner) findViewById(R.id.conveSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.convenioR,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //-------------------------------------------------------------------
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MapFragment fragment = new MapFragment();
        ft.add(android.R.id.content, fragment).commit();
        //-------------------------------------------------------------------

        usuarios = new BDHELPER(this, "DATOS", null, 1);
        database = usuarios.getWritableDatabase();

        dataBD = new ContentValues();
        database.beginTransaction();
        try {
            for (int i = 0;i < nombres.length;i++) {

                dataBD.put("nombre", nombres[i]);
                dataBD.put("latitud", lats[i]);
                dataBD.put("longitud", lots[i]);
                dataBD.put("descripcion", servi[i]);

                database.execSQL("INSERT INTO convenios VALUES(null, '" + nombres[i] + "'," +
                        "'" + lats[i] + "','" + lots[i] + "','" + servi[i] + "')");

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        this.position = position;
        selection = parent.getItemAtPosition(position).toString();
        Toast.makeText(this,"Selección actual: "+selection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
