package com.example.pm2e14586;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;

import com.example.pm2e14586.basededatos.database;
import com.example.pm2e14586.entidades.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewActivity extends AppCompatActivity {

    private static final int REQUEST_CALL = 1;
    EditText txtNombre, txtTelefono, txtNota;
    Spinner pais;
    Button btnGuardar;
    FloatingActionButton contactoEditar, contactoEliminar, contactoCompartir, contactoLlamar;
    Contacto contacto;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtNota = findViewById(R.id.txtNota);
        txtNombre.setEnabled(false);
        txtTelefono.setEnabled(false);
        txtNota.setEnabled(false);
        contactoEditar = findViewById(R.id.contactoEditar);
        contactoEliminar = findViewById(R.id.contactoEliminar);
        btnGuardar = findViewById(R.id.btnGuardarCambio);
        btnGuardar.setVisibility(View.INVISIBLE);
        contactoCompartir = findViewById(R.id.contactoCompartir);
        contactoLlamar = findViewById(R.id.contactoLlamar);

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

        contactoCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "CONTACTO \n" + "Nombre: " + txtNombre.getText() + "\nNúmero teléfono: " + txtTelefono.getText() + "\nNotas: " + txtNota.getText());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);

            }
        });

        contactoLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewActivity.this);
                builder.setMessage("¿Desea llamar a "+txtNombre.getText().toString()+"?")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                makePhoneCall();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).show();

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
        Intent intent = new Intent(this, ActivityListaContactos.class);
        startActivity(intent);
    }


    private void makePhoneCall() {
        String number = txtTelefono.getText().toString();
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(this, "INGRESE UN NÚMERO TELEFÓNICO", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



}