package com.andresvanegas.approdar;

import android.content.Intent;
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

public class CRONOGRAMA extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String[] opciones = new String[] { "Main", "Perfil","Convenios","cerrar sesion"};
    ListView list;
    protected ArrayAdapter<CharSequence> adapter;
    private int position;
    private String selection;
    String datousu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronogram);

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
                        Intent carta = new Intent(CRONOGRAMA.this, MainActivity.class);
                        carta.putExtra("usuario",datousu);
                        startActivity(carta);
                        finish();
                        break;
                    case 1:
                        Intent intent = new Intent(CRONOGRAMA.this, PERFIL.class);
                        intent.putExtra("usuario",datousu);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        Intent intent1 = new Intent(CRONOGRAMA.this, CONVENIOS.class);
                        intent1.putExtra("usuario",datousu);
                        startActivity(intent1);
                        finish();
                        break;
                    case 3:
                        Intent cerrar = new Intent(CRONOGRAMA.this, LOGIN.class);
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
        Spinner spinner = (Spinner) findViewById(R.id.cronoSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.Rutas,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //-------------------------------------------------------------------


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MapFragment fragment = new MapFragment();
        ft.add(android.R.id.content, fragment).commit();
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.position = position;
        selection = parent.getItemAtPosition(position).toString();
        Toast.makeText(this,"Selección actual: "+selection, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}