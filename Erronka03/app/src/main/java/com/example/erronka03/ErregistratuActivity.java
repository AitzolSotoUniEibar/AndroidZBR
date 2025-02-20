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

        etErabiltzailea = findViewById(R.id.etKode);
        etEmaila = findViewById(R.id.etKode);
        etPasahitza = findViewById(R.id.etPasahitza);
        etPasahitza2 = findViewById(R.id.etPasahitza2);
        dbHelper = new DatabaseHelper(this);
    }

    public void Erregistratu(View view){
        String emaila = etEmaila.getText().toString();
        if(!dbHelper.erabiltzaileaExistitzenDa(emaila)) {
            String erabiltzailea = etErabiltzailea.getText().toString();
            String pasahitza = etPasahitza.getText().toString();
            String pasahitza2 = etPasahitza2.getText().toString();
            if(!erabiltzailea.isEmpty() && !pasahitza.isEmpty() && !pasahitza2.isEmpty()){//Datuak dbn gorde
                dbHelper.erabiltzaileaGorde(emaila,erabiltzailea,pasahitza);
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