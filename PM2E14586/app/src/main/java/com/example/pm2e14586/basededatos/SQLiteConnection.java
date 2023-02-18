package com.example.pm2e14586.basededatos;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import androidx.annotation.Nullable;

public class SQLiteConnection extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "ContactosDB";
    public static final String TABLE_CONTACTOS = "Contacto";

    public SQLiteConnection(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_CONTACTOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "telefono TEXT NOT NULL," +
                "pais TEXT NOT NULL," +
                "nota TEXT NOT NULL," +
                "img BLOB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONTACTOS);
        onCreate(sqLiteDatabase);
    }

}
