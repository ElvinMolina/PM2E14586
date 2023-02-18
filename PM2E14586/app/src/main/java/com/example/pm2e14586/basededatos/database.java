package com.example.pm2e14586.basededatos;

import static com.example.pm2e14586.basededatos.SQLiteConnection.TABLE_CONTACTOS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.pm2e14586.entidades.Contacto;

import java.util.ArrayList;

public class database extends SQLiteConnection{

    Context context;

    public database (@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String telefono, String pais, String nota, String img ) {

        long id = 0;

        try {
            SQLiteConnection dbHelper = new SQLiteConnection (context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("pais", pais);
            values.put("nota", nota);
            values.put("img", img);

            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception ex) {
            ex.toString();
        }

        return id;
    }

    public ArrayList<Contacto> mostrarContactos() {

        SQLiteConnection dbHelper = new SQLiteConnection(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contacto> listaContactos = new ArrayList<>();
        Contacto contacto;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " ORDER BY nombre ASC", null);

        if (cursorContactos.moveToFirst()) {
            do {
                contacto = new Contacto();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setPais(cursorContactos.getString(3));
                contacto.setNota(cursorContactos.getString(4));
                contacto.setImg(cursorContactos.getString(5));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public Contacto verContacto(int id) {

        SQLiteConnection dbHelper = new SQLiteConnection(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contacto contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new Contacto();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setTelefono(cursorContactos.getString(2));
            contacto.setPais(cursorContactos.getString(3));
            contacto.setNota(cursorContactos.getString(4));
            contacto.setImg(cursorContactos.getString(5));
        }

        cursorContactos.close();

        return contacto;
    }

    public boolean eliminarContacto(int id) {

        boolean correcto = false;

        SQLiteConnection dbHelper = new SQLiteConnection(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
        }



    public boolean editarContacto(int id, String nombre, String telefono, String nota) {

        boolean correcto = false;

        SQLiteConnection dbHelper = new SQLiteConnection(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET nombre = '" + nombre + "', telefono = '" + telefono + "', nota = '" + nota + "' WHERE id='" + id + "' ");
            correcto = true;
        } catch (Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }

        return correcto;
    }




}

