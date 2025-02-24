package com.example.erronka03;

public class Produktua {
    private int id;
    private String izena;
    private String deskripzioa;
    private float prezioa;
    private String irudia;

    public Produktua(int id,String izena, String deskripzioa, float prezioa, String irudia){
        this.id = id;
        this.izena = izena;
        this.deskripzioa = deskripzioa;
        this.prezioa = prezioa;
        this.irudia = irudia;
    }

    public int getId() {
        return id;
    }

    public String getIzena() {
        return izena;
    }

    public String getDeskripzioa() {
        return deskripzioa;
    }

    public float getPrezioa() {
        return prezioa;
    }

    public String getIrudia() {
        return irudia;
    }
}
