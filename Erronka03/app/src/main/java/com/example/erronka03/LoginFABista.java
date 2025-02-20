package com.example.erronka03;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginFABista extends AppCompatActivity {
    private String userEmail;
    private String erabiltzaileIzena;
    private String kodeZuzena;
    private EditText etKode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_fabista);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener los datos del Intent
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");
        erabiltzaileIzena = intent.getStringExtra("erabiltzailea");
        kodeZuzena = intent.getStringExtra("generatedCode");
    }

    public void KodeEgiaztatu(View view){
        etKode = findViewById(R.id.etKode);
        String kodeInput = etKode.getText().toString();
        Toast.makeText(this,kodeZuzena,Toast.LENGTH_SHORT).show();
        if(kodeInput.equals(kodeZuzena)){//Login egin
            SharedPreferences sharedPref = getSharedPreferences("NireDatuak",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.erabiltzailea), erabiltzaileIzena);
            editor.putString(getString(R.string.emaila), userEmail);
            editor.apply();

            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
    }
}