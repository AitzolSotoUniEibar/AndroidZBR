package com.example.erronka03;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "zbr.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ERABILTZAILEAK = "CREATE TABLE erabiltzaileak (id INTEGER PRIMARY KEY AUTOINCREMENT, emaila TEXT,erabiltzailea TEXT, pasahitza TEXT)";
        String CREATE_TABLE_PRODUKTUAK = "CREATE TABLE produktuak(id INTEGER PRIMARY KEY AUTOINCREMENT, izena TEXT,deskripzioa TEXT,prezioa DOUBLE)";
        db.execSQL(CREATE_TABLE_ERABILTZAILEAK);
        db.execSQL(CREATE_TABLE_PRODUKTUAK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS erabiltzaileak");
        db.execSQL("DROP TABLE IF EXISTS produktuak");
        onCreate(db);
    }

    public void erabiltzaileaGorde(String emaila,String erabiltzailea, String pasahitza) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("emaila", emaila);
        values.put("erabiltzailea", erabiltzailea);
        values.put("pasahitza",pasahitza);
        db.insert("erabiltzaileak", null, values);
        db.close();
    }

    public Erabiltzailea saioaHasi(String erabiltzailea,String pasahitza){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM erabiltzaileak WHERE erabiltzailea=? AND pasahitza=?", new String[]{erabiltzailea,pasahitza});

        Erabiltzailea erabiltzaile = null;

        if (cursor.moveToFirst()) {
            int emailIndex = cursor.getColumnIndex("emaila");
            int erabiltzaileaIndex = cursor.getColumnIndex("erabiltzailea");
            String email = cursor.getString(emailIndex);
            String user = cursor.getString(erabiltzaileaIndex);
            erabiltzaile = new Erabiltzailea(user, email);
        }
        cursor.close();
        return erabiltzaile;
    }

    public boolean erabiltzaileaExistitzenDa(String emaila){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM erabiltzaileak WHERE emaila=?", new String[]{emaila});
        boolean existitzenDa = cursor.getCount() > 0;
        cursor.close();
        return existitzenDa;
    }


}

