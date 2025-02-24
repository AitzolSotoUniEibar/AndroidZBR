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

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;


public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText etErabiltzailea;
    private EditText etPasahitza;
    private String generatedCode; // Código de verificación generado
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        etErabiltzailea = findViewById(R.id.etErabiltzailea);
        etPasahitza = findViewById(R.id.etPasahitza);

        LoginKonprobatu();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.produktuakSortu();
        //databaseHelper.produktuakEzabatu();
        databaseHelper.konektatu();

    }


    //Erabiltzailea logeatuta dagoen konprobatzeko
    private void LoginKonprobatu(){
        SharedPreferences sharedPref = getSharedPreferences("NireDatuak",MODE_PRIVATE);
        String erabiltzaile = sharedPref.getString(getString(R.string.erabiltzailea),"");
        String emaila = sharedPref.getString(getString(R.string.emaila),"");


        if(!erabiltzaile.isEmpty() && !emaila.isEmpty()){//Logeatuta dago
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void SaioaHasi(View view){
        String erabiltzaileaInput = etErabiltzailea.getText().toString();
        String pasahitza = etPasahitza.getText().toString();
        if(!erabiltzaileaInput.isEmpty() && !pasahitza.isEmpty()){
            Erabiltzailea erabiltzailea1 = dbHelper.saioaHasi(erabiltzaileaInput,pasahitza);
            if(erabiltzailea1 != null){
                String userEmaila = erabiltzailea1.getEmaila();
                generatedCode = generateVerificationCode();
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    MailSender mailSender = new MailSender();
                    try {
                        mailSender.sendMail(userEmaila, "Código de verificación", "Tu código es: " + generatedCode);


                        runOnUiThread(() -> {
                            Toast.makeText(LoginActivity.this, "Código enviado con éxito", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, LoginFABista.class);
                            intent.putExtra("userEmail", erabiltzailea1.getEmaila());
                            intent.putExtra("erabiltzailea",erabiltzaileaInput);
                            intent.putExtra("generatedCode", generatedCode);
                            startActivity(intent);
                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Error al enviar el código", Toast.LENGTH_LONG).show());
                    }
                });
            }else{
                Toast.makeText(this,"Login okerra",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Datuak falta dira",Toast.LENGTH_SHORT).show();
        }
    }

    public void ErregistratuBista(View view){
        Intent i = new Intent(this, ErregistratuActivity.class);
        startActivity(i);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // Código de 6 dígitos
        return String.valueOf(code);
    }




}