package com.example.erronka03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class ErregistratuActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText etErabiltzailea;
    private EditText etEmaila;
    private EditText etPasahitza;
    private EditText etPasahitza2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_erregistratu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etErabiltzailea = findViewById(R.id.etErabiltzailea);
        etEmaila = findViewById(R.id.etEmaila);
        etPasahitza = findViewById(R.id.etPasahitza);
        etPasahitza2 = findViewById(R.id.etPasahitza2);
        dbHelper = new DatabaseHelper(this);
    }

    public void Erregistratu(View view) throws Exception {
        String erabiltzaileIzena= etErabiltzailea.getText().toString();

        if(!dbHelper.erabiltzaileaExistitzenDa(erabiltzaileIzena)) {
            String erabiltzailea = etErabiltzailea.getText().toString();
            String pasahitza = etPasahitza.getText().toString();
            String pasahitza2 = etPasahitza2.getText().toString();

            //Datuen konprobazioa
            if(!(pasahitza.length() > 8)){
                Toast.makeText(this, "Pasahitzak 8 karaktere izan behar ditu", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!erabiltzailea.isEmpty() && !pasahitza.isEmpty() && !pasahitza2.isEmpty()){//Datuak dbn gorde
                pasahitza = SecurityUtils.hashPassword(pasahitza);//Pasahitza hasheatu
                dbHelper.erabiltzaileaGorde(erabiltzaileIzena,erabiltzailea,pasahitza);
                Toast.makeText(this, "Datuak gorde dira", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
            }else{
                Toast.makeText(this, "Datuak falta dira", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Erabiltzailea existitzen da", Toast.LENGTH_SHORT).show();
        }
    }
}