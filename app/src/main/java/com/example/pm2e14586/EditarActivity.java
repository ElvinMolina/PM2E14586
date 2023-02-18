package com.example.pm2e14586;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pm2e14586.basededatos.database;
import com.example.pm2e14586.entidades.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarActivity extends AppCompatActivity {

    EditText txtNombre, txtTelefono, txtNota;
    Button btnGuardarCambio;
    FloatingActionButton contactoEditar, contactoEliminar, contactoCompartir, contactoLlamar;
    boolean correcto = false;
    Spinner pais;
    Contacto contacto;
    int id = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtNota = findViewById(R.id.txtNota);

        contactoEditar = findViewById(R.id.contactoEditar);
        contactoEliminar = findViewById(R.id.contactoEliminar);
        contactoCompartir = findViewById(R.id.contactoCompartir);
        contactoLlamar = findViewById(R.id.contactoLlamar);

        contactoEditar.setVisibility(View.INVISIBLE);
        contactoEliminar.setVisibility(View.INVISIBLE);
        contactoCompartir.setVisibility(View.INVISIBLE);
        contactoLlamar.setVisibility(View.INVISIBLE);



        btnGuardarCambio = findViewById(R.id.btnGuardarCambio);
        pais = findViewById(R.id.spinnerPais);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        final database dbContactos = new database(EditarActivity.this);
        contacto = dbContactos.verContacto(id);

        if (contacto != null) {

            txtNombre.setText(contacto.getNombre());
            txtTelefono.setText(contacto.getTelefono());
            txtNota.setText(contacto.getNota());

        }

        btnGuardarCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtNombre.getText().toString().equals("") && !txtTelefono.getText().toString().equals("")) {
                    correcto = dbContactos.editarContacto(id, txtNombre.getText().toString(), txtTelefono.getText().toString(),pais.getSelectedItem().toString(), txtNota.getText().toString());

                    if(correcto){
                        Toast.makeText(EditarActivity.this, "REGISTRO MODIFICADO", Toast.LENGTH_LONG).show();
                        verRegistro();
                    } else {
                        Toast.makeText(EditarActivity.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private void verRegistro(){
        Intent intent = new Intent(this, ActivityListaContactos.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }




}
