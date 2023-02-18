package com.example.pm2e14586;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pm2e14586.basededatos.database;
import com.example.pm2e14586.entidades.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtNota, txtPais;
    Button btnGuardar;
    FloatingActionButton contactoEditar, contactoEliminar;

    Contacto contacto;
    int id =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtNota = findViewById(R.id.txtNota);
        contactoEditar = findViewById(R.id.contactoEditar);
        contactoEliminar = findViewById(R.id.contactoEditar);
        btnGuardar = findViewById(R.id.btnGuardarCambio);
        btnGuardar.setVisibility(View.INVISIBLE);

        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final database dbContactos = new database(ViewActivity.this);
        contacto = dbContactos.verContacto(id);

        if(contacto != null){
            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtNota.setText(contacto.getNota());
        }

        contactoEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewActivity.this, EditarActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        contactoEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
                builder.setMessage("¿Desea eliminar este contacto?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                if(dbContactos.eliminarContacto(id)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();
            }
        });
    }

    private void lista(){
        Intent intent = new Intent(this, ActivityRegistro.class);
        startActivity(intent);
    }






}