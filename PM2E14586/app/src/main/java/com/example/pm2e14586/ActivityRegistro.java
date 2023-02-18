package com.example.pm2e14586;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm2e14586.basededatos.database;


public class ActivityRegistro extends AppCompatActivity {

    EditText nombre, telefono, foto, nota;
    Spinner pais;
    Button btnGuardar, btnLista;

    String pendiente;
    String paisPendiente = "Honduras";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        nombre = (EditText) findViewById(R.id.txtNombre);
        telefono = (EditText) findViewById(R.id.txtTelefono);
        pais = (Spinner) findViewById(R.id.spinnerPais);
        nota = (EditText) findViewById(R.id.txtNota);
        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        btnLista = (Button) findViewById(R.id.btnLista);


        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityListaContactos.class);
                startActivity(intent);
            }
        });


        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(!nombre.getText().toString().equals("") && !telefono.getText().toString().equals("") && !pais.getSelectedItem().equals("seleccione")) {

                    database dbContactos = new database(ActivityRegistro.this);
                    long id = dbContactos.insertarContacto(nombre.getText().toString(), telefono.getText().toString(),
                            pais.getSelectedItem().toString(), nota.getText().toString(), pendiente);

                    if (id > 0) {
                        Toast.makeText(ActivityRegistro.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                        limpiar();
                    } else {
                        Toast.makeText(ActivityRegistro.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ActivityRegistro.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }


            }
  });

}

    private void limpiar() {
        nombre.setText("");
        telefono.setText("");
        nota.setText("");

    }
}