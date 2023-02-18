package com.example.pm2e14586;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm2e14586.basededatos.database;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ActivityRegistro extends AppCompatActivity {

    static final  int REQUEST_IMAGE = 101;
    static final  int PETICION_ACCESS_CAM = 201;
    EditText nombre, telefono, foto, nota;
    Spinner pais;
    Button btnGuardar, btnLista, btnAgregarFoto;
    String pendiente;
    ImageView vistaFoto;
    String currentPhotoPath;




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
        btnAgregarFoto = (Button) findViewById(R.id.btnAgregarFoto);
        vistaFoto = (ImageView) findViewById(R.id.vistaFoto);

        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityListaContactos.class);
                startActivity(intent);
            }
        });

        btnAgregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(!nombre.getText().toString().equals("") && !telefono.getText().toString().equals("") && !pais.getSelectedItem().equals("Seleccione un país") && !nota.getText().equals("")) {

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


    private void permisos()
    {
        // Metodo para obtener los permisos requeridos de la aplicacion
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},PETICION_ACCESS_CAM);
        }
        else
        {
            dispatchTakePictureIntent();
            //TomarFoto();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICION_ACCESS_CAM)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                dispatchTakePictureIntent();
                //TomarFoto();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "se necesita el permiso de la camara",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE)
        {
            //Bundle extra = data.getExtras();
            //Bitmap imagen = (Bitmap) extra.get("data");
            //imageView.setImageBitmap(imagen);

            try {
                File foto = new File(currentPhotoPath);
                vistaFoto.setImageURI(Uri.fromFile(foto));
            }
            catch (Exception ex)
            {
                ex.toString();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.toString();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pm2e14586.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE);
            }
        }
    }


}