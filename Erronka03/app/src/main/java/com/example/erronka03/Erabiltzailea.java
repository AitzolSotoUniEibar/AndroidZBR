package com.example.erronka03;

public class Erabiltzailea {
    private int id;
    private String erabiltzailea;
    private String emaila;
    private String pasahitza;

    public Erabiltzailea(String erabiltzailea,String emaila ,String pasahitza){
        this.emaila = emaila;
        this.erabiltzailea = erabiltzailea;
        this.pasahitza = pasahitza;
    }

    public String getErabiltzailea(){
        return this.erabiltzailea;
    }

    public String getEmaila(){
        return this.emaila;
    }

    public String getPasahitza() {
        return pasahitza;
    }
}
