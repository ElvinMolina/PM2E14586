package com.example.pm2e14586;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pm2e14586.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.example.pm2e14586.adaps.ListContacAdapter;
import com.example.pm2e14586.basededatos.database;
import com.example.pm2e14586.entidades.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ActivityListaContactos extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaContactos;
    ArrayList<Contacto> listaArrayContactos;
    FloatingActionButton contactoNuevo;
    ListContacAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contactos);
        txtBuscar = findViewById(R.id.txtBuscar);
        listaContactos = findViewById(R.id.listaContactos);
        contactoNuevo = findViewById(R.id.contactoNuevo);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        database dbContactos = new database(ActivityListaContactos.this);

        listaArrayContactos = new ArrayList<>();

        adapter = new ListContacAdapter(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        contactoNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nuevoRegistro();
            }
        });

        txtBuscar.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menuNuevo:
                nuevoRegistro();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void nuevoRegistro(){
        Intent intent = new Intent(this, ActivityRegistro.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        adapter.filtrado(s);
        return false;
    }


}