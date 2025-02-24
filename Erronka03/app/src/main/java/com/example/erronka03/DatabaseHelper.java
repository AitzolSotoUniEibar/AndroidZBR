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
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_ERABILTZAILEAK = "CREATE TABLE erabiltzaileak (id INTEGER PRIMARY KEY AUTOINCREMENT, emaila TEXT,erabiltzailea TEXT, pasahitza TEXT, rol TEXT)";
        String CREATE_TABLE_PRODUKTUAK = "CREATE TABLE produktuak(id INTEGER PRIMARY KEY AUTOINCREMENT, izena TEXT,deskripzioa TEXT,prezioa FLOAT,irudia TEXT)";
        db.execSQL(CREATE_TABLE_ERABILTZAILEAK);
        db.execSQL(CREATE_TABLE_PRODUKTUAK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS erabiltzaileak");
        db.execSQL("DROP TABLE IF EXISTS produktuak");
        onCreate(db);
    }

    public void konektatu(){
        SQLiteDatabase db1 = this.getWritableDatabase();

    }

    /*ERABILTZAILEAK*/
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

    public void erabiltzaileakEzabatu(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.delete("erabiltzaileak",null,null);
    }

    public void erabiltzaileaEguneratu(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.delete("erabiltzaileak",null,null);
    }

    public void erabiltzaileaEguneratu(int id, String emaila, String erabiltzailea, String pasahitza, String rol) {
        SQLiteDatabase db1 = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("emaila", emaila);
        values.put("erabiltzailea", erabiltzailea);
        values.put("pasahitza", pasahitza);
        values.put("rol", rol);

        int filasAfectadas = db1.update("erabiltzaileak", values, "id = ?", new String[]{String.valueOf(id)});
    }

    //PRODUKTUAK
    public void produktuakSortu(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        Cursor cursor = db1.rawQuery("SELECT * FROM produktuak", null);
        if(cursor.getCount() <= 0){//Ez badago produkturik produktuak sortu
            String insertQuery = "INSERT INTO produktuak (izena, deskripzioa, prezioa, irudia) VALUES " +
                    "('Galleta de Chocolate', 'Galleta crujiente con trozos de chocolate', 1.50, 'zabalaprod1.jpg'), " +
                    "('Galleta de Avena', 'Galleta saludable con avena y miel', 1.20, 'zabalaprod1.jpg'), " +
                    "('Galleta de Mantequilla', 'Galleta clásica con mantequilla de alta calidad', 1.00, 'zabalaprod1.jpg'), " +
                    "('Galleta de Almendra', 'Galleta artesanal con almendras tostadas', 1.80, 'zabalaprod1.jpg');";
            db1.execSQL(insertQuery);
        }
        db1.close();
    }

    public void produktuakEzabatu(){
        SQLiteDatabase db1 = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM produktuak";
        db1.execSQL(deleteQuery);
    }

    public List<Produktua> getAllProduktuak(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM produktuak",null);

        List<Produktua> produktuakList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String izena = cursor.getString(cursor.getColumnIndexOrThrow("izena"));
                float prezioa = cursor.getFloat(cursor.getColumnIndexOrThrow("prezioa"));
                String deskripzioa = cursor.getString(cursor.getColumnIndexOrThrow("deskripzioa"));
                String irudia = cursor.getString(cursor.getColumnIndexOrThrow("irudia"));
                Produktua produktua = new Produktua(id, izena, deskripzioa,prezioa,irudia);
                produktuakList.add(produktua);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return produktuakList;
    }


}

