package com.andresvanegas.approdar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.andresvanegas.approdar.datos.BDHELPER;

public class PERFIL extends AppCompatActivity {
    String datousu;
    BDHELPER usuarios;
    SQLiteDatabase database;
    ContentValues dataBD;
    TextView usuario,documento,contacto;
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;
    private String[] opciones = new String[] { "Main", "cronograma","cerrar sesion"};
    ListView list;
    protected ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        usuario = (TextView) findViewById(R.id.epusuario);
        documento = (TextView)findViewById(R.id.epdocumento);
        contacto = (TextView)findViewById(R.id.epcontacto);

        Intent intent4 = getIntent();
        Bundle extras = intent4.getExtras();
        if(extras != null){
            datousu = (String)extras.get("usuario");
        }

        usuarios = new BDHELPER(this, "DATOS", null, 1);
        database = usuarios.getWritableDatabase();

        Cursor cursor = database.rawQuery("select * from usuarios where nombre='"+datousu+"'",null);
        if(cursor.moveToFirst() == true){
            usuario.setText(cursor.getString(1));
            documento.setText(cursor.getString(2));
            contacto.setText(cursor.getString(3));
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
                        Intent carta = new Intent(PERFIL.this, MainActivity.class);
                        carta.putExtra("usuario",datousu);
                        startActivity(carta);
                        finish();
                        break;
                    case 1:
                        Intent intent = new Intent(PERFIL.this, CRONOGRAMA.class);
                        intent.putExtra("usuario",datousu);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        Intent cerrar = new Intent(PERFIL.this, LOGIN.class);
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
