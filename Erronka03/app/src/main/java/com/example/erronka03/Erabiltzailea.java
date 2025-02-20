package com.example.erronka03;

public class Erabiltzailea {
    String erabiltzailea;
    String emaila;
    String pasahitza;

    public Erabiltzailea(String erabiltzailea,String emaila){
        this.emaila = emaila;
        this.erabiltzailea = erabiltzailea;
    }

    public String getErabiltzailea(){
        return this.erabiltzailea;
    }

    public String getEmaila(){
        return this.emaila;
    }
}
